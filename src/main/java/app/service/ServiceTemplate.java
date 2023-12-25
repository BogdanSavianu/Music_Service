package app.service;

import app.model.Song;

import java.util.List;

public interface ServiceTemplate<T,IdType, NameType>{
    T save(T entity);

    T update(T entity);

    List<T> findAll();

    T findById(IdType id);

    boolean delete(T entity);

    boolean deleteByID(IdType id);
}
