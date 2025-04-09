'use client';

import { Category } from "@/types/product-domain.type";
import React from "react";
import NavigationLinkItem from "./NavigationLinkItem";

const categories: Category[] = [
    // { id: 1, title: "Tất cả", slug: "tat-ca", image: "/images/categories/all.jpg" },
    { id: 2, title: "Thời trang nam", slug: "thoi-trang-nam", image: "/images/categories/mens-clothing.jpg" },
    { id: 3, title: "Phụ kiện", slug: "phu-kien", image: "/images/categories/accessories.jpg" },
    { id: 4, title: "Giày dép", slug: "giay-dep", image: "/images/categories/shoes.jpg" },
    { id: 5, title: "Nhà bếp", slug: "nha-bep", image: "/images/categories/kitchen.jpg" },
    { id: 6, title: "Thời trang nữ", slug: "thoi-trang-nu", image: "/images/categories/womans-clothing.jpg" },
    { id: 7, title: "Điện tử", slug: "dien-tu", image: "/images/categories/electronics.jpg" },
    { id: 8, title: "Em bé", slug: "em-be", image: "/images/categories/baby.jpg" },
    { id: 9, title: "Mỹ phẩm", slug: "my-pham", image: "/images/categories/cosmetics.jpg" },
    { id: 10, title: "Đồ chơi", slug: "do-choi", image: "/images/categories/toys.jpg" },
    { id: 11, title: "Thể thao", slug: "the-thao", image: "/images/categories/sports.jpg" },
    { id: 12, title: "Sức khỏe", slug: "suc-khoe", image: "/images/categories/health.jpg" },
    { id: 13, title: "Sách", slug: "sach", image: "/images/categories/books.jpg" },
    { id: 14, title: "Thú cưng", slug: "thu-cung", image: "/images/categories/pet-supplies.jpg" },
    { id: 15, title: "Dụng cụ", slug: "dung-cu", image: "/images/categories/tools.jpg" },
    { id: 16, title: "Nhà cửa", slug: "nha-cua", image: "/images/categories/home.jpg" },
    { id: 17, title: "Làm vườn", slug: "lam-vuon", image: "/images/categories/garden.jpg" },
    { id: 18, title: "Tạp hóa", slug: "tap-hoa", image: "/images/categories/grocery.jpg" },
    { id: 19, title: "Trang sức", slug: "trang-suc", image: "/images/categories/jewelry.jpg" },
    { id: 20, title: "Đồng hồ", slug: "dong-ho", image: "/images/categories/watches.jpg" },
    { id: 21, title: "Nhạc cụ", slug: "nhac-cu", image: "/images/categories/musical-instruments.jpg" },
    { id: 22, title: "Văn phòng phẩm", slug: "van-phong-pham", image: "/images/categories/office-supplies.jpg" },
];

const CategoryDropdown: React.FC = () => {
    return (
        <ul className="w-[400px] md:w-[500px] lg:w-[900px] grid grid-cols-6 gap-2 p-4">
            {categories.map((item) => (
                <NavigationLinkItem
                    key={item.id}
                    href={`/category/${item.slug}`}
                    longStyle={true}>
                    {item.title}
                </NavigationLinkItem>
            ))}
        </ul>
    );
}

export default CategoryDropdown;