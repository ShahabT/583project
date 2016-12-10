#!/bin/bash

## USAGE: sqlpfc.sh src/benchmark/<name>/*.java benchmark.<name>.Main <pause>

echo "           ####### REGULAR COMPILING #######           "

javac -cp mysql-connector.jar:./src src/queryManager/*.java $1


echo
echo "           ####### PROFILING #######           "
echo
echo

export SQL_PROFILING=PROFILE
java -cp mysql-connector.jar:./src $2 10
export SQL_PROFILING=


echo
echo
echo "           ####### COMPILING WITH PREFETCH #######           "

javac -cp mysql-connector.jar:./src src/queryManager/*.java $1


echo
echo "           ####### RUNNING WITH PREFETCH #######           "
echo
echo

java -cp mysql-connector.jar:./src $2 $3


# putting the original one back
cp src/queryManager/QueryPrefetcher.java.empty src/queryManager/QueryPrefetcher.java