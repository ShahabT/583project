DROP DATABASE IF EXISTS 583a;
CREATE DATABASE 583a;
use 583a;
CREATE TABLE business (
	_unnamed INTEGER NOT NULL, 
	attributes VARCHAR(1275) NOT NULL, 
	business_id VARCHAR(22) NOT NULL, 
	categories VARCHAR(177) NOT NULL, 
	city VARCHAR(43), 
	full_address VARCHAR(118) NOT NULL, 
	hours VARCHAR(372) NOT NULL, 
	latitude FLOAT NOT NULL, 
	longitude FLOAT NOT NULL, 
	name VARCHAR(64) NOT NULL, 
	neighborhoods VARCHAR(65) NOT NULL, 
	open BOOL NOT NULL, 
	review_count INTEGER NOT NULL, 
	stars FLOAT NOT NULL, 
	state VARCHAR(4), 
	type VARCHAR(8) NOT NULL, 
	CHECK (open IN (0, 1))
);
CREATE TABLE checkin (
	_unnamed INTEGER NOT NULL, 
	business_id VARCHAR(22) NOT NULL, 
	checkin_info VARCHAR(1373) NOT NULL, 
	type VARCHAR(7) NOT NULL
);
CREATE TABLE tip (
	_unnamed INTEGER NOT NULL, 
	business_id VARCHAR(22) NOT NULL, 
	date DATE NOT NULL, 
	likes INTEGER NOT NULL, 
	text VARCHAR(500), 
	type VARCHAR(3) NOT NULL, 
	user_id VARCHAR(22) NOT NULL
);
CREATE TABLE review (
	_unnamed VARCHAR(716) NOT NULL, 
	business_id VARCHAR(22), 
	date VARCHAR(22), 
	review_id VARCHAR(39), 
	stars INTEGER, 
	text VARCHAR(5000), 
	type VARCHAR(6), 
	user_id VARCHAR(22), 
	votes VARCHAR(45)
);
CREATE TABLE user (
	_unnamed INTEGER NOT NULL, 
	average_stars FLOAT NOT NULL, 
	compliments VARCHAR(184) NOT NULL, 
	elite VARCHAR(72) NOT NULL, 
	fans INTEGER NOT NULL, 
	friends VARCHAR(2405) NOT NULL, 
	name VARCHAR(32), 
	review_count INTEGER NOT NULL, 
	type VARCHAR(4) NOT NULL, 
	user_id VARCHAR(22) NOT NULL, 
	votes VARCHAR(51) NOT NULL, 
	yelping_since VARCHAR(7) NOT NULL
);
