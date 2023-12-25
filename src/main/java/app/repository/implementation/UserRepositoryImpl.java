package app.repository.implementation;

import app.configuration.HibernateConfiguration;
import app.model.Playlist;
import app.model.User;
import app.repository.UserRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Set;

public class UserRepositoryImpl implements UserRepository {
    @Override
    public User save(User entity) {
        SessionFactory sessionFactory = HibernateConfiguration.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Integer idOnPersonSaved = (Integer) session.save(entity);

        transaction.commit();
        session.close();

        return findById(idOnPersonSaved);
    }

    @Override
    public User update(User entity) {
        // TO DO
        // Same logic - extract it somehow
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
    public List<User> findAll() {
        SessionFactory sessionFactory = HibernateConfiguration.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        // Native SQL - not preferred
//         Query query = session.createSQLQuery("select * from user");

        TypedQuery<User> query = session.getNamedQuery("findAllUsers");
        List<User> users = query.getResultList();

        transaction.commit();
        session.close();

        return users;
    }

    @Override
    public User findById(Integer id) {
        SessionFactory sessionFactory = HibernateConfiguration.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        // HQL - Hibernate Query Language, but use named query instead to reuse them
         Query query = session.createQuery("from User where id=:id");
         query.setParameter("id", id);

//        TypedQuery<User> query = session.getNamedQuery("findPersonById");
//        query.setParameter("id", id);

        User user;
        try {
            user = (User) query.getSingleResult();
        } catch (NoResultException e) {
            user = null;
        }

        transaction.commit();
        session.close();

        return user;
    }


    @Override
    public boolean delete(User entity) {
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

        User userToDelete = session.get(User.class,id);
        if(userToDelete!=null)
            session.delete(userToDelete);

        transaction.commit();
        session.close();

        return findById(id) == null;
    }

    @Override
    public User findByName(String name) {
        SessionFactory sessionFactory = HibernateConfiguration.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        // Use a Named Query - best solution
        TypedQuery<User> query = session.getNamedQuery("findUserByName");
        query.setParameter("name", name);
        User user;
        try {
            user = query.getSingleResult();
        } catch (NoResultException e) {
            user = null;
        }


        transaction.commit();
        session.close();

        return user;
    }

    @Override
    public User findByNameAndPassword(String name, String password) {
        SessionFactory sessionFactory = HibernateConfiguration.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        // TO DO
        // Same logic - extract it somehow
        TypedQuery<User> query = session.getNamedQuery("findUserByNameAndPassword");
        query.setParameter("name", name);
        query.setParameter("password", password);

        User user;
        try {
            user = query.getSingleResult();
        } catch (NoResultException e) {
            user = null;
        }

        transaction.commit();
        session.close();

        return user;
    }

}
