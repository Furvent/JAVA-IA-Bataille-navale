package ai;

import java.util.List;

import adrar.barbeverte.PointBean;
import adrar.barbeverte.enums.ModeAI;
import adrar.barbeverte.exceptions.NoPointDeterminateToGiveItBackToPlayer;

public class Core {
	// ===========================================================
	// Constants
	// ===========================================================
	private final int GRID_SIZE;

	// ===========================================================
	// Fields
	// ===========================================================
	private List<PointBean> alreadyTriedPointList;
	private SearchingBoatMode searchMode;
	private SinkingBoatMode sinkMode;
	private PointBean lastPointSentToPlayer;
	private ModeAI mode;

	// ===========================================================
	// Constructors
	// ===========================================================
	public Core(int gridSize) {
		GRID_SIZE = gridSize;
		searchMode = new SearchingBoatMode(GRID_SIZE);
		mode = ModeAI.SEARCH;
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
	public PointBean dearIAGiveMeAPoint() throws NoPointDeterminateToGiveItBackToPlayer {
		lastPointSentToPlayer = null;
		switch (mode) {
		case SEARCH:
			lastPointSentToPlayer = searchMode.debugGiveRandomePoint();
			break;
		case SINK:
			lastPointSentToPlayer = sinkMode.determinateAPointToStrike();
			break;
		}
		if (lastPointSentToPlayer == null) {
			throw new NoPointDeterminateToGiveItBackToPlayer();
		} else {
			return lastPointSentToPlayer;
		}
	}

	public void getFeedBackAboutPointSentToPlayer(boolean pointWasTouched) {
		alreadyTriedPointList.add(lastPointSentToPlayer);
		if (pointWasTouched) {
			pointSentToPlayerTouchedABoat();
		} else {
			pointSentToPlayerTouchedABoat();
		}
	}

	private void pointSentToPlayerTouchedABoat() {
		System.out.println("The shoot touch a boat !");
		switch (mode) {
		case SEARCH:
			System.out.println("IA is now in sinking mode");
			sinkMode = new SinkingBoatMode(lastPointSentToPlayer, GRID_SIZE);
			break;
		case SINK:
			System.out.println("IA is already in sinking mode");
			sinkMode.successfullyTouchedAnotherPoint();
			break;
		}
	}

	private void pointSentToPlayerDidntTouchABoat() {
		System.out.println("The shoot didn't touch a boat");
		switch (mode) {
		case SEARCH:
			break;
		case SINK:
			break;
		}
	}

}
