package app.controller.rest;

import app.dto.AlbumDTO;
import app.model.Album;
import app.model.Song;
import app.service.AlbumService;
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
@RequestMapping("/album")
public class AlbumController {

    private AlbumService albumService = ServiceSinglePointAccess.getAlbumService();

    // Map http://localhost:8080/user/all
    // Get - to take data from server
    @GetMapping("/all")
    public ResponseEntity<List<Album>> getAllAlbums() {

        // Return a Response which has a status and a body (data)
        return ResponseEntity.status(HttpStatus.OK).body(albumService.findAll());
    }

    // {id} - is a value sent by url and is named path variable
    // {id} - will be taken from path - Path Variable
    // Attention - GET doesn't have a body
    @GetMapping("/id/{id}")
    public ResponseEntity<Album> getAlbumById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(albumService.findById(id));
    }

    // Post - create data
    // RequestBody - is the data sent to server through request
    // For POST, PUT, DELETE we can send information both : Path Variable & RequestBody
    @PostMapping("/create")
    public ResponseEntity<Album> createAlbum(@RequestBody Album album) {
        return ResponseEntity.status(HttpStatus.OK).body(albumService.save(album));
    }

    @PostMapping("/addSongToAlbum/{id}")
    public ResponseEntity<Album> addSongToPlaylist(@PathVariable String albumName, @RequestBody Song song) {
            albumService.addSongToAlbum(albumName, song);
            return ResponseEntity.status(HttpStatus.OK).build();
    }

    // Put - update data
    // Put with path variable
    @PutMapping("/updateName/{id}/{name}")
    public ResponseEntity<Album> updateAlbumName(@PathVariable Integer id, @PathVariable String name) {
        Album album = albumService.findById(id);
        album.setName(name);
        Album albumUpdated = albumService.update(album);
        return ResponseEntity.status(HttpStatus.OK).body(album);
    }

    @PutMapping("/update")
    public ResponseEntity<Album> update(@RequestBody Album album) {
        Album albumFromDB = albumService.findById(album.getId());
        albumFromDB.setName(album.getName());
        albumFromDB.setAuthor(album.getAuthor());
        Album albumUpdated = albumService.update(albumFromDB);
        return ResponseEntity.status(HttpStatus.OK).body(albumUpdated);
    }

    // Delete
    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> deleteById(@RequestBody Integer id) {
        Album album = albumService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(albumService.delete(album));
    }

    @Operation(summary = "Get details (name, email, password, list of songs) from all users")
    @GetMapping("/details_all")
    public ResponseEntity<List<AlbumDTO>> getAllAlbumDetails() {

        List<Album> albums = albumService.findAll();
        List<AlbumDTO> albumsDTO = new ArrayList<>();

        for (Album album: albums) {
            AlbumDTO albumDTO = new AlbumDTO();
            albumDTO.setName(album.getName());
            albumDTO.setAuthor(album.getAuthor());

            albumsDTO.add(albumDTO);
        }

        return ResponseEntity.status(HttpStatus.OK).body(albumsDTO);
    }

}