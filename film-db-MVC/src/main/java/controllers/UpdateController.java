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
 * Servlet implementation class UpdateController
 */
@WebServlet("/UpdateFilm")
public class UpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// gets ID of film to update and directs to the update interface
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		FilmDAO dao = new FilmDAO();

		int id = Integer.valueOf(request.getParameter("id"));
		Film updateFilm = dao.getFilmByID(id);

		request.setAttribute("updateFilms", updateFilm);
		RequestDispatcher rd = request.getRequestDispatcher("update.jsp");
		rd.include(request, response);
	}

	// Gets the updated details of a film and send information to DAO
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		FilmDAO dao = new FilmDAO();

		// gets parameters for new film details
		int id = Integer.valueOf(request.getParameter("id"));
		String title = request.getParameter("title");
		int year = Integer.valueOf(request.getParameter("year"));
		String director = request.getParameter("director");
		String stars = request.getParameter("stars");
		String review = request.getParameter("review");

		Film f = new Film(id, title, year, director, stars, review);

		try {
			dao.updateFilm(f);
			response.sendRedirect("./films");
			System.out.print("\n\n----Film Updated----\n\n");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
