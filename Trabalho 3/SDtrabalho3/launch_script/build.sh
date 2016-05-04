#!/usr/bin/env bash
set -x
echo "	@ Compiling all source code "
find . -name "*.class" -exec rm -rf {} \;
find . -type d -empty -delete
find ../src -name "*.java" > sources.txt
javac @sources.txt -d .
rm sources.txt

rm -rf javas

mkdir -p javas/dir_clientSide/coach
mkdir -p javas/dir_clientSide/contestant
mkdir -p javas/dir_clientSide/referee
mkdir -p javas/dir_serverSide/general_info_repo
mkdir -p javas/dir_serverSide/playground
mkdir -p javas/dir_serverSide/referee_site
mkdir -p javas/dir_serverSide/bench
mkdir -p javas/dir_registry

# linux: ditto
# osx: ditto

ditto registry/ javas/dir_registry/
ditto structures/ javas/dir_registry/structures/
ditto interfaces/ javas/dir_registry/interfaces/

ditto bench/ javas/dir_serverSide/bench/
ditto structures/ javas/dir_serverSide/bench/structures/
ditto interfaces/ javas/dir_serverSide/bench/interfaces/

ditto referee_site/ javas/dir_serverSide/referee_site/
ditto structures/ javas/dir_serverSide/referee_site/structures/
ditto interfaces/ javas/dir_serverSide/referee_site/interfaces/

ditto playground/ javas/dir_serverSide/playground/
ditto structures/ javas/dir_serverSide/playground/structures/
ditto interfaces/ javas/dir_serverSide/playground/interfaces/

ditto general_info_repo/ javas/dir_serverSide/general_info_repo/
ditto structures/ javas/dir_serverSide/general_info_repo/structures/
ditto interfaces/ javas/dir_serverSide/general_info_repo/interfaces/

ditto entities/coach/ javas/dir_clientSide/coach/
ditto structures/ javas/dir_clientSide/coach/structures/
ditto interfaces/ javas/dir_clientSide/coach/interfaces/

ditto entities/contestant/ javas/dir_clientSide/contestant/
ditto structures/ javas/dir_clientSide/contestant/structures/
ditto interfaces/ javas/dir_clientSide/contestant/interfaces/

ditto entities/referee/ javas/dir_clientSide/referee/
ditto structures/ javas/dir_clientSide/referee/structures/
ditto interfaces/ javas/dir_clientSide/referee/interfaces/

mkdir logs

rm -rf bench
rm -rf entities
rm -rf structures
rm -rf interfaces
rm -rf general_info_repo
rm -rf playground
rm -rf referee_site
rm -rf registry
