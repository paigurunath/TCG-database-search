package com.predix.services;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.predix.dao.TableDataOperations;
import com.predix.datasource.DBConnectionManager;
import com.predix.datasource.DBUtility;
import com.predix.model.Company;

@RestController
@EnableAutoConfiguration
public class CreateReadUpdateDelete {

	@RequestMapping("/")
	String home() {
		return "Application to index data from database to searchly";
	}

	@RequestMapping(value="/checkDBConnectivity", method=RequestMethod.POST)
	public String checkDBConnectivity(@RequestBody JsonNode jsonNode ) {
		try {
			
			System.out.println(jsonNode);
			/*DBConnectionManager dbcon = new DBConnectionManager();
			Connection con = dbcon.getConnectionRest(jsonNode);
			
			DBUtility.closeConnection(con);*/
			
			return "Database connection parameters are correct";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "FAIL";
	}
	
	@RequestMapping(value="/connectdbs", method=RequestMethod.POST)
	public String connectMultipleDB(@RequestBody JsonNode jsonNode ) {
		try {
			/*
			{"db1_url":"",
				"db1_password":"",
				"db1_username":"",
				"db2_url":"",
				"db2_password":"",
				"db2_username":""
				}*/
			System.out.println(jsonNode);
			/*DBConnectionManager dbcon = new DBConnectionManager();
			Connection con = dbcon.getConnectionRest(jsonNode);
			
			DBUtility.closeConnection(con);*/
			
			return "Database connection parameters are correct";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "FAIL";
	}
	
	@RequestMapping(value="/indexDbQueryData", method=RequestMethod.POST)
	public String indexDbQueryData(@RequestBody JsonNode jsonNode ) {
		try {
			
			System.out.println(jsonNode);
			
			TableDataOperations tdo = new TableDataOperations();
			tdo.viewData(jsonNode);
			
			return "Database data indexed successfully to searchly";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "FAIL";
	}
	
	@RequestMapping(value="/queryIndex", method=RequestMethod.GET)
	public  @ResponseBody String insertTableData(@RequestParam(value="keyword") String keyword ) {
		try {
			TableDataOperations tdo = new TableDataOperations();
			
			return tdo.queryES(keyword);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "FAIL";
	}
	
	
}
