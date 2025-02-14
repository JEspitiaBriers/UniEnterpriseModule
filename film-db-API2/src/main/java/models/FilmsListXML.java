package models;

import java.util.List;

import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "films")
public class FilmsListXML {
	@XmlElement(name = "film")
	private List<Film> filmsListXML;

	public FilmsListXML() {
	}

	public FilmsListXML(List<Film> filmsListXML) {
		this.filmsListXML = filmsListXML;
	}

	public List<Film> getFilmsListXML() {
		return filmsListXML;
	}

	public void setFilmsListXML(List<Film> filmsListXML) {
		this.filmsListXML = filmsListXML;
	}
}