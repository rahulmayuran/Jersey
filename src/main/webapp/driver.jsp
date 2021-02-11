<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Driver view</title>
</head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="//code.jquery.com/jquery-migrate-1.2.1.min.js"></script>

<script> 
$(document).ready(function(){
	let transportButton = document.getElementById("mylogapp"); 


	transportButton.addEventListener("click", 

	function buttonclickhandler() { 

		// Instantiate an new XHR Object 
		const xhr = new XMLHttpRequest(); 

		// Open an obejct (GET/POST, PATH, 
		// ASYN-TRUE/FALSE) 
		xhr.open("GET", 
				"http://localhost:8082/JerseyMaven/api/logapp", true);
		
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

				let getUnitsFromDropDown = document.getElementById('oldunit').value; //Beijing
				console.log(getUnitsFromDropDown);
				
				for(let j=0 ; j<objJson.length;j++)
					{
						console.log("Transport at position "+[j]);
						console.log(objJson[j]);

						if(objJson[j].units === getUnitsFromDropDown){
							console.log("\n")
							console.log("Unit name "+objJson[j].units+" matched at position "+ [j]);
							console.log("\n")
							
							/* let sourcefield = document.getElementById('source'); */
							let destinationfield = document.getElementById('destination');
							
							/*  sourcefield.value = objJson[j].source; */
							newdest.value = objJson[j].destination;
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

<link rel="stylesheet" href="CSS/index.css" />
<body>

<h1>Driver's view</h1>

<div class ="input-section" id="sec1">
    
    
			
			<label for="oldunit">Transport unit</label>
			<input type="text"  id="oldunit"  name="oldunit" value="${Transport}" readonly="true"/>
			
			
			<label for="olddest">Old destination location</label>
			<input type="text"  id="olddest"  name="olddest" value="${Destination}" readonly="true"/>
			
			<label for="newdest">New Destination location</label>
			<input type="text"  id="newdest"  name="newdest" value="${Destination}"  required/>
			
			<button type="submit" value="updated" id="mylogapp">Fetch from LogApp</button>
			<p id = "list"></p>
	
</div>
</body>
</html>