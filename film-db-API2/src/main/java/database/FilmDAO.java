package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import models.Film;

public class FilmDAO {

	Film oneFilm = null;
	Connection conn = null;
	Statement stmt = null;
	String user = "briersja";
	String password = "droGghel6";
	String url = "jdbc:mysql://mudfoot.doc.stu.mmu.ac.uk:6306/" + user;

	public FilmDAO() {
	}

	//loads connection to database
	private void openConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception e) {
			System.out.println(e);
		}

		//connecting to database
		try {
			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();
		} catch (SQLException se) {
			System.out.println(se);
		}
	}
	
	//closes database connection
	private void closeConnection() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//creates film object of next film in the database
	private Film getNextFilm(ResultSet rs) {
		Film thisFilm = null;
		try {
			thisFilm = new Film(rs.getInt("id"), rs.getString("title"), rs.getInt("year"), rs.getString("director"),
					rs.getString("stars"), rs.getString("review"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return thisFilm;
	}

	//creates an ArrayList containing every Film in the database
	public ArrayList<Film> getAllFilms() {

		ArrayList<Film> allFilms = new ArrayList<Film>();
		
		openConnection();
		//create select statement and execute it
		try {
			String selectSQL = "SELECT * FROM films";
			ResultSet rs1 = stmt.executeQuery(selectSQL);
			//retrieve the results
			while (rs1.next()) {
				oneFilm = getNextFilm(rs1);
				allFilms.add(oneFilm);
			}
			
			stmt.close();
			closeConnection();
		} catch (SQLException se) {
			System.out.println(se);
		}
		return allFilms;
	}

	//selects specific film according to id
	public Film getFilmByID(int id) {

		openConnection();
		oneFilm = null;
		//create select statement and execute it
		try {
			String selectSQL = "select * from films where id=" + id;
			ResultSet rs1 = stmt.executeQuery(selectSQL);
			//retrieve the results
			while (rs1.next()) {
				oneFilm = getNextFilm(rs1);
			}

			stmt.close();
			closeConnection();
		} catch (SQLException se) {
			System.out.println(se);
		}
		return oneFilm;
	}
	
	//inserts a new film into the database
	public void insertFilm(Film f) throws SQLException {
		//create insert statement and executes it
		openConnection();
		try {
			String insertSQL = "INSERT INTO films (title, year, director, stars, review) "
					+ "values('" + f.getTitle() + "','" + f.getYear() + "','" + f.getDirector()
					+ "','" + f.getStars() + "','" + f.getReview() + "');";
			stmt.execute(insertSQL);
			closeConnection();
		} catch (SQLException se) {
			throw new SQLException("Film not Added");
		}
	}
	
	public void updateFilm(Film f) throws SQLException {
		//create update statement and executes it
		openConnection();
		try {
			String updateSQL = "UPDATE films SET title = '" + f.getTitle() + "', year = '" +f.getYear()
					+ "', director = '" + f.getDirector()+ "', stars = '" + f.getStars()
					+"', review = '" + f.getReview() + "' WHERE id= " + f.getId() + ";";
			stmt.execute(updateSQL);
			closeConnection();
		} catch (SQLException se) {
			throw new SQLException("Film not Updated");
		}
	}
	
	//deletes film according to selected ID
	public void deleteFilm(int id) throws SQLException {
		//create delete statement and execute it
		openConnection();
		try {
			String deleteSQL = "DELETE FROM films WHERE id = " + id + ";";
			stmt.execute(deleteSQL);
			closeConnection();
		} catch (SQLException se) {
			throw new SQLException("Film not Deleted");
		}
	}
}
