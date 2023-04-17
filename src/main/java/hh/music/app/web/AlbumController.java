package hh.music.app.web;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import hh.music.app.domain.Album;
import hh.music.app.domain.AlbumRepository;
import hh.music.app.domain.ArtistRepository;
import jakarta.validation.Valid;

@Controller
public class AlbumController {

	@Autowired
	private AlbumRepository albumRepository;

	@Autowired
	private ArtistRepository artistRepository;
	

	// Get-method used for displaying album data on the albumList.html page
	@GetMapping("/albumList")
	public String albums(Model model) {
		model.addAttribute("albums", albumRepository.findAll());
		return "albumList";
	}

	// Get-method used for getting all artists from artistRepository, and making a
	// new Album-entity when navigating to addAlbum.html
	// The entity is then filled with user inputted values on the page itself using
	// Thymeleaf + a selected Artist for said album
	@GetMapping("/addAlbum")
	public String addAlbum(Model model) {
		model.addAttribute("album", new Album());
		model.addAttribute("artists", artistRepository.findAll());
		return "addAlbum";
	}

	// Delete-method that checks if the user has logged in using 'ADMIN'-level
	// credentials
	// Only allows deletion from the specified repository after the above statement
	// is true
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/deleteAlbum/{id}")
	public String deleteAlbum(@PathVariable("id") Long id, Model model) {
		albumRepository.deleteById(id);
		return "redirect:../index";
	}

	// Save-method using BindingResult validation to check if user formed entities
	// match their restrictions
	// Otherwise returns to the add"Entity".html page with validation error messages
	// Causes troubles with form resubmissions but that's life
	@PostMapping("/saveAlbum")
	public String saveAlbum(@Valid @ModelAttribute("album") Album album, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) { // validation errors
			model.addAttribute("artists", artistRepository.findAll());
			return "addAlbum"; // return back to form
		} else { // no validation errors
			albumRepository.save(album);
			return "redirect:/index";
		}
	}

	// REST-method for finding all albums
	@GetMapping("/albums")
	public @ResponseBody List<Album> albumListRest() {
		return (List<Album>) albumRepository.findAll();
	}

	/*
	 * Defunct methods I tried to use for saving album cover images and displaying
	 * them
	 * 
	 * @GetMapping("/upload") public String upload() { return "upload"; }
	 * 
	 * @GetMapping("/albumcovers") public String displayAlbumCovers(Model model) {
	 * model.addAttribute("albumcovers", albumCoverRepository.findAll()); return
	 * "albumcovers"; }
	 *
	 * 
	 * 
	 * @PostMapping("/upload") public String handleFileUpload(@RequestParam("file")
	 * MultipartFile file,
	 * 
	 * @RequestParam("album_id") Long album_id) { if
	 * (!file.getContentType().equals("image/jpeg") &&
	 * !file.getContentType().equals("image/png")) { // Handle invalid file type }
	 * if (file.getSize() > 5 * 1024 * 1024) { // Handle file too large } String
	 * fileName = file.getOriginalFilename(); Path path = Paths.get(
	 * "C:\\Users\\pessa\\git\\music.app\\src\\main\\resources\\static\\uploads\\" +
	 * fileName); try { Files.write(path, file.getBytes()); } catch (IOException e)
	 * { // Handle file write error } // Insert record into database
	 * albumCoverRepository.save(new AlbumCover(fileName, file.getContentType(),
	 * file.getSize(), path.toString(), album_id)); return "redirect:/albumcovers";
	 * }
	 */
}
