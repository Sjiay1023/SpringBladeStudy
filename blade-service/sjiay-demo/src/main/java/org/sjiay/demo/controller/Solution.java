package org.sjiay.demo.controller;

import org.apache.commons.lang3.Functions;
import org.yaml.snakeyaml.events.Event;

import javax.print.attribute.standard.PrinterURI;
import java.security.Key;
import java.util.*;

/**
 * @description
 * @author: 86183
 * @create: 2020/10/19 15:08
 **/
public class Solution {

	public class ListNode {
       	int val;
       	ListNode next;
       	ListNode(int x) { val = x; }
   	}
	public ListNode oddEvenList(ListNode head) {
		if (head==null){
			return null;
		}
		ListNode evenHead = head.next;
		ListNode odd = head;
		ListNode even = evenHead;
		while (even!=null && even.next!=null){
			odd.next = even.next;
			odd = odd.next;
			even.next = odd.next;
			even = even.next;
		}
		odd.next = evenHead;
		return head;
	}
	public int[] sortArrayByParityII(int[] A) {
		Arrays.sort(A);
		List<Integer> evenList = new ArrayList<>();
		List<Integer> oddList = new ArrayList<>();
		for (int i=0;i<A.length;i++){
			if (A[i]%2==0){
				evenList.add(A[i]);
			}else {
				oddList.add(A[i]);
			}
		}
		int j=0,k=0;
		for (int i=0;i<A.length;i++){
			if (i % 2 == 0) {
				A[i]=evenList.get(j);
				j++;
			}else {
				A[i]=oddList.get(k);
				k++;
			}
		}
		return A;
	}
	public void dfs(int left, int right, int n, String s, List<String> list){
		if (left==n&&left==right){
			list.add(s);
			return;
		}
		if (left + 1 <= n){
			dfs(left+1,right,n,s+"(",list);
		}
		if (right + 1 <= left){
			dfs(left,right+1,n,s+")",list);
		}
	}
	public List<String> generateParenthesis(int n) {
		List<String> list = new ArrayList<>();
		int left = 0, right = 0;
		dfs(0,0,n,"",list);
		return list;

	}
	public int busyStudent(int[] startTime, int[] endTime, int queryTime) {
		int count=0;
		for (int i=0;i<startTime.length;i++){
			if (startTime[i]>=queryTime && endTime[i]<=queryTime){
				count++;
			}
		}
		return count;
	}
	public int calculate(String s) {
		int x=1,y=0;
		for (int i=0;i<s.length();i++){
			if (s.charAt(i)=='A'){
				x = 2 * x + y;
			}
			if (s.charAt(i)=='B'){
				y = 2 * y + x;
			}
		}
		return x+y;
	}
	public int islandPerimeter(int[][] grid) {
		int count = 0;
		int broken = 0;
		for (int i=0;i<grid.length;i++){
			for (int j=0;j<grid[0].length;j++){
				if (grid[i][j]==1){
					count++;
					if (i < grid.length-1 && grid[i+1][j]==1){
						broken++;
					}
					if (j < grid[0].length-1 && grid[i][j+1]==1){
						broken++;
					}
				}
			}
		}
		return count*4-broken*2;
	}
	public int subtractProductAndSum(int n) {
		int sum = 0;
		int product = 1;
		while (n>0){
			int temp = n%10;
			product*=temp;
			sum+=temp;
			n = n/10;
		}
		return product-sum;
	}
	public int[] decompressRLElist(int[] nums) {
		List<Integer> list = new ArrayList<>();
		for (int i =0 ;i<nums.length-1;i+=2){
			for (int j = 1;j<=nums[i];j++){
				list.add(nums[i+1]);
			}
		}
		return list.stream().mapToInt(Integer::intValue).toArray();

	}
	public int sumNumbers(TreeNode root) {
		return helper(root,0);
	}
	public int helper(TreeNode root,int i){
		if (root==null){
			return 0;
		}
		int temp = root.val*10+i;
		if (root.left==null&&root.right==null){
			return temp;
		}
		return helper(root.right,temp)+helper(root.left,temp);
	}

