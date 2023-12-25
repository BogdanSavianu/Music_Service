package app.service.implementation;

import app.model.Song;
import app.repository.SongRepository;
import app.service.SongService;
import app.single_point_access.RepositorySinglePointAccess;

import java.util.List;

public class SongServiceImpl implements SongService {
    private SongRepository songRepository = RepositorySinglePointAccess.getSongRepository();
    @Override
    public Song save(Song song) {
        return songRepository.save(song);
    }

    @Override
    public Song update(Song song) {
        return songRepository.update(song);
    }

    @Override
    public List<Song> findAll() {
        return songRepository.findAll();
    }

    @Override
    public Song findById(Integer id) {
        return songRepository.findById(id);
    }

    @Override
    public boolean delete(Song song) {
        return songRepository.delete(song);
    }

    @Override
    public boolean deleteByID(Integer id) {
        return songRepository.deleteByID(id);
    }
}
