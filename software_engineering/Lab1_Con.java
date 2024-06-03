package software_engineering;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.File;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

/** 读取原始文件，并将其转化为标准文件data.txt */
/** 주석 추가 test */
class ReadFile {
	public void transformFile(String string) {
		InputStream is = null;
		OutputStream os = null;
		try {
			is = new FileInputStream(string);
			os = new FileOutputStream("data.txt");
			int size = is.available();
			char str;
			boolean flag = true;
			for (int i = 0; i < size; i++) {
				str = (char) is.read();
				if (str >= 'a' && str <= 'z') {
					os.write(str);
					flag = true;
				} else if (str >= 'A' && str <= 'Z') {
					os.write((char) (str + 32));
					flag = true;
				} else {
					if (flag && str == ' ') {
						os.write(str);
						flag = false;
					} else {
						if (flag) {
							os.write(' ');
							flag = false;
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (os != null) {
					os.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

// 图的边结点类
class Edge {
	/** 邻接顶点序号 */
	protected int verAdj;
	/** 邻接顶点名称 */
	protected String Name;
	/** 边的权值 */
	protected int cost;
	/** 下一个边结点 */
	protected Edge link;
}

// 顶点表中的结点类
class Vertex {
	/** 顶点序号 */
	protected int verName;
	/** 顶点名称 **/
	protected String Name;
	/** 边链表的头指针 */
	protected Edge adjacent;
}

/** 以邻接表的形式存储图结构 */
class Graph {
	/** 指向顶点表的引用 */
	public Vertex[] head;
	/* 当前顶点的个数 */
	private int vertexNum;
	/* 文件路径 */
	private String str;

	public Graph(String string) {
		this.str = string;
	}

	public int buildGraph() {
		InputStream is = null;
		try {
			is = new FileInputStream(this.str);
			int size = is.available();
			this.vertexNum = size;
			this.head = new Vertex[this.vertexNum];
			for (int i = 0; i < this.vertexNum; i++) {
				head[i] = new Vertex();
				head[i].verName = i;
				head[i].adjacent = null;
				head[i].Name = "!";
			}
			int charnum = this.vertexNum;
			// System.out.println(i);
			StringBuffer string1 = new StringBuffer();
			char str1 = (char) is.read();
			while (str1 == ' ') {
				str1 = (char) is.read();
				charnum -= 1;
			}
			charnum -= 1;
			while (str1 != ' ') {
				string1.append(str1);
				str1 = (char) is.read();
				charnum -= 1;
			}
			int num = 0;
			head[num++].Name = string1.toString();
			while (charnum > 0) {
				StringBuffer string2 = new StringBuffer();
				str1 = (char) is.read();
				charnum -= 1;
				while (str1 != ' ' && charnum >= 0) {
					string2.append(str1);
					str1 = (char) is.read();
					charnum -= 1;
				}
				for (int j = 0; j < this.vertexNum; j++) {
					if (head[j].Name.equals(string1.toString())) {
						Edge q = head[j].adjacent;
						Edge qr = null;
						while (q != null) {
							if (q.Name.equals(string2.toString())) {
								q.cost += 1;
								break;
							}
							qr = q;
							q = q.link;
						}
						if (q == null) {
							Edge p = new Edge();
							// p.verAdj = num;
							p.Name = string2.toString();
							p.cost = 1;
							p.link = null;
							if (qr == null) {
								head[j].adjacent = p;
							} else {
								p.link = head[j].adjacent;
								head[j].adjacent = p;
							}
							int n = findVertex(string2.toString());
							if (n == -1) {
								p.verAdj = num;
								head[num++].Name = string2.toString();
							} else
								p.verAdj = n;
						}
						break;
					}
				}
				string1 = string2;
			}
			// System.out.println("finished!!!");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		int a = 0;
		for (int i = 0; i < vertexNum && !head[i].Name.equals("!"); i++) {
			a += 1;
		}
		this.vertexNum = a;
		// System.out.println(a);
		return this.vertexNum;
	}

	/** 展示有向图유향도 보이기 */
	public void showDirectedGraph() {
		GraphViz gv = new GraphViz();
		gv.addln(gv.start_graph());
		for (int i = 0; i < this.vertexNum; i++) {
			Edge p = this.head[i].adjacent;
			while (p != null) {
				gv.addln(this.head[i].Name + "->" + p.Name + "[label=" + p.cost + "];");
				p = p.link;
			}
		}
		gv.addln(gv.end_graph());
		// System.out.println(gv.getDotSource());

		String type = "gif";
		File out = new File(Lab1_Con.pathtext + "/out." + type);
		gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), type), out);
	}

	public int findVertex(String str) {
		for (int i = 0; i < this.vertexNum; i++) {
			if (head[i].Name.equals(str)) {
				return i;
			}
			if (head[i].Name.equals("!")) {
				break;
			}
		}
		return -1;
	}

	public void printGraph() {
		for (int i = 0; i < this.vertexNum; i++) {
			if (!head[i].Name.equals("!")) {
				System.out.print(head[i].Name + ":");
				Edge p = head[i].adjacent;
				while (p != null) {
					System.out.print(p.Name + "(" + p.cost + ")" + " ");
					p = p.link;
				}
				System.out.print("\n");
			} else {
				break;
			}
		}
	}
}

class BFS {
	/** 指向顶点表的引用꼭짓점 테이블을 가리키는 참조 */
	private Vertex[] head;
	/* 当前顶点的个数현재 정점의 개수 */
	private int vertexNum;

	BFS(Vertex[] H, int num) {
		this.head = H;
		this.vertexNum = num;
	}

	// 得到第一个邻接结点的下标첫 번째 인접 노드의 첨자를 가져옵니다
	int getFirstNeighbor(int index) {
		if (head[index].adjacent != null) {
			return head[index].adjacent.verAdj;
		}
		return -1;
	}

	// 私有函数，广度优先遍历
	private String[] broadFirstSearch(boolean[] isVisited, int i, String destination) {
		int u;
		int flag = 0;
		String[] str;
		LinkedList<Integer> queue = new LinkedList<Integer>();
		// 访问结点i
		// System.out.print(getValueByIndex(i)+" ");
		isVisited[i] = true;
		// 结点入队列
		// queue.addLast(i);
		// w = getFirstNeighbor(i);
		Edge p = null;
		p = head[i].adjacent;
		if (p == null) {
			// System.out.println("No bridge words from word1 to word2!");
			str = new String[1];
			str[0] = "0";
			return str;
		} else {
			while (p != null) {
				queue.addLast(p.verAdj);
				isVisited[p.verAdj] = true;
				p = p.link;
			}
		}
		str = new String[this.vertexNum];
		int a = 0;
		while (!queue.isEmpty()) {
			u = ((Integer) queue.removeFirst()).intValue();
			p = head[u].adjacent;
			while (p != null) {
				if (!isVisited[p.verAdj]) {
					if (p.Name.equals(destination)) {
						// System.out.println("The bridge words from word1 to
						// word2 are:");
						str[a++] = head[u].Name;
						// System.out.print(head[u].Name+" ");
						flag = 1;
						break;
					}
					isVisited[p.verAdj] = true;
				}
				p = p.link;
			}
		}
		if (flag == 0) {
			// str = new String[1];
			str[0] = "0";
		}
		return str;
	}

	// 对外公开函数，广度优先遍历
	String[] broadFirstSearch(String word1, String word2) {
		boolean[] isVisited = new boolean[this.vertexNum];
		String[] str;
		for (int i = 0; i < this.vertexNum; i++)
			isVisited[i] = false;
		for (int i = 0; i < this.vertexNum; i++) {
			if (head[i].Name.equals(word2))
				break;
			else if (head[i].Name.equals("!")) {
				// System.out.println("No word1 or word2 in the graph!");
				str = new String[1];
				str[0] = "-1";
				return str;
			}
		}
		for (int i = 0; i < this.vertexNum; i++) {
			if (!isVisited[i] && head[i].Name.equals(word1)) {
				str = broadFirstSearch(isVisited, i, word2);
				return str;
			} else if (head[i].Name.equals("!")) {
				// System.out.println("No word1 or word2 in the graph!");
				break;
			}
		}
		str = new String[1];
		str[0] = "-1";
		return str;
	}
}

class Node {
	int id;
	int d;
}

class Dij {
	private static int INF = 9999;
	ArrayList<Integer>[] prev;
	int size;
	Node[] dist;
	PriorityQueue<Node> queue = new PriorityQueue<Node>();
	boolean[] flag;

	@SuppressWarnings("unchecked") // ***
	public Dij(int vertexNum) {
		size = vertexNum;
		prev = new ArrayList[size];
		flag = new boolean[size];
		dist = new Node[size];
		// System.out.println(dist.length);
		for (int i = 0; i < size; i++) {
			prev[i] = new ArrayList<Integer>();
			prev[i].add(-1);
			flag[i] = false;
			dist[i] = new Node();
			dist[i].id = i;
			dist[i].d = INF;
		}
		queue = new PriorityQueue<Node>(size, new Comparator<Node>() {
			public int compare(Node n1, Node n2) {
				return n1.d - n2.d;
			}
		});
	}

	public void dijkstra(int s, Vertex[] head) {
		// System.out.println(G.head.length);
		dist[s].d = 0;
		queue.add(dist[s]);
		while (queue.peek() != null) {
			Node temp = queue.poll();
			Edge p = head[temp.id].adjacent;
			int u = temp.id;

			if (flag[u])
				continue;
			flag[u] = true;

			while (p != null) {
				int tempid = p.verAdj;
				int tempcost = p.cost;
				if (!flag[tempid]) {
					if (dist[tempid].d > dist[u].d + tempcost) {
						dist[tempid].d = dist[u].d + tempcost;
						prev[tempid].clear();
						prev[tempid].add(u);
						queue.add(dist[tempid]);
					} else if (dist[tempid].d == dist[u].d + tempcost) {
						prev[tempid].add(u);
					}
				}
				p = p.link;
			}
		}
	}

	public ArrayList<ArrayList<Integer>> findpath(int beg, int end) {
		ArrayList<ArrayList<Integer>> childPaths = null;
		ArrayList<ArrayList<Integer>> midPaths = new ArrayList<>();
		if (beg != end) {
			for (int i = 0; i < prev[end].size(); i++) {
				childPaths = findpath(beg, prev[end].get(i));
				for (int j = 0; j < childPaths.size(); j++) {
					childPaths.get(j).add(end);
				}
				if (midPaths.size() == 0) {
					midPaths = childPaths;
				} else {
					midPaths.addAll(childPaths);
				}
			}
		} else {
			ArrayList<Integer> temp = new ArrayList<>(1);
			temp.add(beg);
			midPaths.add(temp);
		}
		return midPaths;
	}
}

public class Lab1_Con {
	public static final String pathtext = System.getProperty("user.dir");
	/** 指向顶点表的引用 */
	public static Vertex[] head;
	/* 当前顶点的个数 */
	public static int vertexNum;
	private static String[] str;
	private static boolean flag = false;
	public static ArrayList<ArrayList<Integer>> waypoint;

	public static void main(String args[]) {
		Login loginframe = new Login();
		loginframe.run();
		// loginframe.setVisible(true);
		// System.out.println(System.getProperty("user.dir"));
	}

	/** 查询桥接词 */
	public static String queryBridgeWords(String word1, String word2) {
		StringBuffer str_ = new StringBuffer();
		word1 = word1.toLowerCase();
		word2 = word2.toLowerCase();
		BFS bfsSearch = new BFS(head, vertexNum);
		str = bfsSearch.broadFirstSearch(word1, word2);
		if (str[0].equals("-1") && !flag)
			str_.append("-1");
		else if (str[0].equals("0") && !flag)
			str_.append("0");
		else if (!flag) {
			for (int i = 0; i < str.length && str[i] != null; i++) {
				str_.append(str[i] + " ");
			}
		}
		return str_.toString();
	}

	/** 根据bridge word 生成新文本 */
	public static String generateNewText(String inputText) {
		String[] str1 = inputText.split(" ");
		String[] str2 = new String[2 * str1.length];
		int j = 0;
		flag = true;
		int i;
		for (i = 0; i < str1.length - 1; i++) {
			queryBridgeWords(str1[i].toLowerCase(), str1[i + 1].toLowerCase());
			// Handle cases when queryBridgeWords returns -1 or 0
			if (str[0].equals("0") || str[0].equals("-1")) {
				str2[j++] = str1[i];
			} else {
				str2[j++] = str1[i];
				str2[j++] = str[0];
			}
		}
		flag = false;
		str2[j] = str1[i];
		StringBuffer string = new StringBuffer();
		for (i = 0; i < str2.length && str2[i] != null; i++) {
			string.append(str2[i]);
			string.append(" ");
		}
		return string.toString();
	}

	/** 计算两个单词之间的最短路径 */
	public static String calcShortestPath(String word1, String word2) {
		StringBuffer ans = new StringBuffer();
		int w1 = -1;
		int w2 = -1;
		word1 = word1.toLowerCase();
		word2 = word2.toLowerCase();
		for (int i = 0; i < vertexNum; i++) {
			if (word1.equals(head[i].Name))
				w1 = i;
			if (word2.equals(head[i].Name))
				w2 = i;
		}
		if (w1 == -1 || w2 == -1)
			return "文本中不存在输入的某个词";
		Dij D = new Dij(vertexNum);
		D.dijkstra(w1, head);
		int INF = 9999;
		if (D.dist[w2].d == INF) {
			ans.append("不可达");
			return ans.toString();
		}
		waypoint = new ArrayList<ArrayList<Integer>>();
		waypoint = D.findpath(w1, w2);
		for (int i = 0; i < waypoint.size(); i++) {
			// ans += "[";
			for (int j = 0; j < waypoint.get(i).size() - 1; j++) {
				ans.append(head[waypoint.get(i).get(j)].Name);
				ans.append("->");
			}
			ans.append(head[waypoint.get(i).get(waypoint.get(i).size() - 1)].Name);
			ans.append("  " + "(length: " + D.dist[w2].d + ")\n");
		}
		return ans.toString();
	}

	/** 计算一个单词到其他之间的最短路径 */
	public static String calcShortestPath_Oneword(String word) {
		int wd = -1;
		StringBuffer ans = new StringBuffer();
		word = word.toLowerCase();
		for (int i = 0; i < vertexNum; i++) {
			if (word.equals(head[i].Name))
				wd = i;
		}
		if (wd == -1) {
			return "文本中不存在输入的词";
		}
		Dij D = new Dij(vertexNum);
		D.dijkstra(wd, head);
		for (int i = 0; i < vertexNum; i++) {
			if (i == wd) {
				continue;
			}
			ans.append(word + "==>" + head[i].Name + ":\n");
			ans.append(calcShortestPath(word, head[i].Name));
			ans.append("\n");
		}
		return ans.toString();
	}

	/** 随机游走 */
	public static String randomWalk() {
		Random rand = new Random();
		int r = rand.nextInt(vertexNum);
		StringBuffer string = new StringBuffer();
		int[][] isVisited = new int[vertexNum][vertexNum];
		for (int i = 0; i < vertexNum; i++) {
			for (int j = 0; j < vertexNum; j++) {
				isVisited[i][j] = 0;
			}
		}
		try {
			string.append(head[r].Name);
			string.append(" ");
			Edge p = head[r].adjacent;
			while (p != null) { /** 随机性欠考虑 */
				// System.out.print(head[p.verAdj].Name+" ");
				string.append(head[p.verAdj].Name);
				string.append(" ");
				if (isVisited[r][p.verAdj] == 0) {
					isVisited[r][p.verAdj] = 1;
					r = p.verAdj;
					p = head[p.verAdj].adjacent;
				} else {
					break;
				}
			}
		} catch (Exception e) {
			string.append("0");
		}
		return string.toString();
	}
}