	public boolean uniqueOccurrences(int[] arr) {
		HashMap<Integer,Integer> map = new HashMap<>();
		for (int i=0;i<arr.length;i++){
			map.put(arr[i],map.getOrDefault(arr[i],0)+1);
		}
		Set<Integer> times = new HashSet<>();
		for (Map.Entry<Integer,Integer> entry : map.entrySet()){
			times.add(entry.getValue());
		}
		if (times.size() == map.size()){
			return true;
		}
		return false;
	}
	public int numJewelsInStones(String J, String S) {
		int sum = 0;
		for (int i=0;i<J.length();i++){
			for (int j=0;j<S.length();j++){
				if (J.charAt(i)==S.charAt(j)){
					sum++;
				}
			}
		}
		return sum;
	}
	public String defangIPaddr(String address) {
		return address.replace(".","[.]");
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

	public List<Integer> preorderTraversal(TreeNode root) {
		List<Integer> result = new ArrayList<>();
		preOrder(root,result);
		return result;
	}
	public int minTimeToVisitAllPoints(int[][] points) {
		int sum=0;
		for (int i=0;i<points.length-1;i++){
			int maxTemp = Math.max(Math.abs(points[i][0]-points[i+1][0]),Math.abs(points[i][1]-points[i+1][1]));
			sum+=maxTemp;
		}
		return sum;
	}
	public int[] sortByBits(int[] arr) {
		for (int i=0;i<arr.length;i++){
			for (int j=i+1;j<arr.length;j++){
				if (getNumber(arr[i])>getNumber(arr[j])){
					int temp = arr[i];
					arr[i] = arr[j];
					arr[j] = temp;
				}
				if (getNumber(arr[i])==getNumber(arr[j])&&arr[i]>arr[j]){
					int temp = arr[i];
					arr[i] = arr[j];
					arr[j] = temp;
				}
			}
		}
		return arr;
	}
	public int findNumbers(int[] nums) {
		int count = 0;
		for (int i=0;i<nums.length;i++){
			if(isEven(nums[i])){
				count++;
			}
		}
		return count;
	}
	public int[][] kClosest(int[][] points, int K) {

		Arrays.sort(points, Comparator.comparingInt((int[] point) -> (point[0] * point[0] + point[1] * point[1])));
		return Arrays.copyOfRange(points,0,K);
	}
	public int diagonalSum(int[][] mat) {
		int len = mat.length;
		int sum = 0;
		for (int i=0;i<len;i++){
			for (int j=0;j<mat[0].length;j++){
				if (i==j){
					sum+=mat[i][j];
				}
				if (i+j == len-1){
					sum+=mat[i][j];
				}
			}
		}
		if (len%2!=0){
			sum = sum - mat[len/2][len/2];
		}
		return sum;
	}
	public int getDecimalValue(ListNode head) {
		List<Integer> list = new ArrayList<>();
		while (head!=null){
			list.add(head.val);
			head = head.next;
		}
		int sum = 0;
		for (int i=0;i<list.size();i++){
			if (list.get(i)!=0){
				sum+=getsum(list.size()-i-1);
			}
		}
		return sum;
	}
	public void nextPermutation(int[] nums) {
		int i = nums.length-2;
		while(i>=0&&nums[i]>=nums[i+1]){
			i--;
		}
		if (i>=0){
			int j = nums.length-1;
			while(j>=0&&nums[i]>=nums[j]){
				j--;
			}
			swap(nums,i,j);
		}
		reverse(nums,i+1);
	}
	/*
	 * 贪心的算法：->举出了反例，是不正确的
	 *
	 * 最直观的转移方程：
	 *
	 * dp[i]: 代表到key[i]为止，拼接所需要的最少步数
	 * dp[i-1]: 到key[i - 1]为止，拼接所需要的最少步数
	 *
	 * dp[i] = dp[i - 1] + (从key[i-1]到key[i]在圆盘上所要走的最短距离) + 1 （按button需要的步数为1）
	 *
	 * 下标x和下标y在圆盘上的最短距离： |x - y| 或者 n - |x - y|
	 * 即Math.min(Math.abs(x - y), n - Math.abs(x - y))
	 *
	 * 但是，
	 * key[i-1] 在圆盘上可以出现多次
	 * key[i] 在圆盘上可以出现多次
	 * 因此一个维度是不够的，再增加一个维度
	 *
	 * 定义转移方程：
	 * dp[i][j] 代表到key[i]为止拼接所需要的最少步数，
	 * 并且这个key[i]是第j个在圆盘上出现的key[i]
	 *
	 * 比如说key[i] = 'd'，在ring圆盘上出现位置的下标：2, 7, 8
	 * dp[i][0] 代表到key[i]为止拼接所需要的最少步数，并且这个key[i]是位于下标位置为2的key[i]
	 * dp[i][1] 代表到key[i]为止拼接所需要的最少步数，并且这个key[i]是位于下标位置为7的key[i]
	 * ...以此类推
	 *
	 * 上一个字符是key[i-1] = 'a'，在ring圆盘上出现的位置下标是: 4, 9
	 * dp[i][0] 代表到key[i - 1]为止拼接所需要的最少步数，并且这个key[i - 1]是位于下标位置为4的key[i]
	 * dp[i][1] 代表到key[i - 1]为止拼接所需要的最少步数，并且这个key[i - 1]是位于下标位置为9的key[i]
	 *
	 * dp[i][j] =
	 * Math.min(
	 * dp[i-1][0] + 上一个字符key[i-1]（第0个出现的key[i - 1]）到这一个字符key[i]（第j个出现的key[i]）的最短距离,
	 * dp[i-1][1] + 上一个字符key[i-1]（第1个出现的key[i - 1]）到这一个字符key[i]（第j个出现的key[i]）的最短距离,
	 * ....
	 * dp[i-1][k] + 上一个字符key[i-1]（第k个出现的key[i - 1]）到这一个字符key[i]（第j个出现的key[i]）的最短距离,
	 * )  + 1 (按button的步数为1)
	 * */
	public int findRotateSteps(String ring, String key) {
		char[] ringChar = ring.toCharArray();
		char[] keyChar = key.toCharArray();
		List<Integer>[] lists = new ArrayList[26];
		for (int i=0;i<26;i++){
			lists[i] = new ArrayList<>();
		}
		int n = ringChar.length,m = keyChar.length;

		for (int i=0;i<n;i++){
			char ch = ringChar[i];
			lists[ch-'a'].add(i);
		}
		//定义dp数组
		int[][] dp = new int[m][150];
		//计算dp[0][j]
		for (int j=0; j<lists[keyChar[0]-'a'].size();j++){
			int x = lists[keyChar[0]-'a'].get(j);
			int y = lists[ringChar[0]-'a'].get(0);
			dp[0][j] = Math.min(Math.abs(x-y),n-Math.abs(x-y))+1;
		}

		for (int i=1;i<keyChar.length;i++){
			// 列出当前的字符，和上一个的字符分别是什么
			char cur = keyChar[i],pre = keyChar[i-1];
			for (int j=0;j<lists[cur-'a'].size();j++){
				int x = lists[cur-'a'].get(j);
				int minSteps = Integer.MAX_VALUE;
				for (int k=0;k<lists[pre-'a'].size();k++){
					// 上一个字符pre出现在ring圆盘上每一个位置的下标
					int y = lists[pre-'a'].get(k);
					int steps = dp[i - 1][k] + Math.min(Math.abs(x - y), n - Math.abs(x - y)) + 1;
					minSteps = Math.min(minSteps, steps);
				}
				dp[i][j] = minSteps;
			}
		}
		int ans = Integer.MAX_VALUE;
		for (int k =0;k<150;k++){
			if (dp[keyChar.length-1][k]==0)break;
			ans = Math.min(ans,dp[keyChar.length-1][k]);
		}
		return ans;
	}
	private void reverse(int[] nums, int i) {
		int start = i,end = nums.length-1;
		while(start<end){
			swap(nums,start,end);
			start++;
			end--;
		}
	}
	private void swap(int[] nums, int i, int j) {
		int temp = nums[i];
		nums[i] = nums[j];
		nums[j] = temp;
	}


	public int getsum(int n){
		int sum = 1;
		for (int i=0;i<n;i++){
			sum*=2;
		}
		return sum;
	}
	public Boolean isEven(int x){
		int count=0;
		while(x!=0){
			x/=10;
			count++;
		}
		if (count%2==0){
			return true;
		}
		return false;

	}
	public int getNumber(int x){
		int count = 0;
		while (x!=0){
			x = x&(x-1);
			count++;
		}
		return count;
	}
	public int sumOddLengthSubarrays(int[] arr) {
		int ans =0,len = arr.length;
		for (int i=0;i<len;i++){
			int left = i+1,right = len-i;
			int leftOdd = left/2;
			int leftEven = (left+1)/2;
			int rightOdd = right/2;
			int rightEven= (right+1)/2;
			ans += arr[i]*(leftOdd*rightOdd + leftEven*rightEven);
		}
		return ans;
	}
	public int numberOfSteps (int num) {
		int count=0;
		while (num!=0){
			if (num % 2 == 0) {
				num/=2;
				count++;
			}else {
				num -=1;
				count++;
			}
		}
		return count;
	}
	public int maxDepth(String s) {
		Queue<Character> queue = new LinkedList<>();
		int max = 0;
		for (int i=0;i<s.length();i++){
			if (s.charAt(i)=='('){
				queue.offer(s.charAt(i));
			}
			if (s.charAt(i)==')' && !queue.isEmpty()){
				queue.poll();
			}
			if (queue.size()>max){
				max = queue.size();
			}
		}
		return max;
	}
	public int ladderLength(String beginWord, String endWord, List<String> wordList) {
		Set<String> visited = new HashSet<>();
		Queue<String> queue = new LinkedList<>();
		if (!wordList.contains(endWord)){
			return 0;
		}
		queue.offer(beginWord);
		visited.add(beginWord);
		int count = 0;
		while (queue.size()>0){
			int size = queue.size();
			count++;
			for (int i=0;i<size;i++){
				String start = queue.poll();
				for (String word : wordList){
					if (visited.contains(word)){
						continue;
					}
					if (!istransfer(start,word)){
						continue;
					}
					if (word.equals(endWord)){
						return count+1;
					}
					visited.add(word);
					queue.offer(word);
				}
			}
		}
		return 0;
	}

	private boolean istransfer(String s1, String s2){
		if (s1.length()!=s2.length()){
			return false;
		}
		int count = 0;
		for (int i=0;i<s1.length();i++){
			if (s1.charAt(i)!=s2.charAt(i)){
				count++;
			}
		}
		if (count>1){
			return false;
		}
		return count==1;
	}



	Queue<String> queue = new LinkedList<>();
	public int[] intersection(int[] nums1, int[] nums2) {
		Set<Integer> integerSet = new HashSet<>();
		for (int i=0;i<nums1.length;i++){
			for (int j=0;j<nums2.length;j++){
				if (nums1[i] == nums2[j]){
					integerSet.add(nums1[i]);
				}
			}
		}
		Integer[] temp = integerSet.toArray(new Integer[] {});
		int[] intArray = new int[temp.length];
		for (int i = 0; i < temp.length; i++) {
			intArray[i] = temp[i].intValue();
		}
		return intArray;
	}
	public int numTeams(int[] rating) {
		int ans = 0;
		for (int i=0;i<rating.length-1;i++){
			int iless=0,imore=0;
			int jless=0,jmore=0;
			for (int j=0;j<i;j++){
				if (rating[j] < rating[i]){
					iless++;
				}else{
					imore++;
				}
			}
			for (int k=i+1;k<rating.length;k++){
				if (rating[k] < rating[i]){
					jless ++;
				}else{
					jmore++;
				}
			}
			ans+=iless*jmore+imore*jless;
		}
		return ans;
	}
	public int[] createTargetArray(int[] nums, int[] index) {
		List<Integer> integerList = new ArrayList<>();
		for (int i=0;i<nums.length;i++){
			integerList.add(index[i],nums[i]);
		}
		return integerList.stream().mapToInt(Integer::intValue).toArray();
	}
	public void preOrder(TreeNode node,List<Integer> list){
		if (node==null){
			return;
		}
		list.add(node.val);
		preOrder(node.left,list);
		preOrder(node.right,list);

	}

	public int[] smallerNumbersThanCurrent(int[] nums) {
		int[] result = new int[nums.length];
		for (int i=0;i<nums.length;i++){
			int sum =0;
			for (int j=0;j<nums.length;j++){
				if (nums[i]>nums[j]){
					sum++;
				}
			}
			result[i] = sum;
		}
		return result;
	}
	public void deleteNode(ListNode node) {
		ListNode p = node;
		while (p.next!=null){
			p.val = p.next.val;
			p = p.next;
			if (p.next.next == null){
				p.next.val = p.next.next.val;
				p.next = null;
				break;
			}
		}
	}
	public int minCount(int[] coins) {
		int sum = 0;
		for (int i=0;i<coins.length;i++){
			sum+=getTimes(coins[i]);
		}
		return sum;
	}
	public int getTimes(int count){
		if (count%2==0){
			return count/2;
		}else{
			return count/2+1;
		}
	}
	public boolean isPalindrome(ListNode head) {
		if (head == null){
			return true;
		}
		List<Integer> temp = new ArrayList<>();
		while(head!=null){
			temp.add(head.val);
			head = head.next;
		}
		int start = 0,end = temp.size()-1;
		while(start < end){
			if (!temp.get(start).equals(temp.get(end))){
				return false;
			}
			start++;
			end--;
		}
		return true;
	}

	public static void main(String[] args) {
//		Integer a = -129;
//		Integer b = -129;
//		System.out.println(a == b);
//		System.out.println(a.equals(b));
//		String s1 = new StringBuilder("go")
//			.append("od").toString();
//		System.out.println(s1.intern() == s1);
//		String s2 = new StringBuilder("ja")
//			.append("va").toString();
//		System.out.println(s2.intern() == s2);
		String s1 = "Programming";
		String s2 = new String("Programming");
		String s3 = "Program";
		String s4 = "ming";
		String s5 = "Program" + "ming";
		String s6 = s3 + s4;
		System.out.println(s1 == s2);
		System.out.println(s1 == s5);
		System.out.println(s1 == s6);
		System.out.println(s1 == s6.intern());
		System.out.println(s2 == s2.intern());

	}
}
