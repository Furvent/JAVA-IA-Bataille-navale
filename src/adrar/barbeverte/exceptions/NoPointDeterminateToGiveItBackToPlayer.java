package adrar.barbeverte.exceptions;

public class NoPointDeterminateToGiveItBackToPlayer extends Exception {
	// ===========================================================
	// Constants
	// ===========================================================
	private static final String ERROR_MESSAGE = "No point send back to player after he asked for.";

	// ===========================================================
	// Methods
	// ===========================================================

	private static final long serialVersionUID = 6L;

	public NoPointDeterminateToGiveItBackToPlayer() {
		super(ERROR_MESSAGE);
	}
}
