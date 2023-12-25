package app.service;

import app.model.User;

import java.util.List;

public interface UserService {

    User save(User user);

    User update(User user);

    List<User> findAll();

    User findById(Integer id);

    User findByName(String name);

    boolean delete(User user);

    boolean deleteByID(Integer id);

    void createPlaylist(User user, String name);
    void addPlaylistToUser(Integer userID, String playlistName);

    User login(String name, String password);
}
