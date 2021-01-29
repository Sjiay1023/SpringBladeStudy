package org.sjiay.demo.thread;

/**
 * @description
 * @author: 86183
 * @create: 2021/1/11 17:02
 **/
public class threadDemo extends Thread{
	@Override
	public void run() {
		super.run();
		System.out.println("需要运行的程序。。。。。。");
	}

	public static void main(String[] args) {
		Thread thread = new threadDemo();
		thread.run();
	}
}
