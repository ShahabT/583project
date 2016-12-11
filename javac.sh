#!/bin/bash

## USAGE: javac.sh <benchmark name> [<pause in ms> <condition>]

echo "           ####### REGULAR COMPILING #######           "

javac -cp mysql-connector.jar:./src src/queryManager/*.java src/benchmark/$1/*.java

if [ $# -lt 3 ]; then exit 0; fi

echo
echo "           ####### RUNNING ORIGINAL #######           "
echo
echo

./run.sh $1 $2 $3