package edu.learn.rest.JerseyMaven;

import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import edu.learn.rest.Entity.Transport;
import edu.learn.rest.Repository.LogAppRepository;

@Path("logapp")
public class LogAppController {

	private static final Logger LOGGER = Logger.getLogger(SQLController.class.getName());
	LogAppRepository logappRepository = new LogAppRepository();

	  @GET
	  @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML}) 
	  public List<Transport> getAllTransportfromDB() 
	  {
		  LOGGER.info("Called from SQLserver with the help of LogAPPRepository");
		  return logappRepository.getListofTransportsFromLogAPPDB(); 
	  }
	
	
}
