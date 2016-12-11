#!/bin/bash

## USAGE: sudo run_yelp_expr.sh

for pause in 1 1000 2000 3000
do
    for condition in 2005 2010 2013 2015 2017
    do
        for buffer in 100 200 400 700 1000 2000
        do
            for iter in 1 2 3
                do ./run.sh yelp $pause $condition $buffer
            done
        done
    done
done