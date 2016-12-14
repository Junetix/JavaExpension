import java.sql.Connection;
 
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SelectExam {

	public static void main(String[] args) throws Exception{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		
		Connection conn = DriverManager.getConnection(
				"jdbc:oracle:thin:@127.0.0.1:1521:orcl","scott","123456");
		Statement stmt = conn.createStatement();
		
		ResultSet rset = stmt.executeQuery("select * from member");
	
		String num,name,addr,phone,result=null;
		System.out.println("num\t"+"name\t"+"addr\t"+"phone\t");
		while(rset.next()){
			num=rset.getString("num");
			name=rset.getString("name");
			addr=rset.getString("addr");
			phone=rset.getString("phone");	
			result =num+"\t"+name+"\t"+addr+"\t"+phone+"\t";
		}		
		System.out.println(result);

	}

}
