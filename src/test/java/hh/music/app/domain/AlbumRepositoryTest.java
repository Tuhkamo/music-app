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
public class AlbumRepositoryTest {

	@Autowired
	private AlbumRepository albumRepository;


	@Test
	public void contextLoads() {
		assertThat(albumRepository).isNotNull();
	}
	
	@Test
	public void testGetNameLikeInput() {
		albumRepository.save(new Album("Test", null, 150.0, 200));

		List<String> albumNames = albumRepository.getNameLikeInput("E");
		assertThat(albumNames).isEmpty();

		albumNames = albumRepository.getNameLikeInput("T");
		assertThat(albumNames).contains("Test");
	}
}
