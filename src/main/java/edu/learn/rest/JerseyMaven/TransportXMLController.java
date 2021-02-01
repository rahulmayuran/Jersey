package edu.learn.rest.JerseyMaven;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import edu.learn.rest.Entity.Transport;

@Path("forknal")
public class TransportXMLController {

	@GET
	@Produces(MediaType.APPLICATION_XML)
	public List<Transport> getTransportAsXML() {
		
		System.out.println("Inside GET method for generating XML response");
		
		Transport transport1 = new Transport();
		transport1.setUnits("MumbaiUnit");
		transport1.setSource("Airoli");
		transport1.setDestination("Vikhroli");
		
		Transport transport2 = new Transport();
		transport2.setUnits("ChennaiUnit");
		transport2.setSource("ChennaiPort");
		transport2.setDestination("Oragadam");
		
		List<Transport> transportList = Arrays.asList(transport1,transport2);
		
		return transportList;
		
	}
}
