#!/bin/bash

## USAGE: sudo run_github_expr.sh

iters=1
benchmark=github

for size in 50 100 300 500 1000 2000
do
    for buffer_percent in 50 100 200
    do
        ./run_expr.sh $benchmark $size $buffer_percent 1

        latency=$(tail -n 1 expr-result/base-$benchmark-$size-$buffer_percent-1 | cut -d ' ' -f 4)

        for pause_percent in 20 50 75
        do
            pause=$(( $latency * $pause_percent / ( 100 - $pause_percent) / 5 ))
            for iter in $(seq 1 $iters)
                do ./run_expr.sh $benchmark $size $buffer_percent $pause
            done
        done
    done
done