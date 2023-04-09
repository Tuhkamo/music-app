package hh.music.app.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
public class Artist {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long artist_id;
	
	@NotEmpty(message = "Cannot be empty")
	private String name, country;
	
	private String description;
	
	@NotNull(message = "Insert a positive number")
	@Min(value = 0L, message = "Insert a positive number")
	private Integer year_formed;
	
	private Boolean active;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "artist")
	@JsonIgnoreProperties("artist")
	private List<Album> albums;
	
	public Artist() {}

	public Artist(String name, Integer year_formed, String description, String country, Boolean active) {
		super();
		this.name = name;
		this.year_formed = year_formed;
		this.description = description;
		this.country = country;
		this.active = active;
	}

	public Long getArtist_id() {
		return artist_id;
	}

	public void setArtist_id(Long artist_id) {
		this.artist_id = artist_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getYear_formed() {
		return year_formed;
	}

	public void setYear_formed(Integer year_formed) {
		this.year_formed = year_formed;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public List<Album> getAlbums() {
		return albums;
	}

	public void setAlbums(List<Album> albums) {
		this.albums = albums;
	}	
	
}
