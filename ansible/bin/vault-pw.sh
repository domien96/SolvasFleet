#!/bin/bash

cat .vault-password 2> /dev/null || (read -p "Please enter vault password: " -s PW && echo $PW)
