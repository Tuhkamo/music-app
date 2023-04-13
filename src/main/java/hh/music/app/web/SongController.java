package hh.music.app.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import hh.music.app.domain.AlbumRepository;
import hh.music.app.domain.ArtistRepository;
import hh.music.app.domain.Song;
import hh.music.app.domain.SongRepository;
import jakarta.validation.Valid;

@Controller
public class SongController {

	@Autowired
	private SongRepository songRepository;

	@Autowired
	private AlbumRepository albumRepository;

	@Autowired
	private ArtistRepository artistRepository;

	// Get-method used for displaying song data on the songList.html page
	@GetMapping("/songList")
	public String songs(Model model) {
		model.addAttribute("songs", songRepository.findAll());
		return "songList";
	}

	// Get-method used for getting all albums from alumRepository, and making a new
	// Song-entity when navigating to addSong.html
	// The entity is then filled with user inputted values on the page itself using
	// Thymeleaf + a selected Album for said song
	@GetMapping("/addSong")
	public String addSong(Model model) {
		model.addAttribute("song", new Song());
		model.addAttribute("albums", albumRepository.findAll());
		return "addSong";
	}

	// Delete-method that checks if the user has logged in using 'ADMIN'-level
	// credentials
	// Only allows deletion from the specified repository after the above statement
	// is true
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/deleteSong/{id}")
	public String deleteSong(@PathVariable("id") Long id, Model model) {
		songRepository.deleteById(id);
		return "redirect:../index";
	}

	// Save-method using BindingResult validation to check if user formed entities
	// match their restrictions
	// Otherwise returns to the add"Entity".html page with validation error messages
	// Causes troubles with form resubmissions but that's life
	@PostMapping("/saveSong")
	public String saveSong(@Valid @ModelAttribute("song") Song song, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) { // validation errors
			model.addAttribute("albums", albumRepository.findAll());
			model.addAttribute("artists", artistRepository.findAll());
			return "addSong"; // return back to form
		} else { // no validation errors
			songRepository.save(song);
			return "redirect:/index";
		}
	}

	// REST-method for finding songs by their id
	@GetMapping("/songs/{id}")
	public @ResponseBody Optional<Song> findBookRestById(@PathVariable("id") Long song_id) {
		return songRepository.findById(song_id);
	}

	// REST-method for finding all songs
	@GetMapping("/songs")
	public @ResponseBody List<Song> songListRest() {
		return (List<Song>) songRepository.findAll();
	}
}
