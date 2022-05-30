package de.dhbw.ti21.webeng2.streaming_playlist.repository;

import de.dhbw.ti21.webeng2.streaming_playlist.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArtistRepository extends JpaRepository<Artist, Long> {

    Artist findById(long id);
    List<Artist> findByName(String name);
    List<Artist> findByNameContaining(String description);
    List<Artist> findByDescriptionContaining(String description);
}
