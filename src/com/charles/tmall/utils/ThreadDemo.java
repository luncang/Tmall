package com.charles.tmall.utils;


public class ThreadDemo extends Thread {
	public String name;
	Thread t;
	public ThreadDemo(String name){
		this.name = name;
	}
	
	public void run(){
		System.out.println("run:"+name);
		try{
		for(int i=4;i>0;i--){
			System.out.println("thread:"+name+","+i);
			Thread.sleep(50);
		}
		}catch(Exception e){
			System.out.println("thread:"+name+" interrunpter");
		}
		
		System.out.println("thread:"+name+" exiting");
		
	}
	
	public void start(){
		System.out.println("start:"+name);
		if(t==null){
			t = new Thread(this,name);
			t.start();
		}
	}
	
	public static void main(String[] args) {
		ThreadDemo t1 = new ThreadDemo("t1");
		t1.start();
		ThreadDemo t2 = new ThreadDemo("t2");
		t2.start();
	}
}
