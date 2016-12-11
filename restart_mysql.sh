sudo service mysql stop
sudo service mysql start --skip-grant-tables --innodb_buffer_pool_size=$(($1 * 1024 * 1024))