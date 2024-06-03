package software_engineering;

import java.io.IOException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.OutputStreamWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

class FirstPage { // 首页
	public static String showmessage() {
		String str = "";
		String string = "";
		try {
			File file = new File("page.txt");
			FileReader reader = new FileReader(file);
			BufferedReader br = new BufferedReader(reader);

			while ((str = br.readLine()) != null) {
				string += str;
				string += "\n";
			}
			br.close();
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return string;
	}
}

class MenuBar extends JFrame implements ActionListener { // 菜单栏
	private static final long serialVersionUID = 1L;
	JMenuBar menuBar = new JMenuBar();
	JMenu file = new JMenu("Options(F)");
	JMenuItem open1 = new JMenuItem("Functional requirements 1");
	JMenuItem open2 = new JMenuItem("Functional requirements2");
	JMenuItem open3 = new JMenuItem("Functional requirements3");
	JMenuItem open4 = new JMenuItem("Functional requirements4");
	JMenuItem open5 = new JMenuItem("Functional requirements5");
	JMenuItem open6 = new JMenuItem("Functional requirements6");
	JMenuItem quit = new JMenuItem("BYE BYE");
	static ShowDirectedGraph showgraph = new ShowDirectedGraph();
	static QueryBridgeWords querybridgewords = new QueryBridgeWords();
	static NewInputText newinputtext = new NewInputText();
	static CalShortestPath shortestpath = new CalShortestPath();
	static RandomWalk randomwalk = new RandomWalk();
	public int vertexNum = 0;
	public Vertex[] head;
	ReadFile File = new ReadFile();
	Graph graph;
	String filestr = "";

	public MenuBar() {
		// 设置助记符为F，按下ALT + F 可以触发该菜单
		file.setMnemonic('F');
		file.setFont(new Font("SansSerif", Font.BOLD, 18));
	}

	public JMenuBar menuBar() {
		// 菜单栏
		open1.setFont(new Font("SansSerif", Font.BOLD, 15));
		open2.setFont(new Font("SansSerif", Font.BOLD, 15));
		open3.setFont(new Font("SansSerif", Font.BOLD, 15));
		open4.setFont(new Font("SansSerif", Font.BOLD, 15));
		open5.setFont(new Font("SansSerif", Font.BOLD, 15));
		open6.setFont(new Font("SansSerif", Font.BOLD, 15));
		quit.setFont(new Font("SansSerif", Font.BOLD, 15));
		file.add(open1);
		file.addSeparator(); // 设置菜单分隔符
		file.add(open2);
		file.addSeparator(); // 设置菜单分隔符
		file.add(open3);
		file.addSeparator(); // 设置菜单分隔符
		file.add(open4);
		file.addSeparator(); // 设置菜单分隔符
		file.add(open5);
		file.addSeparator(); // 设置菜单分隔符
		file.add(open6);
		file.addSeparator(); // 设置菜单分隔符
		file.add(quit);

		menuBar.add(file);
		quit.addActionListener(this);
		open1.addActionListener(this);
		open2.addActionListener(this);
		open3.addActionListener(this);
		open4.addActionListener(this);
		open5.addActionListener(this);
		open6.addActionListener(this);
		return menuBar;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == quit) {
			System.exit(0);
		} else if (e.getSource() == open1) {
			this.filestr = new fileChooser().filestr;
			File.transformFile(this.filestr);

			String str = "data.txt";
			graph = new Graph(str);
			this.vertexNum = graph.buildGraph();
			this.head = graph.head;
			Lab1_Con.head = graph.head;
			Lab1_Con.vertexNum = this.vertexNum;

			// System.out.println(this.filestr);
			querybridgewords.setVisible(false);
			newinputtext.setVisible(false);
			shortestpath.setVisible(false);
			randomwalk.setVisible(false);
			graph.showDirectedGraph();
		} else if (e.getSource() == open2) {
			showgraph = new ShowDirectedGraph();

			showgraph.setVisible(true);
			querybridgewords.setVisible(false);
			newinputtext.setVisible(false);
			shortestpath.setVisible(false);
			randomwalk.setVisible(false);
		} else if (e.getSource() == open3) {
			querybridgewords = new QueryBridgeWords();

			showgraph.setVisible(false);
			querybridgewords.setVisible(true);
			newinputtext.setVisible(false);
			shortestpath.setVisible(false);
			randomwalk.setVisible(false);
		} else if (e.getSource() == open4) {
			newinputtext = new NewInputText();

			showgraph.setVisible(false);
			querybridgewords.setVisible(false);
			newinputtext.setVisible(true);
			shortestpath.setVisible(false);
			randomwalk.setVisible(false);
		} else if (e.getSource() == open5) {
			shortestpath = new CalShortestPath();

			showgraph.setVisible(false);
			querybridgewords.setVisible(false);
			newinputtext.setVisible(false);
			shortestpath.setVisible(true);
			randomwalk.setVisible(false);
		} else if (e.getSource() == open6) {
			randomwalk = new RandomWalk();

			showgraph.setVisible(false);
			randomwalk.showpath();
			querybridgewords.setVisible(false);
			newinputtext.setVisible(false);
			shortestpath.setVisible(false);
			randomwalk.setVisible(true);
		}
	}
}

