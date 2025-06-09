// src/components/checkout/CheckoutHydrator.tsx
"use client";
import { api } from '@/lib/api';
import { clientFetch } from '@/lib/fetch/fetch.client';
import { TStartCheckout } from '@/types/checkout.types';
import { useRouter } from 'next/navigation';
import { useCallback, useEffect, useState } from 'react';
import { toast } from 'sonner';
import { CheckoutSummary } from './CheckoutSummary';
import { DefaultAddress } from './DefaultAddress';
import { OrderList } from './OrderCard';
import { PaymentMethodSection } from './PaymentMethod';
import { PlatformVoucher } from './PlatformVoucher';

export function CheckoutHydrator() {
    const router = useRouter();
    const [startCheckoutData, setStartCheckoutData] = useState<TStartCheckout | null>(null);
    const [loadingCheckout, setLoadingCheckout] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);
    const [selectedPaymentMethod, setSelectedPaymentMethod] = useState<string>('');

    // Fetch start checkout data khi component mount
    useEffect(() => {
        async function fetchStartCheckout() {
            setLoadingCheckout(true);
            setError(null);
            const storedCartItemIds = localStorage.getItem('checkoutCartItemIds');
            if (!storedCartItemIds) {
                toast.error("Không tìm thấy sản phẩm đã chọn để thanh toán.");
                router.push('/cart'); // Quay lại giỏ hàng nếu không có ID
                return;
            }
            const cartItemIds = JSON.parse(storedCartItemIds);
            if (cartItemIds.length === 0) {
                toast.error("Vui lòng chọn ít nhất một sản phẩm để thanh toán.");
                router.push('/cart');
                return;
            }

            const result = await clientFetch(api.checkout.startCheckout({ cartItemIds }));
            if (result.success && result.data) {
                setStartCheckoutData(result.data);
                localStorage.removeItem('checkoutCartItemIds'); // Xóa sau khi đã sử dụng
            } else {
                toast.error(result.error || "Có lỗi xảy ra khi bắt đầu thanh toán.");
                setError(result.error || "Failed to start checkout.");
                router.push('/cart'); // Quay lại giỏ hàng nếu có lỗi API
            }
            setLoadingCheckout(false);
        }
        fetchStartCheckout();
    }, [router]);

    const handleConfirmOrder = useCallback(async () => {
        if (!startCheckoutData) {
            toast.error("Dữ liệu đơn hàng chưa sẵn sàng.");
            return;
        }

        if (!selectedPaymentMethod) {
            toast.error("Vui lòng chọn phương thức thanh toán.");
            return;
        }

        // Logic để gọi API confirmCheckout
        setLoadingCheckout(true);
        const orderIdsToConfirm = startCheckoutData.orders.map(order => order.id);
        const result = await clientFetch(api.checkout.confirmCheckout({ orderIds: orderIdsToConfirm }));
        if (result.success && result.data?.isConfirmed) {
            toast.success(`Đã xác nhận ${result.data.count} đơn hàng thành công!`);
            router.push('/cart'); // Quay lại giỏ hàng nếu có lỗi API
            // router.push('/orders/success'); // Chuyển hướng đến trang thành công
        } else {
            toast.error(result.error || "Xác nhận đơn hàng thất bại. Vui lòng thử lại.");
        }
        setLoadingCheckout(false);

    }, [startCheckoutData, selectedPaymentMethod, router]);

    if (loadingCheckout) {
        return (
            <div className="container mx-auto p-4 flex justify-center items-center min-h-[calc(100vh-150px)]">
                <div className="text-center text-lg text-gray-600">Đang hoàn thành thanh toán...</div>
            </div>
        );
    }

    if (error) {
        return (
            <div className="container mx-auto p-4 flex justify-center items-center min-h-[calc(100vh-150px)]">
                <div className="text-center text-lg text-red-600">
                    Có lỗi xảy ra: {error}
                </div>
            </div>
        );
    }

    if (!startCheckoutData) {
        return (
            <div className="container mx-auto p-4 flex justify-center items-center min-h-[calc(100vh-150px)]">
                <div className="text-center text-lg text-gray-600">Không có dữ liệu thanh toán. Vui lòng thử lại từ giỏ hàng.</div>
            </div>
        );
    }

    const totalFinalItems = startCheckoutData.orders.reduce((sum, order) => sum + order.orderItems.length, 0);

    return (
        <div className="flex flex-col lg:flex-row gap-4">
            <div className="flex-1 lg:w-2/3">
                {/* Address, Order List, Checkout Summary, Payment Method */}
                <div className="flex flex-col gap-4">
                    <div className="space-y-4">
                        <DefaultAddress />
                        <OrderList orders={startCheckoutData.orders} />
                        <PaymentMethodSection
                            selectedPaymentMethod={selectedPaymentMethod}
                            onPaymentMethodChange={setSelectedPaymentMethod}
                        />
                    </div>
                </div>
            </div>

            <div className="lg:w-1/3 flex flex-col gap-4 lg:sticky lg:top-4 lg:self-start">
                <PlatformVoucher />
                <CheckoutSummary
                    totalCheckoutSnapshot={startCheckoutData.totalCheckoutSnapshot}
                    handleConfirmOrder={handleConfirmOrder}
                    loadingCheckout={loadingCheckout}
                    selectedPaymentMethod={selectedPaymentMethod}
                />
            </div>
        </div>

    );
}