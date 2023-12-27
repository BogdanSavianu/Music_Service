package app.controller.rest;

import app.dto.AlbumDTO;
import app.dto.ArtistDTO;
import app.model.Album;
import app.model.Artist;
import app.model.Song;
import app.service.ArtistService;
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
@RequestMapping("/artist")
public class ArtistController {

    private ArtistService artistService = ServiceSinglePointAccess.getArtistService();

    // Map http://localhost:8080/user/all
    // Get - to take data from server
    @GetMapping("/all")
    public ResponseEntity<List<Artist>> getAllArtist() {

        // Return a Response which has a status and a body (data)
        return ResponseEntity.status(HttpStatus.OK).body(artistService.findAll());
    }

    // {id} - is a value sent by url and is named path variable
    // {id} - will be taken from path - Path Variable
    // Attention - GET doesn't have a body
    @GetMapping("/id/{id}")
    public ResponseEntity<Artist> getArtistById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(artistService.findById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Artist> getArtistByName(@PathVariable String name) {
        return ResponseEntity.status(HttpStatus.OK).body(artistService.findByName(name));
    }

    // Post - create data
    // RequestBody - is the data sent to server through request
    // For POST, PUT, DELETE we can send information both : Path Variable & RequestBody
    @PostMapping("/create")
    public ResponseEntity<Artist> createArtist(@RequestBody Artist artist) {
        return ResponseEntity.status(HttpStatus.OK).body(artistService.save(artist));
    }

    @PostMapping("/addSongToArtist/{id}")
    public ResponseEntity<Artist> addSongToArtist(@PathVariable String artistName, @RequestBody Song song) {
            artistService.addSongToArtist(artistName, song);
            return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/addAlbumToArtist/{id}")
    public ResponseEntity<Artist> addAlbumToArtist(@PathVariable String artistName, @RequestBody Album album) {
            artistService.addAlbumToArtist(artistName, album);
            return ResponseEntity.status(HttpStatus.OK).build();
    }

    // Put - update data
    // Put with path variable
    @PutMapping("/updateName/{id}/{name}")
    public ResponseEntity<Artist> updateArtistName(@PathVariable Integer id, @PathVariable String name) {
        Artist artist = artistService.findById(id);
        artist.setName(name);
        Artist artistUpdated = artistService.update(artist);
        return ResponseEntity.status(HttpStatus.OK).body(artist);
    }

    @PutMapping("/update")
    public ResponseEntity<Artist> update(@RequestBody Artist artist) {
        Artist artistFromDB = artistService.findById(artist.getId());
        artistFromDB.setName(artist.getName());
        Artist artistUpdated = artistService.update(artistFromDB);
        return ResponseEntity.status(HttpStatus.OK).body(artistUpdated);
    }

    // Delete
    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> deleteById(@RequestBody Integer id) {
        Artist artist = artistService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(artistService.delete(artist));
    }

    @Operation(summary = "Get details (name) from all artists")
    @GetMapping("/details_all")
    public ResponseEntity<List<ArtistDTO>> getAllArtistDetails() {

        List<Artist> artists = artistService.findAll();
        List<ArtistDTO> artistsDTO = new ArrayList<>();

        for (Artist artist: artists) {
            ArtistDTO artistDTO = new ArtistDTO();
            artistDTO.setName(artist.getName());

            artistsDTO.add(artistDTO);
        }

        return ResponseEntity.status(HttpStatus.OK).body(artistsDTO);
    }

}
