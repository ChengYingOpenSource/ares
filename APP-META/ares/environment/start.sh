#!/usr/bin/env bash

CY_LOG_HOME="/cy/logs/ares"
JAVA_OPTS="-server -Xmx2g -Xms2g -Xmn1024m -Xss256k -XX:MetaspaceSize=96m -XX:MaxMetaspaceSize=512m"
JAVA_OPTS="$JAVA_OPTS -XX:+DisableExplicitGC"
JAVA_OPTS="$JAVA_OPTS -XX:+UseConcMarkSweepGC"
JAVA_OPTS="$JAVA_OPTS -XX:CMSMaxAbortablePrecleanTime=5000"
JAVA_OPTS="$JAVA_OPTS -XX:+CMSClassUnloadingEnabled "
JAVA_OPTS="$JAVA_OPTS -XX:+CMSParallelRemarkEnabled"
JAVA_OPTS="$JAVA_OPTS -XX:+UseFastAccessorMethods"
JAVA_OPTS="$JAVA_OPTS -XX:+UseCMSInitiatingOccupancyOnly"
JAVA_OPTS="$JAVA_OPTS -XX:+ExplicitGCInvokesConcurrent"
JAVA_OPTS="$JAVA_OPTS -XX:CMSInitiatingOccupancyFraction=80"
JAVA_OPTS="$JAVA_OPTS -XX:SurvivorRatio=10"
JAVA_OPTS="$JAVA_OPTS -XX:MaxDirectMemorySize=1g"
JAVA_OPTS="$JAVA_OPTS -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=$CY_LOG_HOME/java.hprof"
JAVA_OPTS="$JAVA_OPTS -Xloggc:$CY_LOG_HOME/gc.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps"
java -jar /cy/server/ares/lib/ares.jar $JAVA_OPTS

