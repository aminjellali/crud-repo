package exceptions;

public class InstanceNotValidException extends Exception {
	private static final long serialVersionUID = 1L;

	public InstanceNotValidException(String message) {
		super(message);
	}
}
