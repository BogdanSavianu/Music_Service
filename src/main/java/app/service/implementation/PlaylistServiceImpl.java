package app.service.implementation;

import app.configuration.HibernateConfiguration;
import app.model.Playlist;
import app.model.Song;
import app.model.User;
import app.repository.PlaylistRepository;
import app.repository.SongRepository;
import app.repository.UserRepository;
import app.service.PlaylistService;
import app.single_point_access.RepositorySinglePointAccess;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class PlaylistServiceImpl implements PlaylistService {

    private PlaylistRepository playlistRepository = RepositorySinglePointAccess.getPlaylistRepository();
    private SongRepository songRepository = RepositorySinglePointAccess.getSongRepository();
    @Override
    public Playlist save(Playlist playlist) {
        return playlistRepository.save(playlist);
    }

    @Override
    public Playlist update(Playlist playlist) {
        return playlistRepository.update(playlist);
    }

    @Override
    public List<Playlist> findAll() {
        return playlistRepository.findAll();
    }

    @Override
    public Playlist findById(Integer id) {
        return playlistRepository.findById(id);
    }

    @Override
    public boolean delete(Playlist playlist) {
        return playlistRepository.delete(playlist);
    }

    @Override
    public boolean deleteByID(Integer id) {
        return playlistRepository.deleteByID(id);
    }

    @Override
    public void addSongToPlaylist(Integer id, Song song) {
        SessionFactory sessionFactory = HibernateConfiguration.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try{
            Playlist playlist = this.findById(id);
            playlist.addSong(song);
            playlistRepository.update(playlist);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
