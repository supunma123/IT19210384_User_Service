package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class User {
	
	//A common method to connect to the DB
		private Connection connect()
		 {
			Connection con = null;
		 try
		 {
			 Class.forName("com.mysql.jdbc.Driver");
		
			 //Provide the correct details: DBServer/DBName, username, password
			 con= DriverManager.getConnection("jdbc:mysql://localhost:3306/loging", "root", "123456");
		 }
		 catch (Exception e)
		 {
			 e.printStackTrace();}
		     return con;
		 }
		
		//====================Client=============================================
		
		//=============insert Client Method===============
		
		public String insertUser(String fname, String lname, String pno, String email,String type, String pw)
		{
			String output = "";
			try
			{
				Connection con = connect();
				if (con == null)
				{
					return "Error while connecting to the database";
				}

				// create a prepared statement
				String query = " insert into user(`userId`,`firstName`,`lastName`,`phoneNo`,`email`,`type`,`password`)"
						 + " values (?, ?, ?, ?, ?,?,?)";
				PreparedStatement preparedStmt = con.prepareStatement(query);
				
				// binding values
				 preparedStmt.setInt(1, 0); 
				 preparedStmt.setString(2, fname); 
				 preparedStmt.setString(3, lname); 
				 preparedStmt.setString(4, pno); 
				 preparedStmt.setString(5, email);
				 preparedStmt.setString(6, type);
				 preparedStmt.setString(7, pw); 
				

				//execute the statement
				preparedStmt.execute();
			
				con.close();
				
		 		String newUser = readUsers(); 
				 output = "{\"status\":\"success\", \"data\": \"" + 
				 newUser + "\"}"; 
			}
			catch (Exception e)
			{
				output = "{\"status\":\"error\", \"data\": \"Error while inserting the Client.\"}";
				System.err.println(e.getMessage());
			}
			return output;
		}
		
		//=============Read all Clients ===============
		
		public String readUsers()
		 {
			 String output = "";
			 try
			 {
				 Connection con = connect();
			 if (con == null)
			 {
				 return "Error while connecting to the database for reading."; }
			 
			 // Prepare the html table to be displayed
			 output = "<table border='1'><tr>"+
			 "<th>First Name</th>" +
			 "<th>Last Name</th>" +
			 "<th>Phone Number</th>" +
			 "<th>Email</th>" +
			 "<th>Type</th>" +
			 "<th>Password</th>"
			 + "<th>Update</th><th>Remove</th></tr>";
			
			 String query = "select * from user";
			 Statement stmt = con.createStatement();
			 ResultSet rs = stmt.executeQuery(query);
			 
			 // iterate through the rows in the result set
			 while (rs.next())
			 {
				 String userId = Integer.toString(rs.getInt("userId")); 
				 String firstName = rs.getString("firstName"); 
				 String lastName = rs.getString("lastName"); 
				 String phoneNo = rs.getString("phoneNo"); 
				 String email = rs.getString("email");
				 String type = rs.getString("type");
				 String password = rs.getString("password");
				 
				 // Add into the html table
				 output += "<tr><td><input id='hidItemIDUpdate' name='hidItemIDUpdate' type='hidden' value='" + userId
							+ "'>" + firstName + "</td>";
				 output += "<td>" + lastName + "</td>"; 
				 output += "<td>" + phoneNo + "</td>"; 
				 output += "<td>" + email + "</td>"; 
				 output += "<td>" + type + "</td>"; 
				 output += "<td>" + password + "</td>";


				 
				 // buttons
		            output += "<td><input name='btnUpdate' type='button' value='Update' class=' btnUpdate btn btn-secondary' data-userid='" + userId + "'></td>"
		            		+ "<td><input name = 'btnRemove' type='button' value = 'Remove' "
		            		+ "class = 'btnRemove btn btn-danger' data-userid='" + userId + "'>"
		            		+"</td></tr>";
		            		
		 }
			 con.close();
			 
			 // Complete the html table
			 output += "</table>";
		 }
		 catch (Exception e)
		 {
			 output = "Error while reading Users.";
			 System.err.println(e.getMessage());
		 }
			 return output;
		 }
		
		//=============Updating a Client Method===============

		public String updateUser(String userId, String fname, String lname, String pno, String email, String type, String pw)
		{
			 String output = "";
			 try
			 {
			 Connection con = connect();
			 if (con == null)
			 {
				 return "Error while connecting to the database for updating."; }
				
			 	// create a prepared statement
			     String query = "UPDATE user SET firstName=?,lastName=?,phoneNo=?,email=?,type=?,password=? WHERE userId=?";
				 PreparedStatement preparedStmt = con.prepareStatement(query);
				 
				 
				 // binding values
				 preparedStmt.setString(1, fname); 
				 preparedStmt.setString(2, lname); 
				 preparedStmt.setString(3, pno); 
				 preparedStmt.setString(4, email);
				 preparedStmt.setString(5, type);
				 preparedStmt.setString(6, pw); 
				 
				 preparedStmt.setInt(7, Integer.parseInt(userId));
				 
				 // execute the statement
				 preparedStmt.execute();
				 con.close();
				 
			 		String newUser = readUsers(); 
					 output = "{\"status\":\"success\", \"data\": \"" + 
					 newUser + "\"}"; 
			 }
			 catch (Exception e)
			 {
					output = "{\"status\":\"error\", \"data\": \"Error while updating the Client.\"}";
					System.err.println(e.getMessage());
			 }
			 	return output;
			 }
		
		
		//=============Deleting Client Method===============
		
		public String deleteUser(String userId)
		 {
			String output = "";
		 try
		 {
			 Connection con = connect();
			 if (con == null)
			 {
				 return "Error while connecting to the database for deleting."; }
				 
			 	// create a prepared statement
				 String query = "delete from user where userId=?";
				 PreparedStatement preparedStmt = con.prepareStatement(query);
				 
				 // binding values
				 preparedStmt.setInt(1, Integer.parseInt(userId));
				 
				 // execute the statement
				 preparedStmt.execute();
				 con.close();
					String newUser = readUsers(); 
					 output = "{\"status\":\"success\", \"data\": \"" + 
					 newUser + "\"}"; 
			 }
			 catch (Exception e)
			 {
					output = "{\"status\":\"error\", \"data\": \"Error while deleting the item.\"}";
					System.err.println(e.getMessage());
			 }
			 	return output;
			 }

}
