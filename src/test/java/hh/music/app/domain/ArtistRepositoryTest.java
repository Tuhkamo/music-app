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
public class ArtistRepositoryTest {

	@Autowired
	private ArtistRepository artistRepository;


	@Test
	public void contextLoads() {
		assertThat(artistRepository).isNotNull();
	}
	
	@Test
	public void testGetNameLikeInput() {
		artistRepository.save(new Artist("Test", 2000, "TestDesc", "TestCountry", true));

		List<String> artistNames = artistRepository.getNameLikeInput("E");
		assertThat(artistNames).isEmpty();

		artistNames = artistRepository.getNameLikeInput("T");
		assertThat(artistNames).contains("Test");
	}
}
