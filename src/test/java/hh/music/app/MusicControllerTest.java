package hh.music.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import hh.music.app.domain.Album;
import hh.music.app.domain.AlbumRepository;
import hh.music.app.domain.Artist;
import hh.music.app.domain.ArtistRepository;
import hh.music.app.domain.Song;
import hh.music.app.domain.SongRepository;
import hh.music.app.web.MusicController;

@ExtendWith(SpringExtension.class)  // JUnit5 eli Jupiter
@DataJpaTest
public class MusicControllerTest {

    @Mock
    private SongRepository sRepo;

    @Mock
    private AlbumRepository albRepo;

    @Mock
    private ArtistRepository artRepo;

    @InjectMocks
    private MusicController musicController;

    @Mock
    private Model model;

    @Test
    public void testIndex() {
        List<Song> songs = Arrays.asList(new Song(), new Song());
        List<Album> albums = Arrays.asList(new Album(), new Album());
        List<Artist> artists = Arrays.asList(new Artist(), new Artist());

        when(sRepo.findAll()).thenReturn(songs);
        when(albRepo.findAll()).thenReturn(albums);
        when(artRepo.findAll()).thenReturn(artists);

        String result = musicController.index(model);

        assertEquals("index", result);
        verify(model, times(1)).addAttribute("songs", songs);
        verify(model, times(1)).addAttribute("albums", albums);
        verify(model, times(1)).addAttribute("artists", artists);
    }

    @Test
    public void testArtists() {
        List<Artist> artists = Arrays.asList(new Artist(), new Artist());

        when(artRepo.findAll()).thenReturn(artists);

        String result = musicController.artists(model);

        assertEquals("artistList", result);
        verify(model, times(1)).addAttribute("artists", artists);
    }

    @Test
    public void testAlbums() {
        List<Album> albums = Arrays.asList(new Album(), new Album());

        when(albRepo.findAll()).thenReturn(albums);

        String result = musicController.albums(model);

        assertEquals("albumList", result);
        verify(model, times(1)).addAttribute("albums", albums);
    }

    @Test
    public void testSongs() {
        List<Song> songs = Arrays.asList(new Song(), new Song());

        when(sRepo.findAll()).thenReturn(songs);

        String result = musicController.songs(model);

        assertEquals("songList", result);
        verify(model, times(1)).addAttribute("songs", songs);
    }

    @Test
    public void testDeleteSong() {
        Long id = 1L;

        String result = musicController.deleteSong(id, model);

        assertEquals("redirect:../index", result);
        verify(sRepo, times(1)).deleteById(id);
    }
    
    @Test
    @WithMockUser(authorities = "ADMIN")
    public void testDeleteAlbum() {
        Long id = 1L;

        String result = musicController.deleteAlbum(id, model);

        verify(albRepo, times(1)).deleteById(id);
        assertEquals("redirect:../index", result);
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void testDeleteArtist() {
        Long id = 1L;

        String result = musicController.deleteArtist(id, model);

        verify(artRepo, times(1)).deleteById(id);
        assertEquals("redirect:../index", result);
    }

    @Test
    public void testSaveSong_ValidationError() {
        Song song = new Song();
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);

        String result = musicController.saveSong(song, bindingResult, model);

        verify(albRepo, times(1)).findAll();
        verify(artRepo, times(1)).findAll();
        assertEquals("addSong", result);
    }

    @Test
    public void testSaveSong_NoValidationError() {
        Song song = new Song();
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);

        String result = musicController.saveSong(song, bindingResult, model);

        verify(sRepo, times(1)).save(song);
        assertEquals("redirect:/index", result);
    }

    @Test
    public void testSaveAlbum_ValidationError() {
        Album album = new Album();
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);

        String result = musicController.saveAlbum(album, bindingResult, model);

        verify(artRepo, times(1)).findAll();
        assertEquals("addAlbum", result);
    }

    @Test
    public void testSaveAlbum_NoValidationError() {
        Album album = new Album();
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);

        String result = musicController.saveAlbum(album, bindingResult, model);

        verify(albRepo, times(1)).save(album);
        assertEquals("redirect:/index", result);
    }

    @Test
    public void testSaveArtist_ValidationError() {
        Artist artist = new Artist();
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);

        String result = musicController.saveArtist(artist, bindingResult, model);

        assertEquals("addArtist", result);
    }

    @Test
    public void testSaveArtist_NoValidationError() {
        Artist artist = new Artist();
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);

        String result = musicController.saveArtist(artist, bindingResult, model);

        verify(artRepo, times(1)).save(artist);
        assertEquals("redirect:/index", result);
    }
}