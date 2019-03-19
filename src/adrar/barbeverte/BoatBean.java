package adrar.barbeverte;

import java.awt.List;

import adrar.barbeverte.enums.DirectionBean;

public class BoatBean {

	// ===========================================================
	// Fields
	// ===========================================================
	private int size;
	private PointBean originPoint;
	private List touchedPointBeanList;
	private DirectionBean directionBean;

	// ===========================================================
	// Constructors
	// ===========================================================
	public BoatBean(int size, PointBean originPoint, DirectionBean directionBean) {
		this.size = size;
		this.originPoint = originPoint;
		this.directionBean = directionBean;
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

	public List getTouchedPointBeanList() {
		return touchedPointBeanList;
	}

	public void setTouchedPointBeanList(List touchedPointBeanList) {
		this.touchedPointBeanList = touchedPointBeanList;
	}

	public DirectionBean getDirectionBean() {
		return directionBean;
	}

	public void setDirectionBean(DirectionBean directionBean) {
		this.directionBean = directionBean;
	}

	// ===========================================================
	// Methods
	// ===========================================================
	public boolean DoesItTouchAtThisPoint(PointBean point) {

	}
}
