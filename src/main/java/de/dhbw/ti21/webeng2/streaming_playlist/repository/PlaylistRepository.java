package de.dhbw.ti21.webeng2.streaming_playlist.repository;

import de.dhbw.ti21.webeng2.streaming_playlist.model.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

    List<Playlist> findById(long id);
    List<Playlist> findByNameContaining(String name);

    List<Playlist> findBySongsTitleContaining(String title);
}
