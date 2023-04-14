package controllerTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import hh.music.app.domain.Album;
import hh.music.app.domain.AlbumRepository;
import hh.music.app.domain.Artist;
import hh.music.app.domain.ArtistRepository;
import hh.music.app.web.AlbumController;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AlbumControllerTest {

	@Mock
	private AlbumRepository albumRepository;

	@Mock
	private ArtistRepository artistRepository;

	@Mock
	private Model model;

	@Mock
	private BindingResult bindingResult;

	@InjectMocks
	private AlbumController albumController;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void contextLoads() {
		assertThat(albumController).isNotNull();
	}

	@Test
	public void testAlbumList() {
		List<Album> albumList = new ArrayList<>();
		albumList.add(new Album());
		when(albumRepository.findAll()).thenReturn(albumList);
		assertThat("albumList".equals(albumController.albums(model)));
	}

	@Test
	public void testAddAlbum() {
		List<Artist> artistList = new ArrayList<>();
		artistList.add(new Artist());
		when(artistRepository.findAll()).thenReturn(artistList);
		assertThat("addAlbum".equals(albumController.addAlbum(model)));
	}

	@Test
	public void testDeleteAlbum() {
		Long id = 1L;
		assertThat("redirect:../index".equals(albumController.deleteAlbum(id, model)));
	}

	@Test
	public void testSaveAlbum() {
		Album album = new Album();
		when(bindingResult.hasErrors()).thenReturn(false);
		when(albumRepository.save(any(Album.class))).thenReturn(album);
		assertThat("redirect:/index".equals(albumController.saveAlbum(album, bindingResult, model)));
	}

	@Test
	public void testAlbumListRest() {
		List<Album> albumList = new ArrayList<>();
		albumList.add(new Album());
		when(albumRepository.findAll()).thenReturn(albumList);
		assertThat(albumList.equals(albumController.albumListRest()));
	}

}