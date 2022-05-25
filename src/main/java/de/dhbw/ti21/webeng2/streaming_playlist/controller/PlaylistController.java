package de.dhbw.ti21.webeng2.streaming_playlist.controller;

import de.dhbw.ti21.webeng2.streaming_playlist.model.Playlist;
import de.dhbw.ti21.webeng2.streaming_playlist.model.Song;
import de.dhbw.ti21.webeng2.streaming_playlist.repository.PlaylistRepository;
import de.dhbw.ti21.webeng2.streaming_playlist.repository.SongRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@AllArgsConstructor
@RestController
@RequestMapping("/playlist")
public class PlaylistController {
    private PlaylistRepository playlistRepository;
    private SongRepository songRepository;

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

    @PostMapping
    public ResponseEntity<Playlist> postPlaylist(@RequestBody Playlist playlist){
        try{
            //remove duplicate songs from song
            playlist.setSongs(new HashSet<>(playlist.getSongs()));

            Set<Song> songs = new HashSet<>();
            for (Song song : playlist.getSongs()) {
                if (this.songRepository.existsById(song.getId())) {
                    songs.add(this.songRepository.findById(song.getId()));
                    continue;
                }

                if(this.songRepository.findFirstByTitleContaining(song.getTitle()) != null) {
                    songs.add(this.songRepository.findFirstByTitleContaining(song.getTitle()));
                    continue;
                }

                if(!songs.contains(song)) {
                    return ResponseEntity.badRequest().build();
                }
            }
            playlist.setSongs(songs);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(this.playlistRepository.save(playlist));
        }
        catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/song")
    public ResponseEntity<Playlist> postSongToPlaylist(@RequestParam long playlistId, @RequestParam long songId){
        try{
            Playlist playlist = this.playlistRepository.findById(playlistId);
            Song song = this.songRepository.findById(songId);

            if(playlist == null || song == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            playlist.getSongs().add(song);
            this.playlistRepository.save(playlist);
            return ResponseEntity.ok(playlist);
        }
        catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @DeleteMapping("/song")
    public ResponseEntity<Playlist> removeSongFromPlaylist(@RequestParam long playlistId, @RequestParam long songId){
        try{
            Playlist playlist = this.playlistRepository.findById(playlistId);
            Song song = this.songRepository.findById(songId);

            if(playlist == null || song == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            playlist.getSongs().remove(song);
            this.playlistRepository.save(playlist);
            return ResponseEntity.ok(playlist);
        }
        catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deletePlaylist(@RequestParam Long id){
        try{
            if(!this.playlistRepository.existsById(id)){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            this.playlistRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
