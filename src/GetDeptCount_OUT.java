import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Types;

public class GetDeptCount_OUT {

	public static void main(String[] args) throws Exception {

		String url = "jdbc:mysql://localhost:3306/demo";
		String user = "student";
		String password = "student";

		Connection myConn = null;
		CallableStatement myStmt = null;

		try {
			myConn = DriverManager.getConnection(url, user, password);
			
			String theDept = "Engineering";
			
			myStmt = myConn.prepareCall("{call get_count_for_department(?, ?)}");  // prepare procedure call

			// register and set parameter
			myStmt.setString(1, theDept);  // set first param
			myStmt.registerOutParameter(2, Types.INTEGER);  // register 2nd param as OUT 
			
			// call stored procedure
			System.out.println("\nCalling stored procedure: get_count_for_department('" + theDept + "')");
			myStmt.execute();
			System.out.println("End stored procedure");
			
			int theCount = myStmt.getInt(2);  // get return value
			System.out.println("\nThe count = " + theCount);
			
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
