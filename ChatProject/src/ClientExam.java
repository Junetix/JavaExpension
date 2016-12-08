import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientExam {

	public static void main(String[] args) {
		Socket socket = null;
		try {
			socket = new Socket("192.168.0.206", 9001);
			Thread thread1 = new SenderThread(socket);
			Thread thread2 = new ReceiverThread(socket);
			thread1.start();
			thread2.start();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}

class SenderThread extends Thread {
	Socket socket;

	public SenderThread(Socket socket) {
		this.socket = socket;

	}
	@Override
	public void run(){
		try{
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			PrintWriter writer = new PrintWriter(socket.getOutputStream());
			while(true){
				String str = reader.readLine();
				if(str.equals("bye")){
					break;
				}
				writer.println(str);
				writer.flush();
			}
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}finally{
			try{
				socket.close();
			}catch(Exception e){
				
			}
		}
	}
}



class ReceiverThread extends Thread{
	Socket socket;
	public ReceiverThread(Socket socket) {
		this.socket= socket;
	}
	@Override
	public void run(){
		try{
			BufferedReader reader= new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while(true){
				String str = reader.readLine();
				if(str==null){
					break;
				}	
				System.out.println("¼ö½Å>"+str);
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
}