package adrar.barbeverte.exceptions;

public class NoAxeException extends Exception {
	// ===========================================================
	// Constants
	// ===========================================================
	private static final String ERROR_MESSAGE = "No Axe allocated, can't shoot";

	// ===========================================================
	// Fields
	// ===========================================================
	private static final long serialVersionUID = 6L;

	// ===========================================================
	// Constructors
	// ===========================================================
	public NoAxeException() {
		super(ERROR_MESSAGE);
	}
}
