package org.sjiay.demo.controller;

import org.springblade.core.boot.file.IFileProxy;
import org.yaml.snakeyaml.events.Event;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @description
 * @author: 86183
 * @create: 2020/10/19 15:08
 **/
public class LeetCodeSolution {
	class DSU {
		int[] parent;

		public DSU(int len) {
			parent = new int[len];
			for (int i = 0; i < len; ++i)
				parent[i] = i;
		}

		public int find(int x) {
//			return parent[x] != x ? parent[x] = find(parent[x]) : x;
			if (x==parent[x]){
				return x;
			}else {
				parent[x] = find(parent[x]);
				return parent[x];
			}
		}

		public void union(int x, int y) {
			parent[find(x)] = find(y);
		}
	}

	public static void main(String[] args) {
		List<User> list = new ArrayList<>();
		list.add(new User(DateUtils.getBeginTime("20000101")));
		list.add(new User(DateUtils.getBeginTime("20210101")));
		list.add(new User(DateUtils.getBeginTime("19990101")));
		list = list.stream().sorted(Comparator.comparing(User::getAccessTime).reversed())
			.collect(Collectors.toList());
		System.out.println(list);

	}
	int R;//图的行数！
	int C;//图的列数！
	int[][] graph;//每个点的高度！
	boolean[][] isCome;//是否来过这个点！
	int[][] dir = {{1,0},{-1,0},{0,-1},{0,1}};//方向数组！
	public int minimumEffortPath(int[][] heights) {
		R = heights.length;
		C = heights[0].length;
		graph  = heights;
		int left = 0;
		int right = 1000000;
		while (true){
			isCome = new boolean[R][C];
			if (left > right){
				break;
			}
			int mid = (left+right)/2;
			if (DFS(0,0,mid)){
				right = mid;
			}else{
				left = mid + 1;
			}
		}
		return right;

	}

	private boolean DFS(int i, int j, int mid) {
		if(i == R - 1 && j == C - 1){
			return true;
		}
		isCome[i][j] = true;//标记点已经来过！
		for(int[] d : dir){
			int x = i + d[0];
			int y = j + d[1];
			//判断这个点是否符合题意！
			if(x < 0 || x >= R || y < 0 || y >= C || isCome[x][y]){
				continue;
			}
			//判断预估值能不能通过这个条路！
			if(mid < Math.abs(graph[i][j] - graph[x][y])){
				continue;
			}
			//如果存在这样一条路，说明预估值是可行的！
			if(DFS(x,y,mid)) return true;
		}
		return false;
	}

	public static List<Integer> addToArrayForm(int[] A, int K) {
		List<Integer> list = new ArrayList<>();
		int tmp =1;
		for(int i=A.length-1;i>=0;i--){
			int sum = A[i]+K%10;
			K/=10;
			if (sum>10){
				K++;
				sum-=10;
			}
			list.add(sum);
		}
		for (;K>0;K/=10){
			list.add(K%10);
		}
		Collections.reverse(list);
		return list;
	}
	public int maximumProduct(int[] nums) {
		int len = nums.length;
		Arrays.sort(nums);
		int max = nums[len-1]*nums[len-2]*nums[len-3];
		if(nums[0]>0){
			return max;
		}else{
			if (nums[0]*nums[1]*nums[len-1]>max){
				max = nums[0]*nums[1]*nums[len-1];
			}
		}
		return max;
	}
	public int minCostConnectPoints(int[][] points) {
		List<Edge> list = new ArrayList<>();
		for(int i=0;i<points.length-1;i++){
			for (int j=i+1;i<points.length;j++){
				int len = Math.abs(points[i][0]-points[j][0])+Math.abs(points[i][1]-points[j][1]);
				if (len>0){
					list.add(new Edge(i,j,len));
				}
			}
		}
		Collections.sort(list);
		DSU dsu = new DSU(points.length+1);
		int res =0;
		for (Edge edge : list) {
			int x = edge.getX();
			int y = edge.getY();
			int len = edge.getLen();
			if (dsu.find(x)==dsu.find(y)){
				continue;
			}
			res+=len;
			dsu.union(x,y);
		}
		return res;
	}
	class Edge implements Comparable<Edge>{
		private int x;
		private int y;
		private int len;

		public Edge(int x, int y, int len) {
			this.x = x;
			this.y = y;
			this.len = len;
		}

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}

		public int getLen() {
			return len;
		}

		public void setLen(int len) {
			this.len = len;
		}

