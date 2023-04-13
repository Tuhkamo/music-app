package hh.music.app;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import hh.music.app.domain.AlbumRepository;
import hh.music.app.domain.Artist;
import hh.music.app.domain.ArtistRepository;
import hh.music.app.domain.SongRepository;
import hh.music.app.web.ArtistController;

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
        assert (actualViewName.equals(expectedViewName));
    }

    @Test
    @DisplayName("Test artist list page")
    public void testArtistListPage() {
        String expectedViewName = "artistList";
        List<Artist> artists = Arrays.asList(new Artist(), new Artist());
        when(artistRepository.findAll()).thenReturn(artists);

        String actualViewName = artistController.artists(model);

        verify(model).addAttribute("artists", artists);
        assert (actualViewName.equals(expectedViewName));
    }

    @Test
    @DisplayName("Test save artist method with binding errors")
    public void testSaveArtistWithBindingErrors() {
        String expectedViewName = "addArtist";
        when(bindingResult.hasErrors()).thenReturn(true);

        String actualViewName = artistController.saveArtist(new Artist(), bindingResult, model);

        assert (actualViewName.equals(expectedViewName));
    }

    @Test
    @DisplayName("Test delete artist method")
    public void testDeleteArtist() {
        String expectedViewName = "redirect:../index";
        Long id = 1L;

        String actualViewName = artistController.deleteArtist(id, model);

        verify(artistRepository).deleteById(id);
        assert (actualViewName.equals(expectedViewName));
    }
}
