package adrar.barbeverte;

import java.util.ArrayList;
import java.util.List;

import adrar.barbeverte.exceptions.AlreadyTouchedPointOnThisBoatException;
import adrar.barbeverte.exceptions.WrongPointOnBoatTouchedException;

public class BoatBean {

	// ===========================================================
	// Fields
	// ===========================================================
	private List<PointBean> pointBeanList;
	private List<PointBean> touchedPointBeanList;

	// ===========================================================
	// Constructors
	// ===========================================================
	public BoatBean(List<PointBean> pointBeanList) {
		touchedPointBeanList = new ArrayList<>();
		this.pointBeanList = pointBeanList;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	public int getSize() {
		return pointBeanList.size();
	}

	public List<PointBean> getTouchedPointBeanList() {
		return touchedPointBeanList;
	}

	public List<PointBean> getPointBeanList() {
		return pointBeanList;
	}

	// ===========================================================
	// Methods
	// ===========================================================

	/**
	 * Rajoute un point � la liste des points endommag�s. V�rifie si ce point existe
	 * bien dans le bateau, et v�rifie aussi que le bateau ne soit pas d�j�
	 * endommag� � ce point l�.
	 *
	 * @param point
	 */
	public void takeDamageAtThisPoint(PointBean point)
			throws WrongPointOnBoatTouchedException, AlreadyTouchedPointOnThisBoatException {
		if (!(isThisPointExistInAListOfPoint(point, pointBeanList))) {
			throw new WrongPointOnBoatTouchedException(this);
		} else if (isThisPointExistInAListOfPoint(point, touchedPointBeanList)) {
			throw new AlreadyTouchedPointOnThisBoatException(this);
		} else {
			touchedPointBeanList.add(point);
		}
	}

	public String getListOfPointsDescription(List<PointBean> list) {
		String description = "";

		for (PointBean point : list) {
			description += " " + point.getPosDescription();
		}
		return description;
	}

	private boolean isThisPointExistInAListOfPoint(PointBean point, List<PointBean> list) {
		for (PointBean boatPoint : list) {
			if (boatPoint.haveSamePosition(point)) {
				return true;
			}
		}
		return false;
	}
}
