package hh.music.app.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import hh.music.app.domain.AlbumRepository;
import hh.music.app.domain.ArtistRepository;
import hh.music.app.domain.SongRepository;

@Controller
public class QueryController {

	@Autowired
	private SongRepository songRepository;

	@Autowired
	private AlbumRepository albumRepository;

	@Autowired
	private ArtistRepository artistRepository;

	// Get-method used for navigating to search.html
	@GetMapping("/search")
	public String search(Model model) {
		return "search";
	}

	// Get requests for user inputted queries

	// Uses user inputted value for searching the names and lengths of songs longer
	// than input
	@GetMapping("/searchLongerThan")
	public String getLongerLength(@RequestParam("length") Double length, Model model) {
		model.addAttribute("names", songRepository.getLongerLengthName(length));
		model.addAttribute("lengths", songRepository.getLongerLength(length));
		return "searchResults";
	}

	// Uses user input for searching anything that begins with input-char
	@GetMapping("/searchLikeInput")
	public String getLikeInput(@RequestParam("input") String input, Model model) {
		model.addAttribute("songNames", songRepository.getNameLikeInput(input));
		model.addAttribute("albumNames", albumRepository.getNameLikeInput(input));
		model.addAttribute("artistNames", artistRepository.getNameLikeInput(input));
		return "searchResults";

	}

	// Uses user input for searching album names similar to input
	@GetMapping("/searchByAlbum")
	public String getByAlbum(@RequestParam("input") String input, Model model) {
		model.addAttribute("names", songRepository.getSongsFromAlbum(input));
		return "searchResults";
	}

	// Uses user input for searching artist names similar to input
	@GetMapping("/searchByArtist")
	public String getByArtist(@RequestParam("input") String input, Model model) {
		model.addAttribute("names", songRepository.getSongsFromArtist(input));
		return "searchResults";
	}

	// Uses user input for searching avg bpm based on album name
	@GetMapping("/searchAlbumAvgBpm")
	public String getAlbumAvgBpm(@RequestParam("input") String input, Model model) {
		model.addAttribute("lengths", songRepository.getAlbumAvgBpm(input));
		model.addAttribute("names", albumRepository.getNameLikeInput(input));
		return "searchResults";
	}
}
