package org.ace.utils;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Table;

public class HBaseUtil {

	private String zookeeperIp;
	private String zookeeperPort;
	
	public HBaseUtil(String zookeeperIp, String zookeeperPort) {
		super();
		this.zookeeperIp = zookeeperIp;
		this.zookeeperPort = zookeeperPort;
	}

	public Connection getConn() throws IOException{
		Configuration conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", zookeeperIp);
		conf.set("hbase.zookeeper.property.clientPort", zookeeperPort);
        return ConnectionFactory.createConnection(conf);
	}
	
	public Table getTable(Connection conn,String tablename) throws IOException{
		return conn.getTable(TableName.valueOf(tablename));
	}
	
	public Table getTable(String tablename) throws IOException{
		return getTable(getConn(), tablename);
	}
	
	public void closeConn(Connection conn){
		if(conn != null){
			try {
				conn.close();
			} catch (IOException e) {
			}
		}
	}
}
