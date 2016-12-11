#!/bin/bash

## USAGE: run.sh  <benchmark name> <pause in ms> <condition>

java -cp mysql-connector.jar:./src benchmark.$1.Main $2 $3
