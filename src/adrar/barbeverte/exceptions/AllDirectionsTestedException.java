package adrar.barbeverte.exceptions;

public class AllDirectionsTestedException extends Exception {
	// ===========================================================
	// Constants
	// ===========================================================
	private static final String ERROR_MESSAGE = "All directions tested, can't give a proper direction";

	// ===========================================================
	// Methods
	// ===========================================================

	private static final long serialVersionUID = 7L;

	public AllDirectionsTestedException() {
		super(ERROR_MESSAGE);
	}
}
