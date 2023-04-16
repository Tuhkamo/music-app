package hh.music.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class) // JUnit5 eli Jupiter
@DataJpaTest
public class SongRepositoryTest {

	@Autowired
	private SongRepository songRepository;
	
	@Autowired
	private AlbumRepository albumRepository;
	
	@Autowired
    private ArtistRepository artistRepository;


	@Test
	public void contextLoads() {
		assertThat(songRepository).isNotNull();
	}
	
	@Test
	public void testGetLongerLengthName() {
		songRepository.save(new Song("Test", 150.0, 200));

		List<String> songNames = songRepository.getLongerLengthName(200.0);
		assertThat(songNames).isEmpty();

		songNames = songRepository.getLongerLengthName(100.0);
		assertThat(songNames).contains("Test");
	}
	
	@Test
	public void testGetLongerLength() {
		songRepository.save(new Song("Test", 150.0, 200));

		List<String> songNames = songRepository.getLongerLength(200.0);
		assertThat(songNames).isEmpty();

		songNames = songRepository.getLongerLength(100.0);
		assertThat(songNames).contains("150.0");
	}
	
	@Test
	public void testGetNameLikeInput() {
		songRepository.save(new Song("Test", 150.0, 200));

		List<String> songNames = songRepository.getNameLikeInput("E");
		assertThat(songNames).isEmpty();

		songNames = songRepository.getNameLikeInput("T");
		assertThat(songNames).contains("Test");
	}
	
	@Test
	public void testGetSongsFromAlbum() {
		Album testAlbum = new Album("TestAlbum", "TestGenre", 100.00, 2000);
		albumRepository.save(testAlbum);
		
		songRepository.save(new Song("TestSong", 150.0, 200, testAlbum));
		List<String> songNames = songRepository.getSongsFromAlbum("testalb");
		
		assertThat(songNames).contains("TestSong");
		
	}
	
	@Test
	public void testGetSongsFromArtist() {
		Artist testArtist = new Artist("TestArtist", 1500, null, "TEST", true);
		artistRepository.save(testArtist);
		
		Album testAlbum = new Album("TestAlbum", "TestGenre", 100.00, 2000, testArtist);
		albumRepository.save(testAlbum);
		
		songRepository.save(new Song("TestSong", 150.0, 200, testAlbum));
		List<String> songNames = songRepository.getSongsFromArtist("testart");
		
		assertThat(songNames).contains("TestSong");
		
	}
}
