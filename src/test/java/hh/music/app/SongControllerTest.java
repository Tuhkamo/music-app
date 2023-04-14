package hh.music.app;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import hh.music.app.domain.Album;
import hh.music.app.domain.AlbumRepository;
import hh.music.app.domain.Artist;
import hh.music.app.domain.ArtistRepository;
import hh.music.app.domain.Song;
import hh.music.app.domain.SongRepository;
import hh.music.app.web.SongController;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class SongControllerTest {

	@Mock
	private SongRepository songRepository;
	
	@Mock
	private AlbumRepository albumRepository;

	@Mock
	private Model model;

	@Mock
	private BindingResult bindingResult;

	@InjectMocks
	private SongController songController;
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void contextLoads() {
		assertThat(songController).isNotNull();
	}
	
	@Test
	public void testSongList() {
		List<Song> songList = new ArrayList<>();
		songList.add(new Song());
		when(songRepository.findAll()).thenReturn(songList);
		assertThat("songList".equals(songController.songs(model)));
	}
	
	@Test
	public void testAddSong() {
		List<Album> albumList = new ArrayList<>();
		albumList.add(new Album());
		when(albumRepository.findAll()).thenReturn(albumList);
		assertThat("addSong".equals(songController.addSong(model)));
	}
	
	@Test
	public void testDeleteSong() {
		Long id = 1L;
		songController.deleteSong(id, model);
		assertThat(songRepository.findById(id).equals(null));
	}
}
