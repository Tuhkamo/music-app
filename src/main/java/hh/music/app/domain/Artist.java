package hh.music.app.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Artist {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long artist_id;
	
	private String name;
	private Integer years_active;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "artist")
	@JsonIgnoreProperties("artist")
	private List<Album> albums;
	
	public Artist() {}

	public Artist(String name, Integer years_active) {
		super();
		this.name = name;
		this.years_active = years_active;
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

	public Integer getYears_active() {
		return years_active;
	}

	public void setYears_active(Integer years_active) {
		this.years_active = years_active;
	}

	public List<Album> getAlbums() {
		return albums;
	}

	public void setAlbums(List<Album> albums) {
		this.albums = albums;
	}	
	
}
