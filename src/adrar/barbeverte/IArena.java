package adrar.barbeverte;

import ai.Core;

public class IArena {
	// ===========================================================
	// Constants
	// ===========================================================
	private final int NUMBER_OF_TRY = 1;
	private final int GRID_SIZE = 10;

	// ===========================================================
	// Fields
	// ===========================================================
	private PlayerBean player1;
	private PlayerBean player2;

	// ===========================================================
	// Constructors
	// ===========================================================
	public IArena() {
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

	public void launchTest() {
		Core ai1 = new Core(GRID_SIZE);
		Core ai2 = new Core(GRID_SIZE);
		player1 = new PlayerBean(ai1, "player1");
		player2 = new PlayerBean(ai2, "player2");
		for (int i = 0; i < NUMBER_OF_TRY; i++) {
			launchAParty();
		}
	}

	public void debugLaunchTest() {
		Core ai1 = new Core(GRID_SIZE);
		Core ai2 = new Core(GRID_SIZE);
		player1 = new PlayerBean(ai1, "player1");
		player2 = new PlayerBean(ai2, "player2");
		debugLaunchParty();
	}

	private void launchAParty() {
		Party party = new Party(player1, player2, 100);
		party.initParty(GRID_SIZE);
		debugBoatDescription(player1);
		debugBoatDescription(player2);
		party.RunParty();
		debugBoatDescription(player1);
		debugBoatDescription(player2);
	}

	private void debugBoatDescription(PlayerBean player) {
		System.out.println("DEBUG : Player x :");
		for (BoatBean boatDebug : player.getFleet().getBoatList()) {
			System.out.println("Pour le bateau : " + boatDebug.getListOfPointsDescription());
			for (PointBean pointDebug : boatDebug.getPointMap().keySet()) {
				System.out.println("Pour le point : " + pointDebug.getPosDescription());
				System.out.println("value: " + boatDebug.getPointMap().get(pointDebug));
			}
		}
	}

	private void debugLaunchParty() {
		Party party = new Party(player1, player2, 100);
		party.debugInitParty();
		debugBoatDescription(player1);
		debugBoatDescription(player2);
		party.RunParty();
	}
}
