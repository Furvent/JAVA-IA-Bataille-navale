package adrar.barbeverte;

import java.util.ArrayList;
import java.util.List;

import adrar.barbeverte.enums.Direction;
import adrar.barbeverte.exceptions.AlreadyTouchedPointOnThisBoatException;
import adrar.barbeverte.exceptions.WrongPointOnBoatTouchedException;

public class BoatBean {

	// ===========================================================
	// Fields
	// ===========================================================
	private int size;
	private PointBean originPoint;
	private List<PointBean> pointBeanList;

	private List<PointBean> touchedPointBeanList;
	private Direction direction;

	// ===========================================================
	// Constructors
	// ===========================================================
	public BoatBean(int size, PointBean originPoint, Direction direction) {
		this.size = size;
		this.originPoint = originPoint;
		this.direction = direction;
		touchedPointBeanList = new ArrayList<>();
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public PointBean getOriginPoint() {
		return originPoint;
	}

	public void setOriginPoint(PointBean originPoint) {
		this.originPoint = originPoint;
	}

	public List<PointBean> getTouchedPointBeanList() {
		return touchedPointBeanList;
	}

	public List<PointBean> getPointBeanList() {
		return pointBeanList;
	}

	public Direction getDirectionBean() {
		return direction;
	}

	public void setDirectionBean(Direction directionBean) {
		direction = directionBean;
	}

	// ===========================================================
	// Methods
	// ===========================================================

	/**
	 * Rajoute un point à la liste des points endommagés. Vérifie si ce point existe
	 * bien dans le bateau, et vérifie aussi que le bateau ne soit pas déjà
	 * endommagé à ce point là.
	 *
	 * @param point
	 */
	public void takeDamageAtThisPoint(PointBean point)
			throws WrongPointOnBoatTouchedException, AlreadyTouchedPointOnThisBoatException {
		if (isThisPointExistInPointList(point, pointBeanList)) {
			throw new WrongPointOnBoatTouchedException(this);
		} else if (isThisPointExistInPointList(point, touchedPointBeanList)) {
			throw new AlreadyTouchedPointOnThisBoatException(this);
		} else {
			touchedPointBeanList.add(point);
		}
	}

	private boolean isThisPointExistInPointList(PointBean point, List<PointBean> list) {
		for (PointBean boatPoint : list) {
			if (boatPoint.equals(point)) {
				return true;
			}
		}
		return false;
	}
}
