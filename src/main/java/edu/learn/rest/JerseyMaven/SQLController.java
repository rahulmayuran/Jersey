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

/*  Controller class
 *  value of @Path is json, 
 *  so that you have to hit http://(localhost:port)/(ProjectName/contextPath)/(web.xml servlet mapping)/(@Path value)
 *  Here it is http://localhost:8082/JerseyMaven/api/json
 */
@Path("json")
public class SQLController {

	//To log everything happening in Controller class
	private static final Logger LOGGER = Logger.getLogger(SQLController.class.getName());
	
	/* Autowiring annotation should be helpful to inject the dependencies
	 * but as we are working with corejava and JDBC without any beans.xml or
	 * spring annotations, it is not applicable here
	*/
	SQLTransportRepository transportRepository = new SQLTransportRepository();

	  //GET is a public @interface of HttpMethod
	  @GET
	  @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML}) 
	  public List<Transport> getAllTransportfromDB() 
	  {
		  LOGGER.info("Called from SQLserver with the help of SQLTransportRepository");
		  return transportRepository.getListofTransportsFromDB(); 
	  }
	  
	  /*
	   * A simple check to print the units value binding to the units object
	   */
	  @GET
	  @Path("/{units}")
	    public Response getTransportsByunits(@PathParam("units") String units){
	         
		  	LOGGER.info("To print the message in Postman using Response.status().entity().build()");
	        return Response.status(200).entity("Got Transport with unit : " + units).build();
	    }
	  
	  /*
	   * The method level annotations will always override the Class level annotations
	   * In that sense, you have to add the unit/BK1010 etc to url path and hit URL.
	   * Using @PathParam , a binding happens with the url that you hit in postman and it's
	   * value is copied to this oneunit field, that is sent to repository class to fetch Transport Object
	   */
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
		  LOGGER.info("Value is " + transportRepository.fetchByTransportUnitsFromDB(trans.getUnits()).getUnits());
		  LOGGER.info("Value is " + transportRepository.fetchByTransportUnitsFromDB(trans.getUnits()));
		  
		  /* This condition will check the transport unit field to fetch the Transport object
		   *  and that matches with the value of transportUnit field that you are hitting 
		   *  from the postman are equal or not.
		   *  if null, then it is a new unit and can be inserted.
		   */
		  if(transportRepository.fetchByTransportUnitsFromDB(trans.getUnits()).getUnits()==null) 
		  {
			transportRepository.insertIntoSQLFromPostman(trans);
		  }
		  else 
		  {
		  	  LOGGER.log(Level.FINE,"Duplicate Transport unit entry :" + trans.getUnits());
			  Response.status(200).entity("Duplicate entries").build(); 
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
				  for(int n=200;n<500;n++) {
				  Response.status(n).entity("Duplicate entries").build();
			  }
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
		  else {
			 
				  Response.status(200).entity("Unit : "+units+" not found and delete operation can't be done").build();
			  
		  }
		  
		  return deleteTransport;
	  }
}
