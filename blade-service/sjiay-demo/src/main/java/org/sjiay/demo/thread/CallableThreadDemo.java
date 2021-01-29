package org.sjiay.demo.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * @description
 * @author: 86183
 * @create: 2021/1/11 17:10
 **/
public class CallableThreadDemo implements Callable<Integer> {
	public static void main(String[] args) {
		CallableThreadDemo callableThreadDemo = new CallableThreadDemo();
		FutureTask<Integer> future = new FutureTask<>(callableThreadDemo);
		for (int i=0;i<21;i++){
			System.out.println(Thread.currentThread().getName()+" 的循环变量i的值"+i);
			if(i==20)//i为20的时候创建ft线程
			{
				new Thread(future,"有返回值的线程FutureTask").start();
			}
		}

		//ft线程结束时，获取返回值
		try
		{
			System.out.println("子线程的返回值："+future.get());//get()方法会阻塞，直到子线程执行结束才返回
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		} catch (ExecutionException e)
		{
			e.printStackTrace();
		}
	}
	@Override
	public Integer call() throws Exception {
		int i=0;
		for (;i<10;i++){
			System.out.println(Thread.currentThread().getName()+" "+i);
		}
		return i;
	}
}
