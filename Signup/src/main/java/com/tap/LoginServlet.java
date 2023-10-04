package com.tap;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
    public LoginServlet() {
        super();
       
    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			PrintWriter out = response.getWriter();
			Class.forName("com.mysql.cj.jdbc.Driver");
		    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/chaithradb","root","Chaitu@123");
		    String name =request.getParameter("username");
		    String password=request.getParameter("password");
		    String email=request.getParameter("email");
            PreparedStatement ps=con.prepareStatement("select * from students where name=? and password=? and email=?");
            ps.setString(1, name);
            ps.setString(2, password);
            ps.setString(3, email);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
            	RequestDispatcher rd=request.getRequestDispatcher("Welcome");
            	rd.include(request, response);
            }
            else {
            	out.println("<font color=red size=18>login failed!!<br>");
            	out.println("<a href=Login.html>Try Again!!");
            }
		
		
		
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
