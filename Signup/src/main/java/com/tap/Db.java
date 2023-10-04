package com.tap;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Db {
	private String url="jdbc:mysql://localhost:3306/chaithradb";
	private String username="root";
	private String password="Chaitu@123";
	private String driver="com.mysql.cj.jdbc.Driver";
	public void loadDriver(String driver) 
	{
	    try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public Connection getConnection()
	{
		Connection con=null;
		try {
			con = DriverManager.getConnection(url,username,password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}
    public String insert(Student student)
    {
    	loadDriver(driver);
    	Connection con=getConnection();
    	String result="data entered successfully";
    	String sql="insert into chaithradb.students values(?,?,?,?,?,?)";
    	try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1,student.getName());
			ps.setString(2,student.getEmail());
			ps.setString(3, student.getPhone());
			ps.setString(4, student.getGender());
			ps.setString(5, student.getBranch());
			ps.setString(6, student.getPassword());
			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			result="Data not entered";
		}
		return result;
    	
    }

}
