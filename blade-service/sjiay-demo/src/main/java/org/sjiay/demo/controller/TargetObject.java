package org.sjiay.demo.controller;

/**
 * @description
 * @author: 86183
 * @create: 2021/1/19 17:37
 **/
public class TargetObject {
	private String value;

	public TargetObject() {
		value = "JavaGuide";
	}

	public void publicMethod(String s) {
		System.out.println("I love " + s);
	}

	private void privateMethod() {
		System.out.println("value is " + value);
	}
}
