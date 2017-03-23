#!/bin/sh

date >> ~/backuplog
echo $@ >> ~/backuplog
/usr/bin/sudo /usr/bin/rsync "$@";
