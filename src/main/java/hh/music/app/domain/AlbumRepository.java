package hh.music.app.domain;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface AlbumRepository extends CrudRepository<Album, Long> {

	@Query("SELECT name FROM Album where LOWER(name) LIKE ?1% OR UPPER(name) LIKE ?1%")
	List<String> getNameLikeInput(String input);
	
}

