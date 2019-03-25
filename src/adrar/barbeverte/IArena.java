package adrar.barbeverte;

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
		player1 = new PlayerBean();
		player2 = new PlayerBean();
		for (int i = 0; i < NUMBER_OF_TRY; i++) {
			launchAParty();
		}
	}

	private void launchAParty() {
		Party party = new Party(player1, player2);
		party.initParty(GRID_SIZE);
		debugSunkBoat(player1);
		debugSunkBoat(player2);
		party.RunParty();
		debugSunkBoat(player1);
		debugSunkBoat(player2);
	}

	private void debugSunkBoat(PlayerBean player) {
		System.out.println("DEBUG : Player x :");
		for (BoatBean boatDebug : player.getFleet().getBoatList()) {
			System.out.println("Pour le bateau : " + boatDebug.getListOfPointsDescription());
			for (PointBean pointDebug : boatDebug.getPointMap().keySet()) {
				System.out.println("Pour le point : " + pointDebug.getPosDescription());
				System.out.println("value: " + boatDebug.getPointMap().get(pointDebug));
			}
		}
	}
}
