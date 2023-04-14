package hh.music.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import hh.music.app.domain.AlbumRepository;
import hh.music.app.domain.ArtistRepository;
import hh.music.app.domain.SongRepository;
import hh.music.app.web.QueryController;

@ExtendWith(MockitoExtension.class)
public class QueryControllerTest {

    @Mock
    private SongRepository songRepository;

    @Mock
    private AlbumRepository albumRepository;

    @Mock
    private ArtistRepository artistRepository;

    @Mock
    private Model model;

    @InjectMocks
    private QueryController controller;

    @Test
    public void testSearch() {
        String viewName = controller.search(model);
        assertEquals("search", viewName);
    }

    @Test
    public void testGetLongerLength() {
        Double length = 3.5;
        when(songRepository.getLongerLengthName(length)).thenReturn(Arrays.asList("Song 1", "Song 2"));
        when(songRepository.getLongerLength(length)).thenReturn(Arrays.asList("4.2", "5.3"));
        String viewName = controller.getLongerLength(length, model);
        assertEquals("searchResults", viewName);
        verify(model).addAttribute("names", Arrays.asList("Song 1", "Song 2"));
        verify(model).addAttribute("lengths", Arrays.asList("4.2", "5.3"));
    }

}