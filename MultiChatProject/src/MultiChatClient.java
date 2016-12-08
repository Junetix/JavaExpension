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
	// 로그인 패널
	private JPanel loginPanel;
	// 로그인 버튼
	private JButton loginButton;
	// 대화명 라벨
	private JLabel label1;
	// 대화명 입력 텍스트 필드
	private JTextField idField;

	// 로그아웃 패널 구성
	private JPanel logoutPanel;
	// 대화명 출력 라벨
	private JLabel label2;
	// 로그아웃 버튼
	private JButton logoutButton;

	// 입력패널 구성
	private JPanel msgPanel;
	// 메시지 입력 텍스트 필드
	private JTextField msgField;
	// 종료 버튼
	private JButton exitButton;

	// 메인 윈도우
	private JFrame jframe;
	// 채팅내용 출력창
	private JTextArea msgArea;

	// 카드 레이아웃용
	private Container tab;
	private CardLayout clayout;
	private Thread thread;

	// 상태 플래그
	boolean status;

	// 생성자
	public MultiChatClient(String ip) {
		this.ip = ip;

		// 로그인 패널구성
		loginPanel = new JPanel();
		// 레이아웃 설정
		loginPanel.setLayout(new BorderLayout());
		idField = new JTextField(15);
		loginButton = new JButton("로그인");
		// 이벤트 리스너 등록
		loginButton.addActionListener(this);
		label1 = new JLabel("대화명");
		// 패널 위젯 구성

		loginPanel.add(label1, BorderLayout.WEST);
		loginPanel.add(idField, BorderLayout.CENTER);
		loginPanel.add(loginButton, BorderLayout.EAST);

		// 로그아웃 패널구성
		logoutPanel = new JPanel();
		// 레이아웃 설정
		logoutPanel.setLayout(new BorderLayout());
		label2 = new JLabel();// 여기에 나중에 참여한 사용자의 아이디가 setting된다.
		logoutButton = new JButton("로그아웃");
		// 이벤트 리스너 등록
		logoutButton.addActionListener(this);
		// 패널 위젯구성
		logoutPanel.add(label2, BorderLayout.CENTER);
		logoutPanel.add(logoutButton, BorderLayout.EAST);

		// 입력 패널 구성
		msgPanel = new JPanel();
		// 레이아웃 설정
		msgPanel.setLayout(new BorderLayout());
		msgField = new JTextField();
		// 이벤트 리스너 등록
		msgField.addActionListener(this);
		// 패널 위젯 구성
		exitButton = new JButton("종료");
		msgPanel.add(msgField, BorderLayout.CENTER);
		msgPanel.add(exitButton, BorderLayout.EAST);

		// 로그인/로그아웃 패널 선택을 위한 카드 레이아웃 패널
		tab = new JPanel();
		clayout = new CardLayout();
		tab.setLayout(clayout);
		tab.add(loginPanel, "login");
		tab.add(logoutPanel, "logout");

		// 메인 프래임 구성
		jframe = new JFrame("::Multi Chat::");
		msgArea = new JTextArea("", 10, 30);
		// JtextArea 의 내용을 수정하지 못하게 출력용으로
		msgArea.setEditable(false);
		// 스크롤 수직은 항상 수평은 필요할때만
		JScrollPane jsp = new JScrollPane(msgArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		// 프레임들 쌓기
		jframe.add(tab, BorderLayout.NORTH);
		jframe.add(jsp, BorderLayout.CENTER);
		jframe.add(msgPanel, BorderLayout.SOUTH);
		// 로그인 패널 우선표시
		clayout.show(tab, "login");
		// 패킹
		jframe.pack();
		// 크기조정 불가설정
		jframe.setResizable(false);
		// 프레임 표시
		jframe.setVisible(true);

	}

	public void connectServer() {
		try {
			socket = new Socket(ip, 8888);
			System.out.println("[Client]Sever 연결 성공");
			// 입출력 스트림 생성
			inMsg = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			outMsg = new PrintWriter(socket.getOutputStream(), true); // true파라메터를
																		// 전달하면
																		// 읽고 나서
																		// 바로
																		// 버퍼비워줌
																		// flush메소드
																		// 호출

			// 서버에 로그인 메세지 전달
			outMsg.println(id + "/" + "login");

			// 메세지 수신을 위한 스레드 생성
			thread = new Thread(this);
			thread.start();
		} catch (Exception e) {
			System.out.println("[MultiChatClient]connectServer()Exception!!");
		}
	}

	// 이벤트 처리 오버라이딩

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		// 종료버튼 처리
		if (obj == exitButton) {
			System.exit(0);
		} else if (obj == loginButton) {
			id = idField.getText();
			label2.setText("대화명" + id);
			clayout.show(tab, "logout");
			connectServer();
		} else if (obj == logoutButton) {
			// 로그아웃 메시지 전송
			outMsg.println(id + "/" + "logout");
			// 대화창 클리어
			msgArea.setText("");
			// 로그인 패널로 전환
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
			//메시지 전송
			outMsg.println(id+"/"+msgField.getText());
			//입력창 클리어
			msgField.setText("");
		}

	}
	@Override
	public void run() {
		//수신메시지용 변수
		String msg;
		String[] rmsg; //파싱된 메시지를 담을 배열
		
		status = true;
		while(status){
			try{
				//메시지 수신 파싱
				msg = inMsg.readLine();
				rmsg = msg.split("/");// "/"를 기준으로 자른다.
				//jtextArea에 수신된 메시지 추가
				msgArea.append(rmsg[0]+">"+rmsg[1]+"\n");
				
				//커서를 현재 대화 메시지에 표시
				msgArea.setCaretPosition(msgArea.getDocument().getLength());

			}catch(IOException e){
				status = false;
			}
		}
		System.out.println("[MutiChatClient]"+thread.getName()+"종료됨");
	}

	public static void main(String[] args) {
		MultiChatClient mcc = new MultiChatClient("192.168.0.206");
	}

	

}
