-- TOKENS with created_at, updated_at
INSERT INTO tokens (
  user_id, refresh_token, refresh_exp_date, deleted,
  created_at, updated_at
)
VALUES
  ('f1e8e7b2-0001-4c61-84b9-000000000001', 'token_dino1', NOW() + INTERVAL '30 days', false, NOW(), NOW()),
  ('f1e8e7b2-0002-4c61-84b9-000000000002', 'token_dino2', NOW() + INTERVAL '30 days', false, NOW(), NOW()),
  ('f1e8e7b2-0003-4c61-84b9-000000000003', 'token_dino3', NOW() + INTERVAL '30 days', false, NOW(), NOW()),
  ('f1e8e7b2-0004-4c61-84b9-000000000004', 'token_dino4', NOW() + INTERVAL '30 days', false, NOW(), NOW());
