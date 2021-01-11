#!/bin/bash
set -e

PRG="$0"
PRGDIR=`dirname "$PRG"`

if [ -z "$JAVA_HOME" -a -z "$JRE_HOME" ]; then
        JAVA_PATH=`which java`
        if [ "x$JAVA_PATH" != "x" ]; then
                JAVA_PATH=`dirname $JAVA_PATH`
                JRE_HOME=`dirname $JAVA_PATH`
        fi
        if [ -z "$JAVA_HOME" -a -z "$JRE_HOME" ]; then
                echo "Neither the JAVA_HOME nor the JRE_HOME environment variable is defined"
                echo "At least one of these environment variable is needed to run this program"
                exit 1
        fi
fi

if [ -z "$JRE_HOME" ]; then
  JRE_HOME="$JAVA_HOME"
fi

if [ -z "$_RUNJAVA" ]; then
  _RUNJAVA="$JRE_HOME"/bin/java
fi

if [ -z "$HEAP_SIZE" ]; then
  HEAP_SIZE=1073741824
fi

Xms=$HEAP_SIZE
Xmx=$HEAP_SIZE
Xmn=$[HEAP_SIZE/2]

JAVA_OPTS="${JAVA_OPTS} -server -Xms${Xms} -Xmx${Xmx} -Xmn${Xmn}"
JAVA_OPTS="${JAVA_OPTS} -XX:+UseParNewGC -XX:+CMSParallelRemarkEnabled -XX:+UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFraction=75"
#JAVA_OPTS="${JAVA_OPTS} -XX:+UseG1GC -XX:G1HeapRegionSize=16m -XX:G1ReservePercent=25 -XX:InitiatingHeapOccupancyPercent=30 -XX:SoftRefLRUPolicyMSPerMB=0 -XX:SurvivorRatio=8"
JAVA_OPTS="${JAVA_OPTS} -verbose:gc -Xloggc:/dev/shm/gc_%p.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintGCApplicationStoppedTime -XX:+PrintAdaptiveSizePolicy"
JAVA_OPTS="${JAVA_OPTS} -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=5 -XX:GCLogFileSize=30m"
JAVA_OPTS="${JAVA_OPTS} -XX:-OmitStackTraceInFastThrow"
JAVA_OPTS="${JAVA_OPTS} -XX:+AlwaysPreTouch"
JAVA_OPTS="${JAVA_OPTS} -XX:MaxDirectMemorySize=${HEAP_SIZE}"
#JAVA_OPTS="${JAVA_OPTS} -XX:-UseLargePages -XX:-UseBiasedLocking -XX:+DisableExplicitGC"
#JAVA_OPTS="${JAVA_OPTS} -Xdebug -Xrunjdwp:transport=dt_socket,address=9555,server=y,suspend=n"
JAVA_OPTS="${JAVA_OPTS} -Duser.timezone=Asia/Shanghai"
JAVA_OPTS="${JAVA_OPTS} ${JAVA_OPTS_EXT}"

exec $_RUNJAVA $JAVA_OPTS -jar app.jar "$@"