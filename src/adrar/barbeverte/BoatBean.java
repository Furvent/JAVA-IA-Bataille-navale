package adrar.barbeverte;

import java.util.ArrayList;
import java.util.List;

import adrar.barbeverte.enums.Direction;

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
	 * WIP TODO Lever exception
	 * 
	 * @param point
	 */
	public void takeDamageAtThisPoint(PointBean point) {
		touchedPointBeanList.add(point);
	}
}
