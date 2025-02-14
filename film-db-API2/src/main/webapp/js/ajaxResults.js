/*
Sends GET request to server to retrieve films
*/
function ajaxResultGet(address, resultRegion, contentType) {
	var request = getRequestObject();
	request.onreadystatechange =
		function() {
			handleFilm(request,
				resultRegion);
		};
	request.open("GET", address, true);
	request.setRequestHeader("Accept",
		contentType);
	request.send(null);
}

/*
Sends POST request to server to insert a film
*/
function ajaxResultInsert(address, data, resultRegion, contentType) {
	var request = getRequestObject();
	request.onreadystatechange =
		function() {
			showResponseText(request,
				resultRegion);
		};
	request.open("POST", address, true);
	request.setRequestHeader("Content-Type",
		contentType);
	request.send(data);
	//window.location.reload(true);
}

/*
Sends PUT request to server to update a film
*/
function ajaxResultUpdate(address, data, resultRegion, contentType) {
	var request = getRequestObject();
	request.onreadystatechange =
		function() {
			showResponseText(request,
				resultRegion);
		};
	request.open("PUT", address, true);
	request.setRequestHeader("Content-Type",
		contentType);
	request.send(data);
	//window.location.reload(true);
}

/*
Sends DELETE request to server to update a film
*/
function ajaxResultDelete(address, data, resultRegion, contentType) {
	var request = getRequestObject();
	request.onreadystatechange =
		function() {
			showResponseText(request,
				resultRegion);
		};
	request.open("DELETE", address, true);
	request.setRequestHeader("Content-Type",
		contentType);
	request.send(data);
	//window.location.reload(true);
}