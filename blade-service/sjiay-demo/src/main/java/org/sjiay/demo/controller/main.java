package org.sjiay.demo.controller;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @description
 * @author: 86183
 * @create: 2021/1/20 16:50
 **/
public class main {
	public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException {
		Class<?> targetClass = Class.forName("org.sjiay.demo.controller.TargetObject");
		TargetObject targetObject = (TargetObject) targetClass.newInstance();
		Method[] methods = targetClass.getDeclaredMethods();
		for (Method method : methods) {
			System.out.println(method.getName());
		}
		/**
		 * 获取指定参数并调用
		 */
		Method publicMethod = targetClass.getDeclaredMethod("publicMethod",String.class);
		publicMethod.invoke(targetObject,"JavaGuide");
		/**
		 * 获取指定参数并对参数进行修改
		 */
		Field field = targetClass.getDeclaredField("value");
		//为了对类中的参数进行修改我们取消安全检查
		field.setAccessible(true);
		field.set(targetObject,"JavaGuide");
		/**
		 * 调用 private 方法
		 */
		Method method = targetClass.getDeclaredMethod("privateMethod");
		method.setAccessible(true);
		method.invoke(targetObject);


	}
}
