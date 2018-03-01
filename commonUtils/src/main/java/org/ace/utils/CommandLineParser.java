package org.ace.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 解析命令输入参数
 * 并存入到map中，
 * @author Liangsj
 *
 */
public class CommandLineParser {

	/** 要解析的命令参数前缀 */
	private static final String ARG_OPT="-D";
	
	/** map缓存 */
	private Map<String, String> props = new HashMap<String, String>(); 
	
	/**
	 * 解析命令输入参数
	 * 以{@link CommandLineParser.ARG_OPT} 开头的参数，将除截取，并解析key和value到缓存中
	 * @param args 命令输入参数
	 * 如 -Dkey=value -D key=value -D key value 都会认为是要解析的输入参数
	 * @return 返回除了要解析的参数以外的参数
	 */
	public String[] parse(String[] args) {
		if (args == null) {
			return null;
		}
		List<String> newArgs = new ArrayList<String>(args.length);
		for (int i = 0; i < args.length; i++) {
			String prop = null;
			if (args[i].equals(ARG_OPT)) {
				if (i < args.length - 1) {
					prop = args[++i];
				}
			} else if (args[i].startsWith(ARG_OPT)) {
				prop = args[i].substring(ARG_OPT.length(), args[i].length());
			} else {
				newArgs.add(args[i]);
			}
			if (prop != null) {
				if (prop.contains("=")) {
					String[] p = prop.split("=");
					props.put(p[0], p[1]);
				} else {
					if (i < args.length - 1) {
						props.put(prop, args[++i]);
					}
				}
			}
		}
		return newArgs.toArray(new String[newArgs.size()]);
	}
	
	/**
	 * 取得命令参数中key的值
	 * @param key
	 * @return
	 */
	public String getProp(String key){
		return props.get(key);
	}
	
	/**
	 * 取得所有命令参数key和value
	 * @return
	 */
	public Map<String, String> getProps() {
		return props;
	}

	/**
	 * 返回所有命令参数key和value的String，一般用于打印
	 * @return
	 */
	public String getAllPropsString(){
		StringBuilder sb = new StringBuilder();
		for(Map.Entry<String, String> entry : props.entrySet()){
			sb.append(entry.getKey()).append("=").append(entry.getValue()).append("\n");
		}
		if(sb.toString().endsWith("\n")){
			return sb.substring(0, sb.length() - 1);
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		CommandLineParser parser= new CommandLineParser();
		String[] otherArgs = parser.parse(args);
		System.out.println(Arrays.toString(otherArgs));
		System.out.println(parser.getAllPropsString());
		System.out.println(parser.getProp("sdf"));
	}
}
