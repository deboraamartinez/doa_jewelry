package doa_jewelry.exception;

public class EntityNotFoundException extends RepositoryException {
    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityNotFoundException(Class<?> clazz, Long id) {
        super("Entidade " + clazz.getSimpleName() + " não encontrada com o ID: " + id);
    }
}
