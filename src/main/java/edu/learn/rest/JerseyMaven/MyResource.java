package edu.learn.rest.JerseyMaven;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     * Changing it to {@value JSON} in the MediaType , so that you can check in postman
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getIt() {
    	
        return "Got it!";
    }
}
