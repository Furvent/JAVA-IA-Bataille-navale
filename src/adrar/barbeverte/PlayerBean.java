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
	private String name;

	// ===========================================================
	// Constructors
	// ===========================================================
	public PlayerBean(Core ai, String name) {
		this.ai = ai;
		score = 0;
		this.name = name;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	public int getScore() {
		return score;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
		} catch (RuntimeException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pointBean;
	}

	public void getFeedbackAboutLastShoot(ShotFeedback feedback) {
		ai.getFeedBackAboutPointSentToPlayer(feedback);
	}

	public void initAIData() {
		ai.initAllData();
	}
}
