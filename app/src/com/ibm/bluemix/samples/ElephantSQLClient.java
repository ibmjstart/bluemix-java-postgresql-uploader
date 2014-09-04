/*-------------------------------------------------------------------*/
/*                                                                   */
/* Copyright IBM Corp. 2013 All Rights Reserved                      */
/*                                                                   */
/*-------------------------------------------------------------------*/
/*                                                                   */
/*        NOTICE TO USERS OF THE SOURCE CODE EXAMPLES                */
/*                                                                   */
/* The source code examples provided by IBM are only intended to     */
/* assist in the development of a working software program.          */
/*                                                                   */
/* International Business Machines Corporation provides the source   */
/* code examples, both individually and as one or more groups,       */
/* "as is" without warranty of any kind, either expressed or         */
/* implied, including, but not limited to the warranty of            */
/* non-infringement and the implied warranties of merchantability    */
/* and fitness for a particular purpose. The entire risk             */
/* as to the quality and performance of the source code              */
/* examples, both individually and as one or more groups, is with    */
/* you. Should any part of the source code examples prove defective, */
/* you (and not IBM or an authorized dealer) assume the entire cost  */
/* of all necessary servicing, repair or correction.                 */
/*                                                                   */
/* IBM does not warrant that the contents of the source code         */
/* examples, whether individually or as one or more groups, will     */
/* meet your requirements or that the source code examples are       */
/* error-free.                                                       */
/*                                                                   */
/* IBM may make improvements and/or changes in the source code       */
/* examples at any time.                                             */
/*                                                                   */
/* Changes may be made periodically to the information in the        */
/* source code examples; these changes may be reported, for the      */
/* sample code included herein, in new editions of the examples.     */
/*                                                                   */
/* References in the source code examples to IBM products, programs, */
/* or services do not imply that IBM intends to make these           */
/* available in all countries in which IBM operates. Any reference   */
/* to the IBM licensed program in the source code examples is not    */
/* intended to state or imply that IBM's licensed program must be    */
/* used. Any functionally equivalent program may be used.            */
/*-------------------------------------------------------------------*/
package com.ibm.bluemix.samples;

import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ElephantSQLClient {
	
	public ElephantSQLClient() {
		try {
			createTable();
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}
		
	/**
	 * Grab text from ElephantSQL
	 * 
	 * @return List of Strings of text from ElephantSQL
	 * @throws Exception 
	 */
	public List<String> getResults() throws Exception {
		String sql = "SELECT text FROM posts ORDER BY id DESC";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet results = null;
		
		try {
			connection = getConnection();
			statement = connection.prepareStatement(sql);
			results = statement.executeQuery();
			List<String> texts = new ArrayList<String>();
			
			while (results.next()) {
				texts.add(results.getString("text"));
			} 
			
			return texts;
		} finally {
			if (results != null) {
				results.close();
			}
			
			if (statement != null) {
				statement.close();
			}
			
			if (connection != null) {
				connection.close();
			}
		}
	}
	
	/**
	 * Insert text into ElephantSQL
	 * 
	 * param posts List of Strings of text to insert
	 * @return number of rows affected
	 * @throws Exception 
	 * @throws Exception 
	 */
	public int addPosts(List<String> posts) throws Exception {
		String sql = "INSERT INTO posts (text) VALUES (?)";
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = getConnection();
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(sql);
			
			for (String s : posts) {
				statement.setString(1, s);
				statement.addBatch();
			}
			int[] rows = statement.executeBatch();
			connection.commit();
			
			return rows.length;
		} catch (SQLException e) {
			SQLException next = e.getNextException();
			
			if (next != null) {
				throw next;
			}
			
			throw e;
		} finally {
			if (statement != null) {
				statement.close();
			}
			
			if (connection != null) {
				connection.close();
			}
		}
	}
	
	/**
	 * Delete all rows from ElephantSQL
	 * @return number of rows affected
	 * @throws Exception 
	 */
	public int deleteAll() throws Exception {
		String sql = "DELETE FROM posts WHERE TRUE";
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = getConnection();
			statement = connection.prepareStatement(sql);
			return statement.executeUpdate();
		} finally {
			if (statement != null) {
				statement.close();
			}
			
			if (connection != null) {
				connection.close();
			}	
		}
	}
	
	private static Connection getConnection() throws Exception {
		Map<String, String> env = System.getenv();
		
		if (env.containsKey("VCAP_SERVICES")) {
			// we are running on cloud foundry, let's grab the service details from vcap_services
			JSONParser parser = new JSONParser();
			JSONObject vcap = (JSONObject) parser.parse(env.get("VCAP_SERVICES"));
			JSONObject service = null;
			
			// We don't know exactly what the service is called, but it will contain "elephantsql"
			for (Object key : vcap.keySet()) {
				String keyStr = (String) key;
				if (keyStr.toLowerCase().contains("elephantsql")) {
					service = (JSONObject) ((JSONArray) vcap.get(keyStr)).get(0);
					break;
				}
			}
			
			if (service != null) {
				JSONObject creds = (JSONObject) service.get("credentials");
				URI uri = URI.create((String) creds.get("uri"));
				String url = "jdbc:postgresql://" + uri.getHost() + ":" + uri.getPort() + uri.getPath();
				String username = uri.getUserInfo().split(":")[0];
				String password = uri.getUserInfo().split(":")[1];
				return DriverManager.getConnection(url, username, password);
			}
		}
		
		throw new Exception("No ElephantSQL service URL found. Make sure you have bound the correct services to your app.");
	}
	
	/**
	 * Create the posts table if it doesn't already exist
	 * 
	 * @throws Exception
	 */
	private void createTable() throws Exception {
		String sql = "CREATE TABLE IF NOT EXISTS posts (" +
						"id serial primary key, " +
						"text text" +
					 ");";
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			connection = getConnection();
			statement = connection.prepareStatement(sql);
			statement.executeUpdate();
		} finally {			
			if (statement != null) {
				statement.close();
			}
			
			if (connection != null) {
				connection.close();
			}
		}
	}
}
