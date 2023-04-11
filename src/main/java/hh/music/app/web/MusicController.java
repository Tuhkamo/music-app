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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import hh.music.app.domain.Album;
import hh.music.app.domain.AlbumRepository;
import hh.music.app.domain.Artist;
import hh.music.app.domain.ArtistRepository;
import hh.music.app.domain.Song;
import hh.music.app.domain.SongRepository;
import jakarta.validation.Valid;

@Controller
public class MusicController {

	@Autowired
	private SongRepository sRepo;

	@Autowired
	private AlbumRepository albRepo;

	@Autowired
	private ArtistRepository artRepo;

	@GetMapping("/index")
	public String index(Model model) {
		model.addAttribute("songs", sRepo.findAll());
		model.addAttribute("albums", albRepo.findAll());
		model.addAttribute("artists", artRepo.findAll());
		return "index";
	}

	@GetMapping("/artistList")
	public String artists(Model model) {
		model.addAttribute("artists", artRepo.findAll());
		return "artistList";
	}

	@GetMapping("/albumList")
	public String albums(Model model) {
		model.addAttribute("albums", albRepo.findAll());
		return "albumList";
	}

	@GetMapping("/songList")
	public String songs(Model model) {
		model.addAttribute("songs", sRepo.findAll());
		return "songList";
	}

	@GetMapping("/addArtist")
	public String addArtist(Model model) {
		model.addAttribute("artist", new Artist());
		return "addArtist";
	}

	@GetMapping("/addAlbum")
	public String addAlbum(Model model) {
		model.addAttribute("album", new Album());
		model.addAttribute("artists", artRepo.findAll());
		return "addAlbum";
	}

	@GetMapping("/addSong")
	public String addSong(Model model) {
		model.addAttribute("song", new Song());
		model.addAttribute("albums", albRepo.findAll());
		return "addSong";
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/deleteSong/{id}")
	public String deleteSong(@PathVariable("id") Long id, Model model) {
		sRepo.deleteById(id);
		return "redirect:../index";
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/deleteAlbum/{id}")
	public String deleteAlbum(@PathVariable("id") Long id, Model model) {
		albRepo.deleteById(id);
		return "redirect:../index";
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/deleteArtist/{id}")
	public String deleteArtist(@PathVariable("id") Long id, Model model) {
		artRepo.deleteById(id);
		return "redirect:../index";
	}

	@PostMapping("/saveSong")
	public String saveSong(@Valid @ModelAttribute("song") Song song, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) { // validation errors
			model.addAttribute("albums", albRepo.findAll());
			model.addAttribute("artists", artRepo.findAll());
			return "addSong"; // return back to form
		} else { // no validation errors
			sRepo.save(song);
			return "redirect:/index";
		}
	}

	@PostMapping("/saveAlbum")
	public String saveAlbum(@Valid @ModelAttribute("album") Album album, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) { // validation errors
			model.addAttribute("artists", artRepo.findAll());
			return "addAlbum"; // return back to form
		} else { // no validation errors
			albRepo.save(album);
			return "redirect:/index";
		}
	}

	@PostMapping("/saveArtist")
	public String saveArtist(@Valid @ModelAttribute("artist") Artist artist, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) { // validation errors
			return "addArtist"; // return back to form
		} else { // no validation errors
			artRepo.save(artist);
			return "redirect:/index";
		}
	}

	@GetMapping("/songs/{id}")
	public @ResponseBody Optional<Song> findBookRestById(@PathVariable("id") Long song_id) {
		return sRepo.findById(song_id);
	}

	@GetMapping("/songs")
	public @ResponseBody List<Song> songListRest() {
		return (List<Song>) sRepo.findAll();
	}

	@GetMapping("/albums")
	public @ResponseBody List<Album> albumListRest() {
		return (List<Album>) albRepo.findAll();
	}

	@GetMapping("/artists")
	public @ResponseBody List<Artist> artistListRest() {
		return (List<Artist>) artRepo.findAll();
	}

	/*
	 * @GetMapping("/upload")
		public String upload() {
		return "upload";
	}

	@GetMapping("/albumcovers")
	public String displayAlbumCovers(Model model) {
		model.addAttribute("albumcovers", albumCoverRepository.findAll());
		return "albumcovers";
	}

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

	@GetMapping("/search")
	public String search(Model model) {
		return "search";
	}
	
	// Get requests for user inputted queries

	@GetMapping("/searchLongerThan")
	public String getLongerLength(@RequestParam("length") Double length, Model model) {
		model.addAttribute("names", sRepo.getLongerLengthName(length));
		model.addAttribute("lengths", sRepo.getLongerLength(length));
		return "searchResults";
	}

	@GetMapping("/searchLikeInput")
	public String getLikeInput(@RequestParam("input") String input, Model model) {
		model.addAttribute("songNames", sRepo.getNameLikeInput(input));
		model.addAttribute("albumNames", albRepo.getNameLikeInput(input));
		model.addAttribute("artistNames", artRepo.getNameLikeInput(input));
		return "searchResults";

	}

	@GetMapping("/searchByAlbum")
	public String getByAlbum(@RequestParam("input") String input, Model model) {
		model.addAttribute("names", sRepo.getSongsFromAlbum(input));
		return "searchResults";
	}

	@GetMapping("/searchByArtist")
	public String getByArtist(@RequestParam("input") String input, Model model) {
		model.addAttribute("names", sRepo.getSongsFromArtist(input));
		return "searchResults";
	}
	
	@GetMapping("/searchAlbumAvgBpm")
	public String getAlbumAvgBpm(@RequestParam("input") String input, Model model) {
		model.addAttribute("lengths", sRepo.getAlbumAvgBpm(input));
		model.addAttribute("names", albRepo.getNameLikeInput(input));
		return "searchResults";
	}

}
