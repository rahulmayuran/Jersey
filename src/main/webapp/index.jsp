<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="edu.learn.rest.Repository.SQLTransportRepository"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="//code.jquery.com/jquery-migrate-1.2.1.min.js"></script>

<script> 
$(document).ready(function(){
	let transportButton = document.getElementById("getTransports"); 


	transportButton.addEventListener("click", 

	function buttonclickhandler() { 

		// Instantiate an new XHR Object 
		const xhr = new XMLHttpRequest(); 

		// Open an obejct (GET/POST, PATH, 
		// ASYN-TRUE/FALSE) 
		xhr.open("GET", 
				"http://localhost:8082/JerseyMaven/api/json", true);
		
		// When response is ready 
		xhr.onload = function () { 
			if (this.status === 200) { 

				//obj is the response obtained from postman
				let obj = this.response;
				console.log("Response object from GET request to fetch all transports " + obj);
				console.log("\n")
				
				console.log("Prototype of response object below");
				console.log(obj.__proto__);
				console.log("\n")

				objJson = JSON.parse(obj);
				console.log("Parsing the response object below");
				console.log(objJson); // In browser console, we can see the JSON
				console.log(objJson[0].source); // in 1st record of DB, at pos 0, the source is sweden
				console.log("\n")
				
				console.log("Prototype of parsed response object below")
				console.log(objJson.__proto__);
				console.log("\n")
				
				// Getting the ul element 
				let list = document.getElementById("list");
				
				StringConv = JSON.stringify(objJson);
				list.innerHTML = StringConv;

				let getUnitsFromDropDown = document.getElementById('unit').value;
				console.log(getUnitsFromDropDown);
				
				for(let j=0 ; j<objJson.length;j++)
					{
						console.log("Transport at position "+[j]);
						console.log(objJson[j]);

						if(objJson[j].units === getUnitsFromDropDown){
							console.log("\n")
							console.log("Unit name "+objJson[j].units+" matched at position "+ [j]);
							console.log("\n")
							
							let sourcefield = document.getElementById('source');
							let destinationfield = document.getElementById('destination');
							
							sourcefield.value = objJson[j].source;
							destinationfield.value = objJson[j].destination;
							}
					}
			} 
			else { 
				console.log("File not found"); 
			} 
		} 

		// At last send the request 
		xhr.send(); 
	});
});
</script> 


<meta charset="ISO-8859-1">
<title>Admin view</title>
</head>
<link rel="stylesheet" href="CSS/index.css" />



<body>
    <h1>Forknal Admin View!</h1>
    
	    <div class="input-section" id="divid">
	    <hr/>
	    JSON Responses
	    <hr/>
	    <p>Visit <a href="http://jersey.java.net">Project Jersey website</a>
	    for more information on Jersey!
  
    
	<%! String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";%>
	<%!String url = "jdbc:sqlserver://localhost:1433;databaseName=LocationForknal";%>
	<%!String user = "localforknal";%>
	<%!String psw = "";%>
	
<form id="getList" action="rest" method="post">

	<%
	Connection con = null;
	PreparedStatement ps = null;
	try
	{
	Class.forName(driverName);
	con = DriverManager.getConnection(url,user,psw);
	String sql = "Select * from dbo.JerseyTransport";
	ps = con.prepareStatement(sql);
	ResultSet rs = ps.executeQuery(); 
	%>
	
<label for="unit">Enter the transport unit</label>
<select id = "unit" name="unit" required>
<optgroup label="Transport Units" style="font-weight:bold;background-color: gray;color: white"></optgroup>
	<%
	while(rs.next())
	{
	String units = rs.getString("units"); 
	%>
<option value="<%=units %>"><%=units %></option>
	<%
	}
	%>
</select>
	<%
	}
	catch(SQLException sqe)
	{ 
	out.println(sqe);
	}
	%>
<label for="source">Enter the source location</label>
<input type="text"  id="source"  name="source"  required/>

<label for="destination">Enter the destination location</label>
<input type="text"  id="destination"  name="destination"  required/>

<button type="submit" value="sendunit" id="sunit">Send to Driver</button>

</form>

<button type="button" value="getTransports" id="getTransports">Get Transports->REST API</button>

<p id="list"></p>

  </div>
</body>
</html>
