INSERT INTO public.addresses (
    address_id,
    contact_name,
    contact_phone,
    province,
    district,
    commune,
    street,
    is_default,
    user_id,
    created_at,
    updated_at,
    is_deleted
)
VALUES (
    '2353499a-7469-48ee-806d-10d5676fdc2f',  -- Tự động tạo address_id 
    'Dino',             -- Tên người liên hệ
    '0986123456',       -- Số điện thoại 10 số
    'TPHCM',            -- Tỉnh/Thành
    'Thủ Đức',          -- Quận/Huyện
    'Linh Xuân',        -- Phường/Xã
    'KTX Khu B - ĐHQG', -- Địa chỉ chi tiết
    TRUE,               -- is_default
    'cecf1aa7-a4cc-46d8-9377-70b491afd5ec', -- user_id
    NOW(),              -- created_at
    NOW(),              -- updated_at
    FALSE               -- is_deleted
);
