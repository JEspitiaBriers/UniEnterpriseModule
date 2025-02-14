package controllers;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.FilmDAO;
import models.Film;

/**
 * Servlet implementation class InsertController
 */
@WebServlet("/InsertFilm")
public class InsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// directs to insert interface with input form
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher rd = request.getRequestDispatcher("insert.jsp");
		rd.include(request, response);
	}

	// gets user input details and sends information to DAO
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		FilmDAO dao = new FilmDAO();

		// gets parameters for new film details
		String title = request.getParameter("title");
		int year = Integer.valueOf(request.getParameter("year"));
		String director = request.getParameter("director");
		String stars = request.getParameter("stars");
		String review = request.getParameter("review");

		Film insertF = new Film(title, year, director, stars, review);

		try {
			dao.insertFilm(insertF);
			response.sendRedirect("./films");
			System.out.print("\n\n----Film Inserted----\n\n");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
