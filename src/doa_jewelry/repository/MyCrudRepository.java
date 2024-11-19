package doa_jewelry.repository;

import doa_jewelry.exception.RepositoryException;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

public abstract class MyCrudRepository<E> {

    public E save(E entity) throws RepositoryException {
        return entity;
    }

    public E update(E entity) throws RepositoryException {
        // Implement update logic
        return entity;
    }

    public void deleteById(Long id) throws RepositoryException {
        // Implement delete logic
    }

    public Optional<E> findById(Long id) throws RepositoryException {
        // Implement findById logic
        return Optional.empty();
    }

    public List<E> findAll() throws RepositoryException {
        // Implement findAll logic
        return new ArrayList<>();
    }

    public boolean existsById(Long id) {
        // Implement existsById logic
        return false;
    }
}
