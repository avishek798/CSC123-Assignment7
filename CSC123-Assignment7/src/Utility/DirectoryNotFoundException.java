package Utility;

public class DirectoryNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public DirectoryNotFoundException() {
		super();
	}

	public DirectoryNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

	public DirectoryNotFoundException(String message, Throwable cause) {
		super(message, cause);

	}

	public DirectoryNotFoundException(String message) {
		super(message);

	}

	public DirectoryNotFoundException(Throwable cause) {
		super(cause);

	}

}
