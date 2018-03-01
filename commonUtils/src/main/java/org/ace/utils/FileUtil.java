package org.ace.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

	public static String completePath(String path){
		if(path == null) {
			return null;
		}
		if(path.endsWith("/") || path.endsWith("\\")){
			return path;
		} else {
			return path + "/";
		}
	}
	
	public static List<String> readFile(File file, String encoding, boolean readFirstRow){
		List<String> result = new ArrayList<String>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file), encoding));
			String line = null;
			if(!readFirstRow){
				br.readLine();
			}
			while((line = br.readLine())!=null) {
				result.add(line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("文件不存在", e);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public static void mkdir(String pathstr){
		File path  = new File(pathstr);
		if(!path.exists()){
			path.mkdirs();
		}
	}
	
	
	public static void writeFile(String file, String encoding, List<String> datalist, String headrow){
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), encoding));
			if(headrow !=null && !headrow.trim().equals("")){
				pw.println(headrow);
			}
			for(String line : datalist) {
				pw.println(line);
			}
			pw.flush();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}  finally{
			pw.close();
		}
	}
	
	public static boolean isFileExits(String file){
		File f = new File(file);
		return f.exists();
	}
}
