package adrar.barbeverte;

import adrar.barbeverte.enums.ShotFeedback;
import ai.Core;

public final class PlayerBean {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================
	private FleetBean fleet;
	private Core ai;
	private int score;

	// ===========================================================
	// Constructors
	// ===========================================================
	public PlayerBean(Core ai) {
		this.ai = ai;
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

	public PointBean chooseAPoint() {
		PointBean pointBean = null;
		try {
			pointBean = ai.dearIAGiveMeAPoint();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pointBean;
	}

	public void getFeedbackAboutLastShoot(ShotFeedback feedback) {

	}

	public void initAIData() {
		ai.initAllData();
	}
}
