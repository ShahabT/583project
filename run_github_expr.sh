#!/bin/bash

## USAGE: sudo run_github_expr.sh

iters=1
benchmark=github

echo "size, buffer %, pause %, orig latency, pf latency" >> expr-result/$benchmark

for size in 100 300 500 1000 2000
do
    for buffer_percent in 50 100 200
    do
        ./run_expr.sh $benchmark $size $buffer_percent 1

        base_latency=$(tail -n 1 expr-result/base-$benchmark-$size-$buffer_percent-1 | cut -d ' ' -f 4)
        pf_latency=$(tail -n 1 expr-result/base-$benchmark-$size-$buffer_percent-1 | cut -d ' ' -f 4)

        echo "$size, $buffer_percent, 0, $base_latency, $pf_latency" >> expr-result/$benchmark

        for pause_percent in 20 50 75
        do
            pause=$(( $latency * $pause_percent / ( 100 - $pause_percent) / 5 ))
            for iter in $(seq 1 $iters)
            do
                ./run_expr.sh $benchmark $size $buffer_percent $pause
                echo "$size, $buffer_percent, $pause_percent, $base_latency, $pf_latency" >> expr-result/$benchmark
            done
        done
    done
done