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
            CascadeType.REFRESH,
            CascadeType.DETACH
    })
    @JsonIgnoreProperties("songs")
    private Set<Artist> artists;

    @Column(name = "genre")
    private String genre;

    @Column(name = "release")
    private Date releaseDate;

    @ManyToMany(mappedBy = "songs", cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.DETACH
    })
    @JsonIgnoreProperties("songs")
    private List<Playlist> playlists;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Song)) return false;

        Song song = (Song) o;

        if (getTitle() != null ? !getTitle().equals(song.getTitle()) : song.getTitle() != null) return false;
        if (getArtists() != null ? !getArtists().equals(song.getArtists()) : song.getArtists() != null) return false;
        if (getGenre() != null ? !getGenre().equals(song.getGenre()) : song.getGenre() != null) return false;
        if (getReleaseDate() != null ? !getReleaseDate().equals(song.getReleaseDate()) : song.getReleaseDate() != null)
            return false;
        return getPlaylists() != null ? getPlaylists().equals(song.getPlaylists()) : song.getPlaylists() == null;
    }

    @Override
    public int hashCode() {
        int result = getTitle() != null ? getTitle().hashCode() : 0;
        result = 31 * result + (getArtists() != null ? getArtists().hashCode() : 0);
        result = 31 * result + (getGenre() != null ? getGenre().hashCode() : 0);
        result = 31 * result + (getReleaseDate() != null ? getReleaseDate().hashCode() : 0);
        result = 31 * result + (getPlaylists() != null ? getPlaylists().hashCode() : 0);
        return result;
    }

    public void addArtist(Artist artist) {
        this.artists.add(artist);
        artist.getSongs().add(this);
    }

    public void removeArtist(Artist artist) {
        this.artists.remove(artist);
        artist.getSongs().remove(this);
    }

    public void addPlaylist(Playlist playlist) {
        this.playlists.add(playlist);
        playlist.getSongs().add(this);
    }

    public void removePlaylist(Playlist playlist) {
        this.playlists.remove(playlist);
        playlist.getSongs().remove(this);
    }
}