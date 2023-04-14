package hh.music.app;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
import hh.music.app.domain.SongRepository;
import hh.music.app.web.ArtistController;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ArtistControllerTest {

    @Mock
    private SongRepository songRepository;

    @Mock
    private ArtistRepository artistRepository;

    @Mock
    private AlbumRepository albumRepository;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private ArtistController artistController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
	public void contextLoads() {
		assertThat(artistController).isNotNull();
	}

    @Test
    @DisplayName("Test index page")
    public void testIndexPage() {
        String expectedViewName = "index";
        List<Artist> artists = Arrays.asList(new Artist(), new Artist());
        when(songRepository.findAll()).thenReturn(Arrays.asList());
        when(albumRepository.findAll()).thenReturn(Arrays.asList());
        when(artistRepository.findAll()).thenReturn(artists);

        String actualViewName = artistController.index(model);

        verify(model).addAttribute("songs", Arrays.asList());
        verify(model).addAttribute("albums", Arrays.asList());
        verify(model).addAttribute("artists", artists);
        assertThat(actualViewName.equals(expectedViewName));
    }

    @Test
    @DisplayName("Test artist list page")
    public void testArtistListPage() {
        String expectedViewName = "artistList";
        List<Artist> artists = Arrays.asList(new Artist(), new Artist());
        when(artistRepository.findAll()).thenReturn(artists);

        String actualViewName = artistController.artists(model);

        verify(model).addAttribute("artists", artists);
        assertThat(actualViewName.equals(expectedViewName));
    }

    @Test
    @DisplayName("Test save artist method with binding errors")
    public void testSaveArtistWithBindingErrors() {
        String expectedViewName = "addArtist";
        when(bindingResult.hasErrors()).thenReturn(true);

        String actualViewName = artistController.saveArtist(new Artist(), bindingResult, model);

        assertThat(actualViewName.equals(expectedViewName));
    }

    @Test
    @DisplayName("Test delete artist method, deletes all connected entities")
    public void testDeleteArtist() {
        Long id = 1L;
        artistController.deleteArtist(id, model);
		assertThat(artistRepository.findById(id).equals(null));
    }
}
