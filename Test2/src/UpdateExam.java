import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class UpdateExam {

	public static void main(String[] args) throws Exception {
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		
		Connection conn = DriverManager.getConnection(
				"jdbc:oracle:thin:@127.0.0.1:1521:orcl","scott","123456");
		Statement stmt = conn.createStatement();
		
		String sql = "update member set addr =";
		String num, name, addr, phone;
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Member Table �� �߰��ϱ�");
		System.out.print("������ �й� �Է� : ");
		num = br.readLine();
		System.out.print("�ּ� �Է� : ");
		addr = br.readLine();
		System.out.print("��ȭ��ȣ �Է� : ");
		phone = br.readLine();
		sql+= "'"+addr+"',phone="+"'"+phone+"' where num="+num;
		System.out.println(sql);
		stmt.executeUpdate(sql); //������ �ڷ᰹���� int ������ ����
		//stmt.executeQuery(sql); ResultSet�� ����
		System.out.println("������ ���̽� ���� ����!");
		
	}

}
