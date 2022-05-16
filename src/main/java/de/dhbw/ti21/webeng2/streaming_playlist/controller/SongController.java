package de.dhbw.ti21.webeng2.streaming_playlist.controller;

import de.dhbw.ti21.webeng2.streaming_playlist.model.Song;
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

    private SongRepository repository;

    @GetMapping( "/song")
    public ResponseEntity<List<Song>> getSong(@RequestParam(required = false) int id){
        try{
            List<Song> songs = new ArrayList<>();

            if(id == 0){
                return new ResponseEntity<>(this.repository.findAll(), HttpStatus.OK);
            }

            return new ResponseEntity<>(this.repository.findById(id), HttpStatus.OK);
        }
        catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping( "/song")
    public ResponseEntity<Song> postSong(@RequestBody Song song){
        try{
            return new ResponseEntity<>(this.repository.save(song), HttpStatus.OK);
        }
        catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}