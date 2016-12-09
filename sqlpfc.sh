#!/bin/bash

echo "           ####### REGULAR COMPILING #######           "

javac -cp mysql-connector.jar:./src src/queryManager/*.java $1


echo
echo "           ####### PROFILING #######           "
echo
echo

export SQL_PROFILING=PROFILE
java -cp mysql-connector.jar:./src $2
export SQL_PROFILING=


echo
echo
echo "           ####### COMPILING WITH PREFETCH #######           "

javac -cp mysql-connector.jar:./src src/queryManager/*.java $1


echo
echo "           ####### RUNNING WITH PREFETCH #######           "
echo
echo

java -cp mysql-connector.jar:./src $2
