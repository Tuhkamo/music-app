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
	public CommandLineRunner musicDemo(SongRepository srepo, AlbumRepository albrepo, ArtistRepository artrepo,
			UserRepository urepo) {
		return (args) -> {
			log.info("Saving test Artists");
			
			// Setting up test data for repositories to be displayed at localhost:8080/index
			
			Artist artist1 = new Artist("System of a Down", 1994,
					"System of a Down (also known as SoaD or simply System) is an American heavy metal band formed in Glendale, California, in 1994.",
					"USA", true);
			Artist artist2 = new Artist("HOME", 2012,
					"Randall Goffe, more commonly known as HOME, is a Synthwave/Chillwave artist from Punta Gorda, Florida. HOME's music is very influenced by 70s and 80s music, layered with a bit of Chiptune and Chillsynth.",
					"USA", true);
			artrepo.save(artist1);
			artrepo.save(artist2);

			log.info("fetching all artists");
			for (Artist artist : artrepo.findAll()) {
				log.info(artist.toString());
			}

			log.info("Saving test Albums");
			Album album1 = new Album("Toxicity", "Alternative Metal", 44.01, 1996, artist1);
			Album album2 = new Album("Odyssey", "Synthwave", 47.48, 2014, artist2);
			albrepo.save(album1);
			albrepo.save(album2);

			User user1 = new User("admin", "$2a$10$BLIn0lduNSlBQmmLKypjj.y9Edo23JsmLX.XULxYPa1inuKAGhXc6", "ADMIN");
			urepo.save(user1);

			log.info("Saving test Songs");
			srepo.save(new Song("Prison Song", 3.21, 100, album1));
			srepo.save(new Song("Needles", 3.14, 94, album1));
			srepo.save(new Song("Deer Dance", 2.55, 160, album1));
			srepo.save(new Song("Jet Pilot", 2.06, 96, album1));
			srepo.save(new Song("X", 1.57, 91, album1));
			srepo.save(new Song("Bounce", 1.55, 93, album1));
			srepo.save(new Song("Forest", 4.00, 150, album1));
			srepo.save(new Song("ATWA", 2.56, 87, album1));
			srepo.save(new Song("Science", 2.43, 89, album1));
			srepo.save(new Song("Shimmy", 1.51, 119, album1));
			srepo.save(new Song("Toxicity", 3.39, 117, album1));
			srepo.save(new Song("Psycho", 3.46, 146, album1));
			srepo.save(new Song("Aerials", 3.55, 79, album1));
			srepo.save(new Song("Arto", 2.14, 123, album1));

			srepo.save(new Song("Intro", 3.09, 100, album2));
			srepo.save(new Song("Native", 4.02, 100, album2));
			srepo.save(new Song("Decay", 4.11, 180, album2));
			srepo.save(new Song("Oort Cloud", 3.26, 176, album2));
			srepo.save(new Song("Tides", 3.57, 113, album2));
			srepo.save(new Song("Nights (I Wish I Could Be There)", 3.06, 135, album2));
			srepo.save(new Song("Odyssey", 6.10, 110, album2));
			srepo.save(new Song("New Machines", 2.57, 200, album2));
			srepo.save(new Song("Resonance", 3.33, 170, album2));
			srepo.save(new Song("Come Back Down", 4.53, 95, album2));
			srepo.save(new Song("Half Moon", 4.21, 140, album2));
			srepo.save(new Song("On The Way Out", 4.01, 127, album2));

			log.info("fetch all songs");
			for (Song song : srepo.findAll()) {
				log.info(song.toString());
			}

		};
	}

}
