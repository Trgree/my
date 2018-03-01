#!/bin/bash
#Created by lsj 2017-2-15

if [ $# -lt 1 ]; then
        echo `date +"%Y-%m-%d %H:%M:%S"`  "Usage:<DATE> "
        exit 1
fi

DATE=$1

#LOCAL
WORKPATH=/home/bigdata/project/lsj/wordcount
JAVALIB=${WORKPATH}/mr
LOGFILE=${WORKPATH}/log/wordcount_${DATE}.log
MRJAR=$JAVALIB/wordcount.jar

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

hadoop jar ${MRJAR} org.ace.example.WordDriver $ARGS  1>>$LOGFILE 2>>$LOGFILE

ret=$?
if [ ${ret} -ne 0 ]; then
     exit ${ret}
fi

echo `date +"%Y-%m-%d %H:%M:%S"`    "Exec Success [$DATE]!" | tee -a $LOGFILE

exit 0
