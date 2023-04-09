package hh.music.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import hh.music.app.domain.User;
import hh.music.app.domain.UserRepository;
import hh.music.app.domain.Album;
import hh.music.app.domain.AlbumRepository;
import hh.music.app.domain.Artist;
import hh.music.app.domain.ArtistRepository;
import hh.music.app.domain.Song;
import hh.music.app.domain.SongRepository;

@SpringBootApplication
public class Application {
	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	public CommandLineRunner musicDemo(SongRepository srepo, AlbumRepository albrepo, ArtistRepository artrepo, UserRepository urepo) {
		return (args) -> {
			log.info("Saving test Artists");
			
			Artist artist1 = new Artist("System of a Down", 1994, "System of a Down (also known as SoaD or simply System) is an American heavy metal band formed in Glendale, California, in 1994.", "USA", true);
			Artist artist2 = new Artist("HOME", 2012, "Randall Goffe, more commonly known as HOME, is a Synthwave/Chillwave artist from Punta Gorda, Florida. HOME's music is very influenced by 70s and 80s music, layered with a bit of Chiptune and Chillsynth.", "USA", true);
			artrepo.save(artist1);
			artrepo.save(artist2);
			
			log.info("fetching all artists");
			for (Artist artist : artrepo.findAll()) {
				log.info(artist.toString());
			}
			
			log.info("Saving test Albums");
			Album album1 = new Album("Toxicity", "Rock", 44.01, 1996, artist1);
			Album album2 = new Album("Odyssey", "Synthwave", 47.48, 2014, artist2);
			albrepo.save(album1);
			albrepo.save(album2);
			
			User user1 = new User("admin", "$2a$10$BLIn0lduNSlBQmmLKypjj.y9Edo23JsmLX.XULxYPa1inuKAGhXc6", "ADMIN");
			urepo.save(user1);
			
			log.info("Saving test Songs");
			srepo.save(new Song("Prison Song", 3.21, 100, album1, artist1));
			srepo.save(new Song("Needles", 3.14, 94, album1, artist1));
			srepo.save(new Song("Deer Dance", 2.55, 160, album1, artist1));
			srepo.save(new Song("Resonance", 3.33, 170, album2, artist2));
			srepo.save(new Song("Odyssey", 6.10, 110, album2, artist2));
			srepo.save(new Song("Oort Cloud", 3.26, 176, album2, artist2));
			
			
			log.info("fetch all songs");
			for (Song song : srepo.findAll()) {
				log.info(song.toString());
			}

		};
	}

}
