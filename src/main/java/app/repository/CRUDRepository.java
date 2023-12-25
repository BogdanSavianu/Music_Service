package app.repository;

import java.util.List;

public interface CRUDRepository<T, IdType, NameType> {

    T save(T entity);

    T update(T entity);

    T findById(IdType id);
    T findByName(NameType name);

    List<T> findAll();

    boolean delete(T entity);

    boolean deleteByID(IdType id);

}
