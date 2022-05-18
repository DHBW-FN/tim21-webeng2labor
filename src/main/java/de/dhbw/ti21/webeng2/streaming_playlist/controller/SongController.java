package de.dhbw.ti21.webeng2.streaming_playlist.controller;

import de.dhbw.ti21.webeng2.streaming_playlist.model.Artist;
import de.dhbw.ti21.webeng2.streaming_playlist.model.Song;
import de.dhbw.ti21.webeng2.streaming_playlist.repository.ArtistRepository;
import de.dhbw.ti21.webeng2.streaming_playlist.repository.SongRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@AllArgsConstructor
@RestController
public class SongController {

    private SongRepository songRepository;
    private ArtistRepository artistRepository;

    @GetMapping( "/song")
    public ResponseEntity<List<Song>> getSong(@RequestParam(required = false) Integer id){
        try{
            List<Song> songs = new ArrayList<>();

            if(id == null){
                return new ResponseEntity<>(this.songRepository.findAll(), HttpStatus.OK);
            }

            return new ResponseEntity<>(this.songRepository.findById(id), HttpStatus.OK);
        }
        catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping( "/song")
    public ResponseEntity<Song> postSong(@RequestBody Song song){
        try{
            //remove duplicate artists from song
            song.setArtists(new HashSet<>(song.getArtists()));


            //Prevent duplicates in database
            Set<Artist> artists = new HashSet<>(song.getArtists());
            for (Artist artist : song.getArtists()) {
                for (Artist artist1 : this.artistRepository.findByName(artist.getName())) {
                    if(Objects.equals(artist.getName(), artist1.getName())){
                        artists.remove(artist);
                        artists.add(artist1);
                    }
                }
            }
            song.setArtists(artists);

            return new ResponseEntity<>(this.songRepository.save(song), HttpStatus.OK);
        }
        catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}