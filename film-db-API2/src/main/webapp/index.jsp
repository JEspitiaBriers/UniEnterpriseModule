<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>

<!-- Link CSS pages and set head content -->
<head>
<link rel="shortcut icon" href="#">
<meta charset="UTF-8">
<title>Films List</title>
<link rel="stylesheet" type="text/css" href="./css/bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="./DataTables/datatables.css">
<link rel="stylesheet" type="text/css" href="./css/styleInterface.css">
</head>

<body>
	<!-- create drop down menu at top of page to select format -->
	<select class="form-select" style=" margin-bottom: 5px; width: 152px"
		aria-label=".form-select-lg example" id="format"
		onchange="typeReload()">
		<option selected>Data Format</option>
		<option value="json">JSON</option>
		<option value="xml">XML</option>
		<option value="text">TEXT</option>
	</select>
	
	<!-- button to open insert film model -->
	<button type="button" class="btn btn-primary" data-bs-toggle="modal"
		data-bs-target="#insertModal" id="insertButton">Insert New Film</button>

	<!-- start of insert modal -->
	<div class="modal" id="insertModal">
		<div class="modal-dialog">
			<div class="modal-content">

				<div class="modal-header">
					<h4 class="modal-title">Enter Film Details</h4>
					<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				</div>

				<!-- series of data inputs for new film -->
				<div class="modal-body">
					<label><b>Add New Film</b></label><br>
					<p>
						<label>Title: </label> <input type="text" id="titleI" />
					</p>
					<p>
						<label>Year: </label> <input type="number" id="yearI" />
					</p>
					<p>
						<label>Director:</label> <input type="text" id="directorI" />
					</p>
					<p>
						<label>Stars: </label> <input type="text" id="starsI" />
					</p>
					<p>
						<label>Reviews: </label> <input type="text" id="reviewI" />
					</p>
					<p>
						<!-- Calls JS function in ajaxCRUD.js to handle insert request -->
						<input type="button" onclick="insertFilm('insertMessage')"
							value="Add new film">
					</p>
					<div id="insertMessage"></div>
				</div>

				<!-- modal close button -->
				<div class="modal-footer">
					<button type="button" class="btn btn-danger"
						data-bs-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
	<!-- end of insert modal -->
	
	<!-- start of update modal -->
	<div class="modal" id="updateModal">
		<div class="modal-dialog">
			<div class="modal-content">

				<div class="modal-header">
					<h4 class="modal-title">Enter Updated Details</h4>
					<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				</div>

				<!-- Series of data input to update existing film -->
				<div class="modal-body">
					<label><b>Update This Film?</b></label>
					<form>
						<p>
							<label>Title: </label> <input type="text" id="title"
								class="modal-title" />
						</p>
						<p>
							<label>Year: </label> <input type="number" id="year"
								class="modal-year" />
						</p>
						<p>
							<label>Director:</label> <input type="text" id="director"
								class="modal-director" />
						</p>
						<p>
							<label>Stars: </label> <input type="text" id="stars"
								class="modal-stars" />
						</p>
						<p>
							<label>Reviews: </label> <input type="text" id="review"
								class="modal-review" />
						</p>
						<input type="hidden" id="id" class="modal-id" />
						<p>
							<!-- Calls function in ajaxCRUD.js to handle update request -->
							<input type="button" onclick="updateFilm('updateBox')"
								value="Update film">
						</P>
					</form>
					<div id="updateBox"></div>
				</div>

				<!-- Modal close button -->
				<div class="modal-footer">
					<button type="button" class="btn btn-danger"
						data-bs-dismiss="modal">Close</button>
				</div>

			</div>
		</div>
	</div>
	<!-- end of update modal -->
	
	<!-- Start of delete modal -->
	<div class="modal" id="deleteModal">
		<div class="modal-dialog">
			<div class="modal-content">

				<!-- Modal Header -->
				<div class="modal-header">
					<h4 class="modal-title">Delete Film</h4>
					<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				</div>

				<!-- Modal body -->
				<div class="modal-body">
					<label><b>Delete This Film?</b></label>
					<p>
					<form>
						<label>Title: </label> <input type="text" class="modal-title"
							readonly></input>
						</p>
						<p>
							<label>Year: </label> <input type="number" class="modal-year"
								readonly></input>
						</p>
						<p>
							<label>Director:</label> <input type="text"
								class="modal-director" readonly></input>
						</p>
						<p>
							<label>Stars: </label> <input type="text" class="modal-stars"
								readonly></input>
						</p>
						<p>
							<label>Reviews: </label> <input type="text" class="modal-review"
								readonly></input>
						</p>
						<p>
							<input type="hidden" id="id" class="modal-id" />
						</p>
						<p>
							<!-- calls function in ajaxCRUD.js to handle delete request -->
							<input type="button" onclick="deleteFilm('deleteBox')"
								value="Delete Film">
						</p>
					</form>
					<div id="deleteBox"></div>
				</div>

				<!-- modal close button -->
				<div class="modal-footer">
					<button type="button" class="btn btn-danger"
						data-bs-dismiss="modal">Close</button>
				</div>

			</div>
		</div>
	</div>
	<!-- end of delete modal -->
	
	<!-- DataTable generation point -->
	<table onload="getFilms('tableGen')" id="tableGen" class="display">
	</table>
</body>


<!-- including all of the JavaScript files. -->
<footer>
	<script type="text/javascript" src="./js/jquery-3.6.1.min.js"></script>
	<script type="text/javascript" src="./js/bootstrap.min.js"></script>
	<script type="text/javascript" src="./DataTables/datatables.js"></script>

	<script type="text/javascript" src="./js/main.js"></script>
	<script type="text/javascript" src="./js/displayUtils.js"></script>
	<script type="text/javascript" src="./js/ajaxResults.js"></script>
	<script type="text/javascript" src="./js/ajaxCRUD.js"></script>
</footer>
</html>