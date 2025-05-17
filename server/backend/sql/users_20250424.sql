-- USERS with created_at, updated_at
INSERT INTO users (
  user_id, username, email, roles, status, name, phone, dob, gender, password, photo, deleted,
  created_at, updated_at
)
VALUES 
  ('f1e8e7b2-0001-4c61-84b9-000000000001', 'dino1', null, ARRAY['BUYER'], 'LIVE', 'Dino One', '0123456789', '1995-01-01', 'MALE', 'hashed_password', null, false, NOW(), NOW()),
  ('f1e8e7b2-0002-4c61-84b9-000000000002', 'dino2', null, ARRAY['BUYER'], 'LIVE', 'Dino Two', '0123456790', '1996-02-02', 'FEMALE', 'hashed_password', null, false, NOW(), NOW()),
  ('f1e8e7b2-0003-4c61-84b9-000000000003', null, 'dino3@gmail.com', ARRAY['BUYER'], 'LIVE', 'Dino Three', '0123456791', '1997-03-03', 'MALE', 'hashed_password', null, false, NOW(), NOW()),
  ('f1e8e7b2-0004-4c61-84b9-000000000004', null, 'dino4@gmail.com', ARRAY['BUYER'], 'LIVE', 'Dino Four', '0123456792', '1998-04-04', 'FEMALE', 'hashed_password', null, false, NOW(), NOW());
