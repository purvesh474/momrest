package momrest.exception;

public class FileStorageException extends RuntimeException{
	public FileStorageException(String message) {
        super(message);
        System.out.println(message);
    }

    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
        System.out.println(message);
    }
}
