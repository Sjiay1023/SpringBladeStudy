package org.sjiay.demo.controller;

import org.springframework.boot.web.reactive.context.StandardReactiveWebEnvironment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: SpringBlade
 * @description
 * @author: YouName
 * @create: 2020-07-31 14:17
 **/
public class TestStream {
	public static void main(String[] args) {
//		List<User> userList = new ArrayList<>();
//		userList.add(new User(1L,"张三",10, "清华大学"));
//		userList.add(new User(2L,"李四",12, "清华大学"));
//		userList.add(new User(3L,"王五",15, "清华大学"));
//		userList.add(new User(4L,"赵六",12, "清华大学"));
//		userList.add(new User(5L,"田七",25, "北京大学"));
//		userList.add(new User(6L,"小明",16, "北京大学"));
//		userList.add(new User(7L,"小红",14, "北京大学"));
//		userList.add(new User(8L,"小华",14, "浙江大学"));
//		userList.add(new User(9L,"小丽",17, "浙江大学"));
//		userList.add(new User(10L,"小何",10, "浙江大学"));
//
//		filter(userList);
//		distinct(userList);
//		limit(userList);
//		sorted(userList);
//		skip(userList);
		List<String> list = new ArrayList<>();
		list.add("1");
		list.add("2");
		list.add("3");
		for (String s : list) {
			if ("3".equals(s)){
				list.remove(s);
			}
		}
		System.out.println(list);

	}

	/**
	 * 根据条件过滤属性
	 * @param list
	 */
	private static void filter(List<User> list){
		System.out.println("学校是清华大学的User");
		List<User> userList1 = list.stream().filter(user -> "清华大学".equals(user.getSchool())).collect(Collectors.toList());
		userList1.forEach(user -> System.out.println(user.getName() + ','));
	}

	/**
	 * 根据某一属性去重
	 * @param list
	 */
	private static void distinct(List<User> list){
		System.out.println("所有user的年龄集合");
		List<Integer> userAgeList = list.stream().map(User::getAge).distinct().collect(Collectors.toList());
		System.out.println("userAgeList= " + userAgeList);
	}

	/**
	 * 返回前n个元素的流
	 * @param list
	 */
	private static void limit(List<User> list){
		System.out.println("年龄是偶数的前两位User");
		List<User> userList = list.stream().filter(user -> user.getAge()%2==0).limit(2).collect(Collectors.toList());
		userList.forEach(user -> System.out.println(user.getName() + ','));
	}

	/**
	 * 按age字段进行排序
	 * @param list
	 */
	private static void sorted(List<User> list){
		System.out.println("年龄从大到小排序");
		List<User> userList = list.stream().sorted((s1,s2) -> s2.getAge() - s1.getAge()).collect(Collectors.toList());
		userList.forEach(user -> System.out.println(user.getName() + ','));
	}

	/**
	 * 跳过n个元素后输出
	 * @param list
	 */
	private static void skip(List<User> list){
		System.out.println("跳过前面两个User的其他所有User");
		List<User> userList = list.stream().skip(2).collect(Collectors.toList());
		userList.forEach(user -> System.out.println(user.getName() + ','));
	}




}
