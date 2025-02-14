// Get the browser-specific request object
function getRequestObject() {
	if (window.XMLHttpRequest) { //For modern browsers
		return (new XMLHttpRequest());
	} else if (window.ActiveXObject) { //For Internet explorer
		return (new ActiveXObject("Microsoft.XMLHTTP"));
	} else { //invalid broswer
		return (null);
	}
}

//Put response text in the HTML element that has given ID.
function showResponseText(request, resultRegion) {
	if ((request.readyState == 4) &&
		(request.status == 200)) {
		htmlInsert(resultRegion, request.responseText);
	}
}

//Handles data according to format type and initiates DataTable
function handleFilm(request, resultRegion) {
	if ((request.readyState == 4) && (request.status == 200)) {
		var filmsAll = request.responseText;
		var allFilms;
		const headers = request.getAllResponseHeaders();

		//Content type is XML
		if (headers.includes("application/xml")) {

			//Make DOM parser to convert data from XML
			const parser = new DOMParser();
			var xmlDoc = parser.parseFromString(filmsAll, "application/xml");
			var filmNum = xmlDoc.getElementsByTagName("film");
			var xmlString = '[';

			//Iterates film attributes, making them into valid JSON format
			for (var i = 0; i < filmNum.length; i++) {
				var id = xmlDoc.getElementsByTagName("id")[i].childNodes[0].nodeValue;
				var title = xmlDoc.getElementsByTagName("title")[i].childNodes[0].nodeValue;
				var year = xmlDoc.getElementsByTagName("year")[i].childNodes[0].nodeValue;
				var director = xmlDoc.getElementsByTagName("director")[i].childNodes[0].nodeValue;
				var stars = xmlDoc.getElementsByTagName("stars")[i].childNodes[0].nodeValue;
				var review = xmlDoc.getElementsByTagName("review")[i].childNodes[0].nodeValue;
				xmlString = xmlString.concat('{"id":' + id + ', "title":"' + title + '", "year":' +
					year + ', "director":"' + director + '", "stars":"' + stars + '", "review":"' + review + '"},');
			}

			//Parses new string into JSON, which DataTables can read the data from
			xmlString = xmlString.substring(0, xmlString.length - 1).concat(']');
			allFilms = JSON.parse(xmlString);
		}

		//Content type is text
		else if (headers.includes("text/plain")) {

			var filmSingle = filmsAll.substring(1,filmsAll.length-1).split("][");
			var textStringAfter;
			var textString = '[';

			//Iterates film attributes, making them into valid JSON format
			for (var i = 0; i < filmSingle.length; i++) {
				var filmString = filmSingle[i].toString();
				var id = filmString.substring(filmString.indexOf("id") + 4, filmString.indexOf("title") - 1);
				var title = filmString.substring(filmString.indexOf("title") + 7, filmString.indexOf("year") - 1);
				var year = filmString.substring(filmString.indexOf("year") + 6, filmString.indexOf("director") - 1);
				var director = filmString.substring(filmString.indexOf("director") + 10, filmString.indexOf("stars") - 1);
				var stars = filmString.substring(filmString.indexOf("stars") + 7, filmString.indexOf("review") - 1);
				var review = filmString.substring(filmString.indexOf("review") + 8, filmString.length);
				textString = textString.concat('{"id":' + id + ', "title":"' + title + '", "year":' + year +
					', "director":"' + director + '", "stars":"' + stars + '", "review":"' + review + '"},');
			}

			//Parses new string into JSON, which DataTables can read the data from
			textStringAfter = textString.substring(0, textString.length - 1).concat(']');
			console.log(textStringAfter);
			allFilms = JSON.parse(textStringAfter);
		}

		//Content type is JSON (default)
		else {
			allFilms = JSON.parse(request.responseText);
		}

		//instantiates a DataTable
		$(document).ready(function() {
			$('#tableGen').DataTable({
				lengthMenu: [
					[5, 10, 25, 50, -1],
					[5, 10, 25, 50, 'All'],
				],

				//gets the film data and parses to DataTable columns
				data: allFilms,

				//organises search and pagelength features
				dom: "<'row'<f><l>>" +
					"<'row'<'col-sm-12'tr>>" +
					"<'row'<'col-sm-12 col-md-5'i><'col-sm-12 col-md-7'p>>",

				//removes labels fron search function and add placeholder text
				//changes page length text
				language: {
					search: "_INPUT_",
					searchPlaceholder: "Search...",
					lengthMenu: "_MENU_Films per page"

				},

				//creates DataTable columns and parses required data to each
				columns: [
					{ data: 'title', title: 'Title' },
					{ data: 'year', title: 'Year' },
					{ data: 'director', title: 'Director' },
					{ data: 'stars', title: 'Stars' },
					{
						data: 'review', title: 'Reviews',

						/*
						 *if the review string is longer than 140 chars
						 *shorten and add "...". hovering over review column will
						 *show full review in popup box
						 */
						render: $.fn.dataTable.render.ellipsis(140, true)
					},
					{
						title: 'Actions',
						width: '160px',

						//cannot sort by this column
						orderable: false,

						//calls "makeModalButtons" for each film row
						render: function(data, type, row) {
							return makeModalButtons(row.title, row.year, row.director,
								row.stars, row.review, row.id)
						}
					},
					{ data: 'id', searchable: true, visible: false }
				],
			});
		})
	}
}

