package hh.music.app.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import hh.music.app.domain.Album;
import hh.music.app.domain.AlbumRepository;
import hh.music.app.domain.Artist;
import hh.music.app.domain.ArtistRepository;
import hh.music.app.domain.Song;
import hh.music.app.domain.SongRepository;

@Controller
public class SongController {

	@Autowired
	private SongRepository srepo;
	
	@Autowired
	private AlbumRepository albRepo;
	
	@Autowired
	private ArtistRepository artRepo;
	
	@GetMapping("/index")
	public String index(Model model) {
		model.addAttribute("songs", srepo.findAll());
		model.addAttribute("albums", albRepo.findAll());
		model.addAttribute("artists", artRepo.findAll());
		return "index";
	}
	
	@GetMapping("/addArtist")
	public String addArtist(Model model){
    	model.addAttribute("artist", new Artist());
        return "addArtist";
    }
	
	@GetMapping("/addAlbum")
	public String addAlbum(Model model){
    	model.addAttribute("album", new Album());
    	model.addAttribute("artists", artRepo.findAll());
        return "addAlbum";
    }
	
	@GetMapping("/addSong")
	public String addSong(Model model){
    	model.addAttribute("song", new Song());
    	model.addAttribute("artists", artRepo.findAll());
    	model.addAttribute("albums", albRepo.findAll());
        return "addSong";
    }
	
	
	@PostMapping("/saveSong")
    public String saveSong(Song song){
        srepo.save(song);
        return "redirect:/index";
    }
	
	@PostMapping("/saveAlbum")
    public String saveSong(Album album){
        albRepo.save(album);
        return "redirect:/index";
    }
	
	@PostMapping("/saveArtist")
    public String saveArtist(Artist artist){
        artRepo.save(artist);
        return "redirect:/index";
    }
	
	@GetMapping("/songs")
	public @ResponseBody List<Song> songListRest(){
		return (List<Song>) srepo.findAll();
	}
	
	@GetMapping("/albums")
	public @ResponseBody List<Album> albumListRest(){
		return (List<Album>) albRepo.findAll();
	}
	
	@GetMapping("/artists")
	public @ResponseBody List<Artist> artistListRest(){
		return (List<Artist>) artRepo.findAll();
	}
	
}
