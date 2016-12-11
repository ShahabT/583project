CREATE TABLE github (
	created_at DATETIME NOT NULL, 
	repo VARCHAR(54) NOT NULL, 
	user VARCHAR(27) NOT NULL, 
	org VARCHAR(36), 
	type VARCHAR(29) NOT NULL, 
	id BIGINT NOT NULL, 
	user_email VARCHAR(84)
);
