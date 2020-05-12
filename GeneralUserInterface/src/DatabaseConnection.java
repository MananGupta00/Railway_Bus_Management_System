

import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.SQLException; 

// This class can be used to initialize the database connection 
public class DatabaseConnection { 
	public static Connection initializeDatabase() 
		throws SQLException, ClassNotFoundException 
	{ 
		
		String dbDriver = "com.mysql.jdbc.Driver";
		String dbURL = "jdbc:mysql://localhost:3306/";
		// Database name to access
		String dbName = "railwaybus";
		String dbUsername = "root";
		String dbPassword = "manan";

		Class.forName(dbDriver); 
		Connection con = DriverManager.getConnection(dbURL + dbName, 
													dbUsername, 
													dbPassword); 
		return con; 
	} 
} 

