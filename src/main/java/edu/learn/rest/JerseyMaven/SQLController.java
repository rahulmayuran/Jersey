package edu.learn.rest.JerseyMaven;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
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

@Path("forknal")
public class SQLController {

	private static final Logger LOGGER = Logger.getLogger(SQLController.class.getName());
	SQLTransportRepository transportRepository = new SQLTransportRepository();

	  @GET
	  @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML}) 
	  public List<Transport> getAllTransportfromDB() {
		  LOGGER.info("Called from SQLserver with the help of SQLTransportRepository");
		  return transportRepository.getListofTransportsFromDB(); 
	  }
	  
	
	  @GET
	  @Path("unit/{units}")
	  @Produces(MediaType.APPLICATION_JSON) 
	  public Transport getTransportUnits(@PathParam("units") String units) 
	  {
		  LOGGER.info("Fetching source and destination with transport unit field : " + units );
		  return transportRepository.fetchByTransportUnitsFromDB(units); }
	 
	  
	  @GET
	  @Path("unit/check/{units}")
	  public Response getTransportsByunits(@PathParam("units") String units){
	         
	  	LOGGER.info("To print the message in Postman using Response.status().entity().build()");
	  	LOGGER.info("Fetching object from DB with this unit"+transportRepository.fetchByTransportUnitsFromDB(units).getUnits());
	  	
        if(units.equals(transportRepository.fetchByTransportUnitsFromDB(units).getUnits())) 
        {
        	return Response.status(200).entity("This unit : " + units+" matched with DB").build();
        }
        else {
        	return Response.status(200).entity("This unit : " + units+" is not matched with DB").build();
        }
	        
	    }
	 
	  
	  @POST
	  @Path("unit/add")
	  @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	  public Transport createTransport(Transport trans) {
		  
		  LOGGER.log(Level.INFO,"Creating a Transport JSON object :" + trans);
		  transportRepository.insertIntoSQLFromPostman(trans);
		  return trans;
	  }
	  
	  @PUT
	  @Path("unit/{units}")
	  @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	  public Transport updateTransport(Transport trans) throws SQLException {
		  
		  LOGGER.log(Level.INFO,"Inside updateTransport() before updation, The transport JSON object :" + trans);
		  
		  LOGGER.info("Is the unit field matching with DB ? " + 
		  transportRepository.fetchByTransportUnitsFromDB(trans.getUnits()).getUnits());
		  
		  LOGGER.info("Source field in  DB is " + 
				  transportRepository.fetchByTransportUnitsFromDB(trans.getUnits()).getSource());
		  
		  LOGGER.info("Destination field in  DB ? " + 
				  transportRepository.fetchByTransportUnitsFromDB(trans.getUnits()).getDestination());
		  
		  
		  LOGGER.info("The transport unit provided in the POSTMAN request body " + trans.getUnits());
		  
		  //The DB record match with Request body units
		  if(transportRepository.fetchByTransportUnitsFromDB(trans.getUnits()).getUnits().equals(trans.getUnits())) 
		  { 
			  transportRepository.UpdateEntriesIntoDB(trans);
			  LOGGER.log(Level.INFO,"Existing record updated to " + trans);
			  
		  }
		  else
		  {
			  Response.serverError();
			  LOGGER.info("Kindly don't change the Transport unit field :" + trans.getUnits());
		  }
		  return trans;
	  }
}
