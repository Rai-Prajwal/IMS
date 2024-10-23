package com.ataraxia.IMS.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import com.ataraxia.IMS.Utils.DateUtils;

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
				"registration_date Date,"+
				"president_name TEXT,"+
				"institution_name TEXT,"+
				"address TEXT,"+
				"phone_no INTEGER,"+
				"members_count INTEGER,"+
				"expiry_date Date,"+
				"verified_by TEXT)";
		
		try(PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.execute();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
    public void insertRegistration(String registrationNo, LocalDate registrationDate, String presidentName,
            String institutionName, String address, long phoneNo,
            int membersCount, LocalDate expiryDate, String verifiedBy) {
    	String sql = "INSERT INTO registrations (registration_no, registration_date, president_name, " +
    			"institution_name, address, phone_no, members_count, expiry_date, verified_by) " +
    			"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    	
    	if(isRegistrationExist(registrationNo)) {
    		System.out.println("Registration number already exists.");
    		return;
    	}
    	
    	try (PreparedStatement pstmt = con.prepareStatement(sql)) {
    		pstmt.setString(1, registrationNo);
    		pstmt.setDate(2, DateUtils.localDateToSqlDate(registrationDate));
    		pstmt.setString(3, presidentName);
    		pstmt.setString(4, institutionName);
    		pstmt.setString(5, address);
    		pstmt.setLong(6, phoneNo);
    		pstmt.setInt(7, membersCount);
    		pstmt.setDate(8, DateUtils.localDateToSqlDate(expiryDate));
    		pstmt.setString(9, verifiedBy);
    		pstmt.executeUpdate();
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    }
    
    private boolean isRegistrationExist(String registrationNo) {
    	String sql = "SELECT registration_no FROM registrations WHERE registration_no = ?";
    	try(PreparedStatement pstmt = con.prepareStatement(sql)){
    		pstmt.setString(1, registrationNo);
    		try(ResultSet rs = pstmt.executeQuery()){
    			return rs.next();
    		}
    	}catch(SQLException e) {
    		e.printStackTrace();
    	}
    	return false;
    }
    
    public ResultSet getAllRegistrations() {
    	String sql = "SELECT * FROM registrations";   	
    	try {
    		PreparedStatement pstmt = con.prepareStatement(sql);
    		return pstmt.executeQuery();
    	}catch (SQLException e) {
    		e.printStackTrace();
    	}
    	return null;
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
