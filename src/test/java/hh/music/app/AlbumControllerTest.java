package hh.music.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import hh.music.app.domain.Album;
import hh.music.app.domain.AlbumRepository;
import hh.music.app.domain.Artist;
import hh.music.app.domain.ArtistRepository;
import hh.music.app.web.AlbumController;

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
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testAlbumList() {
		List<Album> albumList = new ArrayList<>();
		albumList.add(new Album());
		when(albumRepository.findAll()).thenReturn(albumList);
		assertEquals("albumList", albumController.albums(model));
	}

	@Test
	public void testAddAlbum() {
		List<Artist> artistList = new ArrayList<>();
		artistList.add(new Artist());
		when(artistRepository.findAll()).thenReturn(artistList);
		assertEquals("addAlbum", albumController.addAlbum(model));
	}

	@Test
	public void testDeleteAlbum() {
		Long id = 1L;
		assertEquals("redirect:../index", albumController.deleteAlbum(id, model));
	}

	@Test
	public void testSaveAlbum() {
		Album album = new Album();
		when(bindingResult.hasErrors()).thenReturn(false);
		when(albumRepository.save(any(Album.class))).thenReturn(album);
		assertEquals("redirect:/index", albumController.saveAlbum(album, bindingResult, model));
	}

	@Test
	public void testAlbumListRest() {
		List<Album> albumList = new ArrayList<>();
		albumList.add(new Album());
		when(albumRepository.findAll()).thenReturn(albumList);
		assertEquals(albumList, albumController.albumListRest());
	}

}