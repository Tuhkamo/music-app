package hh.music.app.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface AlbumRepository extends CrudRepository<Album, Long> {

	// Define a query to count the average bpm for an artist
    
	
}

