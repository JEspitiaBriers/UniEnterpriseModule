/*
 * calls ajaxResultGet() to handle GET request to server
 */
function getFilms(table) {
	var contentType = setContentType(); //in main.js
	ajaxResultGet("http://localhost:8080/film-db-API/film-api", table, contentType); //in ajaxResults.js
}

/*
 * Retrieves data from user input, passes data to ajaxResultInsert()
 * which handles POST (insert) request to server
 */
function insertFilm(messageBox) {
	var list = $('#titleI, #yearI, #directorI, #starsI, #reviewI')
	var input = {}
	list.each(function() {
		input[this.id.substring(0, this.id.length - 1)] = $(this).val()
	})
	var contentType = setContentType(); //in main.js
	var data = applyContentType(contentType, input, 'insert'); //in main.js
	ajaxResultInsert("http://localhost:8080/film-db-API/film-api", data, messageBox, contentType); //in ajaxResults.js
}

/*
 * Retrieves data from user input, passes data to ajaxResultUpdate()
 * which handles PUT (update) request to server
 */
function updateFilm(messageBox) {
	var list = $('#id, #title, #year, #director, #stars, #review')
	var input = {}
	list.each(function() {
		input[this.id] = $(this).val()
	})
	var contentType = setContentType(); //in main.js
	var data = applyContentType(contentType, input, 'update'); //in main.js
	ajaxResultUpdate("http://localhost:8080/film-db-API/film-api", data, messageBox, contentType); //in ajaxResults.js
}

/*
 * Retrieves data from pre-populated form, passes data to ajaxResultDelete()
 * which handles DELETE request to server
 */
function deleteFilm(messageBox) {
	var id = $('#id');
	var value = id.val();
	var keyValue = { "id": value };
	var contentType = setContentType(); //in main.js
	var data = applyContentType(contentType, keyValue, 'delete'); //in main.js
	ajaxResultDelete("http://localhost:8080/film-db-API/film-api", data, messageBox, contentType);  //in ajaxResults.js
}
