package edu.learn.rest.JerseyMaven;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import edu.learn.rest.Entity.Transport;
import edu.learn.rest.Repository.TransportRepository;

@Path("xml")
public class TransportJSON {

	TransportRepository transportRepository = new TransportRepository();

	  @GET
	  @Produces(MediaType.APPLICATION_XML) 
	  public List<Transport> getAllTransport() {
		  System.out.println("Called getAllTransports() Method from Repository class");
		  return transportRepository.getAllTransport(); 
	  }
	  
	  @GET
	  @Path("/{units}")
	  @Produces(MediaType.APPLICATION_XML)
	  public Transport getTransportUnits(@PathParam("units") String units) 
	  {
		  System.out. println("Fetching source and destination with transport unit field : " + units ); 
		  return transportRepository.getTransportUnit(units); 
	  }
	 
	  
	  @POST
	  @Path("add")
	  public Transport createTransport(Transport trans) {
		  
		  System.out.println("Creating a Transport XML object :" + trans);
		  transportRepository.create(trans);
		  return trans;
	  }
}
