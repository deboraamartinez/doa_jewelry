package doa_jewelry.exception;

public class EntityAlreadyExistsException extends RepositoryException {
    public EntityAlreadyExistsException(String message) {
        super(message);
    }

    public EntityAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
