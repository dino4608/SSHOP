import { Dot } from "lucide-react";

const ProductDescription = () => {

    return (
        <div className="p-4 rounded-lg border border-gray-200 space-y-4">
            {/* Header */}
            <div className="text-lg font-semibold">
                Product description
            </div>

            {/* Specifications area */}
            <div className="space-y-4 text-sm">
                <div className="text-gray-500 font-medium">
                    Specifications
                </div>
                <div className="leading-6">
                    {[1, 2, 3, 4, 5].map((i) => (
                        <div
                            key={i}
                            className="flex"
                        >
                            <div className="w-1/3 flex">
                                <Dot />Brand
                            </div>
                            <div className="w-2/3">
                                SAMSUNG
                            </div>
                        </div>
                    ))}
                </div>
            </div>

            {/* Description area */}
            <div className="space-y-4 text-sm">
                <div className="text-gray-500 font-medium">
                    Description
                </div>
                <div className="">
                    <p className="leading-6">
                        Galaxy S25 Ultra - Cánh tay đắc lực AI <br />
                        - Now Brief: Đưa ra các đề xuất được cá nhân hoá dựa trên phân tích thông minh về lịch trình riêng, sở thích và mục tiêu cá nhân. <br />
                        - Seamless Actions across Apps: Ra câu lệnh bằng lời nói để xử lý tác vụ liên ứng dụng <br />
                        - Camera góc siêu rộng 50MP: Nâng tầm nhiếp ảnh di động với Camera nâng cấp 50MP, giúp bắt trọn phong cảnh ở góc rộng chi tiết hơn 4x lần và sáng hơn 34% (so với S24 Ultra) <br />
                        - Video camera đêm: Hệ thống khử nhiễu thông minh, giúp video thật sắc nét, chấp mọi điều kiện ánh sáng <br />
                        - Vi xử lý thế hệ mới: Thế hệ Galaxy S với hiệu năng mạnh mẽ nhất cùng vi xử lý 3nm, lần đầu tiên có trên Galaxy - Snapdragon 8 Elite for Galaxy <br />
                        - Hệ điều hành: Android 15, One UI 7 <br />
                        - Dung lượng RAM: 12GB <br />
                        - Dung lượng bộ nhớ: 256GB, 512GB, 1TB <br />
                    </p>
                </div>
            </div>
        </div>
    );
};

export default ProductDescription;
