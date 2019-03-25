package adrar.barbeverte;

public class PlayerBean {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================
	private FleetBean fleet;
	private int score;

	// ===========================================================
	// Constructors
	// ===========================================================
	public PlayerBean() {
		score = 0;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	public int getScore() {
		return score;
	}

//	public void setScore(int score) {
//		this.score = score;
//	}

	public FleetBean getFleet() {
		return fleet;
	}

	public void setFleet(FleetBean fleet) {
		this.fleet = fleet;
	}
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================
	public void getDescription() {
		fleet.getDescription();
	}

	/*
	 * Juste un bouchon pour l'instant
	 */
	public PointBean choosePoint() {
		return getRandomPoint(10);
	}

	public void incrementeScore() {
		score++;
	}

	private PointBean getRandomPoint(int gridSize) {
		return new PointBean(getRandomInt(1, gridSize), getRandomInt(1, gridSize));
	}

	private int getRandomInt(int min, int max) {
		return (int) (Math.random() * max + min);
	}
}
