import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StoredProcedure_IN {

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
			int increaseAmt = 10000;
			
			// show salaries BEFORE increase via store procedure
			System.out.println("Salaries before raise:\n");
			showSalaries(myConn, theDept);
			
			myStmt = myConn.prepareCall("{call increase_salaries_for_department(?, ?)}");  // prepare procedure call

			// set parameters
			myStmt.setString(1, theDept);
			myStmt.setDouble(2, increaseAmt);
			
			// call stored procedure
			System.out.println("\nCalling stored procedure to increase " + theDept + " Dept. salaries by " + increaseAmt);
			myStmt.executeQuery();
			System.out.println("End stored procedure");
			
			System.out.println("\n\nSalaries AFTER raise:\n");
			showSalaries(myConn, theDept);
			
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

	private static void showSalaries(Connection myConn, String theDepartment) throws SQLException {
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			// Prepare statement
			myStmt = myConn.prepareStatement("select * from employees where department=?");
			myStmt.setString(1, theDepartment);
			
			// Execute SQL query
			myRs = myStmt.executeQuery();

			// Process result set
			while (myRs.next()) {
				String lastName = myRs.getString("last_name");
				String firstName = myRs.getString("first_name");
				double salary = myRs.getDouble("salary");
				String department = myRs.getString("department");
				
				System.out.printf("%s, %s, %s, %.2f\n", lastName, firstName, department, salary);
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			close(myStmt, myRs);
		}
	}
	
	private static void close(Connection myConn, Statement myStmt,
			ResultSet myRs) throws SQLException {
		if (myRs != null) {
			myRs.close();
		}

		if (myStmt != null) {
			myStmt.close();
		}

		if (myConn != null) {
			myConn.close();
		}
	}

	private static void close(Statement myStmt, ResultSet myRs)
			throws SQLException {

		close(null, myStmt, myRs);
	}
	
}
