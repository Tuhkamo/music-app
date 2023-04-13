package hh.music.app.web;

import java.util.List;

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
import hh.music.app.domain.Artist;
import hh.music.app.domain.ArtistRepository;
import hh.music.app.domain.SongRepository;
import jakarta.validation.Valid;

@Controller
public class ArtistController {

	@Autowired
	private SongRepository songRepository;

	@Autowired
	private AlbumRepository albumRepository;

	@Autowired
	private ArtistRepository artistRepository;

	// Get-method used for displaying all music data on the index.html page
	// "songs", "albums" and "artists" are processed using Thymeleaf
	@GetMapping("/index")
	public String index(Model model) {
		model.addAttribute("songs", songRepository.findAll());
		model.addAttribute("albums", albumRepository.findAll());
		model.addAttribute("artists", artistRepository.findAll());
		return "index";
	}

	// Get-method used for displaying artist data on the artistList.html page
	@GetMapping("/artistList")
	public String artists(Model model) {
		model.addAttribute("artists", artistRepository.findAll());
		return "artistList";
	}

	// Get-method used for making a new Artist-entity when navigating to
	// addArtist.html
	// The entity is then filled with user inputted values on the page itself using
	// Thymeleaf
	@GetMapping("/addArtist")
	public String addArtist(Model model) {
		model.addAttribute("artist", new Artist());
		return "addArtist";
	}

	// Delete-method that checks if the user has logged in using 'ADMIN'-level
	// credentials
	// Only allows deletion from the specified repository after the above statement
	// is true
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/deleteArtist/{id}")
	public String deleteArtist(@PathVariable("id") Long id, Model model) {
		artistRepository.deleteById(id);
		return "redirect:../index";
	}
	
	// Save-method using BindingResult validation to check if user formed entities
	// match their restrictions
	// Otherwise returns to the add"Entity".html page with validation error messages
	// Causes troubles with form resubmissions but that's life
	@PostMapping("/saveArtist")
	public String saveArtist(@Valid @ModelAttribute("artist") Artist artist, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) { // validation errors
			return "addArtist"; // return back to form
		} else { // no validation errors
			artistRepository.save(artist);
			return "redirect:/index";
		}
	}

	// REST-method for finding all artists
	@GetMapping("/artists")
	public @ResponseBody List<Artist> artistListRest() {
		return (List<Artist>) artistRepository.findAll();
	}
}
