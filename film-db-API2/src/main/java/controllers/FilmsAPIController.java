package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import database.FilmDAO;
import jakarta.xml.bind.JAXB;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import models.*;

@WebServlet("/film-api")
public class FilmsAPIController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*
	 * Retrieves all films and directs to front-end
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		FilmDAO dao = new FilmDAO();

		ArrayList<Film> allFilms = dao.getAllFilms();
		
		/*
		 * If selected content type of page is XML,
		 * converts data into valid xml format
		 */
		if (request.getHeader("Accept").equals("application/xml")) {
			FilmsListXML filmsXML = new FilmsListXML(allFilms);
			StringWriter sw = new StringWriter();
			JAXBContext context;
			try {
				context = JAXBContext.newInstance(FilmsListXML.class);
				Marshaller m = context.createMarshaller();
				m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
				m.marshal(filmsXML, sw);
				response.addHeader("Accept", "application/xml");
				out.write(sw.toString());
			} catch (JAXBException e) {
				e.printStackTrace();
			}	
		} 
		
		/*
		 * If selected content type of page is text,
		 * marshals data into valid text format
		 */
		else if (request.getHeader("Accept").equals("text/plain")) {
			String[] filmSingle = allFilms.toString().replaceAll("Film", "").split("], ");
			String filmTextFormat = "[";
			for(int i = 0; i <filmSingle.length; i++ ) {
				String filmString = filmSingle[i].toString();
				String id = filmString.substring(
						filmString.indexOf("id") + 3, filmString.indexOf("title") - 2);
				String title = filmString.substring(
						filmString.indexOf("title") + 6, filmString.indexOf("year") - 2);
				String year = filmString.substring(
						filmString.indexOf("year") + 5, filmString.indexOf("director") - 2);
				String director = filmString.substring(
						filmString.indexOf("director") + 9, filmString.indexOf("stars") - 2);
				String stars = filmString.substring(
						filmString.indexOf("stars") + 6, filmString.indexOf("review") - 2);
				String review = filmString.substring(
						filmString.indexOf("review") + 7, filmString.length());
				filmTextFormat = filmTextFormat.concat("[id->" + id + "!" + "title->" + title + "!" +
				"year->" + year + "!" + "director->" + director + "!" + "stars->" + stars +
				"!" + "review->" + review + "]");
			}

			String allString = filmTextFormat.substring(0, filmTextFormat.length() - 2);
			response.addHeader("Accept", "text/plain");
			out.write(allString);
		} 
		
		/*
		 * If selected content type of page is JSON (default),
		 * marshals data into valid JSON format
		 */
		else {
			Gson gson = new Gson();
			String json = gson.toJson(allFilms);
			out.write(json);
		}
		out.close();

	}

	/*
	 * Retrieves new film data from user input in selected content type,
	 * converts the data from data format to a Film Object and inserts film.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		FilmDAO dao = new FilmDAO();

		String data = request.getReader().lines().reduce(
				"", (accumulator, actual) -> accumulator + actual);
		Film insertF = new Film();
		
		/*
		 * If content type is xml, data is converted to Film Object using JAXB unmarshaller
		 */
		if (request.getHeader("Content-Type").equals("application/xml")) {
			insertF = JAXB.unmarshal(new StringReader(data), Film.class);
		} 
		
		/*
		 * If the content type if text, the data is iterated and each
		 * attribute is set to a Film Object attribute
		 */
		else if (request.getHeader("Content-Type").equals("text/plain")) {
			String[] detailsArray = data.substring(1, data.length()-1).split("!");
			System.out.print(data.toString());
			String[] toSet = new String[5];
			for (int i = 0; i <= 4; i++) {
				String[] attrArray = detailsArray[i].split("->");
				toSet[i] = attrArray[1];
			}
			insertF.setTitle(toSet[0]);
			insertF.setYear(Integer.valueOf(toSet[1]));
			insertF.setDirector(toSet[2]);
			insertF.setStars(toSet[3]);
			insertF.setReview(toSet[4]);
		} 
		
		/*
		 * If content type is JSON (default) the data is converted
		 *  from JSON to Film Object
		 */
		else {
			Gson gson = new Gson();
			insertF = gson.fromJson(data, Film.class);
		}

		/*
		 * Performs insert method of DAO Object
		 */
		try {
			dao.insertFilm(insertF);
			out.write("Film Inserted");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		out.close();
	}

	/*
	 * Retrieves updated film data from user input in selected content type,
	 * converts the data from data format to a Film Object and updates film.
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		FilmDAO dao = new FilmDAO();

		String data = request.getReader().lines().reduce(
				"", (accumulator, actual) -> accumulator + actual);

		Film updateF = new Film();

		/*
		 * If content type is xml, data is converted to Film Object using JAXB unmarshaller
		 */
		if (request.getHeader("Content-Type").equals("application/xml")) {
			updateF = JAXB.unmarshal(new StringReader(data), Film.class);
		} 
		
		/*
		 * If the content type if text, the data is iterated and each
		 * attribute is set to a Film Object attribute
		 */
		else if (request.getHeader("Content-Type").equals("text/plain")) {
			System.out.print("Check Here for data " + data);
			String[] detailsArray = data.split("!");
			String[] toSet = new String[6];
			for (int i = 0; i <= 5; i++) {
				String[] attrArray = detailsArray[i].split("->");
				toSet[i] = attrArray[1];
			}
			updateF.setTitle(toSet[0]);
			updateF.setYear(Integer.valueOf(toSet[1]));
			updateF.setDirector(toSet[2]);
			updateF.setStars(toSet[3]);
			updateF.setReview(toSet[4]);
			updateF.setId(Integer.valueOf(toSet[5]));
		} 
		
		/*
		 * If content type is JSON (default) the data is converted
		 *  from JSON to Film Object
		 */
		else {
			Gson gson = new Gson();
			updateF = gson.fromJson(data, Film.class);
		}

		/*
		 * Performs insert method of DAO Object
		 */
		try {
			dao.updateFilm(updateF);
			out.write("Film Updated");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		out.close();
	}

	/*
	 * Retrieves the film id data from user input in selected content type,
	 * converts the data from data format to a Film Object and deletes film.
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String data = request.getReader().lines().reduce(
				"", (accumulator, actual) -> accumulator + actual);

		PrintWriter out = response.getWriter();
		FilmDAO dao = new FilmDAO();

		Film deleteF = new Film();

		/*
		 * If content type is xml, data is converted to Film Object using JAXB unmarshaller
		 * an id value is then set to the Film Object
		 */
		if (request.getHeader("Content-Type").equals("application/xml")) {
			String idSet = JAXB.unmarshal(new StringReader(data), String.class);
			deleteF.setId(Integer.valueOf(idSet));
		} 
		
		/*
		 * If the content type if text, the data is iterated and each
		 * attribute is set to a Film Object attribute
		 */
		else if (request.getHeader("Content-Type").equals("text/plain")) {
			String[] detailsArray = data.split("!");
			String[] attrArray = detailsArray[0].split("->");
			deleteF.setId(Integer.valueOf(attrArray[1]));
		} 
		
		/*
		 * If content type is JSON (default) the data is converted
		 *  from JSON to Film Object
		 */
		else {
			Gson gson = new Gson();
			deleteF = gson.fromJson(data, Film.class);
		}

		/*
		 * Performs insert method of DAO Object
		 */
		try {
			dao.deleteFilm(deleteF.getId());
			out.write("Film Deleted");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		out.close();
	}

}
