// src/components/checkout/PaymentMethodSection.tsx
"use client";
import { Label } from "@/components/ui/label";
import { RadioGroup, RadioGroupItem } from "@/components/ui/radio-group";
import { cn } from "@/lib/utils";
import { TPaymentMethod } from "@/types/order.types";
import { ChevronRight } from "lucide-react";

interface PaymentMethodSectionProps {
    selectedPaymentMethod: string;
    onPaymentMethodChange: (method: string) => void;
}

export function PaymentMethodSection({ selectedPaymentMethod, onPaymentMethodChange }: PaymentMethodSectionProps) {
    const paymentMethods: { value: TPaymentMethod, label: string, enabled: boolean }[] = [
        { value: "COD", label: "Thanh toán khi giao", enabled: true },
        { value: "MOMO", label: "Ví điện tử MOMO", enabled: true },
        { value: "ZALO_PAY", label: "Ví điện tử ZALOPAY", enabled: false },
        { value: "VN_PAY", label: "Ví điện tử VNPAY", enabled: false },
        { value: "GOOGLE_PAY", label: "Thanh toán quốc tế GOOGLE PAY", enabled: false },
    ];

    return (
        <section className="bg-white p-4 border border-gray-200">
            <h2 className="text-base font-medium mb-3">Phương thức thanh toán</h2>
            <RadioGroup
                value={selectedPaymentMethod}
                onValueChange={onPaymentMethodChange}
                className="space-y-2"
            >
                {paymentMethods.map(method => (
                    <div key={method.value} className="flex items-center justify-between">
                        <div className="flex items-center">
                            <Label htmlFor={`pm-${method.value}`} className="ml-4 cursor-pointer text-gray-800 font-normal">
                                {method.label}
                            </Label>
                        </div>

                        {!method.enabled ? (
                            <span className="h-6 flex items-center text-sm text-rose-500 cursor-pointer group hover:underline hover:underline-offset-2">
                                Liên kết <ChevronRight className="w-4 h-4 ml-1 text-gray-600" />
                            </span>
                        ) : (
                            <RadioGroupItem
                                id={`pm-${method.value}`}
                                value={method.value}
                                disabled={!method.enabled}
                                className={cn(
                                    "size-6",
                                    "data-[state=checked]:bg-rose-500",
                                    "data-[state=checked]:border-rose-500",
                                    "data-[state=checked]:ring-offset-rose-500"
                                )}
                            />
                        )}


                    </div>
                ))}
            </RadioGroup>
        </section>
    );
}