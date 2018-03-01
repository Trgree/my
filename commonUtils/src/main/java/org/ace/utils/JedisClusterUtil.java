package org.ace.utils;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class JedisClusterUtil {

	private String addressKeyPrefix = "redis.address";
	private JedisCluster jedisCluster;
	private Pattern p = Pattern.compile("^.+[:]\\d{1,5}\\s*$");
	private PropertiesUtil propUtil;
	private GenericObjectPoolConfig conf = new GenericObjectPoolConfig();

	public JedisClusterUtil(PropertiesUtil propUtil) throws Exception {
		this.propUtil = propUtil;
		parseHostAndPort();
		propertiesSet();
		afterPropertiesSet();
	}

	private void propertiesSet() {
		conf.setMinIdle(propUtil.getInt("redis.minIdle", 2));
		conf.setMaxIdle(propUtil.getInt("redis.maxIdle", 100));
		conf.setMaxTotal(propUtil.getInt("redis.maxTotal", 1000));
		conf.setMaxWaitMillis(propUtil.getInt("redis.maxWaitMillis", -1));
	}

	public JedisCluster getCluster() {
		return jedisCluster;
	}

	private Set<HostAndPort> parseHostAndPort() throws Exception {
		try {
			Set<HostAndPort> haps = new HashSet<HostAndPort>();
			for (Object key : propUtil.getProp().keySet()) {
				if (!((String) key).startsWith(addressKeyPrefix)) {
					continue;
				}
				String val = (String) propUtil.getProp().get(key);
				boolean isIpPort = p.matcher(val.trim()).matches();
				if (!isIpPort) {
					throw new IllegalArgumentException("ip 或 port 不合法");
				}
				String[] ipAndPort = val.split(":");
				HostAndPort hap = new HostAndPort(ipAndPort[0], Integer.parseInt(ipAndPort[1]));
				haps.add(hap);
			}
			return haps;
		} catch (IllegalArgumentException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new Exception("解析 jedis 配置文件失败", ex);
		}
	}

	public void afterPropertiesSet() throws Exception {
		Set<HostAndPort> haps = this.parseHostAndPort();
		int connectionTimeout = propUtil.getInt("redis.connectionTimeout", 2000);
		int soTimeout = propUtil.getInt("redis.soTimeout", 2000);
		int maxAttempts = propUtil.getInt("redis.maxAttempts", 5);
		String password = propUtil.getString("redis.password").trim();
		if(password == null || password.equals("")){
			jedisCluster = new JedisCluster(haps, connectionTimeout, soTimeout, maxAttempts, conf);
		} else {
			jedisCluster = new JedisCluster(haps, connectionTimeout, soTimeout, maxAttempts, password, conf);
		}
	}
}
