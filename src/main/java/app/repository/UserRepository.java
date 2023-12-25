package app.repository;

import app.model.User;

public interface UserRepository extends CRUDRepository<User, Integer, String> {

    User findByName(String name);

    User findByNameAndPassword(String name, String password);
}
