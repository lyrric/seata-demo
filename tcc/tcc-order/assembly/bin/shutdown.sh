#!/bin/bash
USER_NAME=`whoami`
if [ "$USER_NAME" = "root" ];then
    echo "----->Root user can not shutdown app.<-----"
    exit 1
fi

cd `dirname $0`
BIN_DIR=`pwd`/

PID=`ps -ef | grep 'java' | grep 'com.github.lyrric.tcc.order.OrderApplication' | grep -v 'grep' | awk '{print $2}'`
if [ ! $PID ]; then
    echo 'Server is not running.'
    exit 0
fi

echo -d 'Stoping the server ...'
kill $PID
echo -e 'Check logs for more details.'