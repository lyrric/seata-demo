#!/bin/bash
USER_NAME=$(whoami)
if [ "$USER_NAME" = "root" ];then
    echo "----->Root user can not start app.<-----"
    exit 1
fi

cd `dirname $0` || exit
cd ..
DEPLOY_DIR=$(pwd)

LIB_DIR=$DEPLOY_DIR/lib
LIB_JARS=`ls $LIB_DIR|grep .jar|awk '{print "'$LIB_DIR'/"$0}'|tr "\n" ":"`

JAVA_OPTS="-Djava.awt.headless=true -Djava.net.preferIPv4Stack=true "
JAVA_MEM_OPTS=""
BITS=`java -version 2>&1 | grep -i 64-bit`
if [ -n "$BITS" ]; then
    JAVA_MEM_OPTS=" -server -Xmx2g -Xms2g -Xmn1g -XX:PermSize=256m -Xss256k -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:+UseCMSCompactAtFullCollection -XX:LargePageSizeInBytes=128m -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70 "
else
    JAVA_MEM_OPTS=" -server -Xms1g -Xmx1g -XX:PermSize=128m -XX:SurvivorRatio=2 -XX:+UseParallelGC "
fi
JAVA_GC_OPTS="-XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:/data/app/logs/at-business/gc.log"

TOMCAT_DIR=$DEPLOY_DIR/temp
if [ -z "$TOMCAT_DIR" ]; then
    echo 'error TOMCAT_DIR is empty'
    exit
fi
rm -rf $TOMCAT_DIR/*
echo -e "Starting the server ..."
nohup java $JAVA_OPTS $JAVA_MEM_OPTS $JAVA_GC_OPTS -classpath $LIB_JARS com.github.lyrric.Application --server.tomcat.basedir=$TOMCAT_DIR >/dev/null 2>&1 &
echo -e "Check the logs for more details."
PID=`ps -ef | grep 'java' | grep 'com.github.lyrric.Application' | grep -v 'grep' | awk '{print $2}'`
echo -e "The PID Is $PID"