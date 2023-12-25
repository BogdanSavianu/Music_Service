package app.service.implementation;

import app.configuration.HibernateConfiguration;
import app.model.Album;
import app.model.Artist;
import app.model.Song;
import app.repository.AlbumRepository;
import app.repository.ArtistRepository;
import app.repository.SongRepository;
import app.service.ArtistService;

import java.util.List;

import app.single_point_access.RepositorySinglePointAccess;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


public class ArtistServiceImpl implements ArtistService {
    private ArtistRepository artistRepository = RepositorySinglePointAccess.getArtistRepository();
    private SongRepository songRepository = RepositorySinglePointAccess.getSongRepository();
    private AlbumRepository albumRepository = RepositorySinglePointAccess.getAlbumRepository();

    @Override
    public Artist save(Artist entity) {
        return artistRepository.save(entity);
    }

    @Override
    public Artist update(Artist entity) {
        return artistRepository.update(entity);
    }

    @Override
    public List<Artist> findAll() {
        return artistRepository.findAll();
    }

    @Override
    public Artist findById(Integer id) {
        return artistRepository.findById(id);
    }

    @Override
    public Artist findByName(String name) {
        return artistRepository.findByName(name);
    }

    @Override
    public boolean delete(Artist entity) {
        return artistRepository.delete(entity);
    }

    @Override
    public boolean deleteByID(Integer id) {
        return artistRepository.deleteByID(id);
    }

    @Override
    public void addSongToArtist(String artistName, Song song){
        SessionFactory sessionFactory = HibernateConfiguration.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();;
        try{
            Artist artist = this.findByName(artistName);
            song.setAuthor(artist);
            //artist.addSong(song);
            songRepository.update(song);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void addAlbumToArtist(String artistName, Album album){
        SessionFactory sessionFactory = HibernateConfiguration.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();;
        try{
            Artist artist = this.findByName(artistName);
            album.setAuthor(artist);
            //artist.addAlbum(album);
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
