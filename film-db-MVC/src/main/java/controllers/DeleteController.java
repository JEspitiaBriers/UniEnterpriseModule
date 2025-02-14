package controllers;

import java.io.IOException;
import java.io.PrintWriter;
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
 * Servlet implementation class DeleteController
 */
@WebServlet("/DeleteFilm")
public class DeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// gets ID of selected film and deletes it
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		FilmDAO dao = new FilmDAO();

		int id = Integer.valueOf(request.getParameter("id"));

		try {
			dao.deleteFilm(id);
			response.sendRedirect("./films");
			System.out.print("\n\n----Film Deleted----\n\n");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
