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
	  
	  @GET
	  @Path("/{units}")
	  @Produces(MediaType.APPLICATION_JSON)
	  public Transport getTransportUnits(@PathParam("units") String units) 
	  {
		  LOGGER.info("Fetching source and destination with transport unit field : " + units ); 
		  return transportRepository.fetchByTransportUnitsFromDB(units); 
	  }
	  
	  @GET
	  @Path("unit/{units}")
	    public Response getTransportsByunits(@PathParam("units") String units){
	         
		  	LOGGER.info("To print the message in Postman using Response.status().entity().build()");
	        return Response.status(200).entity("Got Transport with unit : " + units).build();
	    }
	 
	  
	  @POST
	  @Path("add")
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
		  
		  LOGGER.log(Level.INFO,"Updating a Transport JSON object :" + trans);
		  LOGGER.info("Is the unit field matching with DB ? " + 
		  transportRepository.fetchByTransportUnitsFromDB(trans.getUnits()).getUnits());
		  
		  if(transportRepository.fetchByTransportUnitsFromDB(trans.getUnits()).getUnits() != null) 
		  {
			  LOGGER.log(Level.INFO,"Existing record updated to " + trans);
			  transportRepository.UpdateEntriesIntoDB(trans);
		  }
		  else
		  {
			  LOGGER.fine("Kindly don't change the Transport unit field :" + trans.getUnits());
				  for(int n=200;n<500;n++) {
				  Response.status(n).entity("Duplicate entries").build();
			  }
		  }
		  return trans;
	  }
}
