package adrar.barbeverte;

import java.util.Map;

import adrar.barbeverte.exceptions.AlreadyTouchedPointOnThisBoatException;
import adrar.barbeverte.exceptions.WrongPointOnBoatTouchedException;

public class BoatBean {

	// ===========================================================
	// Fields
	// ===========================================================
	private Map<PointBean, Boolean> pointMap;

	// ===========================================================
	// Constructors
	// ===========================================================
	public BoatBean(Map<PointBean, Boolean> pointMap) {
		this.pointMap = pointMap;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	public int getSize() {
		return pointMap.size();
	}

	public Map<PointBean, Boolean> getPointMap() {
		return pointMap;
	}

	public boolean isSunk() {
		for (boolean touched : pointMap.values()) {
			if (!touched) {
				return false;
			}
		}
		return true;
	}

	// ===========================================================
	// Methods
	// ===========================================================

	public boolean isAPointOfBoat(PointBean point) {
		return isThisPointExistInPointMap(point);
	}

	/**
	 * Touche le bateau à un point donné en argument. Vérifie si ce point existe
	 * bien dans le bateau, et vérifie aussi que le bateau ne soit pas déjà
	 * endommagé à ce point là.
	 *
	 * @param point
	 */
	public void takeDamageAtThisPoint(PointBean point)
			throws WrongPointOnBoatTouchedException, AlreadyTouchedPointOnThisBoatException {
		if (!(isThisPointExistInPointMap(point))) {
			throw new WrongPointOnBoatTouchedException(this);
		} else if (isThisPointAlreadyTouched(point)) {
			throw new AlreadyTouchedPointOnThisBoatException(this);
		} else {
			makeBoatPointTouched(point);
		}
	}

	public String getListOfPointsDescription() {
		String description = "";

		for (PointBean boatPoint : pointMap.keySet()) {
			description += boatPoint.getPosDescription();
			description += " --- ";
		}
		return description;
	}

	public boolean isThisPointAlreadyTouched(PointBean point) {
		for (PointBean boatPoint : pointMap.keySet()) {
			if (boatPoint.haveSamePosition(point)) {
				if (pointMap.get(boatPoint)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean isThisPointExistInPointMap(PointBean point) {
		for (PointBean boatPoint : pointMap.keySet()) {
			if (boatPoint.haveSamePosition(point)) {
				return true;
			}
		}
		return false;
	}

	private void makeBoatPointTouched(PointBean point) {
		System.out.println("Boat : " + getListOfPointsDescription() + " attacked");
		for (Map.Entry<PointBean, Boolean> entry : pointMap.entrySet()) {
			if (entry.getKey().haveSamePosition(point)) {
				entry.setValue(true);
				System.out
						.println("Boat" + getListOfPointsDescription() + "touched, is this boat is sunk : " + isSunk());
				return; // Bonne ou mauvaise idée ?
			}
		}
	}
}
