package edu.learn.rest.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.learn.rest.Entity.Transport;

public class LogAppRepository 
{
	private static final Logger LOGGER = Logger.getLogger(SQLTransportRepository.class.getName());
	Connection connect = null;
	
	public LogAppRepository() 
	{
			try {
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				connect= DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=LocationForknal",
						"localforknal","");
				
				if(connect == null) 
				LOGGER.log(Level.WARNING, "Connection not established with SQL database");
				} 
			catch (SQLException | ClassNotFoundException e) 
					{
					// TODO Auto-generated catch block
					LOGGER.warning("Exception is printed for Connection failure with Datasource" + e);
					}	
				}

	public List<Transport> getListofTransportsFromLogAPPDB() {
		/*
		 * using the constructor to make a connection to Database. 
		 */
		List<Transport> transportList = new ArrayList<>();
		
		String selectQuery = "Select * from dbo.LogAppDB";

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
}
