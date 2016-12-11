#!/bin/bash

## USAGE: sudo run_expr.sh  <benchmark name> <pause in ms> <condition> <buffer size in MB>

benchmark=$1
pause=$2
condition=$3
buffer=$4

echo "######################## EXPR STARTED: $benchmark  pause: $pause condition: $condition buffer: $buffer MB"

./javac.sh $benchmark

./restart_mysql.sh $buffer
sudo sync; sudo sh -c 'echo 3 >/proc/sys/vm/drop_caches'

echo
echo "           ####### RUNNING ORIGINAL #######           "
echo
./run.sh $benchmark $pause $condition | grep "#" | tee -a expr-result/base-$benchmark-$pause-$condition-$buffer

./sqlpfc.sh $benchmark $pause $condition

./restart_mysql.sh $buffer
sudo sync; sudo sh -c 'echo 3 >/proc/sys/vm/drop_caches'

echo
echo "           ####### RUNNING WITH PREFETCH #######           "
echo
./run.sh $benchmark $pause $condition | grep "#" | tee -a expr-result/pf-$benchmark-$pause-$condition-$buffer