package com.example.demo.controller;

import com.alibaba.druid.sql.visitor.functions.Right;
import javafx.scene.chart.ValueAxis;
import org.apache.ibatis.cache.decorators.FifoCache;
import org.omg.PortableInterceptor.ObjectReferenceFactory;
import org.springblade.core.boot.file.IFileProxy;
import org.springframework.context.annotation.Bean;

import javax.validation.constraints.Max;
import java.text.BreakIterator;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @program: SpringBlade
 * @description
 * @author: shenjiayu
 * @create: 2020-07-01 16:54
 **/
public class Solution {
	public List<String> letterCombinations(String digits) {
		Map<Character,String> stringMap = new HashMap<>();
		List<String> resultList = new ArrayList<>();
		if (digits.length()==0){
			return resultList;
		}
		stringMap.put('2',"abc");
		stringMap.put('3',"def");
		stringMap.put('4',"ghi");
		stringMap.put('5',"jkl");
		stringMap.put('6',"mno");
		stringMap.put('7',"pqrs");
		stringMap.put('8',"tuv");
		stringMap.put('9',"wxyz");
		backTrace(resultList,stringMap,digits,0,new StringBuffer());
		return resultList;

	}
	public void backTrace(List<String> combinations,Map<Character,String> stringMap,String digits,int index,StringBuffer combination){
		if (digits.length()==index){
			combinations.add(combination.toString());
		}else{
			char digit = digits.charAt(index);
			String letters = stringMap.get(digit);
			for (int i=0;i<letters.length();i++){
				combination.append(letters.charAt(i));
				backTrace(combinations,stringMap,digits,index+1,combination);
				combination.deleteCharAt(index);
			}
		}

	}
	private Boolean isPalindrome(String s){
		for (int i = 0; i<s.length()/2 ;i++){
			if (s.charAt(i)!=s.charAt(s.length()-i-1)){
				return false;
			}
		}
		return true;
	}
}
	public TreeNode getNode(ListNode left,ListNode right){
		if (left==right){
			return null;
		}
		ListNode fast = left;
		ListNode slow = left;
		while(fast!=right && fast.next != right){
			fast = fast.next.next;
			slow = slow.next;
		}
		TreeNode root = new TreeNode(slow.val);
		root.left = getNode(left,slow);
		root.right = getNode(slow.next,right);
		return root;
	}
	public int countHeight(TreeNode root) {
		if (root == null){
			return 0;
		}
		return Math.max(countHeight(root.left),countHeight(root.right))+1;
	}


	private static void perm(char[] chars,int start,int end,ArrayList<String> list){
		ArrayList<Character> characters = new ArrayList<>();
		if (start>=end){
			list.add(String.valueOf(chars));
		}else {
			for (int i = start;i<=end;i++){
				if (!characters.contains(chars[i])) {
					characters.add(chars[i]);
					swap(chars,start,i);
					perm(chars,start+1,end,list);
					swap(chars,start,i);
				}

			}
		}
	}

	public class ListNode {
			int val;
	     	ListNode next;
	     	ListNode() {}
	 		ListNode(int val) { this.val = val; }
	 		ListNode(int val, ListNode next) { this.val = val; this.next = next; }
	 }

	 public class TreeNode {
	      int val;
	      TreeNode left;
	      TreeNode right;
	      TreeNode() {}
	      TreeNode(int val) { this.val = val; }
	      TreeNode(int val, TreeNode left, TreeNode right) {
	      		this.val = val;
	         	this.left = left;
	        	this.right = right;
	     }
	 }
	private static void swap(char[] chars,int i,int j){
		char temp = chars[i];
		chars[i] = chars[j];
		chars[j] = temp;
	}
	class Node {
		public int val;
		public List<Node> neighbors;

		public Node() {
			val = 0;
			neighbors = new ArrayList<Node>();
		}

		public Node(int _val) {
			val = _val;
			neighbors = new ArrayList<Node>();
		}

		public Node(int _val, ArrayList<Node> _neighbors) {
			val = _val;
			neighbors = _neighbors;
		}

	public static void main(String[] args) {
		// 线程池
		ExecutorService exec = Executors.newCachedThreadPool();
		// 只能5个线程同时访问
		final Semaphore semp = new Semaphore(5);
		// 模拟20个客户端访问
		for (int index = 0; index < 50; index++) {
			final int NO = index;
			Runnable run = new Runnable() {
				@Override
				public void run() {
					try {
						// 获取许可
						semp.acquire();
						System.out.println("Accessing: " + NO);
						Thread.sleep((long) (Math.random() * 6000));
						//availablePermits()指的是当前信号灯库中有多少个可以被使用
						System.out.println("-----------------" + semp.availablePermits());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}finally {
						// 访问完后，释放
						semp.release();
					}
				}
			};
			exec.execute(run);
		}
		// 退出线程池
		exec.shutdown();

	}
}

