package adrar.barbeverte.exceptions;

import adrar.barbeverte.BoatBean;

public final class AlreadyTouchedPointOnThisBoatException extends Exception {
	// ===========================================================
	// Constants
	// ===========================================================
	private static final String ERROR_MESSAGE = "This point already touched this boat ¯\\_(ツ)_/¯";

	// ===========================================================
	// Methods
	// ===========================================================

	private static final long serialVersionUID = 4L;

	public AlreadyTouchedPointOnThisBoatException(BoatBean boat) {
		super(ERROR_MESSAGE + " size of " + boat.getSize() + " and positionned at point "
				+ boat.getListOfPointsDescription());
	}
}
