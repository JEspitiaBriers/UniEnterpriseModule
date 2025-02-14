<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert New Film</title>
</head>
<body>
<!-- form to take user input of new film details -->
	<form action="./InsertFilm" method="POST">
		<label><b>Add New Film</b></label><br>
		<label>Title:</label>
		<input type="text" name="title"/><br>
		<label>Year:</label>
		<input type="number" name="year"/><br>
		<label>Director:</label>
		<input type="text" name="director"/><br>
		<label>Stars:</label>
		<input type="text" name="stars"/><br>
		<label>Reviews:</label>
		<input type="text" name="review"/><br>
		<input type="submit" value="submit">
	</form>
</body>
</html>