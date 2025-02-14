<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Update Film</title>
</head>
<body>
<!-- form to take user input of updated film details -->
	<form action="./UpdateFilm" method="POST">
		<h3>Update "${updateFilms.title}" Details</h3><br>
		<input type="hidden" value="${updateFilms.id}" name="id">
		<label>Film Title</label><br>
		<input type="text" value="${updateFilms.title}" name="title"><br>
		<label>Year of Release</label><br>
		<input type="number" value="${updateFilms.year}" name="year"><br>
		<label>Director</label><br>
		<input type="text" value="${updateFilms.director}" name="director"><br>
		<label>Stars</label><br>
		<input type="text" value="${updateFilms.stars}" name="stars"><br>
		<label>Review</label><br>
		<input type="text" value="${updateFilms.review}" name="review"><br>
		<input type="submit" value="submit"/>
	</form>
</body>
</html>