class fileChooser { // 选择文件
	public String filestr = "";

	public fileChooser() {
		try {
			JFileChooser jfc = new JFileChooser();
			jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			jfc.showDialog(new JLabel(), "选择");
			File file = jfc.getSelectedFile();

			if (file.isDirectory()) {
				// System.out.println("文件夹:"+file.getAbsolutePath());
				this.filestr = file.getAbsolutePath();
			} else if (file.isFile()) {
				// System.out.println("文件:"+file.getAbsolutePath());
				this.filestr = file.getAbsolutePath();
			}

			// System.out.println(jfc.getSelectedFile().getName());
			// this.filestr += ("\\"+jfc.getSelectedFile().getName());
			jfc.setVisible(false);
		} catch (Exception e) {
		}
	}
}

public class PCXJFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	MyShowPanel myshow;

	public PCXJFrame() {
		super("首页");
		this.setSize(750, 720);
		this.setLocation(700, 200);
		this.setResizable(false);
		setJMenuBar(new MenuBar().menuBar());

		myshow = new MyShowPanel();
		this.getContentPane().add(myshow, BorderLayout.CENTER);
		myshow.setVisible(true);
	}
}

class MyShowPanel extends JPanel implements ActionListener, ItemListener {
	private static final long serialVersionUID = 1L;
	static ShowDirectedGraph showgraph = new ShowDirectedGraph();
	static QueryBridgeWords querybridgewords = new QueryBridgeWords();
	static NewInputText newinputtext = new NewInputText();
	static CalShortestPath shortestpath = new CalShortestPath();
	static RandomWalk randomwalk = new RandomWalk();
	static JTextArea text_user; // 文本区
	private JComboBox<Object> alternative;
	private JScrollPane scrollPane;
	private Font font1;
	private Font font2;

	// Lab1_Pair lab1 = new Lab1_Pair();
	public int vertexNum = 0;
	public Vertex[] head;
	ReadFile file = new ReadFile();
	Graph graph;
	JButton b_refresh = new JButton("刷新列表");
	String filestr = "";

