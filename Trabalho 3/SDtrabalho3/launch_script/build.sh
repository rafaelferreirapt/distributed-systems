#!/usr/bin/env bash
set -x
echo "	@ Compiling all source code "
find java -name "*.class" -exec rm -rf {} \;
find java -type d -empty -delete
find ../src -name "*.java" > sources.txt
javac @sources.txt -d .
rm sources.txt

mkdir -p java

mv registry/ java/
mv structures/ java/
mv interfaces/ java/
mv bench/ java/
mv referee_site/ java/
mv playground/ java/
mv general_info_repo/ java/
mv entities/ java/

mkdir logs

