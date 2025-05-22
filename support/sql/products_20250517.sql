insert into products  (
    product_id , status , retail_price , category_id , shop_id ,
    name , slug , 
    thumb, photos , video , size_chart ,
    tier_variations ,
    specifications , 
    description ,
    created_at , updated_at , is_deleted
)
values (
    '6e124bde-3346-4cc8-a66c-b7cdbd4d498d', 'LIVE', 189000, '9f1a8dbe-b7e1-489a-92fc-001', '76032358-48c1-43f0-a5af-68bd80974025',
    '[MUA 2 GIẢM 40%] Áo Singlet thể thao chạy bộ Graphic Photic Coolmate', 'mua-2-giam-40-ao-singlet-the-thao-chay-bo-graphic-photic-coolmate',
    'coolmate-001-001.webp', '{"coolmate-001-002.webp", "coolmate-001-003.webp", "coolmate-001-004.webp", "coolmate-001-005.webp", "coolmate-001-006.webp", "coolmate-001-007.webp"}', null, 'coolmate-001-size.webp',
    '[
		{ "name": "Màu sắc", "options": ["Xanh", "Cam"], "photos": ["coolmate-001-xanh.webp", "coolmate-001-cam.webp"] },
		{ "name": "Size", "options": ["S", "M", "L", "XL", "2XL", "3XL"] }
	
	]'::jsonb,
    '[
        { "name": "From áo", "value": "Regular fit" },
        { "name": "Phong cách", "value": "Gym, Thể thao" },
        { "name": "Chất liệu", "value": "Polyester co dãn" },
		{ "name": "Tính năng", "value": "Thoáng khí" },
        { "name": "Loại áo", "value": "Tank top, Áo trơn" },
		{ "name": "Cổ áo", "value": "Cổ tròn" },
		{ "name": "Tay áo", "value": "Rộng vai, Khoét lưng" },
		{ "name": "Mùa", "value": "Bốn mùa" },
		{ "name": "Hướng dẫn giặt rữa", "value": "Có thể giặt máy" }
    ]'::jsonb,
    '[MUA 2 GIẢM 40%] Áo Singlet thể thao chạy bộ Graphic Photic Coolmate',
    NOW(), NOW(), false
);