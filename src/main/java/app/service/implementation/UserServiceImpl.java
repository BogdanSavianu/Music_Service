package app.service.implementation;

import app.configuration.HibernateConfiguration;
import app.model.Playlist;
import app.model.User;
import app.repository.SongRepository;
import app.repository.UserRepository;
import app.service.UserService;
import app.single_point_access.RepositorySinglePointAccess;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserServiceImpl implements UserService {

    private UserRepository userRepository = RepositorySinglePointAccess.getUserRepository();
    private SongRepository songRepository = RepositorySinglePointAccess.getSongRepository();

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        return userRepository.update(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Integer id) {
        return userRepository.findById(id);
    }
    @Override
    public User findByName(String name){
        return userRepository.findByName(name);
    }

    @Override
    public boolean delete(User user) {
        return userRepository.delete(user);
    }

    @Override
    public boolean deleteByID(Integer id) {
        return userRepository.deleteByID(id);
    }

    public void createPlaylist(User user, String name) {
        Playlist playlist = new Playlist();
        playlist.setName(name);
        playlist.setUser(user);

        user.getPlaylists().add(playlist);
        userRepository.update(user);
    }

    @Override
    public void addPlaylistToUser(Integer userID, String playlistName){
        SessionFactory sessionFactory = HibernateConfiguration.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try{
            User user = this.findById(userID);
            Playlist playlist = new Playlist();
            playlist.setName(playlistName);
            playlist.setUser(user);
            user.addPlaylist(playlist);
           userRepository.update(user);
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
    public User login(String name, String password) {
        return userRepository.findByNameAndPassword(name, password);
    }
}
