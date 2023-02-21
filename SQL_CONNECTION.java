package com.test.lucene;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

class SQL_CONNECTION {


	public static void main(String[] args) throws ClassNotFoundException {
		// TODO Auto-generated method stub
               try {
            	   Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            	   System.out.println("Command executed successfully");
            	   String connString="jdbc:sqlserver://WIN2012\\SQLEXPRESS;databaseName=ANIMALS;integratedSecurity=true;encrypt=false;";
            	   Connection conn=DriverManager.getConnection(connString);
            	   System.out.println("Connection Successful!!");
           
            	   java.sql.Statement stmt=conn.createStatement();
            	   String query="select * from Page_Details";
            	   ResultSet rs= stmt.executeQuery(query);
            	   while(rs.next()) {
            		   System.out.println(rs.getString(1)+"   "+rs.getString(2)+"   "+rs.getString(3)+"   "+rs.getString(4));
            	   }
                   
               } catch(SQLException e) {
            	   System.out.println("Connection Failed");
            	   e.printStackTrace();
               }
               
	}
}
