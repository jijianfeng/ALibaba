package com.jjf.load;

public class ClinitClassLoad {
	public static void main(String args[]){
		LoadClass loadClass = new LoadClass();
		Thread t1 = new Thread(loadClass);
		Thread t2 = new Thread(loadClass);
		t1.start();
		t2.start();
	}
}  

class LoadClass implements Runnable{
	@Override
	public void run() {
		System.out.println("�߳�"+Thread.currentThread().getName()+"��ʼ");
		DeadLoopClass DeadLoopClass = new DeadLoopClass();
		System.out.println("�߳�"+Thread.currentThread().getName()+"����");
	}
}

class DeadLoopClass{
	static {
		System.out.println(Thread.currentThread().getName()+"cinit");// <clinit>()����ֻ����һ��
	}
}