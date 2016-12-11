#!/bin/bash

## USAGE: javac.sh <benchmark name> <pause in ms>

echo "           ####### REGULAR COMPILING #######           "

javac -cp mysql-connector.jar:./src src/queryManager/*.java src/benchmark/$1/*.java


echo
echo "           ####### RUNNING ORIGINAL #######           "
echo
echo

java -cp mysql-connector.jar:./src benchmark.$1.Main $2
