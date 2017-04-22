import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Types;

public class GreetTheDepartment_INOUT {

	public static void main(String[] args) throws Exception {

		String url = "jdbc:mysql://localhost:3306/demo";
		String user = "student";
		String password = "student";

		Connection myConn = null;
		CallableStatement myStmt = null;

		try {
			myConn = DriverManager.getConnection(url, user, password);
			
			String theDept = "Engineering";
			
			myStmt = myConn.prepareCall("{call greet_the_department(?)}");  // prepare procedure call

			// register and set parameter
			myStmt.registerOutParameter(1, Types.VARCHAR);  // OUT parameter also handles INOUT
			myStmt.setString(1, theDept);
			
			// call stored procedure
			System.out.println("\nCalling stored procedure: greet_the_department('" + theDept + "')");
			myStmt.execute();
			System.out.println("End stored procedure");
			
			String theResult = myStmt.getString(1);
			System.out.println("\nThe result = " + theResult);
			
		} catch (Exception exc) {
			exc.printStackTrace();
			
		} finally {
			if (myConn != null) {
				myConn.close();
			}
			
			if (myStmt != null) {
				myStmt.close();
			}
		}
		}
	
}
