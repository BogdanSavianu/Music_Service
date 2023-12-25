package app.service;

import app.model.Album;
import app.model.Artist;
import app.model.Song;

public interface ArtistService extends ServiceTemplate<Artist, Integer, String>{
    Artist findByName(String name);

    void addSongToArtist(String artistName, Song song);

    void addAlbumToArtist(String artistName, Album album);
}
