package de.dhbw.ti21.webeng2.streaming_playlist.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "songs")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "title")
    private String title;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REFRESH
    })
    @JsonIgnoreProperties("songs")
    private Set<Artist> artists;

    @Column(name = "genre")
    private String genre;

    @Column(name = "release")
    private Date releaseDate;

    @ManyToMany(mappedBy = "songs")
    @JsonIgnoreProperties("songs")
    private List<Playlist> playlists;
}