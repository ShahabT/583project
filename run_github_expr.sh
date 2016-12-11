#!/bin/bash

## USAGE: sudo run_yelp_expr.sh

for pause in 1 2000 5000 10000 20000
do
    for condition in 4820998666 4846758527 4872518388 4898278249 4924038110
    do
        for buffer in 200 500 1000 2000 3000 4000
        do
            for iter in 1 2 3
                do ./run.sh yelp $pause $condition $buffer
            done
        done
    done
done