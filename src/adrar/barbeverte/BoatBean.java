package adrar.barbeverte;

import java.util.HashMap;
import java.util.List;
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
	public BoatBean(List<PointBean> pointBeanList) {
		pointMap = new HashMap<>();
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
		} else if (isThisPointExistInPointMap(point)) {
			throw new AlreadyTouchedPointOnThisBoatException(this);
		} else {
			makeBoatPointTouched(point);
		}
	}

	public String getListOfPointsDescription() {
		String description = "";

		for (PointBean boatPoint : pointMap.keySet()) {
			description += boatPoint.getPosDescription();
		}
		return description;
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
		for (Map.Entry<PointBean, Boolean> entry : pointMap.entrySet()) {
			if (entry.getKey().haveSamePosition(point)) {
				entry.setValue(true);
				return; // Bonne ou mauvaise idée ?
			}
		}
	}
}
