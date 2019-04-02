package adrar.barbeverte;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import adrar.barbeverte.enums.ShotFeedback;
import factory.FleetFactory;

public final class Party {
	// ===========================================================
	// Constants
	// ===========================================================
	private final int MAX_TURN;
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
	public Party(PlayerBean player1, PlayerBean player2, int maxTurn) {
		this.player1 = player1;
		this.player2 = player2;
		MAX_TURN = maxTurn;

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

	public void debugInitParty() {
		player1.initAIData();
		player2.initAIData();
		Map<PointBean, Boolean> testPointMap1 = new HashMap<>();
		testPointMap1.put(new PointBean(1, 1), false);
		testPointMap1.put(new PointBean(1, 2), false);
		testPointMap1.put(new PointBean(1, 3), false);
		testPointMap1.put(new PointBean(1, 4), false);
		BoatBean testBoat1 = new BoatBean(testPointMap1);

		ArrayList<BoatBean> boatList1 = new ArrayList<>();
		player1.setFleet(new FleetBean(boatList1));

		player1.getFleet().addBoat(testBoat1);

		Map<PointBean, Boolean> testPointMap2 = new HashMap<>();
		testPointMap2.put(new PointBean(3, 4), false);
		testPointMap2.put(new PointBean(3, 5), false);
		testPointMap2.put(new PointBean(3, 6), false);
		testPointMap2.put(new PointBean(3, 7), false);
		BoatBean testBoat2 = new BoatBean(testPointMap2);

		ArrayList<BoatBean> boatList2 = new ArrayList<>();
		player2.setFleet(new FleetBean(boatList2));

		player2.getFleet().addBoat(testBoat2);
	}

	public void RunParty() {

		while (winner == null) {
			makeATurn();
		}

		winner.incrementeScore();
		describeParty();
	}

	private ShotFeedback playerIsAttackedAtThisPoint(PlayerBean player, PointBean point) {
		return player.getFleet().strikeAtThisPoint(point);
	}

	private void makeATurn() {
		turn++;
		if (turn > MAX_TURN) {
			System.out.println("PARTIE TERMINÉE parce que le MAXIMUM de tour a été atteint");
			winner = player1;
		}
		if (!player1.getFleet().getBoatList().isEmpty()) {
			ShotFeedback feedback = playerIsAttackedAtThisPoint(player2, player1.chooseAPoint());
			System.out.println("Feedback in makeTurn: " + feedback.getShotfeedbackDescription());
			player1.getFeedbackAboutLastShoot(feedback);
			System.out.println("Player 1 has played");
			System.out.println(" ---- Player 2 is playing now");
		} else {
			winner = player2;
		}

		if (!player2.getFleet().getBoatList().isEmpty()) {
			ShotFeedback feedback = playerIsAttackedAtThisPoint(player1, player2.chooseAPoint());
			System.out.println("Feedback in makeTurn: " + feedback.getShotfeedbackDescription());
			player2.getFeedbackAboutLastShoot(feedback);
			System.out.println("Player 2 has played");
			System.out.println(" **** Player 1 is playing now");
		} else {
			winner = player1;
		}
	}

	private void describeParty() {
		System.out.println("La partie s'est terminée après " + turn + " tours. Le gagnant est : ");
		if (winner == player1) {
			System.out.println("Player 1 avec un score actuel de " + player1.getScore() + ".");
		} else {
			System.out.println("Player 2 avec un score actuel de " + player2.getScore() + ".");
		}
	}
}
