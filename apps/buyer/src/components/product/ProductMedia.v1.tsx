'use client';
import { RESOURCES } from '@/lib/constants';
import { getFileUrl } from '@/lib/files';
import { TProductMedia } from '@/types/product.types';
import Image from 'next/image';

type TProductMediaProps = {
    selectedImage: string | null;
    setSelectedImage: (img: string) => void;
    product: TProductMedia;
};

export const ProductMedia = ({ selectedImage, setSelectedImage, product }: TProductMediaProps) => {
    const images = [
        ...(product.thumb ? [product.thumb] : []),
        ...(product.video ? [product.video] : []),
        ...(product.photos ?? []),
        ...(product.sizeGuidePhoto ? [product.sizeGuidePhoto] : [])
    ];

    return (
        <div className="flex gap-4">
            {/* Side photos */}
            <div className="flex flex-col gap-2 max-h-full overflow-y-auto scrollbar-hidden">
                {/* EXP:
                aspect-* h-full: tạo ra một ratio tính toán theo height
                height lại kế thừa từ parent
                parent height: có dãn, tính theo cột phải
                parent width: tính toán một cách tự nhiên, bằng 64px
                 */}
                <div className="h-full aspect-square flex flex-col gap-2">
                    {images.map((image) => (
                        <Image
                            key={image}
                            src={getFileUrl(image, RESOURCES.PRODUCTS.BASE, product.id)}
                            alt={`Photo ${image}`}
                            onMouseEnter={() => setSelectedImage(image)}
                            width={64}
                            height={64}
                            className={`w-16 h-16 object-cover rounded cursor-pointer border-2 ${selectedImage === image ? 'border-blue-500' : 'border-transparent'}`}
                        />
                    ))}
                </div>
            </div>

            {/* Main photos */}
            <div className="flex-1 w-full aspect-square bg-gray-100 rounded-lg flex items-center justify-center relative">
                {/* EXP:
                width của cột phải: flex-1 w-full nên lấy toàn bộ width còn lại
                height của cột phải: aspect-square w-full tính toán chiều cao theo chiều rộng
                */}
                <Image
                    src={getFileUrl(selectedImage || images[0], RESOURCES.PRODUCTS.BASE, product.id)}
                    alt={`Thumbnail ${selectedImage || images[0]}`}
                    fill
                    className="object-contain rounded"
                    sizes="(min-width: 1024px) 500px, 100vw"
                    unoptimized
                    priority
                />
            </div>
        </div>
    );
};