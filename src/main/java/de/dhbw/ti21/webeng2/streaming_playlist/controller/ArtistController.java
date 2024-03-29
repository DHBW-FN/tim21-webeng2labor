package de.dhbw.ti21.webeng2.streaming_playlist.controller;

import de.dhbw.ti21.webeng2.streaming_playlist.model.Artist;
import de.dhbw.ti21.webeng2.streaming_playlist.model.Playlist;
import de.dhbw.ti21.webeng2.streaming_playlist.model.Song;
import de.dhbw.ti21.webeng2.streaming_playlist.repository.ArtistRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/artist")
public class ArtistController {
    private ArtistRepository artistRepository;

    @GetMapping
    public ResponseEntity<List<Artist>> getArtist(@RequestParam(required = false) Map<String, String> params){
        try{
            if(params.isEmpty()){
                return ResponseEntity.ok(this.artistRepository.findAll());
            }

            if(params.containsKey("id")){
                System.out.println(Integer.parseInt(params.get("id")));
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(Collections.singletonList(this.artistRepository.findById(Integer.parseInt(params.get("id")))));
            }

            if(params.containsKey("name")){
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(this.artistRepository.findByNameContaining(params.get("name")));
            }

            if(params.containsKey("description")){
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(this.artistRepository.findByDescriptionContaining(params.get("description")));
            }
        }
        catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping
    public ResponseEntity<Artist> postArtist(@RequestBody Artist artist){
        try{
            for (Artist _artist: this.artistRepository.findByName(artist.getName())){
                if (artist.equals(_artist)){
                    return ResponseEntity.status(HttpStatus.CONFLICT).build();
                }
            }

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(this.artistRepository.save(artist));
        }
        catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteArtist(@RequestParam long id){
        try{
            if (!this.artistRepository.existsById(id)){
                return ResponseEntity.noContent().build();
            }

            //Disassociate all playlists from this artist's songs
            for(Song song : this.artistRepository.findById(id).getSongs()){
                for(Playlist playlist : song.getPlaylists()){
                    playlist.getSongs().remove(song);
                }
            }

            this.artistRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        catch (Exception ex){
            return ResponseEntity.internalServerError().build();
        }
    }
}
