<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Films List</title>
</head>
<body>
	<form action="InsertFilm">
		<input type="submit" value="Insert Film">
	</form>

	<table>
		<tr>
			<th>ID</th>
			<th>Title</th>
			<th>Year</th>
			<th>Director</th>
			<th>Stars</th>
			<th>Review</th>
		</tr>

		<c:forEach items="${films}" var="f">
			<tr>
				<td><em>${f.id}</em></td>
				<td>${f.title}</td>
				<td>${f.year}</td>
				<td>${f.director}</td>
				<td>${f.stars}</td>
				<td>${f.review}</td>
				<td>
				<!-- opens new page for user to input updated film details -->
					<form action="./UpdateFilm">
						<input type="hidden" value="${f.id}" name="id"> <input
							type="submit" value="Update">
					</form>
				</td>
				<td>
				<!-- deletes film when button is pressed -->
					<form action="./DeleteFilm" method="POST">
						<input type="hidden" value="${f.id}" name="id"> <input
							type="submit" value="Delete">
					</form>
				</td>
			</tr>
		</c:forEach>

	</table>

	<br>
</body>
</html>