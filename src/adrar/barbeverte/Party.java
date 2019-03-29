package adrar.barbeverte;

import adrar.barbeverte.enums.ShotFeedback;
import factory.FleetFactory;

public final class Party {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================
	private PlayerBean player1;
	private PlayerBean player2;
	private PlayerBean winner;
	private int turn;

	// ===========================================================
	// Constructors
	// ===========================================================
	public Party(PlayerBean player1, PlayerBean player2) {
		this.player1 = player1;
		this.player2 = player2;
		winner = null;
		turn = 0;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================
	public void initParty(int gridSize) {
		FleetFactory fleetFactory = new FleetFactory(gridSize);
		player1.setFleet(fleetFactory.generateFleet());
		player2.setFleet(fleetFactory.generateFleet());
		player1.initAIData();
		player2.initAIData();
	}

	public void RunParty() {

		while (winner == null) {
			makeATurn();
		}

		winner.incrementeScore();
		describeParty();
	}

	private ShotFeedback playerIsAttackedAtThisPoint(PlayerBean player, PointBean point) {
		if (player.getFleet().isThereABoatAtThisPoint(point)) {
			player.getFleet().strikeAtThisPoint(point);
			return ShotFeedback.TOUCHED;
		} else {
			return ShotFeedback.MISSED;
		}
	}

	private void makeATurn() {
		turn++;
		if (turn > 100) {
			winner = player1;
		}
		if (!player1.getFleet().getBoatList().isEmpty()) {
			playerIsAttackedAtThisPoint(player2, player1.chooseAPoint());
			System.out.println("Player 1 has played");
		} else {
			winner = player2;
		}

		if (!player2.getFleet().getBoatList().isEmpty()) {
			playerIsAttackedAtThisPoint(player1, player2.chooseAPoint());
			System.out.println("Player 2 has played");
		} else {
			winner = player1;
		}
	}

	private void describeParty() {
		System.out.println("La partie s'est termin�e apr�s " + turn + " tours. Le gagnant est : ");
		if (winner == player1) {
			System.out.println("Player 1 avec un score actuel de " + player1.getScore() + ".");
		} else {
			System.out.println("Player 2 avec un score actuel de " + player2.getScore() + ".");
		}
	}
}
