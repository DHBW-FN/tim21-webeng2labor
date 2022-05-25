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
@RequestMapping("/song")
public class SongController {

    private SongRepository songRepository;
    private ArtistRepository artistRepository;

    @GetMapping
    public ResponseEntity<List<Song>> getSong(@RequestParam(required = false) Map<String, String> params){
        try{
            if(params.isEmpty()){
                return ResponseEntity.ok(this.songRepository.findAll());
            }

            if(params.containsKey("id")){
                System.out.println(Integer.parseInt(params.get("id")));
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(Collections.singletonList(this.songRepository.findById(Integer.parseInt(params.get("id")))));
            }

            if(params.containsKey("title")){
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(this.songRepository.findByTitleContaining(params.get("title")));
            }

            if(params.containsKey("artist")){
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(this.songRepository.findByArtistsNameContaining(params.get("artist")));
            }
        }
        catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping
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

            return ResponseEntity.ok(this.songRepository.save(song));
        }
        catch (Exception ex){
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteSong(@RequestParam Long id){
        try{
            this.songRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        catch (Exception ex){
            return ResponseEntity.internalServerError().build();
        }
    }
}