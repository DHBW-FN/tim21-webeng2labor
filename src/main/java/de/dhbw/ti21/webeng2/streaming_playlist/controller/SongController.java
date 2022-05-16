package de.dhbw.ti21.webeng2.streaming_playlist.controller;

import de.dhbw.ti21.webeng2.streaming_playlist.model.Song;
import de.dhbw.ti21.webeng2.streaming_playlist.repository.ArtistRepository;
import de.dhbw.ti21.webeng2.streaming_playlist.repository.SongRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
            if(artistRepository.findByName(song.getArtist().getName()) == null){
                artistRepository.save(song.getArtist());
            }

            song.setArtist(artistRepository.findByName(song.getArtist().getName()));

            return new ResponseEntity<>(this.songRepository.save(song), HttpStatus.OK);
        }
        catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}