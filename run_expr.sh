#!/bin/bash

## USAGE: sudo run_expr.sh  <benchmark name> <pause in ms> <data size in mb> <buffer size in MB>

benchmark=$1
pause=$2
size=$3
buffer=$4

echo "######################## EXPR STARTED: $benchmark  pause: $pause db size: $size buffer: $buffer MB"

./javac.sh $benchmark

./restart_mysql.sh $buffer
sudo sync; sudo sh -c 'echo 3 >/proc/sys/vm/drop_caches'

echo
echo "           ####### RUNNING ORIGINAL #######           "
echo
./run.sh $benchmark $pause $size | grep "#" | tee -a expr-result/base-$benchmark-$pause-$size-$buffer

./sqlpfc.sh $benchmark $pause $size

./restart_mysql.sh $buffer
sudo sync; sudo sh -c 'echo 3 >/proc/sys/vm/drop_caches'

echo
echo "           ####### RUNNING WITH PREFETCH #######           "
echo
./run.sh $benchmark $pause $size | grep "#" | tee -a expr-result/pf-$benchmark-$pause-$size-$buffer