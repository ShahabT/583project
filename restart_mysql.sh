#!/bin/bash

sudo service mysql stop
sudo service mysql start --skip-grant-tables --innodb_buffer_pool_size=$(($1 * 1024 * 1024))

sudo sync; sudo sh -c 'echo 3 >/proc/sys/vm/drop_caches'