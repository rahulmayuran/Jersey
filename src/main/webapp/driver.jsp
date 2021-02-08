<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Driver view</title>
</head>
<link rel="stylesheet" href="CSS/index.css" />
<body>

<h1>Driver's view</h1>

<div class ="input-section" id="sec1">
    
    
			
			<label for="oldunit">Transport unit</label>
			<input type="text"  id="oldunit"  name="oldunit" value="${Transport}" readonly="true"/>
			
			
			<label for="olddest">Old destination location</label>
			<input type="text"  id="olddest"  name="olddest" value="${Destination}" readonly="true"/>
			
			<label for="newdest">New Location location</label>
			<input type="text"  id="newdest"  name="newdest"  required/>
			
			<button type="submit" value="updated">Fetch from LogApp</button>
	
</div>
</body>
</html>