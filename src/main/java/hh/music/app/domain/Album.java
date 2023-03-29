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

@Entity
public class Album {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long album_id;
	
	private String name, genre, art_src;
	private Double length;
	private Integer song_amount;
	
	@Column(name="release_year")
	private Integer year;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "album")
	@JsonIgnoreProperties("album")
	private List<Song> songs;
	
	@ManyToOne
	@JsonIgnoreProperties ("albums")
	@JoinColumn(name = "artist_id")
	private Artist artist;
	
	public Album() {}

	public Album(String name, String genre, String art_src, Double length, Integer song_amount, Integer year, Artist artist) {
		super();
		this.name = name;
		this.genre = genre;
		this.art_src = art_src;
		this.length = length;
		this.song_amount = song_amount;
		this.year = year;
		this.artist = artist;
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

	public String getArt_src() {
		return art_src;
	}

	public void setArt_src(String art_src) {
		this.art_src = art_src;
	}

	public Double getLength() {
		return length;
	}

	public void setLength(Double length) {
		this.length = length;
	}

	public Integer getSong_amount() {
		return song_amount;
	}

	public void setSong_amount(Integer song_amount) {
		this.song_amount = song_amount;
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
