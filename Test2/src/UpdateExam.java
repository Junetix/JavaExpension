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
		System.out.println("Member Table 값 추가하기");
		System.out.print("수정할 학번 입력 : ");
		num = br.readLine();
		System.out.print("주소 입력 : ");
		addr = br.readLine();
		System.out.print("전화번호 입력 : ");
		phone = br.readLine();
		sql+= "'"+addr+"',phone="+"'"+phone+"' where num="+num;
		System.out.println(sql);
		stmt.executeUpdate(sql); //수정된 자료갯수를 int 형으로 리턴
		//stmt.executeQuery(sql); ResultSet을 리턴
		System.out.println("데이터 베이스 수정 성공!");
		
	}

}
