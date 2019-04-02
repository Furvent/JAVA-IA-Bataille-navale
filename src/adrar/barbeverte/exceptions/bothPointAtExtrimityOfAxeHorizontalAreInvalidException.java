package adrar.barbeverte.exceptions;

public class bothPointAtExtrimityOfAxeHorizontalAreInvalidException extends Exception {
	// ===========================================================
	// Constants
	// ===========================================================
	private static final String ERROR_MESSAGE = "Both Point are invalid in horizontal axe. ";

	// ===========================================================
	// Methods
	// ===========================================================

	private static final long serialVersionUID = 4L;

	public bothPointAtExtrimityOfAxeHorizontalAreInvalidException(int firstExtremity, int lastExtremity) {
		super(ERROR_MESSAGE + "First X extremity is: " + firstExtremity + " and last is: " + lastExtremity + ".");
	}
}
