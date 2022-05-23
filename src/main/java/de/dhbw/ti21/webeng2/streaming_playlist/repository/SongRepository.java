package de.dhbw.ti21.webeng2.streaming_playlist.repository;

import de.dhbw.ti21.webeng2.streaming_playlist.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SongRepository extends JpaRepository<Song, Long> {

    List<Song> findById(long id);
    List<Song> findByTitleContaining(String title);
    List<Song> findByArtistsNameContaining(String artist);
}