package app.controller.rest;

import app.dto.SongDTO;
import app.model.Song;
import app.service.SongService;
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
@RequestMapping("/song")
public class SongController {

    private SongService songService = ServiceSinglePointAccess.getSongService();

    // Map http://localhost:8080/user/all
    // Get - to take data from server
    @GetMapping("/all")
    public ResponseEntity<List<Song>> getAllSongs() {

        // Return a Response which has a status and a body (data)
        return ResponseEntity.status(HttpStatus.OK).body(songService.findAll());
    }

    // {id} - is a value sent by url and is named path variable
    // {id} - will be taken from path - Path Variable
    // Attention - GET doesn't have a body
    @GetMapping("/id/{id}")
    public ResponseEntity<Song> getSongById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(songService.findById(id));
    }

    // Post - create data
    // RequestBody - is the data sent to server through request
    // For POST, PUT, DELETE we can send information both : Path Variable & RequestBody
    @PostMapping("/create")
    public ResponseEntity<Song> createSong(@RequestBody Song song) {
        return ResponseEntity.status(HttpStatus.OK).body(songService.save(song));
    }

    // Put - update data
    // Put with path variable
    @PutMapping("/updateName/{id}/{name}")
    public ResponseEntity<Song> updateSongName(@PathVariable Integer id, @PathVariable String name) {
        Song song = songService.findById(id);
        song.setName(name);
        Song songUpdated = songService.update(song);
        return ResponseEntity.status(HttpStatus.OK).body(songUpdated);
    }

    @PutMapping("/update")
    public ResponseEntity<Song> update(@RequestBody Song song) {
        Song songFromDB = songService.findById(song.getId());
        songFromDB.setName(song.getName());
        songFromDB.setDuration(song.getDuration());
        songFromDB.setAuthor(song.getAuthor());
        songFromDB.setAlbum(song.getAlbum());
        Song songUpdated = songService.update(songFromDB);
        return ResponseEntity.status(HttpStatus.OK).body(songUpdated);
    }

    // Delete
    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> deleteById(@RequestBody Integer id) {
        Song song = songService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(songService.delete(song));
    }

    @Operation(summary = "Get details (name, album, author, duration) from all songs")
    @GetMapping("/details_all")
    public ResponseEntity<List<SongDTO>> getAllSongDetails() {

        List<Song> songs = songService.findAll();
        List<SongDTO> songsDTO = new ArrayList<>();

        for (Song song : songs) {
            SongDTO songDTO = new SongDTO();
            songDTO.setAuthor(song.getAuthor());
            songDTO.setAlbum(song.getAlbum());
            songDTO.setName(song.getName());
            songDTO.setDuration(song.getDuration());

            songsDTO.add(songDTO);
        }

        return ResponseEntity.status(HttpStatus.OK).body(songsDTO);
    }

}