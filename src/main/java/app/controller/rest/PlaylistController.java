package app.controller.rest;

import app.dto.PlaylistDTO;
import app.model.Playlist;
import app.model.Song;
import app.service.PlaylistService;
import app.single_point_access.ServiceSinglePointAccess;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController

// every request to http://localhost:8080/user will enter on this controller
@RequestMapping("/playlist")
public class PlaylistController {

    private PlaylistService playlistService = ServiceSinglePointAccess.getPlaylistService();

    // Map http://localhost:8080/user/all
    // Get - to take data from server
    @GetMapping("/all")
    public ResponseEntity<List<Playlist>> getAllPlatlists() {

        // Return a Response which has a status and a body (data)
        return ResponseEntity.status(HttpStatus.OK).body(playlistService.findAll());
    }

    // {id} - is a value sent by url and is named path variable
    // {id} - will be taken from path - Path Variable
    // Attention - GET doesn't have a body
    @GetMapping("/id/{id}")
    public ResponseEntity<Playlist> getPlaylistById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(playlistService.findById(id));
    }

    // Post - create data
    // RequestBody - is the data sent to server through request
    // For POST, PUT, DELETE we can send information both : Path Variable & RequestBody

    @PostMapping("/create")
    public ResponseEntity<Playlist> createPlaylist(@RequestBody Playlist playlist) {
        return ResponseEntity.status(HttpStatus.OK).body(playlistService.save(playlist));
    }

    @PostMapping("/addSongToPlaylist/{id}")
    public ResponseEntity<Playlist> addSongToPlaylist(@PathVariable Integer id, @RequestBody Song song) {
            playlistService.addSongToPlaylist(id, song);
            return ResponseEntity.status(HttpStatus.OK).build();
    }

    // Put - update data
    // Put with path variable
    @PutMapping("/updateName/{id}/{name}")
    public ResponseEntity<Playlist> updatePlaylistName(@PathVariable Integer id, @PathVariable String name) {
        Playlist playlist = playlistService.findById(id);
        playlist.setName(name);
        Playlist playlistUpdated = playlistService.update(playlist);
        return ResponseEntity.status(HttpStatus.OK).body(playlistUpdated);
    }

    @PutMapping("/update")
    public ResponseEntity<Playlist> update(@RequestBody Playlist playlist) {
        Playlist playlistFromDB = playlistService.findById(playlist.getId());
        playlistFromDB.setName(playlist.getName());
        playlistFromDB.setUser(playlist.getUser());
        playlistFromDB.setSongs(playlist.getSongs());
        Playlist playlistUpdated = playlistService.update(playlistFromDB);
        return ResponseEntity.status(HttpStatus.OK).body(playlistUpdated);
    }

    // Delete
    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> deleteById(@RequestBody Integer id) {
        Playlist playlist = playlistService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(playlistService.delete(playlist));
    }

    @Operation(summary = "Get details (name, user, list of songs) from all playlists")
    @GetMapping("/details_all")
    public ResponseEntity<List<PlaylistDTO>> getAllPlaylistDetails() {

        List<Playlist> playlists = playlistService.findAll();
        List<PlaylistDTO> playlistsDTO = new ArrayList<>();

        for (Playlist playlist : playlists) {
            PlaylistDTO playlistDTO = new PlaylistDTO();
            playlistDTO.setUser(playlist.getUser());
            playlistDTO.setName(playlist.getName());

            playlistsDTO.add(playlistDTO);
        }

        return ResponseEntity.status(HttpStatus.OK).body(playlistsDTO);
    }

}