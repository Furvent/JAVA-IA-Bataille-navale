package adrar.barbeverte.exceptions;

import adrar.barbeverte.BoatBean;

public final class WrongPointOnBoatTouchedException extends Exception {
	// ===========================================================
	// Constants
	// ===========================================================
	private static final String ERROR_MESSAGE = "This point is not a part of this boat ¯\\_(ツ)_/¯ Boat : ";

	// ===========================================================
	// Methods
	// ===========================================================

	private static final long serialVersionUID = 4L;

	public WrongPointOnBoatTouchedException(BoatBean boat) {
		super(ERROR_MESSAGE + " size of " + boat.getSize() + " and positionned at points :"
				+ boat.getListOfPointsDescription(boat.getPointBeanList()));
	}
}
