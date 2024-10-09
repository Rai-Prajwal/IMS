package com.ataraxia.IMS.Database;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Usersdb {
    private static final String JDBC_URL = "jdbc:sqlite:src/main/resources/Database/users.db";

    public static void createUsersTable() throws SQLException {
        try (Connection connection = DriverManager.getConnection(JDBC_URL);
             Statement statement = connection.createStatement()) {
            
            String sql = "CREATE TABLE IF NOT EXISTS users (username VARCHAR(30) PRIMARY KEY, password_hash VARCHAR(255))";
            statement.executeUpdate(sql);
        }
    }
    
    public static void addUser(String username, String password) throws SQLException {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        
        try (Connection connection = DriverManager.getConnection(JDBC_URL);
             PreparedStatement pstmt = connection.prepareStatement("INSERT INTO users (username, password_hash) VALUES (?, ?)")) {
            
            pstmt.setString(1, username);
            pstmt.setString(2, hashedPassword);
            pstmt.executeUpdate();
        }
    }
    
    public static void createDefaultAdminAccount() throws SQLException{
    	if(!userExists()) {
    		addUser("admin","admin69");
    	}
    }
    
    private static boolean userExists() throws SQLException{
    	try(Connection connection = DriverManager.getConnection(JDBC_URL);
    		Statement statement = connection.createStatement();
    		ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) as count from users")){
    		
    		if(resultSet.next()) {
    			return resultSet.getInt("count") > 0;
    		}
    	}
    	return false;
    }
    
    public static boolean updateUser(String currentUsername, String newUsername, String newPassword) throws SQLException {
        String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
        
        try (Connection connection = DriverManager.getConnection(JDBC_URL);
             PreparedStatement pstmt = connection.prepareStatement("UPDATE users SET username = ?, password_hash = ? WHERE username = ?")) {
            
            pstmt.setString(1, newUsername);
            pstmt.setString(2, hashedPassword);
            pstmt.setString(3, currentUsername);
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    public static boolean authenticateUser(String username, String password) throws SQLException {
        return verifyUser(username, password);
    }

    public static boolean verifyUser(String username, String password) throws SQLException {
        try (Connection connection = DriverManager.getConnection(JDBC_URL);
             PreparedStatement pstmt = connection.prepareStatement("SELECT password_hash FROM users WHERE username = ?")) {
            
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String storedHash = rs.getString("password_hash");
                    return BCrypt.checkpw(password, storedHash);
                }
            }
        }
        return false;
    }
    
    public static User getUserByUsername(String username) throws SQLException {
        try (Connection connection = DriverManager.getConnection(JDBC_URL);
             PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM users WHERE username = ?")) {
            
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new User(rs.getString("username"), rs.getString("password_hash"));
                }
            }
        }
        return null;
    }
    
    public static class User {
        private String username;
        private String passwordHash;

        public User(String username, String passwordHash) {
            this.username = username;
            this.passwordHash = passwordHash;
        }

        public String getUsername() {
            return username;
        }

        public String getPasswordHash() {
            return passwordHash;
        }
    }
}