//makes update and delete buttons for each film
//which opens an model with a form
function makeModalButtons(title, year, director, stars, review, id) {
	//makes hidden inputs to pass data into the modal for population
	var values = '<input type="hidden" name="title" id="title' + id + '" value="' + title + '">'
		+ '<input type="hidden" name="year" id="year' + id + '" value="' + year + '">'
		+ '<input type="hidden" name="director" id="director' + id + '" value="' + director + '">'
		+ '<input type="hidden" name="stars" id="stars' + id + '" value="' + stars + '">'
		+ '<input type="hidden" name="review" id="review' + id + '" value="' + review + '">'
		+ '<input type="hidden" name="id" id="' + id + '" value="' + id + '">';
	//make buttons to present modals
	var modals = values
		+ '<button onclick="populateModals(' + id + ')" type="button" class="btn btn-primary" data-bs-toggle="modal"'
		+ 'data-bs-target="#updateModal" id="update' + id + '">Update</button>'
		+ '<button onclick="populateModals(' + id + ')" type="button" class="btn btn-primary" data-bs-toggle="modal"'
		+ 'data-bs-target="#deleteModal" id="delete' + id + '">Delete</button>';
	return modals;
}

//populates the modal forms with the film data
function populateModals(id) {
	var title = $("#title" + id).val(); // #name + id so each button is unique and can generate unique input data
	var year = $("#year" + id).val();
	var director = $("#director" + id).val();
	var stars = $("#stars" + id).val();
	var review = $("#review" + id).val();
	$(".modal-title").attr('value', title);
	$(".modal-year").attr('value', year);
	$(".modal-director").attr('value', director);
	$(".modal-stars").attr('value', stars);
	$(".modal-review").attr('value', review);
	$(".modal-id").attr('value', id);
}

// Insert the html data into the element that has the specified id
function htmlInsert(id, htmlData) {
	document.getElementById(id).innerHTML += htmlData;
}

// Return escaped value of textfield that has given id
// The builtin "escape" function converts < to &lt;, etc
function getValue(id) {
	return (escape(document.getElementById(id).value));
}


/*
 *Below is the DataTables function to display the data in a user friendly format
 */
/*
The below code is not written by me, it is a plugin.
Availability: https://datatables.net/plug-ins/dataRender/ellipsis
Author: Allan Jardine (https://uk.linkedin.com/in/allan-jardine-75959411)
License: "Permission is hereby granted ... to any person ... to deal in the Software without restriction" (https://datatables.net/license/mit)
*/
jQuery.fn.dataTable.render.ellipsis = function(cutoff, wordbreak, escapeHtml) {
	var esc = function(t) {
		return ('' + t)
			.replace(/&/g, '&amp;')
			.replace(/</g, '&lt;')
			.replace(/>/g, '&gt;')
			.replace(/"/g, '&quot;');
	};

	return function(d, type, row) {
		// Order, search and type get the original data
		if (type !== 'display') {
			return d;
		}

		if (typeof d !== 'number' && typeof d !== 'string') {
			if (escapeHtml) {
				return esc(d);
			}
			return d;
		}

		d = d.toString(); // cast numbers

		if (d.length <= cutoff) {
			if (escapeHtml) {
				return esc(d);
			}
			return d;
		}

		var shortened = d.substr(0, cutoff - 1);

		// Find the last white space character in the string
		if (wordbreak) {
			shortened = shortened.replace(/\s([^\s]*)$/, '');
		}

		// Protect against uncontrolled HTML input
		if (escapeHtml) {
			shortened = esc(shortened);
		}

		return '<span class="ellipsis" title="' + esc(d) + '">' + shortened + '&#8230;</span>';
	};
};
/*
The above code is not written by me, it is a plugin.
Availability: https://datatables.net/plug-ins/dataRender/ellipsis
Author: Allan Jardine (https://uk.linkedin.com/in/allan-jardine-75959411)
License: "Permission is hereby granted ... to any person ... to deal in the Software without restriction" (https://datatables.net/license/mit)
*/