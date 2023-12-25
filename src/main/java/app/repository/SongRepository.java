package app.repository;

import app.model.Song;

public interface SongRepository extends CRUDRepository<Song, Integer, String>{
    Song findByName(String name);
}
