package app.service.implementation;

import app.configuration.HibernateConfiguration;
import app.model.Album;
import app.model.Song;
import app.repository.AlbumRepository;
import app.service.AlbumService;
import app.single_point_access.RepositorySinglePointAccess;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class AlbumServiceImpl implements AlbumService {
    private AlbumRepository albumRepository = RepositorySinglePointAccess.getAlbumRepository();
    @Override
    public Album save(Album entity) {
        return albumRepository.save(entity);
    }

    @Override
    public Album update(Album entity) {
        return albumRepository.update(entity);
    }

    @Override
    public List<Album> findAll() {
        return albumRepository.findAll();
    }

    @Override
    public Album findById(Integer id) {
        return albumRepository.findById(id);
    }

    @Override
    public Album findByName(String name) {
        return albumRepository.findByName(name);
    }

    @Override
    public boolean delete(Album entity) {
        return albumRepository.delete(entity);
    }

    @Override
    public boolean deleteByID(Integer id) {
        return albumRepository.deleteByID(id);
    }

    @Override
    public void addSongToAlbum(String albumName, Song song) {
        SessionFactory sessionFactory = HibernateConfiguration.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try{
            Album album = this.findByName(albumName);
            song.setAlbum(album);
            album.addSong(song);
            albumRepository.update(album);
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
