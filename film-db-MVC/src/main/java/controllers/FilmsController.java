package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.FilmDAO;
import models.Film;

/**
 * Servlet implementation class FilmsController
 */
@WebServlet("/films")
public class FilmsController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// Retrieves all films and directs to interface
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		FilmDAO dao = new FilmDAO();

		ArrayList<Film> allFilms = dao.getAllFilms();

		request.setAttribute("films", allFilms);
		RequestDispatcher rd = request.getRequestDispatcher("filmsInterface.jsp");
		rd.include(request, response);
	}
}
