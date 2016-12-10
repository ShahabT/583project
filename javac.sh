#!/bin/bash

## USAGE: javac.sh src/benchmark/<name>/*.java benchmark.<name>.Main <pause>

echo "           ####### REGULAR COMPILING #######           "

javac -cp mysql-connector.jar:./src src/queryManager/*.java $1


echo
echo "           ####### RUNNING ORIGINAL #######           "
echo
echo

java -cp mysql-connector.jar:./src $2 $3
