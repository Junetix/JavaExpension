import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Delete {

	public static void main(String[] args) throws Exception {
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		
		Connection conn = DriverManager.getConnection(
				"jdbc:oracle:thin:@127.0.0.1:1521:orcl","scott","123456");
		Statement stmt = conn.createStatement();
		
		String sql = "delete from member ";
		String num, name, addr, phone;
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Member Table 값 추가하기");
		System.out.print("삭제 학번 입력 : ");
		num = br.readLine();
		sql+= "where num="+num;
		System.out.println(sql);
		int res =stmt.executeUpdate(sql);
		if(res!=0){
			System.out.println("데이터 베이스 내용삭제 성공!");
		}else{
			System.out.println("데이터 베이스 내용삭제 실패!(학번없음)");
		}
		
		
	}

}
