package adrar.barbeverte.exceptions;

import adrar.barbeverte.PointBean;

public class CantDeterminateAxeWithThisTwoPointsException extends Exception {
	// ===========================================================
	// Constants
	// ===========================================================
	private static final String ERROR_MESSAGE = "We can't determinate a axe to the unted boat with those two points: ";

	// ===========================================================
	// Methods
	// ===========================================================

	private static final long serialVersionUID = 6L;

	public CantDeterminateAxeWithThisTwoPointsException(PointBean firstPoint, PointBean secondPoint) {
		super(ERROR_MESSAGE + "-- first point: " + firstPoint.getPosDescription() + " and second point: "
				+ secondPoint.getPosDescription());
	}
}
