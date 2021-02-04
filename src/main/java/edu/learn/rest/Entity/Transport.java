package edu.learn.rest.Entity;

/*
 * Entity class/ Model class 
 * without the usage of any annotations, 
 */
public class Transport {

	private String units;
	private String source;
	private String destination;
	public String getUnits() {
		return units;
	}
	public void setUnits(String units) {
		this.units = units;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	@Override
	public String toString() {
		return "Transport [units=" + units + ", source=" + source + ", destination=" + destination + "]";
	}
	
	
}
