package com.ataraxia.IMS.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.ataraxia.IMS.Utils.DateUtils;
import com.ataraxia.Models.BSDate;

public class Registration {
    private Connection con;
    private static final String DB_URL = "jdbc:sqlite:src/main/resources/Database/registration.db";
    
    public Registration() {
        try {
            con = DriverManager.getConnection(DB_URL);
            createTable();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS registrations(" +
            "registration_no TEXT PRIMARY KEY," +
            "registration_year INTEGER," +  // Changed from bs_reg_year
            "registration_month INTEGER," +
            "registration_day INTEGER," +
            "president_name TEXT," +
            "institution_name TEXT," +
            "address TEXT," +
            "phone_no INTEGER," +
            "members_count INTEGER," +
            "expiry_year INTEGER," +  // Changed from bs_exp_year
            "expiry_month INTEGER," +
            "expiry_day INTEGER," +
            "verified_by TEXT)";
        
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.execute();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void insertBSRegistration(String registrationNo, BSDate registrationDate, String presidentName,
            String institutionName, String address, long phoneNo,
            int membersCount, BSDate expiryDate, String verifiedBy) throws SQLException {
        
        String sql = "INSERT INTO registrations (registration_no, " +
                "registration_year, registration_month, registration_day, " +
                "president_name, institution_name, address, phone_no, members_count, " +
                "expiry_year, expiry_month, expiry_day, verified_by) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        if(isRegistrationExist(registrationNo)) {
            throw new SQLException("Registration number already exists.");
        }
        
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, registrationNo);
            pstmt.setInt(2, registrationDate.getYear());
            pstmt.setInt(3, registrationDate.getMonth());
            pstmt.setInt(4, registrationDate.getDay());
            pstmt.setString(5, presidentName);
            pstmt.setString(6, institutionName);
            pstmt.setString(7, address);
            pstmt.setLong(8, phoneNo);
            pstmt.setInt(9, membersCount);
            pstmt.setInt(10, expiryDate.getYear());
            pstmt.setInt(11, expiryDate.getMonth());
            pstmt.setInt(12, expiryDate.getDay());
            pstmt.setString(13, verifiedBy);
            pstmt.executeUpdate();
        }
    }
    
    private boolean isRegistrationExist(String registrationNo) {
        String sql = "SELECT registration_no FROM registrations WHERE registration_no = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, registrationNo);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public ResultSet getAllRegistrations() {
        String sql = "SELECT *, " +
                   "registration_year || '/' || registration_month || '/' || registration_day as registration_date, " +
                   "expiry_year || '/' || expiry_month || '/' || expiry_day as expiry_date " +
                   "FROM registrations";
        try {
            return con.prepareStatement(sql).executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public int getTotalRegistrations() throws SQLException {
        String sql = "SELECT COUNT(*) AS total FROM registrations";
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            return rs.getInt("total");
        }
    }
    
    public int getExpiredRegistrationsCount() throws SQLException {
        BSDate today = DateUtils.getCurrentBSDate();
        
        String sql = "SELECT COUNT(*) AS expired FROM registrations WHERE " +
                    "(expiry_year < ?) OR " +
                    "(expiry_year = ? AND expiry_month < ?) OR " +
                    "(expiry_year = ? AND expiry_month = ? AND expiry_day < ?)";
                    
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, today.getYear());
            stmt.setInt(2, today.getYear());
            stmt.setInt(3, today.getMonth());
            stmt.setInt(4, today.getYear());
            stmt.setInt(5, today.getMonth());
            stmt.setInt(6, today.getDay());
            
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.getInt("expired");
            }
        }
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