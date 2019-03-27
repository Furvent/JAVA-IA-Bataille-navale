package adrar.barbeverte;

public final class PlayerBean {
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
	// Methods
	// ===========================================================
	public void getDescription() {
		fleet.getDescription();
	}

	public void incrementeScore() {
		score++;
	}
}
