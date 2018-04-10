## MR程序Demo

利用官网的WordCount例子，使用eclipse对MR进行单元测试，及部署到linux(shell)

## 一、MR单元测试
 
  具体代码查看 org.ace.MR_WordCount.WordTest
  可测试Map，Reduce或MapReduce，counter

## 二、部署程序：
前提：linux环境，hadoop环境
### 上传程序下所有文件夹到以下linux目录
（也可以用其它目录，但要修改start.sh的WORKPATH变量为相应的路径）
>/home/bigdata/project/lsj/wordcount

```
$cd /home/bigdata/project/lsj/wordcount
$ls
file  input  log  mr  shell
```

### 上传file/input下文件到hdfs目录
 /tmp/wordcount/input
（也可以用其它目录，但要修改start.sh的INPUT变量为相应的路径）

```
$hadoop fs -mkdir -p /tmp/wordcount/input
$hadoop fs -put file/input/* /tmp/wordcount/input
```

### 运行程序

```
$cd shell
$sh start.sh 20170215
```

### 运行成功

```
17/02/15 20:39:47 INFO mapreduce.Job:  map 0% reduce 0%
17/02/15 20:39:52 INFO mapreduce.Job:  map 50% reduce 0%
17/02/15 20:39:53 INFO mapreduce.Job:  map 100% reduce 0%
17/02/15 20:39:58 INFO mapreduce.Job:  map 100% reduce 50%
17/02/15 20:39:59 INFO mapreduce.Job:  map 100% reduce 100%
17/02/15 20:40:00 INFO mapreduce.Job: Job job_1482809144286_5142 completed successfully
```

### 查看结果

```
$hadoop fs -ls /tmp/wordcount/ouput
-rw-r--r-- 2017-02-15 20:39 /tmp/wordcount/ouput/_SUCCESS
-rw-r--r-- 2017-02-15 20:39 /tmp/wordcount/ouput/part-r-00000
-rw-r--r-- 2017-02-15 20:39 /tmp/wordcount/ouput/part-r-00001
-rw-r--r-- 2017-02-15 20:39 /tmp/wordcount/ouput/part-r-00002
-rw-r--r-- 2017-02-15 20:39 /tmp/wordcount/ouput/part-r-00003
```

```
$hadoop fs -cat /tmp/wordcount/ouput/part-r-00001
Bye     1
Bye2    1
Hello   2
Hello2  1
World   2
World2  2
```

