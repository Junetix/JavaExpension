import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
//import java.sql.Statement;

public class InsertExam02 {

	public static void main(String[] args) throws Exception {
		String driver ="oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
		Class.forName(driver);
		
		Connection conn = DriverManager.getConnection(
				url,"scott","123456");
		String sql="Insert into member(num,name,addr,phone) Values(?,?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		
		String num, name, addr, phone;
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Member Table �� �߰��ϱ�");
		System.out.print("�й� �Է� : ");
		num = br.readLine();
		System.out.print("�̸� �Է� : ");
		name = br.readLine();
		System.out.print("�ּ� �Է� : ");
		addr = br.readLine();
		System.out.print("��ȭ��ȣ �Է� : ");
		phone = br.readLine();
		
		pstmt.setString(1, num);
		pstmt.setString(2, name);
		pstmt.setString(3, addr);
		pstmt.setString(4, phone);
		pstmt.executeUpdate();
		
		System.out.println("������ ���̽� ���� ����!");
		
	}

}