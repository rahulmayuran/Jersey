package edu.learn.rest.JerseyMaven;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import edu.learn.rest.Entity.Transport;
import edu.learn.rest.Repository.SQLTransportRepository;

@Path("json")
public class SQLController {

	private static final Logger LOGGER = Logger.getLogger(SQLController.class.getName());
	SQLTransportRepository transportRepository = new SQLTransportRepository();

	  @GET
	  @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML}) 
	  public List<Transport> getAllTransportfromDB() {
		  LOGGER.info("Called from SQLserver with the help of SQLTransportRepository");
		  return transportRepository.getListofTransportsFromDB(); 
	  }
	  
	  /*
	   * A simple check to print the units value binding to the units object
	   */
	  @GET
	  @Path("/{units}")
	    public Response getTransportsByunits(@PathParam("units") String units){
	         
	        return Response.status(200).entity("Got Transport with unit : " + units).build();
	    }
	  
	  @GET
	  @Path("unit/{units}")
	  @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	  public Transport getTransportUnits(@PathParam("units")String oneunit) 
	  {
		  LOGGER.info("Fetching source and destination with transport unit field : " + oneunit ); 
		  return transportRepository.fetchByTransportUnitsFromDB(oneunit); 
	  }
	 
	  
	  @POST
	  @Path("unit")
	  @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	  public Transport createTransport(Transport trans) throws SQLException {
		  
		  LOGGER.log(Level.INFO,"Creating a Transport JSON object :" + trans);
		  if(transportRepository.fetchByTransportUnitsFromDB(trans.getUnits()).getUnits()==null) 
		  {
			transportRepository.insertIntoSQLFromPostman(trans);
		  }
		  else {
			  LOGGER.fine("Seems like a duplicate entry of transport unit, Change the unit atleast");
		  }
			return trans;
	  }
	  
	  /*
	   * Can only change source and Destination fields, as we are comparing 
	   * with respect to Units field.
	   */
	  @PUT
	  @Path("unit")
	  @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	  public Transport updateTransport(Transport trans) throws SQLException {
		  
		  LOGGER.log(Level.INFO,"Updating a Transport JSON object :" + trans);
		  if(transportRepository.fetchByTransportUnitsFromDB(trans.getUnits()).getUnits() == null) 
		  {
			  LOGGER.log(Level.INFO,"Existing record updated to " + trans);
			  transportRepository.UpdateEntriesIntoDB(trans);
		  }
		  else
		  {
			  LOGGER.fine("Kindly don't change the Transport unit field :" + trans.getUnits());
			  transportRepository.duplicateEntry();
		  }
		  return trans;
	  }
	  
	  @DELETE
	  @Path("unit/{units}")
	  @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	  public Transport deleteTransport(@PathParam("units")String units) throws SQLException {
		  
		  LOGGER.log(Level.INFO,"Deleting a Transport JSON object with unit :" + units);
		  Transport deleteTransport = transportRepository.fetchByTransportUnitsFromDB(units);
		  
		  if(deleteTransport.getUnits()!=null) {
			  transportRepository.DeleteTransportsFromDB(units);
		  }
		  
		  return deleteTransport;
	  }
}
