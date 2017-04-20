import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcInsertDemo {
	
public static void main(String[] args) throws SQLException {

	String url = "jdbc:mysql://localhost:3306/demo";
	String user = "student";
	String password = "student";

	Connection myConn = null;
	Statement myStmt = null;
	ResultSet myRs = null;

	try {
		// 1. Get a connection to database
		myConn = DriverManager.getConnection(url, user, password);

		// 	2. Create a statement
		myStmt = myConn.createStatement();

		// 3. Execute SQL query
		String sql = "insert into employees " + " (last_name, first_name, email)"
				+ " values ('Nelson', 'Willie', 'willie.nelson@pot.com')";
		myStmt.executeUpdate(sql);
		System.out.println("Insert complete.");
		
		// 4. Verify insert by getting a list of employees
		myRs = myStmt.executeQuery("select * from employees order by last_name");
		
		// 5. Process the result set
		while (myRs.next()) {
			System.out.println(myRs.getString("last_name") + ", " + myRs.getString("first_name"));
		}
		
	} catch (Exception exc) {
		exc.printStackTrace();
		
	} finally {
		if (myStmt != null) {
			myStmt.close();
		}
		if (myConn != null) {
			myConn.close();
		}
	}
	}
}