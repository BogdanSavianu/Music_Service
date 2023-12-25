package app.repository.implementation;

import app.configuration.HibernateConfiguration;
import app.model.Album;
import app.model.User;
import app.repository.AlbumRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class AlbumRepositoryImpl implements AlbumRepository {
    @Override
    public Album save(Album entity) {
        SessionFactory sessionFactory = HibernateConfiguration.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Integer idOnAlbumSaved = (Integer) session.save(entity);

        transaction.commit();
        session.close();

        return findById(idOnAlbumSaved);
    }

    @Override
    public Album update(Album entity) {
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
    public List<Album> findAll() {
        SessionFactory sessionFactory = HibernateConfiguration.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        // Native SQL - not preferred
//         Query query = session.createSQLQuery("select * from user");

        TypedQuery<Album> query = session.getNamedQuery("findAllAlbums");
        List<Album> albums = query.getResultList();

        transaction.commit();
        session.close();

        return albums;
    }

    @Override
    public Album findById(Integer id) {
        SessionFactory sessionFactory = HibernateConfiguration.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        // HQL - Hibernate Query Language, but use named query instead to reuse them
        TypedQuery<Album> query = session.getNamedQuery("findAlbumById");
        query.setParameter("id", id);

//        TypedQuery<User> query = session.getNamedQuery("findPersonById");
//        query.setParameter("id", id);

        Album album;
        try {
            album = (Album) query.getSingleResult();
        } catch (NoResultException e) {
            album = null;
        }

        transaction.commit();
        session.close();

        return album;
    }


    @Override
    public boolean delete(Album entity) {
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

        Album albumToDelete = session.get(Album.class,id);
        if(albumToDelete!=null)
            session.delete(albumToDelete);

        transaction.commit();
        session.close();

        return findById(id) == null;
    }

    @Override
    public Album findByName(String name) {
        SessionFactory sessionFactory = HibernateConfiguration.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        // Use a Named Query - best solution
        TypedQuery<Album> query = session.getNamedQuery("findAlbumByName");
        query.setParameter("name", name);
        Album album;
        try {
            album = query.getSingleResult();
        } catch (NoResultException e) {
            album = null;
        }

        transaction.commit();
        session.close();

        return album;
    }

}
