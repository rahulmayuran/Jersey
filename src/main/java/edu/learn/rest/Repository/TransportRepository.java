package edu.learn.rest.Repository;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import edu.learn.rest.Entity.Transport;

public class TransportRepository {

	
	  List<Transport> transportList;
	  
	  
	/* 
	 * Using a Repository Constructor to do CRUD operations 
	 * */
	  public TransportRepository() {
	  
	  transportList = new ArrayList<Transport>();
	  
	  Transport transport1 = new Transport(); transport1.setUnits("AK1010");
	  transport1.setSource("Rautpohja");
	  transport1.setDestination("Central Finland");
	  
	  Transport transport2 = new Transport(); transport2.setUnits("BK1010");
	  transport2.setSource("Helsinki"); transport2.setDestination("Factory Area");
	  
	  transportList.add(transport1); transportList.add(transport2); 
	  }
	  
	  
	/*
	 * A GET Request to produce a JSON response for returning the list of two
	 * transports
	 */
	  
	  public List<Transport> getAllTransport() 
	  {
	  
	  return transportList; 
	  }
	 
	  /*
	   * A GET requests to fetch the source and destination with 
	   * Transport unit as the common field
	   */
	  
	  public Transport getTransportUnit(String unit)
	  {
		  for(Transport trans : transportList)
		  {
			 if(trans.getUnits().equals(unit)) 
			 {
				 return trans;
			 }
		  }
		return null;
	  }


	public void create(Transport trans) {
		
		transportList.add(trans);
		
	}
}
