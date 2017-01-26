package com.predix.datasource;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.JsonNode;

public class DBConnectionManager {

	Properties props = new Properties();
	Connection con = null;

	String CLASS_NAME = this.getClass().getName();
	Logger logger = Logger.getLogger(CLASS_NAME);

	public Connection getConnection() {
		
		logger.info("Entering " + CLASS_NAME);

		try {

	        Class.forName("org.postgresql.Driver");
	        con = DriverManager.getConnection("jdbc:postgresql://pellefant-01.db.elephantsql.com:5432/udwzgumv","udwzgumv","ZvRQU0t6v7byKb7C2G5Cv94V8wO1nEGI");
			
			if (con != null) {
				logger.info("Connection created Successfully !");
				System.out.println("Connection created Successfully !");
			} 
			
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return con;
	}
	
	public Connection getConnectionRest(JsonNode dbConnectionDetails) {
		
		logger.info("Entering " + CLASS_NAME);

		try {

			Class.forName("org.postgresql.Driver");
			//"jdbc:postgresql://pellefant-01.db.elephantsql.com:5432/udwzgumv","udwzgumv","ZvRQU0t6v7byKb7C2G5Cv94V8wO1nEGI"
	        //con = DriverManager.getConnection("jdbc:postgresql://pellefant-01.db.elephantsql.com:5432/udwzgumv","udwzgumv","ZvRQU0t6v7byKb7C2G5Cv94V8wO1nEGI");
	       
	        String connectUrl = dbConnectionDetails.get("jdbcconnectionurl").asText().trim();
	        String username  = dbConnectionDetails.get("username").asText().trim();
	        String password = dbConnectionDetails.get("password").asText().trim();
	        
	       /* if(connectUrl.trim().equalsIgnoreCase(connectUrl_copy)) {
	        	System.out.println("url matches");
	        }
	        if(username.trim().equalsIgnoreCase(username_copy)) {
	        	System.out.println("username matches");
	        }
	        if(password.trim().equalsIgnoreCase(password_copy)) {
	        	System.out.println("password matches");
	        }
	        */
			con = DriverManager.getConnection(connectUrl,username,password);
			
	        
			if (con != null) {
				logger.info("Connection created Successfully !");
				System.out.println("Connection created Successfully !");
			} 
			
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} 
		return con;
	}
	
	public Connection getConnectionMultiple(JsonNode dbConnectionDetails) {
		
		logger.info("Entering " + CLASS_NAME);
		Connection con1 = null;
		try {

			Class.forName("org.postgresql.Driver");
			//"jdbc:postgresql://pellefant-01.db.elephantsql.com:5432/udwzgumv","udwzgumv","ZvRQU0t6v7byKb7C2G5Cv94V8wO1nEGI"
	        //con = DriverManager.getConnection("jdbc:postgresql://pellefant-01.db.elephantsql.com:5432/udwzgumv","udwzgumv","ZvRQU0t6v7byKb7C2G5Cv94V8wO1nEGI");
	       
	        String db1_url = dbConnectionDetails.get("db1_url").asText().trim();
	        String db1_username  = dbConnectionDetails.get("db1_username").asText().trim();
	        String db1_password = dbConnectionDetails.get("db1_password").asText().trim();
	        
			con = DriverManager.getConnection(db1_url,db1_username,db1_password);
	        
			if (con != null) {
				logger.info("Connection 1 created Successfully !");
				System.out.println("Connection 1 created Successfully !");
			} 
			
			String db2_url = dbConnectionDetails.get("db2_url").asText().trim();
	        String db2_username  = dbConnectionDetails.get("db2_username").asText().trim();
	        String db2_password = dbConnectionDetails.get("db2_password").asText().trim();
	        
			con1 = DriverManager.getConnection(db2_url,db2_username,db2_password);
	        
			if (con1 != null) {
				logger.info("Connection 2 created Successfully !");
				System.out.println("Connection 2 created Successfully !");
			} 
			
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} 
		return con;
	}
	
	/*public static void main(String args[]) {
		DBConnectionManager dbc = new DBConnectionManager();
		dbc.getConnection();
	}*/
}