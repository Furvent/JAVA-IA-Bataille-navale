package ai;

import java.util.ArrayList;
import java.util.List;

import adrar.barbeverte.PointBean;
import adrar.barbeverte.enums.AxeBoat;
import adrar.barbeverte.enums.Direction;
import adrar.barbeverte.exceptions.AllDirectionsTestedException;
import adrar.barbeverte.exceptions.CantDeterminateAxeWithThisTwoPointsException;

public class SinkingBoatMode {
	// ===========================================================
	// Constants
	// ===========================================================
	private final int GRID_SIZE;

	// ===========================================================
	// Fields
	// ===========================================================
	private PointBean firstPointTouched;
	private List<PointBean> alreadyTouchedPointList;
	private List<Direction> directionTriedList;
	private AxeBoat axeOfBoatHunted;

	// ===========================================================
	// Constructors
	// ===========================================================
	public SinkingBoatMode(PointBean firstPointTouched, int gridSize) {
		this.firstPointTouched = firstPointTouched;
		alreadyTouchedPointList = new ArrayList<>();
		directionTriedList = new ArrayList<>();
		alreadyTouchedPointList.add(firstPointTouched);
		axeOfBoatHunted = null;
		GRID_SIZE = gridSize;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	public void successfullyTouchedAnotherPoint() { // WIP
		if (axeOfBoatHunted == null) {
			System.out.println("In sinking mode, ai will try to determinate the axe of the boat");
			axeOfBoatHunted = determinateAxeWithTwoPoints();
		}
	}

	public PointBean determinateAPointToStrike() {
		if (axeOfBoatHunted == null) {
			PointBean pointToStrike = tryToDeterminateAxeOfBoatWithDirectionTriedList();
		}
	}

	private PointBean tryToDeterminateAxeOfBoatWithDirectionTriedList() {
		System.out.println("Try to determinate the axe of boat with DirectionTriedList");
		PointBean pointToStrike = null;
		if (directionTriedList.size() == 0) {
			System.out.println("No direction tried for now");
			Direction direction = Direction.values()[getRandomInt(0, 3)];
			pointToStrike = getPointFromFirstPointTouchedAndARandomDirection(direction);
		} else {
			System.out.println("Try a direction never test");
			try {
				pointToStrike = tryADirectionsWithDirectionTriedList();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return pointToStrike;
	}

	/**
	 * Recursive
	 *
	 * @param direction
	 * @return
	 */
	private PointBean getPointFromFirstPointTouchedAndARandomDirection(Direction direction) {
		System.out.println("Try to get a point from random direction");
		PointBean point = getPointFromThisPointAndDirection(firstPointTouched, direction);
		if (point.isInThisGrid(GRID_SIZE)) {
			return point;
		} else {
			direction.nexDirection();
			return getPointFromFirstPointTouchedAndARandomDirection(direction);
		}
	}

	/**
	 * Mauvaise fonction, trop longue et deux niveaux d'abstraction
	 *
	 * @return
	 * @throws AllDirectionsTestedException
	 */
	private PointBean tryADirectionsWithDirectionTriedList() throws AllDirectionsTestedException {
		System.out.println("Trying a direction with directionTriedList");
		int numberOfDirectionTried = 0;
		PointBean point = null;
		Direction direction = directionTriedList.get(directionTriedList.size() - 1);
		direction.nexDirection();

		while (point == null) {
			if (isDirectionInDirectionTriedList(direction)) {
				direction.nexDirection();
				numberOfDirectionTried++;
				if (numberOfDirectionTried > 4) {
					throw new AllDirectionsTestedException();
				}
			} else {
				System.out.println("Find a direction never tested");
				directionTriedList.add(direction);
				point = getPointFromFirstPointTouchedAndARandomDirection(direction);
				if (point.isInThisGrid(GRID_SIZE)) {
					System.out.println("Find a valid point in a direction never tested");
					return point;
				} else {
					System.out.println("Point found in this direction is out of grid, trying another direction");
					numberOfDirectionTried++;
				}
			}
		}
		return null;
	}

	private PointBean getPointFromThisPointAndDirection(PointBean basePoint, Direction direction) {

		switch (direction) {
		case UP:
			return new PointBean(basePoint.getAxeX(), basePoint.getAxeY() - 1);

		case RIGHT:
			return new PointBean(basePoint.getAxeX() + 1, basePoint.getAxeY());

		case DOWN:
			return new PointBean(basePoint.getAxeX(), basePoint.getAxeY() + 1);

		case LEFT:
			return new PointBean(basePoint.getAxeX() - 1, basePoint.getAxeY());

		default:
			System.out.println(
					"In SinkingBoat, in method getPointFromThisPointAndDirection, switch reach the default case, that's a big problem");
			return null;
		}
	}

	private AxeBoat determinateAxeWithTwoPoints() throws CantDeterminateAxeWithThisTwoPointsException {
		if (isAxeHorizontal()) {

		} else if (isAxeVertical) {

		} else {
			throw new CantDeterminateAxeWithThisTwoPointsException(firstPoint, secondPoint)
		}
	}

	private int getRandomInt(int min, int max) {
		return (int) ((Math.random() * max) + min);
	}

	private boolean isDirectionInDirectionTriedList(Direction direction) {
		for (Direction directionInList : directionTriedList) {
			if (direction == directionInList) {
				return true;
			}
		}
		return false;
	}
}
