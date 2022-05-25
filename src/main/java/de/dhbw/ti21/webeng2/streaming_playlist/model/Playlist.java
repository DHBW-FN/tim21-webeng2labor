package de.dhbw.ti21.webeng2.streaming_playlist.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "playlists")
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.DETACH
    })
    @JsonIgnoreProperties("playlists")
    private List<Song> songs;

    public void addSong(Song song) {
        this.songs.add(song);
        song.getPlaylists().add(this);
    }

    public void removeSong(Song song) {
        this.songs.remove(song);
        song.getPlaylists().remove(this);
    }
}
