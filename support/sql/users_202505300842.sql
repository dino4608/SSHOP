-- public.users definition

-- Drop table

-- DROP TABLE public.users;

CREATE TABLE public.users (
	user_id varchar(255) NOT NULL,
	created_at timestamptz(6) NULL,
	updated_at timestamptz(6) NULL,
	dob timestamp(6) NULL,
	email varchar(255) NULL,
	gender varchar(255) NULL,
	"name" varchar(255) NULL,
	"password" varchar(255) NULL,
	phone varchar(255) NULL,
	photo varchar(255) NULL,
	roles _varchar NOT NULL,
	status varchar(255) NULL,
	username varchar(255) NULL,
	is_email_verified bool NULL,
	is_phone_verified bool NULL,
	is_deleted bool NULL,
	CONSTRAINT ukr43af9ap4edm43mmtq01oddj6 UNIQUE (username),
	CONSTRAINT users_pkey PRIMARY KEY (user_id)
);

INSERT INTO public.users (user_id,created_at,updated_at,dob,email,gender,"name","password",phone,photo,roles,status,username,is_email_verified,is_phone_verified,is_deleted) VALUES
	 ('c261f9b6-16a5-4354-9759-6f31fae0cb9e','2025-04-25 17:06:52.237123+07','2025-04-25 17:06:52.237123+07',NULL,'dino2@gmail.com',NULL,NULL,'$2a$10$Z4XGNP6q2nDIRRMj/yyZDOf5D//7Ga//NFqJvzE4lut5IdS6Jblie',NULL,NULL,'{BUYER}','LACK_INFO','user1745575612235',false,NULL,false),
	 ('0ae9c7c9-8721-4999-9812-ca8c9c163936','2025-04-25 17:06:57.633522+07','2025-04-25 17:06:57.633522+07',NULL,'dino3@gmail.com',NULL,NULL,'$2a$10$O8/DNNjhiGDdRcvVvW3Tx.51FVSeGuqv2tz0AlrMYi91m0fHxgXBW',NULL,NULL,'{BUYER}','LACK_INFO','user1745575617631',false,NULL,false),
	 ('6e2132dd-6c77-48f1-9998-707fbe1af39a','2025-04-25 17:07:02.380945+07','2025-04-25 17:07:02.380945+07',NULL,'dino4@gmail.com',NULL,NULL,'$2a$10$MFaN7gfalJRwanF5DC3mGuJOWI9wfgFMMbwRKyzPMV8mJOJvg3aMe',NULL,NULL,'{BUYER}','LACK_INFO','user1745575622379',false,NULL,false),
	 ('649f1dbb-0453-4bd5-82a6-8520adb0543d','2025-04-26 16:28:07.413531+07','2025-04-26 16:28:07.413531+07',NULL,'tn9th8@gmail.com',NULL,'Trung Nhân Nguyễn',NULL,NULL,NULL,'{BUYER}','LACK_INFO','user1745659687379',false,NULL,false),
	 ('4c30c4f1-e7b1-47eb-b072-cc0233f28968','2025-04-29 22:02:56.61698+07','2025-04-29 22:02:56.61698+07',NULL,'dino5@gmail.com',NULL,NULL,'$2a$10$8gCEWvPD8NpfBcHBngN6JOdny.04qoqKFvxvkyz4Wp7ao4KB9H52q',NULL,NULL,'{BUYER}','LACK_INFO','user1745938976582',false,NULL,false),
	 ('e7fef810-939e-4e4e-b8fc-50bfaf8a329e','2025-04-30 15:40:04.53868+07','2025-04-30 15:40:04.53868+07',NULL,'dino6@gmail.com',NULL,NULL,'$2a$10$XhyvuL5AlLCgFiHUhR6qseRrmAO7EamC7o.uhu5omgI6hetW2vV/i',NULL,NULL,'{BUYER}','LACK_INFO','user1746002404522',false,NULL,false),
	 ('677d9cf8-da3a-407d-8179-20fb41192ebf','2025-04-30 22:04:18.203321+07','2025-04-30 22:04:18.203321+07',NULL,'nhannguyen291219@gmail.com',NULL,'Trung Nhân 29',NULL,NULL,NULL,'{BUYER}','LACK_INFO','user1746025458175',false,NULL,false),
	 ('76032358-48c1-43f0-a5af-68bd80974025','2025-05-10 16:21:39.34288+07','2025-05-10 16:21:39.34288+07',NULL,'coolmate@gmail.com',NULL,'Coolmate','$2a$10$sIqyoRK1uja72AGpKDNX4evV8j5yMbBJHSJTMU1S11YHROVYhLpFu',NULL,'coolmate.webp','{BUYER,SELLER}','LACK_INFO','coolmate',false,NULL,false),
	 ('cecf1aa7-a4cc-46d8-9377-70b491afd5ec','2025-04-25 17:06:33.487959+07','2025-04-25 17:06:33.487959+07',NULL,'dino1@gmail.com',NULL,'Dino 1','$2a$10$LwnbtVc42660pZWjqsBjDu2bDYCrE6UGAlW.dSSIEI6q3ViGQAPxm',NULL,NULL,'{BUYER}','LACK_INFO','user1745575593477',false,NULL,false);
