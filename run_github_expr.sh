#!/bin/bash

## USAGE: sudo run_github_expr.sh

for pause in 1 1000 2000 5000 10000
do
    for size in 100 300 500 1000 2000
    do
        for buffer in 50 100 500 1000 3000
        do
            for iter in 1 2 3
                do ./run.sh github $pause $size $buffer
            done
        done
    done
done