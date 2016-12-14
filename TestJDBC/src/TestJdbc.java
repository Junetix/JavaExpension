import java.sql.*;
public class TestJdbc {
/* 메인에서 throws..
	public static void main(String[] args) throws Exception {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		//driver 로딩
		
		Connection conn = DriverManager.getConnection(
				"jdbc:oracle:thin:@127.0.0.1:1521:orcl","scott","123456");
		//Connection 객체 생성
		Statement stmt = conn.createStatement();
		//sql 작성
		
		String sql="select ename, deptno from emp";
		//쿼리문 스트링에 담아
		ResultSet rset = stmt.executeQuery(sql);
		//statement에서 쿼리문을 execute 하면 결과가 저장된다.
		int deptno=0;
		String name = null;
		while(rset.next()){
			name = rset.getString("ename");
			deptno = rset.getInt("deptno");
			System.out.println(name+"        "+deptno);
		}
		

	}

} */
	//try catch 블럭에서..
	public static String  driver= "oracle.jdbc.driver.OracleDriver";
	
	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//driver 로딩
			
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@127.0.0.1:1521:orcl","scott","123456");
			//Connection 객체 생성
			//접속하면 커넥션 객체를 리턴
			// Statement는 쿼리문 실행에 필요한  객체
			stmt = conn.createStatement();
			//sql 작성
			
			String sql="select ename, deptno from emp";
			//쿼리문 스트링에 담아
			rset = stmt.executeQuery(sql);
			//statement에서 쿼리문을 execute 하면 결과가 저장된다.
			int deptno=0;
			String name = null;
			while(rset.next()){
				name = rset.getString("ename");
				deptno = rset.getInt("deptno");
				System.out.println(name+"        "+deptno);
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}finally{
			try{
				conn.close();
				stmt.close();
				rset.close();
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
		}
		}
		

}
