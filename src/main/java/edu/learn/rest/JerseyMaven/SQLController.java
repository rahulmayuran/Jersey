package edu.learn.rest.JerseyMaven;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
	 
	  
	  @POST
	  @Path("add")
	  @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	  public Transport createTransport(Transport trans) {
		  
		  LOGGER.log(Level.INFO,"Creating a Transport JSON object :" + trans);
		  transportRepository.insertIntoSQLFromPostman(trans);
		  return trans;
	  }
}
