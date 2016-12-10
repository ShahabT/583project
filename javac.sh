#!/bin/bash

echo "           ####### REGULAR COMPILING #######           "

javac -cp mysql-connector.jar:./src src/queryManager/*.java $1


echo
echo "           ####### RUNNING ORIGINAL #######           "
echo
echo

java -cp mysql-connector.jar:./src $2 $3
