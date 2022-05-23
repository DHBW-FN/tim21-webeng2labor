package de.dhbw.ti21.webeng2.streaming_playlist.controller;

import de.dhbw.ti21.webeng2.streaming_playlist.model.Playlist;
import de.dhbw.ti21.webeng2.streaming_playlist.repository.PlaylistRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/playlist")
public class PlaylistController {
    private PlaylistRepository playlistRepository;

    @GetMapping
    public ResponseEntity<List<Playlist>> getPlaylist(@RequestParam(required = false) Map<String, String> params){
        try{
            if(params.isEmpty()){
                return new ResponseEntity<>(this.playlistRepository.findAll(), HttpStatus.OK);
            }

            if(params.containsKey("id")){
                System.out.println(Integer.parseInt(params.get("id")));
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(Collections.singletonList(this.playlistRepository.findById(Integer.parseInt(params.get("id")))));
            }

            if(params.containsKey("name")){
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(this.playlistRepository.findByNameContaining(params.get("title")));
            }

            if(params.containsKey("song")){
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(this.playlistRepository.findBySongsTitleContaining(params.get("song")));
            }
        }
        catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
