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

	private List<Integer> boatToMakeList = new ArrayList<>();

	private FleetBean fleet; // Use as reference in all instance

	// ===========================================================
	// Constructors
	// ===========================================================
	public FleetFactory(int gridSize) {
		this.gridSize = gridSize;
		boatToMakeList.add(4);
		boatToMakeList.add(3);
		boatToMakeList.add(2);
		boatToMakeList.add(2);
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
		for (int boatSize : boatToMakeList) {
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

	private BoatBean createBoatAtThisPointWithThisSize(PointBean point, int newBoatSize) {
		// Trouver une direction random et tester dans cette direction
		Direction direction = Direction.values()[getRandomInt(0, 3)];
		int howMuchDirectionsTried = 0;
		while (howMuchDirectionsTried < 4) {
			if (isThisDirectionIsOK()) {
				// Instantiate boat
			}
			direction.nexDirection();
			howMuchDirectionsTried++;
		}
		return null;
	}

	private boolean isThisDirectionIsOK() {
		// Regarder si hors grille
		if (!(pointIsOutOfGridBounds(point))) {
			// Regarder si bateau déjà présent
			// Créer le bateau
		}
	}

	private boolean pointIsOutOfGridBounds(PointBean point) {

	}

	private PointBean getRandomPoint() {
		return new PointBean(getRandomInt(1, gridSize), getRandomInt(1, gridSize));
	}

	private int getRandomInt(int min, int max) {
		return (int) (Math.random() * max + min);
	}
}
