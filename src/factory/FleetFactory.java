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

	private List<PointBean> pointsOccupiedList;

	// ===========================================================
	// Constructors
	// ===========================================================
	public FleetFactory(int gridSize) {
		this.gridSize = gridSize;
		pointsOccupiedList = new ArrayList<>();
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
		pointsOccupiedList.clear(); // Remise à zéro de la liste de points déjà occupés
		for (int boatSize : fleetBlueprint) {
			System.out.println("Debug : Generate a boat in function generateFleet");
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
		addPointsOccupied(newBoat);
		return newBoat;
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
		int durectionTriedNumber = 0;
		while ((durectionTriedNumber < 4) && (newBoat == null)) {
			BoatFactory boatFactory = new BoatFactory(newBoatSize, point, direction);
			newBoat = boatFactory.CreateBoat();
			if (!(isBoatGetValidPosition(newBoat))) {
				newBoat = null;
				direction.nexDirection();
				durectionTriedNumber++;
			}

		}
		return newBoat;
	}

	private boolean isBoatGetValidPosition(BoatBean newBoat) {
		boolean check = isBoatInGrid(newBoat) && !isBoatCrossingOthersBoats(newBoat)
				&& !isBoatTooCloseOthersBoats(newBoat);
		return check;
	}

	private boolean isBoatCrossingOthersBoats(BoatBean newBoat) {
		if (fleet.getBoatList().size() > 0) {
			for (BoatBean boatToCompare : fleet.getBoatList()) {
				if (isThoseBoatsCrossingEachOther(newBoat, boatToCompare)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean isBoatTooCloseOthersBoats(BoatBean newBoat) {
		for (PointBean point : newBoat.getPointMap().keySet()) {
			if (isPointTooCloseOtherBoats(point)) {
				return true;
			}
		}
		return false;
	}

	private boolean isPointTooCloseOtherBoats(PointBean point) {
		// Double boucle pour regarder toutes les cases autour du point, donc de -1 à 1
		// pour les x et les y.
		for (int x = -1; x < 2; x++) {

			for (int y = -1; y < 2; y++) {
				if ((x != 0) || (y != 0)) { // Si pas le point actuel
					if (isPointInPointOccupiedList(new PointBean(point.getAxeX() + x, point.getAxeY() + y))) {
						return true;
					}
				}
			}
		}
		return false;
	}

	private boolean isPointInPointOccupiedList(PointBean point) {
		for (PointBean pointOccupied : pointsOccupiedList) {
			if (point.haveSamePosition(pointOccupied)) {
				return true;
			}
		}
		return false;
	}

	private boolean isThoseBoatsCrossingEachOther(BoatBean firstBoat, BoatBean secondBoat) {
		for (PointBean point : firstBoat.getPointMap().keySet()) {
			if (secondBoat.isAPointOfBoat(point)) {
				return true;
			}
		}
		return false;
	}

	private boolean isBoatInGrid(BoatBean newBoat) {
		for (PointBean point : newBoat.getPointMap().keySet()) {
			if (!(point.isInThisGrid(gridSize))) {
				return false;
			}
		}
		return true;
	}

	private PointBean getRandomPoint() {
		return new PointBean(getRandomInt(1, gridSize), getRandomInt(1, gridSize));
	}

	private int getRandomInt(int min, int max) {
		return (int) ((Math.random() * max) + min);
	}

	private void addPointsOccupied(BoatBean boat) {
		for (PointBean point : boat.getPointMap().keySet()) {
			pointsOccupiedList.add(point);
		}
	}
}
