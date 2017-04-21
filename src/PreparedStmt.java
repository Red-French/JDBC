import java.sql.*;

public class PreparedStmt {
	
	public static void main(String[] args) throws SQLException {

		String url = "jdbc:mysql://localhost:3306/demo";
		String user = "student";
		String password = "student";

		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet results = null;

		try {
			myConn = DriverManager.getConnection(url, user, password);
			myStmt = myConn.prepareStatement("select * from employees where salary > ? and department=?");
			
			System.out.println("Prepared statement: salary > 80000 and department = Legal\n");
			
			myStmt.setDouble(1, 80000);  // salary value
			myStmt.setString(2, "Legal");  // department value
			
			results = myStmt.executeQuery();
			
			displayEmployees(myStmt, results);
			
			System.out.println("\nReuse the prepared statement: salary > 25000 and department = HR \n");
			
			myStmt.setDouble(1, 25000);  // salary value
			myStmt.setString(2, "HR");  // department value
			
			results = myStmt.executeQuery();
			
			displayEmployees(myStmt, results);
			
			
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

	private static void displayEmployees(PreparedStatement myStmt, ResultSet results) throws SQLException {
		
		try {	
			while (results.next()) {
				
				String lastName = results.getString("last_name");
				String firstName = results.getString("first_name");
				double salary = results.getDouble("salary");
				String department = results.getString("department");
				
				System.out.printf("%s, %s, %.2f, %s\n", lastName, firstName, salary, department);
			}
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
	}

}
