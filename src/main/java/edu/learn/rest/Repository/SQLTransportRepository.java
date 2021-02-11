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
	
	/*
	 * Expects a select query as the details needs to be fetched from Database
	 */
	public List<Transport> getListofTransportsFromDB() {
		
		
		List<Transport> transportList = new ArrayList<>();
		
		String selectQuery = "Select * from dbo.JerseyTransport";

		try {
			Statement selectStatement = connect.createStatement();
			ResultSet rs = selectStatement.executeQuery(selectQuery);
			while(rs.next()) {
				Transport transport = new Transport();
				transport.setUnits(rs.getString("units"));
				transport.setSource(rs.getString("source"));
				transport.setDestination(rs.getString("destination"));
				transportList.add(transport);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			LOGGER.warning("Exception is printed for Connection failure with Datasource" + e);
		}
		
	
		return transportList;
		
		}
	
		public Transport fetchByTransportUnitsFromDB(String units) {
			
			
			Transport transport = new Transport();
			
			String selectUnitsQuery = "select * from dbo.JerseyTransport where units='"+ units+"'";
			try {
				Statement statement = connect.createStatement();
				ResultSet results =  statement.executeQuery(selectUnitsQuery);
				if(results.next()) {
					transport.setSource(results.getString("source"));
					transport.setDestination(results.getString("destination"));
					transport.setUnits(results.getString("units"));
				}

			} catch (SQLException e) {
				LOGGER.warning("Exception is printed for Connection failure with Datasource" + e);
			}
			return transport;	
		}
		
		public void insertIntoSQLFromPostman(Transport insertTransport) {
			
			String insertQuery = "insert into dbo.JerseyTransport values (?,?,?)";
			try {
				PreparedStatement prepareStatement = connect.prepareStatement(insertQuery);
				prepareStatement.setString(1, insertTransport.getSource());
				prepareStatement.setString(2, insertTransport.getDestination());
				prepareStatement.setString(3, insertTransport.getUnits());
				prepareStatement.executeUpdate();
				
			} catch (SQLException e) {
				
				LOGGER.warning("Exception is printed for Connection failure with Datasource" + e);
			}	
		}
		
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
}
