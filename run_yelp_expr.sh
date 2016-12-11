#!/bin/bash

## USAGE: sudo run_yelp_expr.sh

iters=3
benchmark=yelp

echo "db size, buffer %, pause %, orig latency, pf latency" >> expr-result/$benchmark

for size in 300 500 1000 2000
    do ./run_expr_for_size.sh $benchmark $size $iters
done