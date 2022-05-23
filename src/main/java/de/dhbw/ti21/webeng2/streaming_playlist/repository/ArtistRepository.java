package de.dhbw.ti21.webeng2.streaming_playlist.repository;

import de.dhbw.ti21.webeng2.streaming_playlist.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface ArtistRepository extends JpaRepository<Artist, Long> {

    Artist findById(long id);
    Set<Artist> findByName(String name);
}
