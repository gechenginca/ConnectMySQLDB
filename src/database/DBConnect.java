package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBConnect {
	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;
	private static final String URL = "jdbc:mysql://localhost:3306/world";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "root";
//	private static final String URL = "jdbc:mysql://mathlab.utsc.utoronto.ca:3306/cscc43f17_gecheng_world";
//	private static final String USERNAME = "gecheng";
//	private static final String PASSWORD = "gecheng";
	
	public DBConnect() {
		try {
			// Load driver
			Class.forName("com.mysql.jdbc.Driver");
			// Connect database
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			// Create statement
			statement = connection.createStatement();
		} catch(Exception e) {
			System.out.println("Error: " + e);
		}
	}
	
	public void getData1() {
		try {
			// MySQL query
			String query = "select country.name , code\n" + 
					"from country , city\n" + 
					"where country.code = city.CountryCode\n" + 
					"group by country.Name, code\n" + 
					"having (select count(name) from city where CountryCode = code) > 50;";
			// Execute query
			resultSet = statement.executeQuery(query);
			// Print result
			while(resultSet.next()) {
				System.out.println(resultSet.getString("Name") + " " + resultSet.getString("Code"));
			}
		} catch(Exception e) {
			System.out.println("Error: " + e);
		}
	}
	
	public void getData2() {
		try {
			// MySQL query
			String query = "select count(language) as cnt , language\n" + 
					"from countrylanguage\n" + 
					"group by Language\n" + 
					"order by cnt desc\n" + 
					"limit 1;";
			// Execute query
			resultSet = statement.executeQuery(query);
			// Print result
			while(resultSet.next()) {
				System.out.println(resultSet.getInt("cnt") + " " + resultSet.getString("Language"));
			}
		} catch(Exception e) {
			System.out.println("Error: " + e);
		}
	}
	
	public static void main(String[] args) {
		DBConnect connect = new DBConnect();
		System.out.println("Query1");
		connect.getData1();
		System.out.println();
		System.out.println("Query2");
		connect.getData2();
	}
}
