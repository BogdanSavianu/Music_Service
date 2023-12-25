package app.repository.implementation;

import app.configuration.HibernateConfiguration;
import app.model.Artist;
import app.model.Playlist;
import app.repository.ArtistRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class ArtistRepositoryImpl implements ArtistRepository {
    @Override
    public Artist save(Artist entity) {
        SessionFactory sessionFactory = HibernateConfiguration.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Integer idOnArtistSaved = (Integer) session.save(entity);

        transaction.commit();
        session.close();

        return findById(idOnArtistSaved);
    }

    @Override
    public Artist update(Artist entity) {
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
    public Artist findById(Integer id) {
        SessionFactory sessionFactory = HibernateConfiguration.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        TypedQuery<Artist> query = session.getNamedQuery("findArtistById");
        query.setParameter("id", id);

        Artist artist;
        try {
            artist = (Artist) query.getSingleResult();
        } catch (NoResultException e) {
            artist = null;
        }

        transaction.commit();
        session.close();

        return artist;
    }

    @Override
    public Artist findByName(String name) {
        SessionFactory sessionFactory = HibernateConfiguration.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();


        TypedQuery<Artist> query = session.getNamedQuery("findArtistByName");
        query.setParameter("name", name);

        Artist artist;
        try {
            artist = (Artist) query.getSingleResult();
        } catch (NoResultException e) {
            artist = null;
        }

        transaction.commit();
        session.close();
        return artist;
    }

    @Override
    public List<Artist> findAll() {
        SessionFactory sessionFactory = HibernateConfiguration.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        // Native SQL - not preferred
//         Query query = session.createSQLQuery("select * from user");

        TypedQuery<Artist> query = session.getNamedQuery("findAllArtists");
        List<Artist> artists = query.getResultList();

        transaction.commit();
        session.close();

        return artists;
    }

    @Override
    public boolean delete(Artist entity) {
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

        Artist artistToDelete = session.get(Artist.class,id);
        if(artistToDelete!=null)
            session.delete(artistToDelete);

        transaction.commit();
        session.close();

        return findById(id) == null;
    }

    public boolean deleteByName(String name) {
        SessionFactory sessionFactory = HibernateConfiguration.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Artist artistToDelete = session.get(Artist.class,name);
        if(artistToDelete!=null)
            session.delete(artistToDelete);

        transaction.commit();
        session.close();

        return findByName(name) == null;
    }
}
