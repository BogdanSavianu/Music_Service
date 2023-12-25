package app.service;

import app.model.Playlist;
import app.model.Song;

import java.util.List;

public interface PlaylistService {

    Playlist save(Playlist playlist);

    Playlist update(Playlist playlist);

    List<Playlist> findAll();

    Playlist findById(Integer id);

    boolean delete(Playlist playlist);

    boolean deleteByID(Integer id);

    void addSongToPlaylist(Integer id, Song song);
}
