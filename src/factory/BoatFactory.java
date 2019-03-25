package factory;

import java.util.HashMap;
import java.util.Map;

import adrar.barbeverte.BoatBean;
import adrar.barbeverte.PointBean;
import adrar.barbeverte.enums.Direction;

public final class BoatFactory {
	// ===========================================================
	// Constants
	// ===========================================================
	private final int SIZE;
	private final PointBean ORIGIN_POINT;
	private final Direction DIRECTION;

	// ===========================================================
	// Fields
	// ===========================================================
	private Map<PointBean, Boolean> pointMap;

	// ===========================================================
	// Constructors
	// ===========================================================
	public BoatFactory(int size, PointBean originPoint, Direction direction) {
		SIZE = size;
		ORIGIN_POINT = originPoint;
		DIRECTION = direction;
		pointMap = new HashMap<>();
	}

	// ===========================================================
	// Methods
	// ===========================================================

	public BoatBean CreateBoat() {
		createMapOfPoint();
		BoatBean newBoat = new BoatBean(pointMap);
		return newBoat;
	}

	private void createMapOfPoint() {
		switch (DIRECTION) {
		case UP:
			populateInUpDirection();
			break;
		case RIGHT:
			populateInRightDirection();
			break;
		case DOWN:
			populateInDownDirection();
			break;
		case LEFT:
			populateInLeftDirection();
			break;

		default:
			System.out.println("ERROR, bad direction in boat factory");
			break;
		}
	}

	private void populateInUpDirection() {
		for (int i = 0; i < SIZE; i++) {
			pointMap.put(new PointBean(ORIGIN_POINT.getAxeX(), ORIGIN_POINT.getAxeY() - i), false);
		}
	}

	private void populateInRightDirection() {
		for (int i = 0; i < SIZE; i++) {
			pointMap.put(new PointBean(ORIGIN_POINT.getAxeX() + i, ORIGIN_POINT.getAxeY()), false);
		}
	}

	private void populateInDownDirection() {
		for (int i = 0; i < SIZE; i++) {
			pointMap.put(new PointBean(ORIGIN_POINT.getAxeX(), ORIGIN_POINT.getAxeY() + i), false);
		}
	}

	private void populateInLeftDirection() {
		for (int i = 0; i < SIZE; i++) {
			pointMap.put(new PointBean(ORIGIN_POINT.getAxeX() - i, ORIGIN_POINT.getAxeY()), false);
		}
	}
}