	public MyShowPanel() {
		new FirstPage(); // 完成输入初始成员
		font1 = new Font("SansSerif", Font.BOLD, 16);
		font2 = new Font("Serif", Font.BOLD, 16);
		text_user = new JTextArea("", 26, 52);
		text_user.setEditable(false);
		scrollPane = new JScrollPane(text_user, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(scrollPane);

		Object alter_content[] = { "选择操作", "功能需求1", "功能需求2", "功能需求3", "功能需求4", "功能需求5", "功能需求6" };
		alternative = new JComboBox<>(alter_content);
		alternative.addItemListener(this);
		alternative.setFont(font1);
		b_refresh.setFont(font1);
		text_user.setFont(font2);
		this.add(alternative);
		b_refresh.addActionListener(this);
		this.add(b_refresh);
		text_user.setText(FirstPage.showmessage());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == b_refresh) {
			JOptionPane.showMessageDialog(this, "无需刷新,此列表已经在您修改时自动更新过!");
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) { // 在组合框的下拉列表中选择数据项时触发执行
		// 实现ItemListener接口中的方法
		if (e.getStateChange() == ItemEvent.SELECTED) {
			switch (alternative.getSelectedIndex()) {
				case 0:
					querybridgewords.setVisible(false);
					newinputtext.setVisible(false);
					shortestpath.setVisible(false);
					randomwalk.setVisible(false);
					break;
				case 1:
					this.filestr = new fileChooser().filestr;
					file.transformFile(this.filestr);
					String str = "data.txt";
					graph = new Graph(str);
					this.vertexNum = graph.buildGraph();
					this.head = graph.head;
					Lab1_Con.head = graph.head;
					Lab1_Con.vertexNum = this.vertexNum;

					// System.out.println(this.filestr);
					querybridgewords.setVisible(false);
					newinputtext.setVisible(false);
					shortestpath.setVisible(false);
					randomwalk.setVisible(false);
					graph.showDirectedGraph();
					break;
				case 2:
					showgraph = new ShowDirectedGraph();
					showgraph.setVisible(true);
					querybridgewords.setVisible(false);
					newinputtext.setVisible(false);
					shortestpath.setVisible(false);
					randomwalk.setVisible(false);
					break;
				case 3:
					querybridgewords = new QueryBridgeWords();
					querybridgewords.setVisible(true);
					showgraph.setVisible(false);
					newinputtext.setVisible(false);
					shortestpath.setVisible(false);
					randomwalk.setVisible(false);
					break;
				case 4:
					newinputtext = new NewInputText();
					showgraph.setVisible(false);
					querybridgewords.setVisible(false);
					newinputtext.setVisible(true);
					shortestpath.setVisible(false);
					randomwalk.setVisible(false);
					break;
				case 5:
					shortestpath = new CalShortestPath();
					showgraph.setVisible(false);
					querybridgewords.setVisible(false);
					newinputtext.setVisible(false);
					shortestpath.setVisible(true);
					randomwalk.setVisible(false);
					break;
				case 6:
					randomwalk = new RandomWalk();
					showgraph.setVisible(false);
					randomwalk.showpath();
					querybridgewords.setVisible(false);
					newinputtext.setVisible(false);
					shortestpath.setVisible(false);
					randomwalk.setVisible(true);
					break;
				default:
					break;
			}
		}
	}
}

class ShowDirectedGraph extends JFrame { // 展示有向图
	private static final long serialVersionUID = 1L;
	JPanel MPanel;
	JScrollPane sp;
	picDisplayPanel picPanel;

	public ShowDirectedGraph() {
		super("展示有向图");
		this.setSize(700, 700);
		this.setLocationRelativeTo(null);
		this.setResizable(true);

		MPanel = new JPanel(new GridLayout());
		picPanel = new picDisplayPanel();
		picDisplayPanel.setPic(Lab1_Con.pathtext + "\\out.gif");
		sp = new JScrollPane(picPanel);
		sp.validate();
		MPanel.add(sp);
		setJMenuBar(new MenuBar().menuBar());

		this.add(MPanel);
	}
}

// 查询桥接词
class QueryBridgeWords extends JFrame implements ActionListener, ItemListener {
	private static final long serialVersionUID = 1L;
	private TextField word1;
	private TextField word2;
	private JButton submit;
	static JTextArea text_user;
	private JScrollPane scrollPane;
	picDisplayPanel picPanel;
	private Font font1;
	private Font font2;

