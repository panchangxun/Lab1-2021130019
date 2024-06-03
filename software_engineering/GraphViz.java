package software_engineering;

import java.io.*;

public class GraphViz {
	private static String TEMP_DIR = Lab1_Con.pathtext; // Windows
	private static String DOT = "C:\\Program Files\\Graphviz\\bin\\dot.exe"; // Windows

	private StringBuilder graph = new StringBuilder();

	public GraphViz() {
	}

	public String getDotSource() {
		return graph.toString();
	}

	public void add(String line) {
		graph.append(line);
	}

	public void addln(String line) {
		graph.append(line).append("\n");
	}

	public void addln() {
		graph.append('\n');
	}

	public byte[] getGraph(String dotSource, String type) {
		File dot;
		byte[] imgStream = null;

		try {
			dot = writeDotSourceToFile(dotSource);
			if (dot != null) {
				imgStream = getImgStream(dot, type);
				if (!dot.delete())
					System.err.println("Warning: " + dot.getAbsolutePath() + " could not be deleted!");
				return imgStream;
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return null;
	}

	public int writeGraphToFile(byte[] img, String file) {
		return writeGraphToFile(img, new File(file));
	}

	public int writeGraphToFile(byte[] img, File to) {
		try (FileOutputStream fos = new FileOutputStream(to)) {
			fos.write(img);
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return -1;
		}
		return 1;
	}

	private byte[] getImgStream(File dot, String type) {
		File img;
		byte[] imgStream = null;

		try {
			img = File.createTempFile("graph_", "." + type, new File(GraphViz.TEMP_DIR));
			String[] args = { DOT, "-T" + type, dot.getAbsolutePath(), "-o", img.getAbsolutePath() };
			Process p = Runtime.getRuntime().exec(args);
			p.waitFor();

			try (FileInputStream in = new FileInputStream(img)) {
				imgStream = new byte[in.available()];
				in.read(imgStream);
			}

			if (!img.delete())
				System.err.println("Warning: " + img.getAbsolutePath() + " could not be deleted!");
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}

		return imgStream;
	}

	public File writeDotSourceToFile(String str) throws IOException {
		File temp;
		try {
			temp = File.createTempFile("graph_", ".dot.tmp", new File(GraphViz.TEMP_DIR));
			try (FileWriter fout = new FileWriter(temp)) {
				fout.write(str);
			}
		} catch (Exception e) {
			System.err.println("Error: I/O error while writing the dot source to temp file!");
			return null;
		}
		return temp;
	}

	public String start_graph() {
		return "digraph G {";
	}

	public String end_graph() {
		return "}";
	}

	public void readSource(String input) {
		StringBuilder sb = new StringBuilder();
		try (FileInputStream fis = new FileInputStream(input);
				DataInputStream dis = new DataInputStream(fis);
				BufferedReader br = new BufferedReader(new InputStreamReader(dis))) {
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
		this.graph = sb;
	}
}
