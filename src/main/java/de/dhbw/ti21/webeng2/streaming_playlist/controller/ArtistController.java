package de.dhbw.ti21.webeng2.streaming_playlist.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import de.dhbw.ti21.webeng2.streaming_playlist.model.Artist;
import de.dhbw.ti21.webeng2.streaming_playlist.repository.ArtistRepository;
import io.swagger.models.Response;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RestController
public class ArtistController {
    private ArtistRepository repository;

    @PostMapping("/artist")
    public ResponseEntity<Artist> postArtist(@RequestBody Artist artist){
        try{
            return new ResponseEntity<Artist>(this.repository.save(artist), HttpStatus.OK);
        }
        catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