	public QueryBridgeWords() {
		super("查询桥接词");
		this.setSize(1400, 795);
		this.setLocationRelativeTo(null); // set the frame container
		this.setResizable(false);

		text_user = new JTextArea("", 19, 33);
		text_user.setEditable(false);
		text_user.setLineWrap(true);
		text_user.setWrapStyleWord(true);
		font1 = new Font("SansSerif", Font.BOLD, 16);
		font2 = new Font("Serif", Font.BOLD, 25);
		scrollPane = new JScrollPane(text_user, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		word1 = new TextField(18);
		word2 = new TextField(18);
		submit = new JButton("提交");
		submit.setFont(font1);
		text_user.setFont(font2);
		word1.setFont(font1);
		word2.setFont(font1);

		JPanel MPanel = new JPanel(new GridLayout());
		JPanel RightPanel = new JPanel();
		picPanel = new picDisplayPanel();
		picDisplayPanel.setPic(Lab1_Con.pathtext + "\\out.gif");
		JScrollPane sp = new JScrollPane(picPanel);
		sp.validate();
		MPanel.add(sp);

		RightPanel.add(scrollPane);
		RightPanel.add(word1);
		RightPanel.add(word2);
		RightPanel.add(submit);
		MPanel.add(RightPanel);
		this.add(MPanel);
		setJMenuBar(new MenuBar().menuBar());
		submit.addActionListener(this);
	}

	// 将桥接词以特殊的颜色显示在图片中
	public void showDirectedGraph(String word1, String word2, String[] bridgeword) {
		GraphViz gv = new GraphViz();
		gv.addln(gv.start_graph());

		for (int i = 0; i < Lab1_Con.vertexNum; i++) {
			Edge p = Lab1_Con.head[i].adjacent;
			while (p != null) {
				if (Lab1_Con.head[i].Name.equals(word1) && findBridgeWord(bridgeword, p.Name)) {
					gv.add(Lab1_Con.head[i].Name + "[color = lightblue,style = filled]");
					gv.add(p.Name + "[color = green,style = filled]");
					gv.addln(Lab1_Con.head[i].Name + "->" + p.Name + "[label=" + p.cost + ",color=red];");
				} else if (findBridgeWord(bridgeword, Lab1_Con.head[i].Name) && p.Name.equals(word2)) {
					gv.add(p.Name + "[color = lightblue,style = filled]");
					gv.addln(Lab1_Con.head[i].Name + "->" + p.Name + "[label=" + p.cost + ",color=red];");
				} else {
					gv.addln(Lab1_Con.head[i].Name + "->" + p.Name + "[label=" + p.cost + "];");
				}
				p = p.link;
			}
		}
		gv.addln(gv.end_graph());
		// System.out.println(gv.getDotSource());
		String type = "gif";
		File out = new File(Lab1_Con.pathtext + "/outQBW." + type);
		gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), type), out);
	}

	public boolean findBridgeWord(String[] str, String s) {
		for (int i = 0; i < str.length; i++) {
			if (str[i].equals(s)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == submit) {
			String string1 = Lab1_Con.queryBridgeWords(word1.getText(), word2.getText());
			if (string1.equals("-1")) {
				text_user.setText("No word1 or word2 in the graph!");
			} else if (string1.equals("0")) {
				text_user.setText("No bridge words from word1 to word2!");
			} else {
				String[] str = string1.split(" ");
				String strl = "The bridge words from word1 to word2 are:\n";
				for (int i = 0; i < str.length - 1; i++) {
					strl += str[i] + " , ";
				}
				if (str.length == 1) {
					strl += str[0];
				} else {
					strl += " and " + str[str.length - 1];
				}
				text_user.setText(strl);
				showDirectedGraph(word1.getText(), word2.getText(), str);
				picDisplayPanel.setPic(Lab1_Con.pathtext + "\\outQBW.gif");
			}
		}
	}

	public void itemStateChanged(ItemEvent e) {
	}
}

// 根据桥接词生成新文本
class NewInputText extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	static JTextArea text_user;
	private TextField text; // describe the JTextArea,TextField,button,font
	private JButton tb;
	private JScrollPane scrollPane;
	private Font font1, font2;
	// static Lab1_Pair lab1 = new Lab1_Pair();

	public NewInputText() {
		super("根据桥接词生成新文本");
		this.setSize(1400, 795);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		text_user = new JTextArea("", 19, 33);
		text_user.setEditable(false);
		text_user.setLineWrap(true);
		text_user.setWrapStyleWord(true);

		tb = new JButton("提交");
		text = new TextField(50);
		font1 = new Font("Serif", Font.BOLD, 16);
		font2 = new Font("Serif", Font.BOLD, 25);
		tb.setFont(font1);
		text.setFont(font1);
		text_user.setFont(font2);
		scrollPane = new JScrollPane(text_user, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		JPanel MPanel = new JPanel(new GridLayout());
		JPanel RightPanel = new JPanel();
		picDisplayPanel picPanel = new picDisplayPanel();
		picDisplayPanel.setPic(Lab1_Con.pathtext + "\\out.gif");
		JScrollPane sp = new JScrollPane(picPanel);
		sp.validate();
		MPanel.add(sp);
		RightPanel.add(scrollPane);
		RightPanel.add(text);
		RightPanel.add(tb);
		MPanel.add(RightPanel);

		this.add(MPanel);
		setJMenuBar(new MenuBar().menuBar());
		tb.addActionListener(this); // add listener for button
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == tb) {
			String temp;
			temp = text.getText().replaceAll("[^a-zA-Z]", " ");
			text_user.setText(Lab1_Con.generateNewText(temp));
			// System.out.print(text.getText());
		}
	}
}

