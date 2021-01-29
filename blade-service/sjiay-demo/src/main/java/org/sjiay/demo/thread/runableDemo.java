package org.sjiay.demo.thread;

/**
 * @description
 * @author: 86183
 * @create: 2021/1/11 17:07
 **/
public class runableDemo implements Runnable{
	@Override
	public void run() {
		System.out.println("我是Runnable接口.....");
	}

	public static void main(String[] args) {
		Runnable runnable = new runableDemo();
		Thread thread = new Thread(runnable);
		thread.start();
	}
}
