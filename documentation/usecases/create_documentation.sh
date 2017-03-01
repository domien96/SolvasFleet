#! /bin/bash

if [ -z "$1" ]
  then
    echo "No argument supplied"
fi

find . -name "*.md" -type f | xargs pandoc layout.md --toc --template ./template.tex -o $1
