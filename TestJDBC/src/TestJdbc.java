import java.sql.*;
public class TestJdbc {
/* ���ο��� throws..
	public static void main(String[] args) throws Exception {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		//driver �ε�
		
		Connection conn = DriverManager.getConnection(
				"jdbc:oracle:thin:@127.0.0.1:1521:orcl","scott","123456");
		//Connection ��ü ����
		Statement stmt = conn.createStatement();
		//sql �ۼ�
		
		String sql="select ename, deptno from emp";
		//������ ��Ʈ���� ���
		ResultSet rset = stmt.executeQuery(sql);
		//statement���� �������� execute �ϸ� ����� ����ȴ�.
		int deptno=0;
		String name = null;
		while(rset.next()){
			name = rset.getString("ename");
			deptno = rset.getInt("deptno");
			System.out.println(name+"        "+deptno);
		}
		

	}

} */
	//try catch ������..
	public static String  driver= "oracle.jdbc.driver.OracleDriver";
	
	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//driver �ε�
			
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@127.0.0.1:1521:orcl","scott","123456");
			//Connection ��ü ����
			//�����ϸ� Ŀ�ؼ� ��ü�� ����
			// Statement�� ������ ���࿡ �ʿ���  ��ü
			stmt = conn.createStatement();
			//sql �ۼ�
			
			String sql="select ename, deptno from emp";
			//������ ��Ʈ���� ���
			rset = stmt.executeQuery(sql);
			//statement���� �������� execute �ϸ� ����� ����ȴ�.
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
