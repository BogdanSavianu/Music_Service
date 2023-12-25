package app.service;

import app.model.Album;
import app.model.Song;

public interface AlbumService extends ServiceTemplate<Album, Integer, String>{
    Album findByName(String name);

    void addSongToAlbum(String albumName, Song song);
}
