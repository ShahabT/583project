#!/bin/bash

## USAGE: sudo run_expr.sh  <benchmark name> <data size in mb> <buffer percent> <pause in ms>

benchmark=$1
size=$2
buffer_percent=$3
pause=$4
buffer=$(( $size * $buffer_percent / 100 ))

echo "######################## EXPR STARTED: $benchmark,  pause: $pause, db size: $size MB, buffer: $buffer_percent %"

./javac.sh $benchmark

./restart_mysql.sh $buffer

echo
echo "           ####### RUNNING ORIGINAL #######           "
echo
./run.sh $benchmark $pause $size | grep "#" | tee -a expr-result/base-$benchmark-$size-$buffer_percent-$pause

./sqlpfc.sh $benchmark $pause $size

./restart_mysql.sh $buffer

echo
echo "           ####### RUNNING WITH PREFETCH #######           "
echo
./run.sh $benchmark $pause $size | grep "#" | tee -a expr-result/pf-$benchmark-$size-$buffer_percent-$pause