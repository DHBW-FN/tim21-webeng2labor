package de.dhbw.ti21.webeng2.streaming_playlist.model;

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
@Table(name = "songs")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "artist")
    @ManyToMany
    private List<Artist> artist;

    @Column(name = "genre")
    private String genre;

    @Column(name = "releaseDate")
    private int releaseDate;
}