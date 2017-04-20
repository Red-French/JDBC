import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcDeleteDemo {

	public static void main(String[] args) throws SQLException {

		String url = "jdbc:mysql://localhost:3306/demo";
		String user = "student";
		String password = "student";

		Connection myConn = null;
		Statement myStmt = null;
		ResultSet results = null;

		try {
			myConn = DriverManager.getConnection(url, user, password);
			myStmt = myConn.createStatement();
			
			System.out.println("BEFORE THE DELETE...");
			results = myStmt.executeQuery("select * from employees");
			results.next();  // move cursor to first row (get error otherwise)
			System.out.println(results.getString("first_name") + " " + results.getString("last_name") + ", " + results.getString("email"));

			// DELETE the employee
			System.out.println("\nDELETING EMPLOYEE: John Doe\n");
			
			int rowAffected = myStmt.executeUpdate(
					"delete from employees " +
					"where last_name='Doe' and first_name='John'"
			);
			
			displayEmployee(myStmt, "John", "Doe");
			System.out.println("\nNUMBER OF ROWS AFFECTED: " + rowAffected);
			
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

	private static void displayEmployee(Statement myStmt, String firstName, String lastName) throws SQLException {

		ResultSet results2 = null;
		
		try {	
			
			results2 = myStmt.executeQuery("select * from employees where first_name='John' and last_name='Doe'");
			System.out.println("AFTER THE UPDATE...");
			
			while (results2.next()) {
				System.out.println(results2.getString("first_name") + " " + results2.getString("last_name") + ", " + results2.getString("email"));
			}
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
	}

}
