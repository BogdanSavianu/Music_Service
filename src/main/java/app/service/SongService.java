package app.service;

import app.model.Song;

import java.util.List;

public interface SongService {
    Song save(Song song);

    Song update(Song song);

    List<Song> findAll();

    Song findById(Integer id);

    boolean delete(Song song);

    boolean deleteByID(Integer id);
}
