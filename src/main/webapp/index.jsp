<%@page import="edu.learn.rest.Repository.SQLTransportRepository"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Admin view</title>
</head>
<link rel="stylesheet" href="CSS/index.css" />
<body>
    <h1>Admin View!</h1>
    
	    <div class="input-section">
	    <hr/>
	    JSON Responses
	    <hr/>
	    
	    <%
	    SQLTransportRepository repository = new SQLTransportRepository();
	    %>
	    
	    <form action="rest" method="post">
		
			<label for="unit">Enter the transport unit</label>
			<input type="text"  id="unit"  name="unit"  required/>
			
			<label for="source">Enter the source location</label>
			<input type="text"  id="source"  name="source"  required/>
			
			<label for="destination">Enter the destination location</label>
			<input type="text"  id="destination"  name="destination"  required/>
			
			<button type="submit" value="sendunit" id="sq">Send to Driver</button>
		
		</form>
	    
	    <p>Visit <a href="http://jersey.java.net">Project Jersey website</a>
	    for more information on Jersey!
    </div>
    
    
</body>
</html>
