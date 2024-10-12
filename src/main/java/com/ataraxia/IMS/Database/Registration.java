package com.ataraxia.IMS.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Registration {
	private Connection con;
	private static final String DB_URL = "jdbc:sqlite:src/main/resources/Database/registration.db";
	
	public Registration() {
		try {
			con = DriverManager.getConnection(DB_URL);
			createTable();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void createTable() {
		String sql ="CREATE TABLE IF NOT EXISTS registrations("+
				"registration_no TEXT PRIMARY KEY,"+
				"registration_date INTEGER,"+
				"president_name TEXT,"+
				"institution_name TEXT,"+
				"address TEXT,"+
				"phone_no INTEGER,"+
				"members_count INTEGER,"+
				"expiry_date INTEGER,"+
				"verified_by TEXT)";
		
		try(Statement stmt = con.createStatement()){
			stmt.execute(sql);
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
    public void insertRegistration(String registrationNo, int registrationDate, String presidentName,
            String institutionName, String address, long phoneNo,
            int membersCount, int expiryDate, String verifiedBy) {
    	String sql = "INSERT INTO registrations (registration_no, registration_date, president_name, " +
    			"institution_name, address, phone_no, members_count, expiry_date, verified_by) " +
    			"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    	try (PreparedStatement pstmt = con.prepareStatement(sql)) {
    		pstmt.setString(1, registrationNo);
    		pstmt.setInt(2, registrationDate);
    		pstmt.setString(3, presidentName);
    		pstmt.setString(4, institutionName);
    		pstmt.setString(5, address);
    		pstmt.setLong(6, phoneNo);
    		pstmt.setInt(7, membersCount);
    		pstmt.setInt(8, expiryDate);
    		pstmt.setString(9, verifiedBy);
    		pstmt.executeUpdate();
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    }
    
    public ResultSet getAllRegistrations() throws SQLException {
        String sql = "SELECT * FROM registrations";
        Statement stmt = con.createStatement();
        return stmt.executeQuery(sql);
    }
    
    public void closeConnection() {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
