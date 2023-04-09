package hh.music.app.domain;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ArtistRepository extends CrudRepository<Artist, Long> {

	@Query("SELECT name FROM Artist where LOWER(name) LIKE ?1% OR UPPER(name) LIKE ?1%")
	List<String> getNameLikeInput(String input);
	
}