// 计算最短路径
class CalShortestPath extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JTextArea text_user; // 文本区
	private JScrollPane scrollPane;
	private TextField word1, word2;

	JButton submit = new JButton("查询");
	private Font font1;
	private Font font2;
	OutputStreamWriter os = null;
	picDisplayPanel picPanel;

	public CalShortestPath() {
		super("计算最短路径");
		this.setSize(1400, 795);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		text_user = new JTextArea("", 19, 33);
		text_user.setEditable(false);
		text_user.setLineWrap(true);
		text_user.setWrapStyleWord(true);
		font1 = new Font("SansSerif", Font.BOLD, 16);
		font2 = new Font("Serif", Font.BOLD, 25);
		scrollPane = new JScrollPane(text_user, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		// text_user.setText(FirstPage.showmessage());
		word1 = new TextField(18);
		word2 = new TextField(18);
		submit.setFont(font1);
		text_user.setFont(font2);
		word1.setFont(font1);
		word2.setFont(font1);
		JPanel MPanel = new JPanel(new GridLayout());
		JPanel RightPanel = new JPanel();
		picPanel = new picDisplayPanel();
		picDisplayPanel.setPic(Lab1_Con.pathtext + "\\out.gif");
		JScrollPane sp = new JScrollPane(picPanel);
		sp.validate();
		MPanel.add(sp);

		RightPanel.add(scrollPane);
		RightPanel.add(word1);
		RightPanel.add(word2);
		RightPanel.add(submit);
		MPanel.add(RightPanel);
		this.add(MPanel);
		submit.addActionListener(this);
		setJMenuBar(new MenuBar().menuBar());
	}

	// 将最短路径以突出的方式显示在图中
	public void showDirectedGraph_2word() {
		GraphViz gv = new GraphViz();
		gv.addln(gv.start_graph());
		String[] color = { "red", "deepskyblue", "cyan", "gold", "green", "blue", "brown", "springgreen", "tomato",
				"violet", "violetred" };

		for (int i = 0; i < Lab1_Con.vertexNum; i++) {
			Edge p = Lab1_Con.head[i].adjacent;
			while (p != null) {
				gv.addln(Lab1_Con.head[i].Name + "->" + p.Name + "[label=" + p.cost + "];");
				p = p.link;
			}
		}

		try {
			for (int i = 0; i < Lab1_Con.waypoint.size(); i++) {
				for (int j = 0; j < Lab1_Con.waypoint.get(i).size() - 1; j++) {
					gv.addln(Lab1_Con.head[Lab1_Con.waypoint.get(i).get(j)].Name + "->"
							+ Lab1_Con.head[Lab1_Con.waypoint.get(i).get(j + 1)].Name + "[color=" + color[i] + "];");
				}
			}
		} catch (Exception e) {
		}

		gv.addln(gv.end_graph());
		// System.out.println(gv.getDotSource());
		String type = "gif";
		File out = new File(Lab1_Con.pathtext + "\\outCSP." + type);
		gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), type), out);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == submit) {
			String path = "";
			if (!word1.getText().isEmpty() && !word2.getText().isEmpty()) {
				// text_user.setText(word1.getText()+word2.getText());
				path = Lab1_Con.calcShortestPath(word1.getText(), word2.getText());
				showDirectedGraph_2word();
			} else if (word1.getText().isEmpty() && !word2.getText().isEmpty()
					|| !word1.getText().isEmpty() && word2.getText().isEmpty()) {
				// text_user.setText(word1.getText()+word2.getText());
				path = Lab1_Con.calcShortestPath_Oneword(word1.getText() + word2.getText());
			}

			// try{os.write(path);os.close();}
			// catch(IOException e1){System.out.print("Exception");}
			text_user.setText(path);

			// try{os.write(path);os.close();}
			// catch(IOException e1){System.out.print("Exception");}
			picDisplayPanel.setPic(Lab1_Con.pathtext + "\\outCSP.gif");
		}
	}
}

