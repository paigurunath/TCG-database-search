package com.predix.dao;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.JsonNode;
import com.predix.datasource.QueryInteractor;
import com.predix.model.Company;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.JestResult;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.indices.CreateIndex;

public class TableDataOperations {

	QueryInteractor qi = null;

	public String queryES(String keyword) {

		try {

			JestClientFactory factory = new JestClientFactory();
			factory.setHttpClientConfig(new HttpClientConfig
					.Builder("https://site:a58816bcdd053d309719f1adabc715b9@bifur-eu-west-1.searchly.com")
					.multiThreaded(true)
					.build());
			JestClient client = factory.getObject();

			//String query = "{ \"query\": { \"match\" : { \"person_name\" : \"MAR\" } } }";
			//String query = "{\"query\": { \"query_string\": {\"default_field\": \"person_name\", \"query\": \"MAR\" } } }";
			
			String query = "{\"query\":{\"wildcard\":{\"person_name\":{\"value\":\""+ keyword.trim() +"*\"}}}}";
			Search search = (Search) new Search.Builder(query)
					.addIndex("dbdatalocal")
					.addType("dbdatalocal")
					.build();
			JestResult result = client.execute(search);

			System.out.println("from cloud : " + result.getJsonString());
			client.shutdownClient();

			return result.getJsonString();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public JSONArray viewData(JsonNode jsonNode) {

		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObj = null;
		List<Company> companyList = new ArrayList<Company>();
		Company company = null;

		try {

			JestClientFactory factory = new JestClientFactory();
			factory.setHttpClientConfig(new HttpClientConfig
					.Builder("https://site:a58816bcdd053d309719f1adabc715b9@bifur-eu-west-1.searchly.com")
					.multiThreaded(true)
					.build());
			JestClient client = factory.getObject();
			//client.execute(new CreateIndex.Builder("dbdatalocal").build());

			Index index  = null; 
			qi = new QueryInteractor();
			List<Object> listObject = qi.getList(jsonNode.get("sqlquery").asText().trim(), jsonNode);

			List<String> columnNames = (List<String>)listObject.get(0);
			System.out.println("column name : " + columnNames);

			int k = 0;



			for(int i=1; i<listObject.size(); i++) {

				jsonObj = new JSONObject();
				Object[] obj = (Object[])listObject.get(i);

				for(String columnName: columnNames) {
					jsonObj.put(columnName, obj[k++].toString());
				}

				k = 0;
				index = new Index.Builder(jsonObj.toString()).index("dbdatalocal").type("dbdatalocal").build();
				client.execute(index);
			}

			client.shutdownClient();

			return jsonArray; 
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
