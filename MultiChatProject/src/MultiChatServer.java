import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MultiChatServer {
	private ServerSocket serverSocket = null;
	private Socket socket = null;
	ArrayList<ChatThread> chatlist = new ArrayList<ChatThread>();
	
	//멀티 챗 메인 프로그램
	public void start(){
		try{
			//서버 소켓 생성
			serverSocket = new ServerSocket(8888);
			System.out.println("Server Start");
			//연결대기
			while(true){
				socket = serverSocket.accept();
				//연결 클라이언트에서 스레드 생성
				ChatThread chat = new ChatThread();
				//클라이언트 리스트 추가
				chatlist.add(chat);
				
			}
		}catch(Exception e){
			System.out.println("[MultiChatServer]Start() Exception!!");
			
		}
	}
	
	
	public static void main(String[] args) {
		MultiChatServer server = new MultiChatServer();
		server.start();
		

	}
	public void msgSendAll(String msg){
		for(ChatThread ct : chatlist){
			ct.outMsg.println(msg);
		}
	}
	//각 클라이언트 관리를 위한 스레드 클래스
	class ChatThread extends Thread{
		//수신메시지와 파싱 메시지를 처리하는 변수 선언
		String msg;
		String[] rmsg;
		
		//입출력 스트림생성
		private BufferedReader inMsg = null;
		private PrintWriter outMsg = null;
		
		@Override
		public void run(){
			boolean status = true;
			System.out.println("##ChatThread Start..");
			try{
				inMsg = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				outMsg = new PrintWriter(socket.getOutputStream(),true);
				//루프 돌면서 수신된 메시지 처리하기
				while(status){
					msg = inMsg.readLine();
					rmsg = msg.split("/");
					if(rmsg[1].equals("logout")){
						chatlist.remove(this);
						msgSendAll("Server/"+rmsg[0]+"님이 종료했습니다.");
						//해당 클라이언트 플래그 설정
						status = false;
					}
					//로그인 메시지 일 경우
					else if(rmsg[1].equals("login")){
						msgSendAll("Server/"+rmsg[0]+"님이 로그인했습니다.");
						
					}else{
						msgSendAll(msg);
					}
				}
				//while종료
				//루프를 벗어나면 클라이언트 연결이 종료되므로 스레드가 인터럽트된다.
				this.interrupt();
				System.out.println("##"+this.getName()+"stop!!");	
			}catch(IOException e){
				chatlist.remove(this);
				System.out.println("[ChatThread]run() IOException 발생!");
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	

}
