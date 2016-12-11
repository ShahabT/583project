#!/bin/bash

## USAGE: sqlpfc.sh <benchmark name> <pause in ms>

echo "           ####### REGULAR COMPILING #######           "

javac -cp mysql-connector.jar:./src src/queryManager/*.java src/benchmark/$1/*.java


echo
echo "           ####### PROFILING #######           "
echo
echo

export SQL_PROFILING=PROFILE
java -cp mysql-connector.jar:./src benchmark.$1.Main 10
export SQL_PROFILING=


echo
echo
echo "           ####### COMPILING WITH PREFETCH #######           "

javac -cp mysql-connector.jar:./src src/queryManager/*.java src/benchmark/$1/*.java


echo
echo "           ####### RUNNING WITH PREFETCH #######           "
echo
echo

java -cp mysql-connector.jar:./src benchmark.$1.Main $2


# putting the original one back
cp src/queryManager/QueryPrefetcher.java.empty src/queryManager/QueryPrefetcher.java