// 随机游走
class RandomWalk extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JTextArea text_user;
	private JScrollPane scrollPane;
	JButton next = new JButton("Next");
	JButton stop = new JButton("Stop");
	JButton reset = new JButton("Reset");

	// Lab1_Pair lab1 = new Lab1_Pair();
	String[] str1;
	public String string = "";
	public int step = 0;
	public boolean flag = false;
	private Font font1;
	private Font font2;
	JLabel blank1 = new JLabel("  ");
	JLabel blank2 = new JLabel("  ");
	OutputStreamWriter os = null;
	picDisplayPanel picPanel;

	public RandomWalk() {
		super("随机游走");
		this.setSize(1400, 795);
		this.setLocationRelativeTo(null); // set the frame container
		this.setResizable(false);

		text_user = new JTextArea("", 19, 33);
		text_user.setEditable(false);
		text_user.setLineWrap(true);
		text_user.setWrapStyleWord(true);
		font1 = new Font("SansSerif", Font.BOLD, 16);
		font2 = new Font("Serif", Font.BOLD, 25);
		text_user.setFont(font2);

		scrollPane = new JScrollPane(text_user, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		JPanel MPanel = new JPanel(new GridLayout());
		JPanel RightPanel = new JPanel();
		picPanel = new picDisplayPanel();
		picDisplayPanel.setPic(Lab1_Con.pathtext + "\\out.gif");
		JScrollPane sp = new JScrollPane(picPanel);

		sp.validate();
		MPanel.add(sp);
		RightPanel.add(scrollPane);
		RightPanel.add(next);
		RightPanel.add(this.blank1);
		RightPanel.add(stop);
		RightPanel.add(this.blank2);
		RightPanel.add(reset);
		MPanel.add(RightPanel);
		this.add(MPanel);

		next.setFont(font1);
		stop.setFont(font1);
		reset.setFont(font1);
		next.addActionListener(this);
		stop.addActionListener(this);
		reset.addActionListener(this);

		setJMenuBar(new MenuBar().menuBar());
	}

	public void showpath() {
		str1 = Lab1_Con.randomWalk().split(" ");
		// System.out.print(str1[0]);
	}

	public boolean findNode(String word1, String word2, int num) {
		for (int i = 0; i < num - 1; i++) {
			if (str1[i].equals(word1) && str1[i + 1].equals(word2)) {
				return true;
			}
		}
		return false;
	}

	// 将随机游走的路径以突出的方式展示在图中
	public void showDirectedGraph(int num) {
		GraphViz gv = new GraphViz();
		gv.addln(gv.start_graph());
		for (int i = 0; i < Lab1_Con.vertexNum; i++) {
			Edge p = Lab1_Con.head[i].adjacent;
			while (p != null) {
				if (findNode(Lab1_Con.head[i].Name, p.Name, num)) {
					gv.addln(Lab1_Con.head[i].Name + "->" + p.Name + "[label=" + p.cost + ",color=red];");
				} else {
					gv.addln(Lab1_Con.head[i].Name + "->" + p.Name + "[label=" + p.cost + "];");
				}
				p = p.link;
			}
		}
		gv.addln(gv.end_graph());
		// System.out.println(gv.getDotSource());

		String type = "gif";
		File out = new File(Lab1_Con.pathtext + "/outRDW." + type);
		gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), type), out);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == next) {
			if (!flag && !str1[0].equals("0")) {
				if (step == 0) {
					string += (str1[step++]);
					text_user.setText(string);
				} else if (step < str1.length) {
					string += ("->" + str1[step++]);
					text_user.setText(string);
					showDirectedGraph(step);
					picDisplayPanel.setPic(Lab1_Con.pathtext + "\\outRDW.gif");
				} else {
					try {
						os = new OutputStreamWriter(new FileOutputStream("datarandom.txt"));
						os.write(string);
						os.close();
					} catch (IOException e1) {
					}
					string += "\n已到路的尽头！！！";
					text_user.setText(string);
					flag = true;
					/*
					 * showDirectedGraph(step);
					 * picDisplayPanel.setPic(Lab1_Pair.pathtext+"\\outRDW.gif");
					 */
				}
			} else if (!flag) {
				text_user.setText("请先生成图，再选用此功能！！！");
			}
		} else if (e.getSource() == stop) {
			flag = true;
			try {
				os = new OutputStreamWriter(new FileOutputStream("datarandom.txt"));
				os.write(string);
				os.close();
			} catch (IOException e1) {
			}
		} else if (e.getSource() == reset) {
			showpath();
			flag = false;
			step = 0;
			string = "";
			text_user.setText(string);
		}
	}
}

// 展示图片Panel
class picDisplayPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	public static JLabel picLabel;
	public static int WIDTH;
	public static int HEIGHT;
	public static ImageIcon pic;
	boolean isAltDown = false;
	int percent = 100;

	public picDisplayPanel() {
		picLabel = new JLabel();
		setBackground(Color.WHITE);
		add(picLabel);
	}

	public static void setPic(String path) {
		try {
			pic = new ImageIcon(ImageIO.read(new File(path)));
			WIDTH = pic.getIconWidth();
			HEIGHT = pic.getIconHeight();
			picLabel.setIcon(pic);
			picLabel.repaint();
		} catch (Exception e) {
			System.err.println("Error loading image from path: " + path);
			e.printStackTrace();
		}
	}

	public static void changeSize(int percent) {
		pic.setImage(pic.getImage().getScaledInstance(percent * WIDTH, percent * HEIGHT, Image.SCALE_DEFAULT));
		picLabel.setIcon(pic);
	}
}