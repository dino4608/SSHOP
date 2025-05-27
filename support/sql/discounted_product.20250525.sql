INSERT INTO public.discounted_products (
	discounted_product_id, created_at, is_deleted, updated_at, 
	discount_percent, deal_price, max_deal_price, min_deal_price, price_type, total_limit, buyer_limit, 
	discount_id, product_id)
VALUES(
	'c3e51226-282d-474b-b783-9f4b2d7a7225', NOW(), false, NOW(), 
	20, null, 160000, 160000, 'PRODUCT', null, null, 
	'9cc47a08-1868-4bed-9238-6cf350b564ef', '6e124bde-3346-4cc8-a66c-b7cdbd4d498d');