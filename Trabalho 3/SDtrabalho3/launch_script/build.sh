#!/usr/bin/env bash
set -x
echo "	@ Compiling all source code "
find . -name "*.class" -exec rm -rf {} \;
find . -type d -empty -delete
find ../src -name "*.java" > sources.txt
javac @sources.txt -d .
rm sources.txt

mkdir -p dir_clientSide/Coach
mkdir -p dir_clientSide/Contestant
mkdir -p dir_clientSide/Referee
mkdir -p dir_serverSide/Log
mkdir -p dir_serverSide/Playground
mkdir -p dir_serverSide/RefereeSite
mkdir -p dir_serverSide/Bench
mkdir -p dir_registry
