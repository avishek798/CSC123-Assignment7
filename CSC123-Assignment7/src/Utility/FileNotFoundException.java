package Utility;

public class FileNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public FileNotFoundException() {
		super();

	}

	public FileNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

	public FileNotFoundException(String message, Throwable cause) {
		super(message, cause);

	}

	public FileNotFoundException(String message) {
		super(message);

	}

	public FileNotFoundException(Throwable cause) {
		super(cause);

	}

}
