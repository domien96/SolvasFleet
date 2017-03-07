#! /bin/bash

if [ -z "$1" ]
  then
    echo "No output name supplied"
fi

if [ -z "$2" ]
  then
    echo "No directory supplied"
fi

find $2 -name "*.md" -type f | xargs pandoc layout.md --toc --template ./template.tex -o $1
