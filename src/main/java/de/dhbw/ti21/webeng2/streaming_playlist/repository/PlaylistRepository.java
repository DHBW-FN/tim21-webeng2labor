package de.dhbw.ti21.webeng2.streaming_playlist.repository;

import de.dhbw.ti21.webeng2.streaming_playlist.model.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

    Playlist findById(long id);
    Playlist findByName(String name);
}
