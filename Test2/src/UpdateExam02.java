import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class UpdateExam02 {

	public static void main(String[] args) throws Exception {
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		
		Connection conn = DriverManager.getConnection(
				"jdbc:oracle:thin:@127.0.0.1:1521:orcl","scott","123456");
		
		
		String sql = "update member set addr =?, phone=? where num= ?";
		String num, name, addr, phone;
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Member Table 값 추가하기");
		System.out.print("수정할 학번 입력 : ");
		num = br.readLine();
		System.out.print("주소 입력 : ");
		addr = br.readLine();
		System.out.print("전화번호 입력 : ");
		phone = br.readLine();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1,addr);
		pstmt.setString(2,phone);
		pstmt.setString(3,num);
		pstmt.executeUpdate();
		System.out.println("데이터 베이스 수정 성공!");
		
	}

}
