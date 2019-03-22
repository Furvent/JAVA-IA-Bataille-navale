package factory;

import java.util.ArrayList;
import java.util.List;

import adrar.barbeverte.BoatBean;
import adrar.barbeverte.FleetBean;
import adrar.barbeverte.PointBean;
import adrar.barbeverte.enums.Direction;

public final class FleetFactory {
	// ===========================================================
	// Constants
	// ===========================================================
	// ===========================================================
	// Fields
	// ===========================================================
	private final int gridSize;

	private List<Integer> fleetBlueprint = new ArrayList<>();

	private FleetBean fleet; // Use as reference in all instance

	// ===========================================================
	// Constructors
	// ===========================================================
	public FleetFactory(int gridSize) {
		this.gridSize = gridSize;
		fleetBlueprint.add(4);
		fleetBlueprint.add(3);
		fleetBlueprint.add(2);
		fleetBlueprint.add(2);
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
	public FleetBean generateFleet() {
		fleet = new FleetBean(new ArrayList<>());
		for (int boatSize : fleetBlueprint) {
			fleet.addBoat(generateBoat(boatSize));
		}
		return fleet;
	}

	private BoatBean generateBoat(int newBoatSize) {

		BoatBean newBoat = null;

		while (newBoat == null) {
			// Trouver une Position random et tester à cette position
			newBoat = createBoatAtThisPointWithThisSize(getRandomPoint(), newBoatSize);
		}
		// return new BoatBean(new HashMap<PointBean, Boolean>());
	}

	/**
	 * Create a boat with a point and a size.
	 *
	 * @param point
	 * @param newBoatSize
	 * @return A boat instance, or null if the boat cannot be create at this point
	 */
	private BoatBean createBoatAtThisPointWithThisSize(PointBean point, int newBoatSize) {
		// Trouver une direction random et tester dans cette direction
		Direction direction = Direction.values()[getRandomInt(0, 3)];
		BoatBean newBoat = null;
		int howMuchDirectionsTried = 0;
		while (howMuchDirectionsTried < 4 && newBoat == null) {
			BoatFactory boatFactory = new BoatFactory(newBoatSize, point, direction);
			newBoat = boatFactory.CreateBoat();
			if (!(checkIfBoatGetValidPosition(newBoat))) {
				newBoat = null;
				direction.nexDirection();
				howMuchDirectionsTried++;
			}

		}
		return newBoat;
	}

	private boolean checkIfBoatGetValidPosition(BoatBean newBoat) {
		boolean check = (boatIsNotOutOfGridBoud(newBoat) && boatIsNotCrossingOrCloseToAnotherBoat(newBoat));
		return check;
	}

	private boolean boatIsNotCrossingOrCloseToAnotherBoat(BoatBean newBoat) {
		if (fleet.getBoatList().size() > 0) {
			for (BoatBean boatToCompare : fleet.getBoatList()) {
				if (isThoseBoatsCrossingEachOther(newBoat, boatToCompare) && isThisBoatTooCloseAnotherBoat(newBoat)) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean isThisBoatTooCloseAnotherBoat(BoatBean newBoat) {

		return;
	}

	private boolean isThoseBoatsCrossingEachOther(BoatBean firstBoat, BoatBean secondBoat) {
		for (PointBean point : firstBoat.getPointMap().keySet()) {
			if (secondBoat.isAPointOfBoat(point)) {
				return true;
			}
		}
		return false;
	}

	private boolean pointIsOutOfBoundGrid(PointBean point) {
		boolean check = (point.getAxeX() < 1 && point.getAxeX() > gridSize && point.getAxeY() < 1
				&& point.getAxeY() > gridSize);
		return check;
	}

	private boolean boatIsNotOutOfGridBoud(BoatBean newBoat) {
		for (PointBean point : newBoat.getPointMap().keySet()) {
			if (pointIsOutOfBoundGrid(point)) {
				return false;
			}
		}
		return true;
	}

	private PointBean getRandomPoint() {
		return new PointBean(getRandomInt(1, gridSize), getRandomInt(1, gridSize));
	}

	private int getRandomInt(int min, int max) {
		return (int) (Math.random() * max + min);
	}
}
