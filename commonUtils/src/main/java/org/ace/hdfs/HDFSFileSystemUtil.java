package org.ace.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;

/**
 * 获取HDFS文件系统FileSystem
 * Created by Liangsj on 2018/2/9.
 */
public class HDFSFileSystemUtil {
    private static final int FALIOVER_SLEEP_MILLIS=500;// 连接失败后重连时间间隔
    private static final int FALIOVER_ATTEMPTS=1;//  连接失败后重连次数

    /**
     * 获取FileSystem
     * @param namenodes 主结点连接，格式 为ip:port ,高可用（即有2台namenode）时使用逗号分隔 ip:port,ip:port
     * @return
     */
    public static FileSystem getFileSystem(String namenodes) throws IOException {
        String arr[] = namenodes.split(",");
        if(arr.length == 2){
            return getHAFileSystem(arr[0], arr[1]);
        } else if(arr.length == 1) {
            String ip = null;
            String port = null;
            try {
                ip = arr[0].split(":")[0];
                port = arr[0].split(":")[1];
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("namenode配置不正确,应为 ip:port[,ip:port");
            }
            return getFileSystem(ip, port);
        }
        throw new RuntimeException("namenode配置不正确,应为 ip:port,ip:port");
    }

    /**
     * 获取高可用HA HDFS集群的FileSystem
     * @param namenode1 HA其中一台namenode 格式为ip:port 如 192.168.5.150:8020
     * @param namenode2 HA其中第二台namenode 格式为ip:port 如 192.168.5.151:8020
     * @throws IOException
     */
    public static FileSystem getHAFileSystem(String namenode1, String namenode2) throws IOException {
        Configuration conf = new Configuration(false);
        conf.set("fs.defaultFS", "hdfs://nameservice1");
     //   conf.set("fs.default.name", conf.get("fs.defaultFS"));
        conf.set("dfs.nameservices","nameservice1");
        conf.set("dfs.ha.namenodes.nameservice1", "namenode1,namenode2");
        conf.set("dfs.namenode.rpc-address.nameservice1.namenode1",namenode1);
        conf.set("dfs.namenode.rpc-address.nameservice1.namenode2",namenode2);
        conf.set("dfs.client.failover.proxy.provider.nameservice1" ,"org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider");
        conf.setInt("dfs.client.failover.sleep.base.millis",FALIOVER_SLEEP_MILLIS);// 连接失败后重连时间间隔
        conf.setInt("dfs.client.failover.max.attempts",FALIOVER_ATTEMPTS);// 连接失败后重连次数
        FileSystem fs = FileSystem.get(conf);
        if(!checkConn(fs)) {
            throw new RuntimeException("hdfs 连接异常");
        }
        return fs;
    }

    public static void close(FileSystem fs){
        if(fs !=null){
            try {
                fs.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean checkConn(FileSystem fs){
        if(fs == null){
            return false;
        }
        try {
            return fs.exists(new Path("/"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取HDFS集群的FileSystem,只有一个namenode时使用
     * @param namenodeip hdfs主结点ip
     * @param port hdfs访问端口
     * @throws IOException
     */
    public static FileSystem getFileSystem(String namenodeip, String port) throws IOException {
        Configuration conf = new Configuration(false);
        conf.set("fs.default.name", "hdfs://" + namenodeip + ":" + port);
        conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");
        conf.setInt("dfs.client.failover.sleep.base.millis",FALIOVER_SLEEP_MILLIS);// 连接失败后重连时间间隔
        conf.setInt("dfs.client.failover.max.attempts",FALIOVER_ATTEMPTS);// 连接失败后重连次数
        FileSystem fs = FileSystem.get(conf);
        if(!checkConn(fs)) {
            throw new RuntimeException("hdfs 连接异常");
        }
        return fs;
    }


    public static void main(String[] args) throws Exception {
        FileSystem fs  = HDFSFileSystemUtil.getHAFileSystem("192.168.5.150:8021","192.168.5.151:8021");
        System.out.println(fs.getScheme());
    }



}
