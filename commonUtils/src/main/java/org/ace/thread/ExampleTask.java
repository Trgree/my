package org.ace.thread;

import java.io.Serializable;   
 
public class ExampleTask implements Runnable, Serializable{   
    private static final long serialVersionUID = 0;  
    
    private int index;
    
    public ExampleTask(int index) {
    	this.index = index;
	}

	//每个任务的执行过程  
    public void run(){   
        try {   
        	System.out.println("task" +index+" 执行");
        	Thread.sleep(3000);
        	System.out.println("task" +index+" 执行结束");
        } catch (Exception e) {  
        	System.out.println("task" +index+" 异常");
        }   
    }   
} 