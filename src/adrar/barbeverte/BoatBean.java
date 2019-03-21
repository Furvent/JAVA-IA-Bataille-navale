package adrar.barbeverte;

import java.util.Map;

import adrar.barbeverte.exceptions.AlreadyTouchedPointOnThisBoatException;
import adrar.barbeverte.exceptions.WrongPointOnBoatTouchedException;

public class BoatBean implements Cloneable {

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

	// ===========================================================
	// Methods
	// ===========================================================

	public boolean isAPointOfBoat(PointBean point) {
		return isThisPointExistInPointMap(point);
	}

	/**
	 * Touche le bateau � un point donn� en argument. V�rifie si ce point existe
	 * bien dans le bateau, et v�rifie aussi que le bateau ne soit pas d�j�
	 * endommag� � ce point l�.
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
				return; // Bonne ou mauvaise id�e ?
			}
		}
	}
}
