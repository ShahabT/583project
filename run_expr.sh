#!/bin/bash

## USAGE: sudo run_expr.sh  <benchmark name> <pause in ms> <condition> <buffer size in MB>

benchmark=$1
pause=$2
condition=$3
buffer=$4

./javac.sh $benchmark

sh -c 'echo 3 >/proc/sys/vm/drop_caches'
./restart_mysql.sh buffer

./run.sh $benchmark $pause $condition | grep ">>>" | tee expr-result/base-$benchmark-$pause-$condition-$buffer

./sqlpfc.sh $benchmark $pause $condition

sh -c 'echo 3 >/proc/sys/vm/drop_caches'
./restart_mysql.sh buffer

./run.sh $benchmark $pause $condition | grep ">>>" | tee expr-result/pf-$benchmark-$pause-$condition-$buffer