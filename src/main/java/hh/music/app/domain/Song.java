package hh.music.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
public class Song {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long song_id;
	
	@NotEmpty(message = "Cannot be empty")
	private String name;
	
	@NotNull(message = "Cannot be null")
	private Double length;
	
	@Min(value = 1L, message = "Insert a positive number")
	private int bpm;
	
	@ManyToOne
	@JsonIgnoreProperties ("songs")
	@JoinColumn(name = "album_id")
	private Album album;
	
	public Song() {}

	public Song(String name, Double length, int bpm, Album album) {
		super();
		this.name = name;
		this.length = length;
		this.bpm = bpm;
		this.album = album;
	}

	public Long getSong_id() {
		return song_id;
	}

	public void setSong_id(Long song_id) {
		this.song_id = song_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getLength() {
		return length;
	}

	public void setLength(Double length) {
		this.length = length;
	}

	public int getBpm() {
		return bpm;
	}

	public void setBpm(int bpm) {
		this.bpm = bpm;
	}

	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}
	
	@Override
	public String toString() {
		return "Song [id=" + song_id + ", name=" + name + ", length=" + length + ", bpm=" + bpm + ", album=" + this.getAlbum().getName() + ", artist=" + this.getAlbum().getArtist().getName()+"]";
	}
	
	
}
