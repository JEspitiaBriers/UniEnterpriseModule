/*
 * Calls a function to display a table when the page loads
 */
document.addEventListener("DOMContentLoaded", function() {
	getFilms('tableGen');
});

/*
 * reloads the page when the selected format changes
 */
function typeReload() {
	window.location.reload();
}

/*
 * Content type is selected by user, that selection is retrieved here
 * and then places in the headers of each CRUD request to server
 */
function setContentType() { //from ajaxCRUD.js
	var content = document.getElementById("format").value;
	var contentType;
	if (content == "xml") {
		contentType = "application/xml";
	}
	else if (content == "text") {
		contentType = "text/plain";
	}
	else { //default
		contentType = "application/json";
	}
	return contentType;
}

/*
 * Checks which CRUD function has been selected
 * converts relevant film data into correct format for the selected content type
 */
function applyContentType(type, input, func) { //from ajaxCRUD.js
	var data;

	/*
	 * Selected formatting type is XML
	 */
	if (type == "application/xml") {
		data = "";

		//checks requested CRUD function, manipulates string accordingly
		if (func == "delete") {
			data = data.concat("<id>" + input["id"] + "</id>");
		}
		else if (func == "insert") {
			data = data.concat("<film><title>" + input["title"].trim() +
			 "</title><year>" + input["year"].trim() +
				"</year><director>" + input["director"].trim() +
				 "</director><stars>" + input["stars"].trim() +
				"</stars><review>" + input["review"].trim() +
				 "</review></film>");
		}
		else {
			data = data.concat("<film><id>" + input["id"] + "</id>" +
			 "<title>" + input["title"].trim() + "</title><year>" + 
			 input["year"].trim() + "</year><director>" + input["director"].trim() +
			  "</director><stars>" + input["stars"].trim() +"</stars><review>" + 
			  input["review"].trim() + "</review></film>");
		}
	}

	/*
 	 * Selected formatting type is text
 	 */
	else if (type == "text/plain") {
		data = "";

		//checks requested CRUD function, manipulates string accordingly
		if (func == 'delete') {
			data = "id->" + input["id"] + "!";
		}
		else if (func == "insert") {
			data = data.concat("[title->" + input["title"].trim() + "!" + "year->"
			 + input["year"].trim() + "!" + "director->" +
				input["director"].trim() + "!" + "stars->" + input["stars"].trim() +
				 "!" + "review->" + input["review"].trim()
				+ "!]");
		} else {
			data = data.concat("[title->" + input["title"].trim() + "!" + "year->" + 
			input["year"].trim() + "!" + "director->" +
				input["director"].trim() + "!" + "stars->" + input["stars"].trim() + "!" + 
				"review->" + input["review"].trim() +
				"!" + "id->" + input["id"] + "!]");
		}
	}
	
	/*
	 * Selected formatting type is JSON (default)
	 */
	else {
		data = JSON.stringify(input);
	}
	return data;
}