		@Override
		public int compareTo(Edge o) {
			return Integer.compare(this.len,o.len);
		}
	}

	public int removeStones(int[][] stones) {
		UnionFind unionFind = new UnionFind();
		for (int i=0;i<stones.length;i++){
			unionFind.union(stones[i][0]+10000,stones[i][1]);
		}
		return stones.length - unionFind.getCount();
	}

	public List<List<String>> accountsMerge(List<List<String>> accounts) {
		Map<String, Integer> emailToIndex = new HashMap<>();
		Map<String, String> emailToName = new HashMap<>();
		int emailCount = 0;
		for (List<String> account : accounts) {
			String name = account.get(0);
			int size = account.size();
			for (int i=1;i<size;i++){
				String email = account.get(i);
				if (!emailToIndex.containsKey(email)){
					emailToIndex.put(email, emailCount++);
					emailToName.put(email,name);
				}
			}
		}
		DSU dsu = new DSU(emailCount+1);
		for (List<String> account : accounts) {
			String firstEmail = account.get(1);
			int firstIndex = emailToIndex.get(firstEmail);
			int size = account.size();
			for (int i=2;i<size;i++){
				String nextEmail = account.get(i);
				int nextIndex = emailToIndex.get(nextEmail);
				dsu.union(firstIndex,nextIndex);
			}
		}
		Map<Integer,List<String>> indexToEmails = new HashMap<>();
		for (String email : emailToIndex.keySet()) {
			int index = dsu.find(emailToIndex.get(email));
			List<String> account = indexToEmails.getOrDefault(index, new ArrayList<>());
			account.add(email);
			indexToEmails.put(index,account);
		}
		List<List<String>> merge = new ArrayList<>();
		for (List<String> emails : indexToEmails.values()) {
			Collections.sort(emails);
			String name = emailToName.get(emails.get(0));
			List<String> account = new ArrayList<>();
			account.add(name);
			account.addAll(emails);
			merge.add(account);
		}
		return merge;
	}
	private class UnionFind{
		private Map<Integer,Integer> parent;
		private int count;

		public UnionFind() {
			parent = new HashMap<>();
			count=0;
		}

		public int getCount() {
			return count;
		}

		public int find(int x){
			if (!parent.containsKey(x)){
				count++;
				parent.put(x,x);
			}
			if (x!=parent.get(x)){
				parent.put(x,find(parent.get(x)));
			}
			return parent.get(x);

		}

		public void union(int x,int y){
			int rootX = find(x);
			int rootY = find(y);
			if (rootX == rootY){
				return;
			}
			parent.put(rootX,rootY);
			count--;
		}
	}

	public List<Boolean> prefixesDivBy5(int[] A) {
		List<Boolean> res = new ArrayList<>(A.length);
		int num =0;
		for (int i=0;i<A.length;i++){
			num = (num*2+A[i])%5;
			if(num==0){
				res.add(true);
			}else{
				res.add(false);
			}
		}
		return res;
	}
	public double binary2ten(int[] x){
		double sum = 0;
		int len = x.length;
		for (int i=0;i<len;i++){
			double temp = x[i]*Math.pow(2,len-1-i);
			sum+=temp;
		}
		return sum;
	}
	public int[] findRedundantConnection(int[][] edges) {
		int len = edges.length;
		DSU dsu = new DSU(len);
		for (int i=0;i<len;i++){
			int[] temp = edges[i];
			if(dsu.find(temp[0])!=dsu.find(temp[1])) {
				dsu.union(temp[0],temp[1]);
			}else {
				return temp;
			}
		}
		return new int[0];
	}

	public String smallestStringWithSwaps(String s, List<List<Integer>> pairs) {
		int len = s.length();

		DSU dsu = new DSU(100000);
		//构造下标集合
		for (List<Integer> list : pairs)
			dsu.union(list.get(0), list.get(1));
		//每个下标集合有1个leader，用leader作为key(唯一)，下标集合List作为value
		HashMap<Integer, List<Integer>> map = new HashMap<>();
		//从小到大遍历，使得List<Integer>中的值是有序的(事后不用再排序了)
		for (int i = 0; i < len; ++i) {
			int key = dsu.find(i);
			map.computeIfAbsent(key, unused -> new ArrayList<>()).add(i);
		}

		StringBuilder res = new StringBuilder(s);
		//遍历所有每个下标集合，进行字符局部排序
		for (List<Integer> idx_list : map.values())
			if (idx_list.size() > 1)
				sort(res, idx_list);

		return res.toString();
	}

	//根据下标集合进行局部排序
	private void sort(StringBuilder res, List<Integer> idx_list) {
		int len = idx_list.size();
		char[] array = new char[len];
		//根据下标集合构建字符集合
		for (int i = 0; i < len; ++i)
			array[i] = res.charAt(idx_list.get(i));
		//将字符集合排序
		Arrays.sort(array);
		//将字符按序“插入”回StringBuilder
		for (int i = 0; i < len; ++i)
			res.setCharAt(idx_list.get(i), array[i]);
	}

	public int findCircleNum1(int[][] isConnected) {
		int n = isConnected.length;
		boolean[] visited = new boolean[n];
		int count = 0;
		Queue<Integer> queue = new LinkedList<>();
		for (int i=0;i<n;i++){
			if (!visited[i]){
				count++;
				queue.offer(i);
				while (!queue.isEmpty()){
					int v = queue.poll();
					for (int w=0;w<n;w++){
						if (isConnected[v][w]==1 && !visited[w]){
							visited[w] = true;
							queue.offer(w);
						}
					}
				}
			}
		}
		return count;
	}

	public int findCircleNum(int[][] isConnected) {
		int provinces = isConnected.length;
		boolean[] visited = new boolean[provinces];
		int count = 0;
		for (int i=0;i<provinces;i++){
			if (!visited[i]){
				circleDfs(isConnected,visited,provinces,i);
				count++;
			}
		}
		return count;
	}

	private void circleDfs(int[][] isConnected, boolean[] visited, int provinces, int i) {
		for (int j=0;j<provinces;j++){
			if(!visited[j] && isConnected[i][j]==1){
				visited[j]=true;
				circleDfs(isConnected,visited,provinces,j);
			}
		}
	}

	public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
		int nvars = 0;
		Map<String,Integer> variables = new HashMap<>();

		int n=equations.size();
		for (int i=0;i<n;i++){
			if (!variables.containsKey(equations.get(i).get(0))){
				variables.put(equations.get(i).get(0),nvars++);
			}
			if (!variables.containsKey(equations.get(i).get(1))){
				variables.put(equations.get(i).get(1),nvars++);
			}
		}
		double[][] graph = new double[nvars][nvars];
		for (int i=0;i<nvars;i++){
			Arrays.fill(graph[i],-1.0);
		}

		for (int i=0;i<n;i++){
			int va = variables.get(equations.get(i).get(0));
			int vb = variables.get(equations.get(i).get(1));
			graph[va][vb] = values[i];
			graph[vb][va] = 1.0/values[i];
		}


		for (int k=0;k<nvars;k++){
			for (int i=0;i<nvars;i++){
				for (int j=0;j<nvars;j++){
					if (graph[i][k] > 0 && graph[k][j]>0){
						graph[i][j] = graph[i][k]*graph[k][j];
					}
				}
			}
		}
		int queriesCount = queries.size();
		double[] ret = new double[queriesCount];
		for (int i=0;i<queriesCount;i++){
			List<String> query = queries.get(i);
			double result = -1.0;
			if (variables.containsKey(query.get(0))&&variables.containsKey(query.get(1))){
				int ia = variables.get(query.get(0));
				int ib =  variables.get(query.get(1));
				if (graph[ia][ib]>0){
					result = graph[ia][ib];
				}
			}
			ret[i] = result;
		}
		return ret;
	}
	public List<List<Integer>> largeGroupPositions(String s) {
		int start = 0,end = 0;
		List<List<Integer>> res = new LinkedList<>();
		int count = 1;
		for (int i=1;i<s.length();i++){
			char startChar = s.charAt(start);
			char ch = s.charAt(i);
			if(startChar == ch){
				count++;
			}else{
				end = i-1;
				if (count>=3){
					List<Integer> list = new ArrayList<>();
					list.add(start);
					list.add(end);
					res.add(list);
				}
				start = i;
				count = 1;
			}
		}
		if (count>=3){
			List<Integer> list = new ArrayList<>();
			list.add(start);
			list.add(s.length()-1);
			res.add(list);
		}
		return res;
	}
	public int fib(int n) {
		if (n==0){
			return 0;
		}
		if (n==1){
			return 1;
		}
		return fib(n-1)+fib(n-2);
	}

	public int eraseOverlapIntervals(int[][] intvs) {
		if(intvs.length==0){
			return 0;
		}
		Arrays.sort(intvs, (o1, o2) -> o1[1]-o2[1]);
		int count = 1;
		int x_end = intvs[0][1];
		for (int[] intv : intvs) {
			int start = intv[0];
			if (start >= x_end){
				count++;
				x_end = intv[1];
			}
		}
		return intvs.length-count;
	}
	public int maximumWealth(int[][] accounts) {
		int maxWealth = 0;
		for (int i=0;i<accounts.length;i++){
			int sum = 0;
			for (int j=0;j<accounts[0].length;j++){
				sum+=accounts[i][j];
			}
			if (sum>maxWealth){
				maxWealth=sum;
			}
		}
		return maxWealth;
	}
	public int lastStoneWeight(int[] stones) {
		PriorityQueue<Integer> pq = new PriorityQueue<>((a,b) -> b - a);
		for (int stone : stones) {
			pq.offer(stone);
		}
		while (pq.size()>1){
			int a = pq.poll();
			int b = pq.poll();
			if (a>b){
				pq.offer(a-b);
			}
		}
		return pq.isEmpty() ? 0 : pq.poll();
	}
	public int minPatches(int[] nums, int n) {
		int res = 0;
		int len = nums.length,index=0;
		long x = 1;
		while (x <= n){
			if (index<len && nums[index] <= x){
				x += nums[index];
				index++;
			}else{
				x *= 2;
				res++;
			}
		}
		return res;
	}
	public int maxProfit(int k, int[] prices) {
		if(prices.length==0){
			return 0;
		}
		int len = prices.length;
		k = Math.min(k,len/2);
		int[][] buy = new int[len][k+1];
		int[][] sell = new int[len][k+1];

		buy[0][0] = -prices[0];
		sell[0][0] = 0;
		for(int i=0;i<len;i++){
			buy[0][i] = sell[0][i] = Integer.MIN_VALUE/2;
		}
		for (int i=1;i<len;i++){
			buy[i][0] = Math.max(buy[i-1][0],sell[i-1][0]-prices[i]);
			for (int j=1;j<=k;j++){
				buy[i][j] = Math.max(buy[i-1][j],sell[i-1][j]-prices[i]);
				sell[i][j] = Math.max(sell[i-1][j],buy[i-1][j-1]+prices[i]);
			}
		}
		return Arrays.stream(sell[len-1]).max().getAsInt();
	}
	public boolean halvesAreAlike(String s) {
		int half = s.length()/2;
		int left = 0;
		int right = 0;
		for (int i=0;i<half;i++){
			char ch = s.charAt(i);
			if (isU(ch)){
				left++;
			}
		}
		for (int i=half;i<s.length();i++){
			char ch = s.charAt(i);
			if (isU(ch)){
				left++;
			}
		}
		return left==right;
	}

	private boolean isU(char ch) {
		if(ch == 'a' || ch == 'e'||ch == 'i'||ch == 'o'||ch == 'u'
		|| ch == 'A' || ch == 'E'||ch == 'I'||ch == 'O'||ch == 'U'){
			return true;
		}
		return false;

	}

	public int findContentChildren(int[] g, int[] s) {
		Arrays.sort(g);
		Arrays.sort(s);
		int res = 0;
		if(s.length==0){
			return 0;
		}
		int cookieLength = s.length;
		for (int i=0,j=0;i<g.length&&j<cookieLength;i++,j++){

			while(cookieLength > j && g[i]>s[j]){
				j++;
			}
			if (j<cookieLength){
				res++;
			}
		}
		return res;
	}
	public int candy(int[] ratings) {
		int[] left = new int[ratings.length];
		int[] right = new int[ratings.length];
		Arrays.fill(left,1);
		Arrays.fill(right,1);
		for(int i=1;i<ratings.length;i++){
			if (ratings[i] > ratings[i-1]){
				left[i] = left[i-1]+1;
			}
		}
		int res = left[ratings.length-1];
		for (int j = ratings.length-2;j>=0;j--){
			if (ratings[j] > ratings[j+1]){
				right[j] = right[j+1]+1;
			}
			res+=Math.max(left[j],right[j]);
		}
		return res;
	}
	public int firstUniqChar(String s) {
		if (s.length()==0){
			return -1;
		}
		int[] chars = new int[26];
		for (int i=0;i<s.length();i++){
			char ch = s.charAt(i);
			chars[ch-'a']++;
		}
		int min = Integer.MAX_VALUE;
		for (int i=0;i<chars.length;i++){
			if(chars[i]==1){
				for (int j = 0;j<s.length();j++){
					if ((char)(i+'a') == s.charAt(j)){
						if (j<min){
							min =j;
						}
					}
				}
			}
		}
		if (min == Integer.MAX_VALUE){
			return -1;
		}
		return min;
	}
	public List<List<Integer>> levelOrder(TreeNode root) {
		List<List<Integer>> res = new ArrayList<>();
		if (root == null){
			return res;
		}
		Deque<TreeNode> deque = new LinkedList<>();
		deque.offer(root);
		boolean isEven = true;
		while (!deque.isEmpty()){
			Deque<Integer> level = new LinkedList<>();
			int curLevel = deque.size();
			for (int i=1;i<=curLevel;i++){
				TreeNode node = deque.poll();
				if (isEven){
					level.addLast(node.val);
				}else {
					level.addFirst(node.val);
				}
				if (node.left!=null){
					deque.offer(node.left);
				}
				if (node.right!=null){
					deque.offer(node.right);
				}
			}
			isEven = !isEven;
			res.add(new LinkedList<>(level));
		}
		return res;
	}

	public String reformatNumber(String number) {
		number = number.replace(" ","").replace("-","");
		StringBuilder sb = new StringBuilder();
		int len = number.length();
		int cnt = 0;
		if (len%3==0 || (len%3==2)){
			for (int j=0;j<len;j++){
				sb.append(number.charAt(j));
				cnt++;
				if (cnt==3 && j!=len-1){
					sb.append("-");
					cnt=0;
				}
			}
		}else {
			for (int j=0;j<len;j++){
				sb.append(number.charAt(j));
				cnt++;
				if (cnt == 3){
					sb.append("-");
					cnt=0;
				}
				if (len-1-j == 2 && cnt == 2){
					sb.append("-");
					cnt=0;
				}
			}
		}
		return sb.toString();
	}
	public int minCostClimbingStairs(int[] cost) {
		int[] dp = new int[cost.length+1];
		dp[0] = cost[0];
		dp[1] = cost[1];
		for (int i=2;i<cost.length;i++){
			dp[i] = Math.min(dp[i-1]+cost[i],dp[i-2]+cost[i]);
		}
		dp[cost.length] = Math.min(dp[cost.length-1],dp[cost.length-2]);
		return dp[cost.length];
	}
	public TreeNode invertTree(TreeNode root) {
		if (root==null){
			return null;
		}
		TreeNode left = invertTree(root.left);
		TreeNode right = invertTree(root.right);
		root.left = left;
		root.right = right;
		return root;
	}
	public char findTheDifference(String s, String t) {
		int[] charCount = new int[26];
		for (int i=0;i<s.length();i++){
			char ch = s.charAt(i);
			charCount[ch-'a']++;
		}
		for (int j=0;j<t.length();j++){
			char ch = t.charAt(j);
			charCount[ch-'a']--;
			if (charCount[ch-'a']<0){
				return ch;
			}
		}
		return ' ';
	}
	public int maxProfit(int[] prices, int fee) {
		int[][] dp = new int[prices.length][2];
		dp[0][0] = 0;
		dp[0][1] = -prices[0];
		for (int i=1;i<prices.length;i++){
			dp[i][0] = Math.max(dp[i-1][0],dp[i-1][1]+prices[i]-fee);
			dp[i][1] = Math.max(dp[i-1][0]-prices[i],dp[i-1][1]);
		}
		return dp[prices.length-1][0];
	}
	public int maxProfit(int[] prices) {
		int res=0;
		for (int i=1;i<prices.length;i++){
			res+=Math.max(0,prices[i]-prices[i-1]);
		}
		return res;
	}
	public boolean wordPattern(String pattern, String s) {
		Map<Character, String> map = new HashMap<>();
		String[] str = s.split(" ");
		if (pattern.length()!=str.length){
			return false;
		}
		for (int i=0;i<pattern.length();i++){
			char ch = pattern.charAt(i);
			if (map.containsKey(ch)){
				if (!map.get(ch).equals(str[i])){
					return false;
				}
			}else{
				if (map.containsValue(str[i])){
					return false;
				}
				map.put(ch,str[i]);
			}
		}
		return true;
	}
	public int monotoneIncreasingDigits(int N) {
		char[] strN = Integer.toString(N).toCharArray();
		for (int i=0;i<strN.length-1;i++){
			if (strN[i]>strN[i+1]){
				strN[i] = (char)((strN[i]-'0')-1+'0');
				for (int j=i+1;j<strN.length;j++){
					strN[j] = '9';
				}
				i=-1;
			}
		}
		return Integer.valueOf(String.valueOf(strN));
	}
	public int minPartitions(String n) {
		int max = 0;
		for (int i=0;i<n.length();i++){
			if (Integer.parseInt(String.valueOf(n.charAt(i))) > max){
				max = Integer.parseInt(String.valueOf(n.charAt(i)));
			}
		}
		return max;
	}
	public int numberOfMatches(int n) {
		int res=0;
		while (n>0){
			res+=n/2;
			if (n%2==0){
				n=n/2;
			}else{
				n=n/2+1;
			}
		}
		return res;
	}

	public String predictPartyVictory(String senate) {
		Deque<Integer> Radiant = new LinkedList<>();
		Deque<Integer> Dire = new LinkedList<>();
		for (int i=0;i<senate.length();i++){
			if (senate.charAt(i)=='R'){
				Radiant.offer(i);
			}else{
				Dire.offer(i);
			}
		}
		while (!Radiant.isEmpty() && !Dire.isEmpty()){
			Integer R = Radiant.poll();
			Integer D = Dire.poll();
			if (R<D){
				Radiant.offer(R+senate.length());
			}else{
				Dire.offer(D+senate.length());
			}
		}
		return Radiant.isEmpty() ? "Dire" : "Radiant";
	}


	public boolean lemonadeChange(int[] bills) {
		int five = 0,ten=0;
		if (bills[0]!=5){
			return false;
		}
		for (int bill : bills) {
			if (bill==5){
				five++;
			}else if (bill==10){
				if (five==0){
					return false;
				}
				five--;
				ten++;
			}else{
				if (ten>0&&five>0){
					ten--;
					five--;
				}else if (five>3){
					five-=3;
				}else{
					return false;
				}
			}
		}
		return true;
	}

	public int uniquePaths(int m, int n) {
		int[][] dp = new int[n][m];
		for (int i=0;i<m;i++){
			dp[0][i]=1;
		}
		for (int j=0;j<n;j++){
			dp[j][0]=1;
		}
		for (int i=1;i<m;i++){
			for (int j=1;j<n;j++){
				dp[i][j] = dp[i-1][j]+dp[i][j-1];
			}
		}
		return dp[m-1][n-1];
	}

	public int hammingDistance(int x, int y) {
		int xor = x^y;
		int distance = 0;
		while(xor!=0){
			distance+=1;
			xor = xor&(xor-1);
		}
		return distance;

	}
	public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
		if (t1==null){
			return t2;
		}
		if (t2==null){
			return t1;
		}
		TreeNode res = new TreeNode(t1.val+t2.val);
		res.left = mergeTrees(t1.left,t2.left);
		res.right = mergeTrees(t1.right,t2.right);
		return res;
	}
	public List<List<Integer>> subsets(int[] nums) {
		List<List<Integer>> list = new ArrayList<>(1 << nums.length);
		list.add(new ArrayList<>());
		for (int num : nums){
			int len = list.size();
			for (int j=0;j<len;j++){
				List<Integer> temp = new ArrayList<>(list.get(j));
				temp.add(num);
				list.add(temp);
			}
		}
		return list;
	}
	public int matrixScore(int[][] A) {
		for (int i=0;i<A.length;i++){
			if (A[i][0]==0){
				for (int j=0;j<A[i].length;j++){
					if (A[i][j]==0){
						A[i][j]=1;
					}else{
						A[i][j]=0;
					}
				}
			}
		}
		for (int i=1;i<A[0].length;i++){
			int count = 0;
			for (int j=0;j<A.length;j++){
				if (A[j][i]==0){
					count++;
				}
			}
			if (count > A.length/2){
				for (int j=0;j<A.length;j++){
					if (A[j][i]==0){
						A[j][i]=1;
					}else{
						A[j][i]=0;
					}
				}
			}
		}
		int res=0;
		for (int i=0;i<A.length;i++){
			for (int j=0;j<A[0].length;j++){
				int temp = A[i][j]*((int)Math.pow(2, A[0].length-1-j));
				res+=temp;
			}
		}
		return res;
	}

	public  boolean isPossible(int[] nums) {
		Map<Integer, PriorityQueue<Integer>> map = new HashMap<>();
		for (int num : nums) {
			if (!map.containsKey(num)){
				map.put(num,new PriorityQueue<>());
			}
			if (map.containsKey(num-1)){
				int preLength = map.get(num-1).poll();
				if (map.get(num-1).isEmpty()){
					map.remove(num-1);
				}
				map.get(num).offer(preLength+1);
			}else {
				map.get(num).offer(1);
			}
		}
		Set<Map.Entry<Integer,PriorityQueue<Integer>>> entrySet = map.entrySet();
		for (Map.Entry<Integer,PriorityQueue<Integer>> entry : entrySet){
			PriorityQueue<Integer> queue = entry.getValue();
			if (queue.peek() < 3){
				return false;
			}
		}
		return true;
	}

	public int countPrimes(int n) {
		int count = 0;
		for (int i=2;i<n;i++){
			if (isPrime(i)){
				count+=1;
			}
		}
		return count;
	}
	public Boolean isPrime(int n){
		if (n==0||n==1){
			return false;
		}
		if (n==2||n==3){
			return true;
		}else {
			for (int i=2;i<=(int)Math.sqrt(n);i++){
				if (n%i==0){
					return false;
				}
			}
			return true;
		}

	}

	//12-02 321
	public int[] maxNumber(int[] nums1, int[] nums2, int k) {
		int[] ans = new int[k];
		if (nums1.length+nums2.length < k){
			return ans;
		}
		for (int i = Math.max(k-nums2.length,0);i<=Math.min(nums1.length,k);i++){
			List<Integer> l1 = getLargestList(nums1,i);
			List<Integer> l2 = getLargestList(nums2,k-i);
			int[] merge = mergeList(l1,l2);
			if (isBigger(merge,ans)){
				ans = merge;
			}
		}
		return ans;
	}

	private boolean isBigger(int[] merge, int[] ans) {
		if (ans.length==0){
			return true;
		}
		for (int i=0;i<merge.length;i++){
			if (merge[i]>ans[i]){
				return true;
			}else if (merge[i]<ans[i]){
				return false;
			}
		}
		return false;
	}

	private int[] mergeList(List<Integer> l1, List<Integer> l2) {
		int[] res = new int[l1.size()+l2.size()];
		int l=0,r=0,idx=0;
		StringBuilder sb1 = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		for (int i=l;i<l1.size();i++){
			sb1.append(l1.get(i));
		}
		for (int i=r;i<l2.size();i++){
			sb2.append(l2.get(i));
		}
		String a = sb1.toString();
		String b = sb2.toString();
		while (l < l1.size() || r < l2.size()){
			if (l >= l1.size()){
				res[idx++] = l2.get(r++);
			}else if (r >= l2.size()){
				res[idx++] = l1.get(l++);
			}else if (a.substring(l,l1.size()).compareTo(b.substring(r,l2.size()))>=0){
				res[idx++] = l1.get(l++);
			}else{
				res[idx++] = l2.get(r++);
			}
		}
		return res;
	}

	private List<Integer> getLargestList(int[] nums, int k) {
		List<Integer> list = new ArrayList<>();
		int len = nums.length;
		int popCount = len-k;
		if (k==0){
			return list;
		}
		for (int i=0;i<len;i++){
			while(!list.isEmpty()&&list.get(list.size()-1)< nums[i]&&popCount>0){
				list.remove(list.size()-1);
				popCount--;
			}
			if (list.size()<k){
				list.add(nums[i]);
			}else{
				popCount--;
			}
		}
		return list;
	}

	//1201 34
	public int[] searchRange(int[] nums, int target) {
		int len = nums.length;
		if (len<1){
			return new int[]{-1, -1};
		}
		int[] res = new int[2];
		if(target < nums[0] || target>nums[len-1]){
			res[0]=-1;
			res[1]=-1;
		}
		Map<Integer,Integer> map = new HashMap<>();
		for (int i=0;i<len;i++){
			if (!map.containsKey(nums[i])){
				map.put(nums[i],i);
			}
		}
		if (map.containsKey(target)){
			res[0] = map.get(target);
			int start = map.get(target);
			while (start<len){
				if (target==nums[start]){
					start ++;
				}else{
					break;
				}
			}
			res[1] = start-1;

		}else {
			res[0]=-1;
			res[1]=-1;
		}
		return res;
	}
	//11-30 767
	public String reorganizeString(String S) {
		if (S.length() < 2) {
			return S;
		}
		int[] counts = new int[26];
		int maxCount = 0;
		for(int i=0;i<S.length();i++){
			char c = S.charAt(i);
			counts[c-'a']++;
			maxCount = Math.max(maxCount,counts[c-'a']);
		}
		if(maxCount>(S.length()+1)/2){
			return "";
		}
		char[] resChar = new char[S.length()];
		int oddIndex = 1;
		int evenIndex = 0;
		int halfLength = S.length()/2;
		for (int i=0;i<counts.length;i++){
			char c = (char) ('a'+i);
			while(counts[i]>0&&oddIndex<S.length()&&counts[i]<=halfLength){
				resChar[oddIndex] = c;
				oddIndex+=2;
				counts[i]--;
			}
			while (counts[i]>0){
				resChar[evenIndex] = c;
				evenIndex+=2;
				counts[i]--;
			}
		}
		return new String(resChar);
	}
	public List<List<Integer>> groupThePeople(int[] groupSizes) {
		Map<Integer, List<Integer>> integerListMap = new HashMap<>();
		List<List<Integer>> res = new ArrayList<>();
		for (int i=0;i<groupSizes.length;i++){
			if (!integerListMap.containsKey(groupSizes[i])){
				integerListMap.put(groupSizes[i],new ArrayList<>());
			}
			List<Integer> sub = integerListMap.get(groupSizes[i]);
			sub.add(i);
			integerListMap.put(groupSizes[i],sub);
			if (sub.size()==groupSizes[i]){
				res.add(new ArrayList<>(sub));
				sub.clear();
			}
		}
		return res;
	}

	//1127--454
	public int fourSumCount(int[] A, int[] B, int[] C, int[] D) {
		Map<Integer, Integer> map = new HashMap<>();
		int count =0;
		for (int i=0;i<A.length;i++){
			for (int j=0;j<A.length;j++){
				int sum = A[i]+B[j];
				if (map.containsKey(sum)){
					map.put(sum,map.get(sum)+1);
				}else{
					map.put(sum,1);
				}
			}
		}

		for (int k=0;k<A.length;k++){
			for (int t=0;t<A.length;t++){
				int tmp = -(C[k]+D[t]);
				if (map.containsKey(tmp)){
					count+=map.get(tmp);
				}
			}
		}
		return count;
	}
	//1126--164
	public int maximumGap(int[] nums) {
		if (nums.length<2){
			return 0;
		}
		Arrays.sort(nums);
		int maxGap = 0;
		for (int i=0;i<nums.length-1;i++){
			if(nums[i+1]-nums[i]>=maxGap){
				maxGap=nums[i+1]-nums[i];
			}
		}
		return maxGap;
	}
	//1637
	public int maxWidthOfVerticalArea(int[][] points) {
		Arrays.sort(points, new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				return o1[0]-o2[0];
			}
		});
		int maxDis = 0;
		for (int i=0;i<points.length-1;i++){
			if(points[i+1][0]-points[i][0]>maxDis){
				maxDis = points[i+1][0]-points[i][0];
			}
		}
		return maxDis;
	}

	public int countNodes(TreeNode root) {
		if(root==null){
			return 0;
		}
		return countNodes(root.left)+countNodes(root.right)+1;
	}
	public class ListNode {
       	int val;
       	ListNode next;
       	ListNode(int x) { val = x; }
   	}

	public boolean arrayStringsAreEqual(String[] word1, String[] word2) {
		StringBuilder sb1 = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		for (int i=0;i<word1.length;i++){
			sb1.append(word1[i]);
		}
		for (int i =0;i<word2.length;i++){
			sb2.append(word2[i]);
		}
		if (sb1.toString().equals(sb2.toString())){
			return true;
		}
		return false;
	}
	public int waysToMakeFair(int[] nums) {
		int[][] dp = new int[nums.length][2];
		for (int i=0;i<nums.length;i++){
			int[] array = removeByIndex(nums,i);
			dp[i][0]=0;
			dp[i][1]=0;
			for (int j=0;j<array.length;j++){
				if (j%2==0){
					dp[i][0]+=array[j];
				}else{
					dp[i][1]+=array[j];
				}
			}
		}
		int count=0;
		for (int i=0;i<dp.length;i++){
			if (dp[i][0]==dp[i][1]){
				count++;
			}
		}
		return count;
	}
	public int[] removeByIndex(int[] array, int position){
		int[] newArray = new int[array.length-1];
		for (int i = 0; i < array.length; i++) {
			if (i < position) {
				newArray[i] = array[i];
			} else if (i > position) {
				newArray[i - 1] = array[i];
			}
		}
		return newArray;
	}
	public int findMinArrowShots(int[][] points) {
		if (points.length==0){
			return 0;
		}
		Arrays.sort(points, new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				if (o1[1]>o2[1]){
					return 1;
				}else if (o1[1]<o2[1]){
					return -1;
				}else{
					return 0;
				}
			}
		});
		int curEnd = points[0][1];
		int count = 1;
		for (int i=0;i<points.length;i++){
			if (points[i][0]>curEnd){
				count++;
				curEnd = points[i][1];
			}
		}
		return count;
	}

	public String getSmallestString(int n, int k) {
		char[] chars = new char[n];
		int sum = n * 26;
		for (int i = 0; i < n; i++) {
			if (sum - k > 25) {
				chars[i] = 'a';
				sum -= 26;
				k -= 1;
			} else if (sum == k) {
				chars[i] = 'z';
				sum -= 26;
				k -= 26;
			} else {
				chars[i] = (char)('z' - sum + k);
				k -= 26 - (sum - k);
				sum -= 26;
			}
		}
		return String.valueOf(chars);
	}
	public void moveZeroes(int[] nums) {
		int left = 0,right=0;
		while(right<nums.length){
			if (nums[right]!=0){
				int temp = nums[right];
				nums[right] = nums[left];
				nums[left] = temp;
				left++;
			}
			right++;
		}
	}
	public boolean closeStrings(String word1, String word2) {
		if (word1.length()!=word2.length()){
			return false;
		}
		Map<Character,Integer> mapA = new HashMap<>();
		Map<Character,Integer> mapB = new HashMap<>();
		for (int i=0;i<word1.length();i++){
			if (mapA.get(word1.charAt(i))!=null){
				mapA.put(word1.charAt(i),mapA.get(word1.charAt(i))+1);
			}else{
				mapA.put(word1.charAt(i),1);
			}
			if (mapB.get(word2.charAt(i))!=null){
				mapB.put(word2.charAt(i),mapB.get(word2.charAt(i))+1);
			}else{
				mapB.put(word2.charAt(i),1);
			}
		}
		if (mapA.size()!=mapB.size()){
			return false;
		}
		Iterator<Character> iter1 = mapA.keySet().iterator();
		while (iter1.hasNext()) {
			Character aKey = (Character) iter1.next();
			if (!mapB.containsKey(aKey)) {
				return false;
			}
		}
		List<Integer> a = new ArrayList<>();
		List<Integer> b = new ArrayList<>();
		int i=0,j=0;
		for (Integer value : mapA.values()){
			a.add(value);
			i++;
		}
		for (Integer value : mapA.values()){
			b.add(value);
			j++;
		}
		Collections.sort(a);
		Collections.sort(b);
		for( i=0;i<a.size();i++){
			if(!a.get(i).equals(b.get(i)))
				return false;
		}
		return true;
	}
	public int minimumDeletions(String s) {
		if (s.length()<2){
			return 0;
		}
		List<Integer> listA = new ArrayList<>();
		List<Integer> listB = new ArrayList<>();
		for (int i=0;i<s.length();i++){
			if (s.charAt(i)=='a'){
				listA.add(i);
			}
			if (s.charAt(i)=='b'){
				listB.add(i);
			}
		}
		if (listA.size()==0 || listB.size()==0){
			return 0;
		}
		int tempA = 0,tempB=0;
		for (int i=listA.size()-1;i>0;i--){
			if (listA.get(i)>listB.get(0)){
				tempA++;
			}
		}
		for (int i=0;i<listB.size();i++){
			if (listB.get(i)<listA.get(listA.size()-1)){
				tempB++;
			}
		}
		return Math.min(tempA,tempB);
	}
	List<Integer> result = new ArrayList<>();
	public int minimumJumps(int[] forbidden, int a, int b, int x) {
		List<Integer> list = new ArrayList<>();
		for (int i=0;i<forbidden.length;i++){
			list.add(forbidden[i]);
		}
		jump(list,a,b,x,0);
		if (result.size()==0){
			return -1;
		}else{
			Collections.sort(result);
			return result.get(0);
		}
	}


	public void jump(List<Integer> list,int a, int b, int x,int count){
		if (x<0){
			return;
		}
		if (x > 6000)
			return;
		if (x==0){
			result.add(count);
			return;
		}
		if (!list.contains(x-a)){
			jump(list,a,b,x-a,count+1);
		}
		if (!list.contains(x+b)){
			jump(list,a,b,x+b,count+1);
		}
	}

	public int[] decrypt(int[] code, int k) {
		int len = code.length;
		int[] result = new int[len];
		int[] code2 = new int[len*2];
		for (int i=0;i<len;i++){
			code2[i] = code[i];
			code2[i+len] = code[i];
		}
		if (k==0){
			for (int i=0;i<len;i++){
				result[i] = 0;
			}
		}
		if (k>0){
			for (int i=0;i<len;i++){
				int sum = 0;
				for (int j=i+1;j<i+k+1;j++){
					sum+=code2[j];
				}
				result[i] = sum;
			}
		}
		if (k<0){
			for (int i=len;i<len*2;i++){
				int sum =0;
				for (int j=i+k;j<i;j++){
					sum+=code2[j];
				}
				result[i-len] = sum;
			}
		}
		return result;
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

}
