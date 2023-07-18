package com.tap.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tap.model.Admin;

public class AdminDao {

	private String url="jdbc:mysql://localhost:3306/chaithradb";
	private String username="root";
	private String password="Chaitu@123";
	private String driver="com.mysql.cj.jdbc.Driver";
	
	
	private static final String INSERT_ADMINS_SQL = "insert into admin" + "  (name, email, password) values "
			+ " (?, ?, ?)";

	private static final String SELECT_ADMIN_BY_ID = "select id,name,email,password from admin where id =?";
	private static final String SELECT_ALL_ADMINS = "select * from admin";
	private static final String DELETE_ADMINS_SQL = "delete from admin where id = ?;";
	private static final String UPDATE_ADMINS_SQL = "update admin set name = ?,email= ?, password =? where id = ?;";
	
	public AdminDao() {
	}
	
	protected Connection getConnection() {
		
        Connection connection = null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url,username,password);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void insertAdmin(Admin admin) throws SQLException {
        System.out.println(INSERT_ADMINS_SQL);
        // try-with-resource statement will auto close the connection.
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ADMINS_SQL)) {
            preparedStatement.setString(1, admin.getName());
            preparedStatement.setString(2, admin.getEmail());
            preparedStatement.setString(3, admin.getPassword());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public Admin selectAdmin(int id) {
        Admin admin = null;
        
        // Step 1: Establishing a Connection
        try (Connection connection = getConnection();
            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ADMIN_BY_ID);) {
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                admin = new Admin(id, name, email, password);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return admin;
    }

    public List < Admin > selectAllAdmins() {

        // using try-with-resources to avoid closing resources (boiler plate code)
        List < Admin > admins = new ArrayList < > ();
        // Step 1: Establishing a Connection
        try (Connection connection = getConnection();

            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_ADMINS);) {
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                admins.add(new Admin(id, name, email, password));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return admins;
    }

    public boolean deleteAdmin(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(DELETE_ADMINS_SQL);) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    public boolean updateAdmin(Admin admin) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(UPDATE_ADMINS_SQL);) {
            statement.setString(1, admin.getName());
            statement.setString(2, admin.getEmail());
            statement.setString(3, admin.getPassword());
            statement.setInt(4, admin.getId());

            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
