package app.repository.implementation;

import app.configuration.HibernateConfiguration;
import app.model.Playlist;
import app.model.User;
import app.repository.PlaylistRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class PlaylistRepositoryImpl implements PlaylistRepository {
    @Override
    public Playlist save(Playlist entity) {
        SessionFactory sessionFactory = HibernateConfiguration.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Integer idOnPlaylistSaved = (Integer) session.save(entity);

        transaction.commit();
        session.close();

        return findById(idOnPlaylistSaved);
    }


    @Override
    public Playlist update(Playlist entity) {
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
    public Playlist findById(Integer id) {
        SessionFactory sessionFactory = HibernateConfiguration.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        TypedQuery<Playlist> query = session.getNamedQuery("findPlaylistById");
        query.setParameter("id", id);

        Playlist playlist;
        try {
            playlist = (Playlist) query.getSingleResult();
        } catch (NoResultException e) {
            playlist = null;
        }

        transaction.commit();
        session.close();

        return playlist;
    }


    @Override
    public Playlist findByName(String name) {
        SessionFactory sessionFactory = HibernateConfiguration.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();


        TypedQuery<Playlist> query = session.getNamedQuery("findPlaylistByName");
        query.setParameter("name", name);

        Playlist playlist;
        try {
            playlist = (Playlist) query.getSingleResult();
        } catch (NoResultException e) {
            playlist = null;
        }

        transaction.commit();
        session.close();
        return playlist;
    }

    @Override
    public List<Playlist> findAll() {
        SessionFactory sessionFactory = HibernateConfiguration.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        // Native SQL - not preferred
//         Query query = session.createSQLQuery("select * from user");

        TypedQuery<Playlist> query = session.getNamedQuery("findAllPlaylists");
        List<Playlist> playlists = query.getResultList();

        transaction.commit();
        session.close();

        return playlists;
    }

    @Override
    public boolean delete(Playlist entity) {
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

        Playlist playlistToDelete = session.get(Playlist.class,id);
        if(playlistToDelete!=null)
            session.delete(playlistToDelete);

        transaction.commit();
        session.close();

        return findById(id) == null;
    }
}
