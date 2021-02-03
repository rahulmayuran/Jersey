/**
 * 
 */
package edu.learn.rest.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.learn.rest.Entity.Transport;

/**
 * @author rahulks
 * Using normal jdbc driver without any ORM tools
 * to connect to DB
 *
 */
public class SQLTransportRepository {

	/*
	 * Static and final, so that nothing can change the value of this logger
	 */
	private static final Logger LOGGER = Logger.getLogger(SQLTransportRepository.class.getName());
	Connection connect = null;
/*
 * using the constructor to make a connection to Database. 
 */
	public SQLTransportRepository() 
	{
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			connect= DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=LocationForknal",
					"localforknal","");
			
			if(connect == null) 
			{
			LOGGER.log(Level.WARNING, "Connection not established with SQL database");
			}
			
			} catch (SQLException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				LOGGER.warning("Exception is printed for Connection failure with Datasource" + e);
			}	
		}
	
	public void duplicateEntry() {
		
		LOGGER.fine("Duplicate entry in Database");
		
	}
	
	/*
	 * Expects a select query as the details needs to be fetched from Database
	 * DQL -> needs a Statement interface, executeQuery(sql) method and result set that
	 * iterates every row of the Database.
	 */
	public List<Transport> getListofTransportsFromDB() {
		
		
		List<Transport> transportList = new ArrayList<>();
		
		String selectQuery = "Select * from dbo.JerseyTransport";

		try {
			Statement selectStatement = connect.createStatement();
			ResultSet rs = selectStatement.executeQuery(selectQuery);
			while(rs.next()) {
				Transport transport = new Transport();
				transport.setUnits(rs.getString(4));
				transport.setSource(rs.getString(2));
				transport.setDestination(rs.getString(3));
				
				transportList.add(transport);
				LOGGER.info("Object Count in while loop : " + transportList.size());
			}
			
		} catch (SQLException e) {
			LOGGER.warning("Exception is printed for Connection failure with Datasource" + e);
		}
		
		LOGGER.info("Fetching the list of Transports from the Database with : " + transportList);
		LOGGER.info("Total number of Transport Objects created is  : " + transportList.size());
		LOGGER.info("The URL hit in Postman is http://localhost:8082/JerseyMaven/api/json");
		return transportList;
		
		}
	
		public Transport fetchByTransportUnitsFromDB(String oneunit) {
			
		Transport transport = new Transport();
		
//		Play with "" and '' to make a string to get passed in Select statement
		String selectUnitsQuery = "select * from dbo.JerseyTransport where units='" +oneunit + "'";
		
		LOGGER.info("Query Check : select * from dbo.JerseyTransport where units='" +oneunit + "'");
		
			try {
				Statement statement = connect.createStatement();
				ResultSet results =  statement.executeQuery(selectUnitsQuery);
				
				if(results.next()) {
					transport.setSource(results.getString(2));
					transport.setDestination(results.getString(3));
					transport.setUnits(results.getString(4));
				}
	
			} 
			catch (SQLException e) {
				LOGGER.warning("Exception is printed for Connection failure with Datasource" + e.getMessage());
			}
			
			LOGGER.info("The Transport object fetched with unit :" + oneunit + " is : "+ transport);
			LOGGER.info("The url in postman is http://localhost:8082/JerseyMaven/api/json/unit/"+oneunit);
			return transport;	
		}
		
		/*
		 * As @Id is auto generated using SQL server, you need to mention
		 * the setString as 1,2,3 corresponding to the columns in SQL server
		 * Using preparedStatement for DML operations of insertion, deletion and updation of records
		 * Needs executeUpdate and setting values to specified positions in Database.
		 */
		public void insertIntoSQLFromPostman(Transport insertTransport) throws SQLException {
			
			String insertQuery = "insert into dbo.JerseyTransport values (?,?,?)";
			try 
			{
				PreparedStatement prepareStatement = connect.prepareStatement(insertQuery);
				prepareStatement.setString(1, insertTransport.getSource());
				prepareStatement.setString(2, insertTransport.getDestination());
				prepareStatement.setString(3, insertTransport.getUnits());	
				prepareStatement.executeUpdate();
				LOGGER.log(Level.INFO, "Transport object inserted from POSTman through JDBC : "+ insertTransport);
			}
			catch (SQLException e) {
				LOGGER.warning("Exception is printed for Connection failure with Datasource : " + e);
			}finally {
				connect.close();
			}
			
		}
		/* 
		 * As  is auto generated using SQL server, you need to mention
		 * the setString as 1,2,3 corresponding to the columns to update values
		 */
		public void UpdateEntriesIntoDB(Transport insertTransport) throws SQLException {
			
			String updateQuery = "Update dbo.JerseyTransport set source=?, destination=? where units=?";
			try 
			{
				PreparedStatement prepareStatement = connect.prepareStatement(updateQuery);
				prepareStatement.setString(1, insertTransport.getSource());
				prepareStatement.setString(2, insertTransport.getDestination());
				prepareStatement.setString(3, insertTransport.getUnits());	
				prepareStatement.executeUpdate();
				LOGGER.log(Level.INFO, "Transport object updated from POSTman through JDBC : "+ insertTransport);
			}
			catch (SQLException e) {
				LOGGER.warning("Exception is printed for Connection failure with Datasource : " + e);
			}finally {
				connect.close();
			}
			
		}

		/*
		 * Deleting with the url path /json/unit/{units}
		 * Should provide the unit and delete  
		 * 
		 */
		public void DeleteTransportsFromDB(String units) throws SQLException {
			
			String deleteQuery = "Delete from dbo.JerseyTransport where units=?";
			try 
			{
				PreparedStatement prepareStatement = connect.prepareStatement(deleteQuery);
				prepareStatement.setString(1, units);	
				prepareStatement.executeUpdate();
				LOGGER.log(Level.INFO, "Transport object deleted from POSTman through JDBC with unit: "+ units);
			}
			catch (SQLException e) {
				LOGGER.warning("Exception is printed for Connection failure with Datasource : " + e);
			}finally {
				connect.close();
			}
			
		}

		
	}
