#!/bin/bash

## USAGE: sudo run_expr_for_size.sh $benchmark $size $iters

benchmark=$1
size=$2
iters=$3

for buffer_percent in 50 100 200
do
    ./run_expr.sh $benchmark $size $buffer_percent 1

    base_latency=$(tail -n 1 expr-result/base-$benchmark-$size-$buffer_percent-1 | cut -d ' ' -f 4)
    pf_latency=$(tail -n 1 expr-result/pf-$benchmark-$size-$buffer_percent-1 | cut -d ' ' -f 4)

    echo "$size, $buffer_percent, 0, $base_latency, $pf_latency" >> expr-result/$benchmark

    avg_latency=$(( ($base_latency + $pf_latency) / 2 ))

    for pause_percent in 20 50 80
    do
        pause=$(( $avg_latency * $pause_percent / ( 100 - $pause_percent) / 5 ))
        for iter in $(seq 1 $iters)
        do
            ./run_expr.sh $benchmark $size $buffer_percent $pause

            base_latency=$(tail -n 1 expr-result/base-$benchmark-$size-$buffer_percent-1 | cut -d ' ' -f 4)
            pf_latency=$(tail -n 1 expr-result/pf-$benchmark-$size-$buffer_percent-1 | cut -d ' ' -f 4)
            echo "$size, $buffer_percent, $pause_percent, $base_latency, $pf_latency" >> expr-result/$benchmark
        done
    done
done