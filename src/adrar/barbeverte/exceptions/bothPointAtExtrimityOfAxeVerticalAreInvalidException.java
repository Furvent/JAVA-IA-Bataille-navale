package adrar.barbeverte.exceptions;

public class bothPointAtExtrimityOfAxeVerticalAreInvalidException extends Exception {
	// ===========================================================
	// Constants
	// ===========================================================
	private static final String ERROR_MESSAGE = "Both Point are invalid in vertical axe. ";

	// ===========================================================
	// Methods
	// ===========================================================

	private static final long serialVersionUID = 4L;

	public bothPointAtExtrimityOfAxeVerticalAreInvalidException(int firstExtremity, int lastExtremity) {
		super(ERROR_MESSAGE + "First Y extremity is: " + firstExtremity + " and last is: " + lastExtremity + ".");
	}
}
