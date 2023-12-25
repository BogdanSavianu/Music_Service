package app.repository.implementation;

import app.configuration.HibernateConfiguration;
import app.model.Playlist;
import app.model.Song;
import app.repository.SongRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class SongRepositoryImpl implements SongRepository {
    @Override
    public Song save(Song entity) {
        SessionFactory sessionFactory = HibernateConfiguration.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Integer idOnSongSaved = (Integer) session.save(entity);

        transaction.commit();
        session.close();

        return findById(idOnSongSaved);
    }

    @Override
    public Song update(Song entity) {
        SessionFactory sessionFactory = HibernateConfiguration.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Integer id = entity.getId();
        session.saveOrUpdate(entity);

        transaction.commit();
        session.close();

        return findById(id);
    }

    @Override
    public Song findById(Integer id) {
        return null;
    }


    @Override
    public Song findByName(String name) {
        SessionFactory sessionFactory = HibernateConfiguration.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        TypedQuery<Song> query = session.getNamedQuery("findSongByName");
        query.setParameter("name", name);

        Song song;
        try {
            song = (Song) query.getSingleResult();
        } catch (NoResultException e) {
            song = null;
        }

        transaction.commit();
        session.close();
        return song;
    }

    @Override
    public List<Song> findAll() {
        SessionFactory sessionFactory = HibernateConfiguration.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        // Native SQL - not preferred
//         Query query = session.createSQLQuery("select * from user");

        TypedQuery<Song> query = session.getNamedQuery("findAllSongs");
        List<Song> songs = query.getResultList();

        transaction.commit();
        session.close();

        return songs;
    }

    @Override
    public boolean delete(Song entity) {
        SessionFactory sessionFactory = HibernateConfiguration.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Integer id = entity.getId();
        session.delete(entity);

        transaction.commit();
        session.close();

        return findById(id) == null;
    }

    @Override
    public boolean deleteByID(Integer id) {
        SessionFactory sessionFactory = HibernateConfiguration.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Song songToDelete = session.get(Song.class,id);
        if(songToDelete!=null)
            session.delete(songToDelete);

        transaction.commit();
        session.close();

        return findById(id) == null;
    }
}
