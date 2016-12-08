import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MultiChatClient implements ActionListener, Runnable {
	private String ip;
	private String id;
	private Socket socket;
	private BufferedReader inMsg = null;
	private PrintWriter outMsg = null;
	// �α��� �г�
	private JPanel loginPanel;
	// �α��� ��ư
	private JButton loginButton;
	// ��ȭ�� ��
	private JLabel label1;
	// ��ȭ�� �Է� �ؽ�Ʈ �ʵ�
	private JTextField idField;

	// �α׾ƿ� �г� ����
	private JPanel logoutPanel;
	// ��ȭ�� ��� ��
	private JLabel label2;
	// �α׾ƿ� ��ư
	private JButton logoutButton;

	// �Է��г� ����
	private JPanel msgPanel;
	// �޽��� �Է� �ؽ�Ʈ �ʵ�
	private JTextField msgField;
	// ���� ��ư
	private JButton exitButton;

	// ���� ������
	private JFrame jframe;
	// ä�ó��� ���â
	private JTextArea msgArea;

	// ī�� ���̾ƿ���
	private Container tab;
	private CardLayout clayout;
	private Thread thread;

	// ���� �÷���
	boolean status;

	// ������
	public MultiChatClient(String ip) {
		this.ip = ip;

		// �α��� �гα���
		loginPanel = new JPanel();
		// ���̾ƿ� ����
		loginPanel.setLayout(new BorderLayout());
		idField = new JTextField(15);
		loginButton = new JButton("�α���");
		// �̺�Ʈ ������ ���
		loginButton.addActionListener(this);
		label1 = new JLabel("��ȭ��");
		// �г� ���� ����

		loginPanel.add(label1, BorderLayout.WEST);
		loginPanel.add(idField, BorderLayout.CENTER);
		loginPanel.add(loginButton, BorderLayout.EAST);

		// �α׾ƿ� �гα���
		logoutPanel = new JPanel();
		// ���̾ƿ� ����
		logoutPanel.setLayout(new BorderLayout());
		label2 = new JLabel();// ���⿡ ���߿� ������ ������� ���̵� setting�ȴ�.
		logoutButton = new JButton("�α׾ƿ�");
		// �̺�Ʈ ������ ���
		logoutButton.addActionListener(this);
		// �г� ��������
		logoutPanel.add(label2, BorderLayout.CENTER);
		logoutPanel.add(logoutButton, BorderLayout.EAST);

		// �Է� �г� ����
		msgPanel = new JPanel();
		// ���̾ƿ� ����
		msgPanel.setLayout(new BorderLayout());
		msgField = new JTextField();
		// �̺�Ʈ ������ ���
		msgField.addActionListener(this);
		// �г� ���� ����
		exitButton = new JButton("����");
		msgPanel.add(msgField, BorderLayout.CENTER);
		msgPanel.add(exitButton, BorderLayout.EAST);

		// �α���/�α׾ƿ� �г� ������ ���� ī�� ���̾ƿ� �г�
		tab = new JPanel();
		clayout = new CardLayout();
		tab.setLayout(clayout);
		tab.add(loginPanel, "login");
		tab.add(logoutPanel, "logout");

		// ���� ������ ����
		jframe = new JFrame("::Multi Chat::");
		msgArea = new JTextArea("", 10, 30);
		// JtextArea �� ������ �������� ���ϰ� ��¿�����
		msgArea.setEditable(false);
		// ��ũ�� ������ �׻� ������ �ʿ��Ҷ���
		JScrollPane jsp = new JScrollPane(msgArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		// �����ӵ� �ױ�
		jframe.add(tab, BorderLayout.NORTH);
		jframe.add(jsp, BorderLayout.CENTER);
		jframe.add(msgPanel, BorderLayout.SOUTH);
		// �α��� �г� �켱ǥ��
		clayout.show(tab, "login");
		// ��ŷ
		jframe.pack();
		// ũ������ �Ұ�����
		jframe.setResizable(false);
		// ������ ǥ��
		jframe.setVisible(true);

	}

	public void connectServer() {
		try {
			socket = new Socket(ip, 8888);
			System.out.println("[Client]Sever ���� ����");
			// ����� ��Ʈ�� ����
			inMsg = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			outMsg = new PrintWriter(socket.getOutputStream(), true); // true�Ķ���͸�
																		// �����ϸ�
																		// �а� ����
																		// �ٷ�
																		// ���ۺ����
																		// flush�޼ҵ�
																		// ȣ��

			// ������ �α��� �޼��� ����
			outMsg.println(id + "/" + "login");

			// �޼��� ������ ���� ������ ����
			thread = new Thread(this);
			thread.start();
		} catch (Exception e) {
			System.out.println("[MultiChatClient]connectServer()Exception!!");
		}
	}

	// �̺�Ʈ ó�� �������̵�

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		// �����ư ó��
		if (obj == exitButton) {
			System.exit(0);
		} else if (obj == loginButton) {
			id = idField.getText();
			label2.setText("��ȭ��" + id);
			clayout.show(tab, "logout");
			connectServer();
		} else if (obj == logoutButton) {
			// �α׾ƿ� �޽��� ����
			outMsg.println(id + "/" + "logout");
			// ��ȭâ Ŭ����
			msgArea.setText("");
			// �α��� �гη� ��ȯ
			clayout.show(tab, "login");
			outMsg.close();
			try {
				inMsg.close();
				socket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			status = false;
		}else if(obj==msgField){
			//�޽��� ����
			outMsg.println(id+"/"+msgField.getText());
			//�Է�â Ŭ����
			msgField.setText("");
		}

	}
	@Override
	public void run() {
		//���Ÿ޽����� ����
		String msg;
		String[] rmsg; //�Ľ̵� �޽����� ���� �迭
		
		status = true;
		while(status){
			try{
				//�޽��� ���� �Ľ�
				msg = inMsg.readLine();
				rmsg = msg.split("/");// "/"�� �������� �ڸ���.
				//jtextArea�� ���ŵ� �޽��� �߰�
				msgArea.append(rmsg[0]+">"+rmsg[1]+"\n");
				
				//Ŀ���� ���� ��ȭ �޽����� ǥ��
				msgArea.setCaretPosition(msgArea.getDocument().getLength());

			}catch(IOException e){
				status = false;
			}
		}
		System.out.println("[MutiChatClient]"+thread.getName()+"�����");
	}

	public static void main(String[] args) {
		MultiChatClient mcc = new MultiChatClient("192.168.0.206");
	}

	

}
