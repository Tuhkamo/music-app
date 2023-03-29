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
			log.info("Saving test Artist - System of a Down");
			
			Artist artist1 = new Artist("System of a Down", 29);
			artrepo.save(artist1);
			
			log.info("fetching all artists");
			for (Artist artist : artrepo.findAll()) {
				log.info(artist.toString());
			}
			
			log.info("Saving test Album - Toxicity");
			Album album1 = new Album("Toxicity", "Rock", null, 44.01, null, 1996, artist1);
			albrepo.save(album1);
			
			User user1 = new User("admin", "$2a$10$BLIn0lduNSlBQmmLKypjj.y9Edo23JsmLX.XULxYPa1inuKAGhXc6", "ADMIN");
			urepo.save(user1);
			
			log.info("Saving test Songs: Prison Song, Needles, Deer Dance");
			srepo.save(new Song("Prison Song", 3.21, 100, album1, artist1));
			srepo.save(new Song("Needles", 3.14, 94, album1, artist1));
			srepo.save(new Song("Deer Dance", 2.55, 160, album1, artist1));
			
			log.info("fetch all categories");
			for (Song song : srepo.findAll()) {
				log.info(song.toString());
			}

		};
	}

}
