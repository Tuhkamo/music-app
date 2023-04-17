package hh.music.app.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
public class Album {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long album_id;
	
	@NotEmpty(message = "Cannot be empty")
	private String name;
	
	private String genre;
	
	@NotNull(message = "Insert a positive number")
	private Double length;
	
	@Column(name="release_year")
	@NotNull(message = "Insert a positive number")
	@Min(value=0, message = "Insert a positive number")
	private Integer year;
	
	@ManyToOne
	@JsonIgnoreProperties ("albums")
	@JoinColumn(name = "artist_id")
	private Artist artist;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "album")
	@JsonIgnoreProperties("album")
	private List<Song> songs;
	
	public Album() {}

	public Album(String name, String genre, Double length, Integer year, Artist artist) {
		super();
		this.name = name;
		this.genre = genre;
		this.length = length;
		this.year = year;
		this.artist = artist;
	}
	
	public Album(String name, String genre, Double length, Integer year) {
		super();
		this.name = name;
		this.genre = genre;
		this.length = length;
		this.year = year;
	}

	public Long getAlbum_id() {
		return album_id;
	}

	public void setAlbum_id(Long album_id) {
		this.album_id = album_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}


	public Double getLength() {
		return length;
	}

	public void setLength(Double length) {
		this.length = length;
	}


	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public List<Song> getSongs() {
		return songs;
	}

	public void setSongs(List<Song> songs) {
		this.songs = songs;
	}

	public Artist getArtist() {
		return artist;
	}

	public void setArtist(Artist artist) {
		this.artist = artist;
	}
	
	
	
	
	
}
