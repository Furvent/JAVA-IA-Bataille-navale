package ai;

import adrar.barbeverte.PointBean;

public final class SearchingBoatMode {
	// ===========================================================
	// Constants
	// ===========================================================
	private final int GRID_SIZE;

	// ===========================================================
	// Fields
	// ===========================================================

	// ===========================================================
	// Constructors
	// ===========================================================
	public SearchingBoatMode(int gridSize) {
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
	public PointBean debugGiveRandomePoint() {
		System.out.println("I'm in searching mode, and core ai asked me to find a point");
		return new PointBean(getRandomInt(1, GRID_SIZE), getRandomInt(1, GRID_SIZE));
	}

	private int getRandomInt(int min, int max) {
		return (int) ((Math.random() * max) + min);
	}
}
