package adrar.barbeverte.exceptions;

public class AlreadySendPointException extends Exception {
	// ===========================================================
	// Constants
	// ===========================================================
	private static final String ERROR_MESSAGE = "This point was already tested";

	// ===========================================================
	// Methods
	// ===========================================================

	private static final long serialVersionUID = 4L;

	public AlreadySendPointException() {
		super(ERROR_MESSAGE);
	}
}
