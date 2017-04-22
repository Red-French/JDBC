import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetDeptEmployees_RESULTSET {

	public static void main(String[] args) throws SQLException {

		String url = "jdbc:mysql://localhost:3306/demo";
		String user = "student";
		String password = "student";

		Connection myConn = null;
		CallableStatement myStmt = null;
		ResultSet results = null;

		try {
			myConn = DriverManager.getConnection(url, user, password);
			
			String theDept = "Engineering";
			
			myStmt = myConn.prepareCall("{call get_employees_for_department(?)}");  // prepare procedure call

			myStmt.setString(1, theDept);  // set parameter
			
			// call stored procedure
			System.out.println("\nCalling stored procedure: get_employees_for_department('" + theDept + "')");
			myStmt.execute();
			System.out.println("End stored procedure");
			
			results = myStmt.getResultSet();
			displayResults(results);
			
		} catch (Exception exc) {
			exc.printStackTrace();
			
		} finally {
			if (myConn != null) {
				myConn.close();
			}
			
			if (results != null) {
				results.close();
			}
			
			if (myStmt != null) {
				myStmt.close();
			}
		}
		}
	
	private static void displayResults(ResultSet theResults) throws SQLException {

		try {
			// Process result set
			while (theResults.next()) {
				String lastName = theResults.getString("last_name");
				String firstName = theResults.getString("first_name");
				double salary = theResults.getDouble("salary");
				String department = theResults.getString("department");
				
				System.out.printf("%s, %s, %s, %.2f\n", lastName, firstName, department, salary);
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}
	
}
