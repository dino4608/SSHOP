import { Dot, MapPin, ShieldCheck, Truck } from "lucide-react";

const ProductDelivery = () => {
    return (
        <div className="p-4 border border-gray-200 rounded-lg bg-white flex flex-col gap-4">
            {/* header */}
            <div className="text-lg font-semibold">
                Delivery
            </div>

            {/* Body content with dividers */}
            <div className="flex flex-col divide-y divide-gray-100 text-sm">
                {/* Shipping */}
                <div className="flex items-center gap-2 pb-2">
                    <Truck className="w-5 h-5" />
                    Shipping:
                    <span className="inline-flex text-gray-400 line-through">₫17.000</span>
                    <span className="inline-flex px-2 bg-teal-100 rounded-sm text-teal-500 font-medium">Free shipping</span>
                    <span className="text-teal-500">
                        Get by {new Date().getDate() + 3} - {new Date().getDate() + 5}{' '}
                        {new Date().toLocaleString('default', { month: 'long' })}
                    </span>
                </div>

                {/* Address */}
                <div className="flex items-center gap-2 py-2">
                    <MapPin className="w-5 h-5" />
                    To: Linh Xuân, Thủ Đức, Hồ Chí Minh
                </div>

                {/* <div className="grid grid-rows-3 gap-1 py-2">
                    <div className="row-start-1 flex items-center gap-2">
                        <Package className="w-5 h-5" />
                        From: Tân Bình, Hồ Chí Minh
                    </div>
                    <div className="row-start-2 flex items-center text-gray-400">
                        <ChevronsDown className="w-5 h-5" />
                    </div>
                    <div className="row-start-3 flex items-center gap-2">
                        <MapPin className="w-5 h-5" />
                        To: Linh Xuân, Thủ Đức, Hồ Chí Minh
                    </div>
                </div> */}

                {/* Guarantee */}
                <div className="flex items-center gap-2 pt-2">
                    <ShieldCheck className="w-5 h-5" />
                    <span className="flex items-center">
                        Cash on delivery
                        <Dot className="w-5 h-5" />
                        Free returns in 15 days
                        <Dot className="w-5 h-5" />
                        Easy cancellation
                    </span>
                </div>
            </div>
        </div>

    );
};

export default ProductDelivery;