package software_engineering;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Login extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	JButton b_submit = new JButton("login");
	JButton b_reset = new JButton("logout");
	TextField loginname = new TextField(17);
	JPasswordField password = new JPasswordField(17);
	PCXJFrame userjframe = new PCXJFrame();
	private Font font1;
	private Font font2;
	static Login loginframe = new Login();

	public Login() {
		super("Lab1-Pair Con programming/ 2021130019 潘昶勋");

		JLabel label_id = new JLabel("id");
		JLabel label_password = new JLabel("password");
		JLabel studentNumber = new JLabel("学号: 2021130019");
		JLabel name = new JLabel("名字: 潘昶勋");

		// 스타일과 폰트 설정
		font1 = new Font("SansSerif", Font.BOLD, 25);
		font2 = new Font("SansSerif", Font.BOLD, 18);

		label_id.setFont(font1);
		label_password.setFont(font1);
		loginname.setFont(font1);
		password.setFont(font1);
		b_submit.setFont(font2);
		b_reset.setFont(font2);
		b_submit.setForeground(Color.WHITE);
		b_submit.setBackground(Color.BLUE);
		b_reset.setForeground(Color.WHITE);
		b_reset.setBackground(Color.RED);
		studentNumber.setFont(font1);
		name.setFont(font1);

		b_submit.addActionListener(this);
		b_reset.addActionListener(this);

		this.setResizable(false);
		this.setSize(700, 270);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// GroupLayout 설정
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);

		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
				.addComponent(studentNumber)
				.addComponent(name)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
								.addComponent(label_id)
								.addComponent(label_password))
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(loginname)
								.addComponent(password)))
				.addGroup(layout.createSequentialGroup()
						.addComponent(b_submit)
						.addComponent(b_reset)));

		layout.setVerticalGroup(layout.createSequentialGroup()
				.addComponent(studentNumber)
				.addComponent(name)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(label_id)
						.addComponent(loginname))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(label_password)
						.addComponent(password))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(b_submit)
						.addComponent(b_reset)));

		this.addWindowListener(new WinClose());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == b_submit) {
			String pwd = new String(password.getPassword());
			if (loginname.getText().equals("2021130019") && pwd.equals("0123456789")) {
				JLabel jlabel = new JLabel(
						"<html><body>1.HI~! professor, you need ↓<br><br><html><body>2.Make sure GraphViz is installed on this computer<br><br>2.Please make sure GraphViz.DOT in the Java file.Change the path of exe to a valid path for this computer<br><br></body></html>");
				jlabel.setForeground(Color.blue);
				jlabel.setFont(new Font("SansSerif", Font.BOLD, 22));
				JOptionPane.showMessageDialog(this, jlabel, "Warning!!!", JOptionPane.WARNING_MESSAGE);
				userjframe.setVisible(true);
				loginframe.setVisible(false);
			} else {
				JOptionPane.showMessageDialog(this, "ohh Who are you???\n");
			}
		}
		if (e.getSource() == b_reset) {
			System.exit(0);
		}
	}

	public void run() {
		loginframe.setVisible(true);
	}

	public static void main(String[] args) {
		loginframe.run();
	}
}

class WinClose implements WindowListener {
	public void windowClosing(WindowEvent e) {
		System.exit(0);
	}

	public void windowOpened(WindowEvent e) {
	}

	public void windowActivated(WindowEvent e) {
	}

	public void windowDeactivated(WindowEvent e) {
	}

	public void windowClosed(WindowEvent e) {
	}

	public void windowIconified(WindowEvent e) {
	}

	public void windowDeiconified(WindowEvent e) {
	}
}
