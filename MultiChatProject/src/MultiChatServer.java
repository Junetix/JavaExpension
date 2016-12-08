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
	
	//��Ƽ ê ���� ���α׷�
	public void start(){
		try{
			//���� ���� ����
			serverSocket = new ServerSocket(8888);
			System.out.println("Server Start");
			//������
			while(true){
				socket = serverSocket.accept();
				//���� Ŭ���̾�Ʈ���� ������ ����
				ChatThread chat = new ChatThread();
				//Ŭ���̾�Ʈ ����Ʈ �߰�
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
	//�� Ŭ���̾�Ʈ ������ ���� ������ Ŭ����
	class ChatThread extends Thread{
		//���Ÿ޽����� �Ľ� �޽����� ó���ϴ� ���� ����
		String msg;
		String[] rmsg;
		
		//����� ��Ʈ������
		private BufferedReader inMsg = null;
		private PrintWriter outMsg = null;
		
		@Override
		public void run(){
			boolean status = true;
			System.out.println("##ChatThread Start..");
			try{
				inMsg = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				outMsg = new PrintWriter(socket.getOutputStream(),true);
				//���� ���鼭 ���ŵ� �޽��� ó���ϱ�
				while(status){
					msg = inMsg.readLine();
					rmsg = msg.split("/");
					if(rmsg[1].equals("logout")){
						chatlist.remove(this);
						msgSendAll("Server/"+rmsg[0]+"���� �����߽��ϴ�.");
						//�ش� Ŭ���̾�Ʈ �÷��� ����
						status = false;
					}
					//�α��� �޽��� �� ���
					else if(rmsg[1].equals("login")){
						msgSendAll("Server/"+rmsg[0]+"���� �α����߽��ϴ�.");
						
					}else{
						msgSendAll(msg);
					}
				}
				//while����
				//������ ����� Ŭ���̾�Ʈ ������ ����ǹǷ� �����尡 ���ͷ�Ʈ�ȴ�.
				this.interrupt();
				System.out.println("##"+this.getName()+"stop!!");	
			}catch(IOException e){
				chatlist.remove(this);
				System.out.println("[ChatThread]run() IOException �߻�!");
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	

}
