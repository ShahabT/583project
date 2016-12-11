#!/bin/bash

## USAGE: sqlpfc.sh <benchmark name> <pause in ms> <condition> [run]

echo "           ####### REGULAR COMPILING #######           "

javac -cp mysql-connector.jar:./src src/queryManager/*.java src/benchmark/$1/*.java


echo
echo "           ####### PROFILING #######           "
echo
echo

export SQL_PROFILING=PROFILE
./run.sh $1 1 $3
export SQL_PROFILING=


echo
echo
echo "           ####### COMPILING WITH PREFETCH #######           "

javac -cp mysql-connector.jar:./src src/queryManager/*.java src/benchmark/$1/*.java


# putting the original one back
cp src/queryManager/QueryPrefetcher.java.empty src/queryManager/QueryPrefetcher.java

if [ $# -lt 4 ]; then exit 0; fi

echo
echo "           ####### RUNNING WITH PREFETCH #######           "
echo
echo

./run.sh $1 $2 $3
