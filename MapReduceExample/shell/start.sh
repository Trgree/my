#!/bin/bash
#Created by lsj 2017-2-15

if [ $# -lt 1 ]; then
        echo `date +"%Y-%m-%d %H:%M:%S"`  "Usage:<DATE> "
        exit 1
fi

DATE=$1

SHELLPATH=$(cd `dirname $0`; pwd)
echo $SHELLPATH
[ -z $EXAMPLE_HOME ] && EXAMPLE_HOME=`cd "$SHELLPATH/../" >/dev/null; pwd`
echo $EXAMPLE_HOME
WORKPATH=$EXAMPLE_HOME

JAVALIB=${WORKPATH}/mr
LOGFILE=${WORKPATH}/log/wordcount_${DATE}.log
MRJAR=$JAVALIB/wordcount.jar

#运行参数，input有多个时，用逗号分隔如 -D input=/tmp/wordcount/input,/tmp/wordcount/input2
ARGS="
-D input=/tmp/wordcount/input 
-D output=/tmp/wordcount/ouput 
-D date=$DATE
-D mapreduce.job.reduces=4
"

echo "DATE" ${DATE} 
echo "WORKPATH" ${WORKPATH}
echo "LOGFILE" ${LOGFILE}
echo "INPUT" ${INPUT}
echo "OUTPUT" ${OUTPUT}
echo "MRJAR" ${MRJAR}

echo `date +"%Y-%m-%d %H:%M:%S"`  "Start..."  | tee -a $LOGFILE

hadoop jar ${MRJAR} org.ace.example.WordDriver $ARGS -conf ../config/config.xml  1>>$LOGFILE 2>>$LOGFILE

ret=$?
if [ ${ret} -ne 0 ]; then
     exit ${ret}
fi

echo `date +"%Y-%m-%d %H:%M:%S"`    "Exec Success [$DATE]!" | tee -a $LOGFILE

exit 0
