package spring.auth.services;

import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

public interface BaseDataService<T> {
    List<T> findAll();
    List<T> search(String searchTerm);
    T save(T entity) ;
    T delete(T entity) throws DataIntegrityViolationException;
}
