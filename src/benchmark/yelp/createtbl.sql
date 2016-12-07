create database if not exists 583a;
use 583a;

CREATE table if not exists checkin(check_info VARCHAR(1024), type VARCHAR(10), business_id VARCHAR(32));
CREATE table if not exists business(business_id VARCHAR(32), full_address VARCHAR(10), hours VARCHAR(2048), open VARCHAR(10), categories VARCHAR(1024), city VARCHAR(30), review_count VARCHAR(10), name VARCHAR(1024), neighborhoods VARCHAR(1024), longitude VARCHAR(50), state VARCHAR(10), stars VARCHAR(10), latitude VARCHAR(50), attributes VARCHAR(4000), type VARCHAR(10));
CREATE table if not exists review(votes VARCHAR(60), user_id VARCHAR(30), review_id VARCHAR(30), stars VARCHAR(10), date VARCHAR(20), text VARCHAR(6000), type VARCHAR(10), business_id VARCHAR(32));
CREATE table if not exists tip(user_id VARCHAR(30), text VARCHAR(6000), business_id VARCHAR(32), likes VARCHAR(10), date VARCHAR(20), type VARCHAR(10));
CREATE table if not exists user(yelp_since VARCHAR(10), votes VARCHAR(60), review_count VARCHAR(10), name VARCHAR(1024), user_id VARCHAR(30), friends VARCHAR(6000), fans VARCHAR(10), average_stars VARCHAR(10), type VARCHAR(10), compliments VARCHAR(300), elite VARCHAR(20));
;
