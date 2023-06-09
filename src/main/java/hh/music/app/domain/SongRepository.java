package hh.music.app.domain;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface SongRepository extends CrudRepository<Song, Long> {
	
	@Query("SELECT length FROM Song WHERE length > ?1 ORDER BY length ASC")
	List<String> getLongerLength(Double length);
	
	@Query("SELECT name FROM Song where length > ?1 ORDER BY length ASC")
	List<String> getLongerLengthName(Double length);
	
	@Query("SELECT name "
			+ "FROM Song "
			+ "WHERE LOWER(name) LIKE ?1% "
			+ "OR UPPER(name) LIKE ?1%")
	List<String> getNameLikeInput(String input);
	
	@Query("SELECT s.name "
			+ "FROM Song s "
			+ "JOIN Album alb ON s.album = alb "
			+ "WHERE LOWER(alb.name) LIKE LOWER(concat(?1, '%'))"
			+ "OR UPPER(alb.name) LIKE UPPER(concat(?1, '%'))")
	List<String> getSongsFromAlbum(String input);
	
	@Query("SELECT s.name "
			+ "FROM Song s "
			+ "JOIN Album alb ON s.album = alb "
			+ "JOIN Artist art ON alb.artist = art "
			+ "WHERE LOWER(art.name) LIKE LOWER(concat(?1, '%'))"
			+ "OR UPPER(art.name) LIKE UPPER(concat(?1, '%'))")
	List<String> getSongsFromArtist(String input);
	
	@Query("SELECT AVG(s.bpm) "
			+ "FROM Song s "	
			+ "JOIN Album alb ON s.album = alb "
			+ "WHERE LOWER(alb.name) LIKE LOWER(concat(?1, '%'))"
			+ "OR UPPER(alb.name) LIKE UPPER(concat(?1, '%'))")
	List<Float> getAlbumAvgBpm(String input);

}
