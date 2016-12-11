#!/bin/bash

## USAGE: sudo run_yelp_expr.sh

for pause in 1 1000 2000 4000
do
    for size in 300 500 1000 2000
    do
        for buffer in 100 200 500 1000 3000
        do
            for iter in 1 2 3
                do ./run.sh yelp $pause $size $buffer
            done
        done
    done
done