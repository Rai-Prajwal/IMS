package com.ataraxia.IMS.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Usersdb {
	
	public static void createUsersTable(String[] args) throws SQLException{
		String jdbcUrl = "jdbc:sqlite:src/main/resources/Database/users.db";
		Connection connection = DriverManager.getConnection(jdbcUrl);
		
		String sql = "create table users (username varchar(30), password_hash varchar(255))";
		Statement statement = connection.createStatement();
		statement.executeUpdate(sql);
		
		statement.close();
		connection.close();
		
	}
}
