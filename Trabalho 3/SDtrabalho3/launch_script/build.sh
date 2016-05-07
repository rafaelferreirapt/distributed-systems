#!/usr/bin/env bash
set -x
echo "	@ Compiling all source code "
find ../src -name "*.java" > sources.txt
javac @sources.txt -d .
rm sources.txt

rm -rf java
mkdir -p java

mv registry java/
mv structures java/
mv interfaces java/
mv bench java/
mv referee_site java/
mv playground/ java
mv general_info_repo java/
mv entities java/

tar -pcvzf java.tar.gz java