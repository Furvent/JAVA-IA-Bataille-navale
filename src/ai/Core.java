package ai;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import adrar.barbeverte.PointBean;
import adrar.barbeverte.enums.AxeBoat;
import adrar.barbeverte.enums.Direction;
import adrar.barbeverte.enums.ModeAI;
import adrar.barbeverte.enums.ShotFeedback;
import adrar.barbeverte.exceptions.AllDirectionsTestedException;
import adrar.barbeverte.exceptions.CantDeterminateAxeWithThisTwoPointsException;
import adrar.barbeverte.exceptions.NoAxeException;
import adrar.barbeverte.exceptions.NoPointDeterminateToGiveItBackToPlayer;

/**
 * TODO : Ne pas oublier de rajouter les points touchés dans la map
 * triedPointList
 *
 * @author matthieuKoskas
 *
 */

public final class Core {
	// ===========================================================
	// Constants
	// ===========================================================
	private final int GRID_SIZE;

	// ===========================================================
	// Fields
	// ===========================================================
	private PointBean firstPointTouchedWhenInSinkMode;
	private List<Direction> notTriedDirectionInSinkModeList;
	private AxeBoat axeOfBoatHunted;

	private void initSinkMode() {
		firstPointTouchedWhenInSinkMode = lastPointSentToPlayer;
		notTriedDirectionInSinkModeList = new ArrayList<>();
		notTriedDirectionInSinkModeList.add(Direction.UP);
		notTriedDirectionInSinkModeList.add(Direction.RIGHT);
		notTriedDirectionInSinkModeList.add(Direction.DOWN);
		notTriedDirectionInSinkModeList.add(Direction.LEFT);
		axeOfBoatHunted = AxeBoat.UNDEFINED;
	}

	private Map<PointBean, Boolean> triedPointAndIsPointTouchedMap;

	/**
	 * Classe pour l'instant, pas encore incorporé dans Core
	 */
	private SearchingBoatMode searchMode;
	private PointBean lastPointSentToPlayer;
	private ModeAI mode;

	// ===========================================================
	// Constructors
	// ===========================================================
	public Core(int gridSize) {
		GRID_SIZE = gridSize;
		searchMode = new SearchingBoatMode(GRID_SIZE);
		mode = ModeAI.SEARCH;
		axeOfBoatHunted = AxeBoat.UNDEFINED;
	}

	// ===========================================================
	// Getter & Setter
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
			lastPointSentToPlayer = determinateAPointToStrikeInSinkMode();
			break;
		}
		if (lastPointSentToPlayer == null) {
			throw new NoPointDeterminateToGiveItBackToPlayer();
		} else {
			return lastPointSentToPlayer;
		}
	}

	/**
	 * A diviser en deux fonctions
	 *
	 * @param feedback
	 */
	public void getFeedBackAboutPointSentToPlayer(ShotFeedback feedback) {
		boolean isPointTouched = (feedback == ShotFeedback.MISSED) ? false : true;
		triedPointAndIsPointTouchedMap.put(lastPointSentToPlayer, isPointTouched);

		switch (feedback) {
		case MISSED:
			pointSentToPlayerDidntTouchABoat();
			break;

		case SUNK:
			pointSentToPlayerSunkABoat();
			break;
		case TOUCHED:
			pointSentToPlayerTouchedABoat();
			break;
		default:
			break;
		}
	}

	private void pointSentToPlayerSunkABoat() {
		// TODO Auto-generated method stub

	}

	private PointBean determinateAPointToStrikeInSinkMode() {
		System.out.println("In sinking mode, core ai asked me to determinate a point.");
		PointBean pointToStrike = null;
		if (axeOfBoatHunted == null) {
			System.out.println("But i don't know yet the axe of the boat");
			try {
				pointToStrike = tryToDeterminateAxeOfBoatWithNotTriedDirectionInSinkModeList();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("And i know the axe of the boat");
			try {
				pointToStrike = continueToShootInTheAxeFound();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return pointToStrike;
	}

	private void pointSentToPlayerTouchedABoat() {
		System.out.println("The shoot touch a boat !");
		switch (mode) {
		case SEARCH:
			System.out.println("AI is now in sinking mode");
			initSinkMode();
			break;
		case SINK:
			System.out.println("AI is already in sinking mode");
			getFeedbackSuccessfullyTouchedAnotherPointInSinkMode();
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

	private void getFeedbackSuccessfullyTouchedAnotherPointInSinkMode() {
		System.out.println("In sinking mode, I get info from core ai that the last point sended touch successfully");
		if (axeOfBoatHunted == AxeBoat.UNDEFINED) {
			System.out.println("Now i will try to determinate the axe of the boat");
			try {
				determinateAxeWithTwoPoints(firstPointTouchedWhenInSinkMode, lastPointSentToPlayer);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Appel une méthode qui où diffère seulement l'axe (horizontal ou vertical).
	 * Une extrémité est choisie aléatoirement, et si un point déjà touché ou si les
	 * limites de la grille sont atteintes, l'autre côté est exploré jusqu'à
	 * renvoyer un point non essayé
	 *
	 * @return
	 * @throws NoAxeException
	 */
	private PointBean continueToShootInTheAxeFound() throws NoAxeException {
		if (axeOfBoatHunted == AxeBoat.HORIZONTAL) {
			System.out.println("Continue to shoot in horizontal axe");
			Direction direction = (getRandomInt(1, 2) == 1) ? Direction.LEFT : Direction.RIGHT;
			return choosePointInAxe(lastPointSentToPlayer, direction);

		} else if (axeOfBoatHunted == AxeBoat.VERTICAL) {
			System.out.println("Continue to shoot in vertical axe");
			Direction direction = (getRandomInt(1, 2) == 1) ? Direction.UP : Direction.DOWN;
			return choosePointInAxe(lastPointSentToPlayer, direction);
		} else {
			throw new NoAxeException();
		}
	}

	/**
	 * Recursive. Gros risque d'appel infini, à absolument contrôler.
	 *
	 * @param basePoint
	 * @param direction
	 * @return
	 */
	private PointBean choosePointInAxe(PointBean basePoint, Direction direction) {
		PointBean pointToStrike = getPointFromThisPointAndDirection(basePoint, direction);

		// Si le point choisi a déjà été touché et qu'une partie du bateau avait été
		// révélé
		if (isPointInMapAndWasABoat(pointToStrike)) {
			System.out.println(
					"Point found is another point already touched of the boat. Trying another point in the same axe and the same direction");
			System.out.println("Point is: ");
			System.out.println(pointToStrike.getPosDescription());
			return choosePointInAxe(pointToStrike, direction);

			// Si le point choisi a déjà été touché mais qu'aucune partie du bateau ne s'y
			// trouvait
		} else if (isPointInMapTriedPointAndIsPointTouched(pointToStrike)) {
			System.out.println("Point found was already shoot and no boat was here. Look into opposite direction");
			System.out.println("Point is: ");
			System.out.println(pointToStrike.getPosDescription());
			direction.getOppositeDirection();
			return choosePointInAxe(pointToStrike, direction);

			// Si le point choisi est en dehors de la grille
		} else if (!pointToStrike.isInThisGrid(GRID_SIZE)) {
			System.out.println("Point found is out of grid. Look into opposite direction");
			System.out.println("Point is: ");
			System.out.println(pointToStrike.getPosDescription());
			direction.getOppositeDirection();
			return choosePointInAxe(pointToStrike, direction);

			// Et enfin, si le point choisi est encore complétement inconnu
		} else {
			System.out.println("Point found was never shoot, so we can try it.");
			System.out.println("Point is: ");
			System.out.println(pointToStrike.getPosDescription());
			return pointToStrike;
		}
	}

	/**
	 * Recursive
	 *
	 * @return
	 */
	private PointBean tryToDeterminateAxeOfBoatWithNotTriedDirectionInSinkModeList()
			throws AllDirectionsTestedException {
		System.out.println("Try to determinate the axe of boat with DirectionTriedList");
		PointBean pointToStrike = null;

		if (notTriedDirectionInSinkModeList.size() <= 0) {
			throw new AllDirectionsTestedException();
		}

		int indexRandom = getRandomInt(0, notTriedDirectionInSinkModeList.size() - 1);
		Direction direction = notTriedDirectionInSinkModeList.get(indexRandom);
		pointToStrike = getPointFromThisPointAndDirection(firstPointTouchedWhenInSinkMode, direction);
		// Au fur et à mesure de l'appel de cette fonction la Liste diminue.
		notTriedDirectionInSinkModeList.remove(indexRandom);

		if (!isPointInMapTriedPointAndIsPointTouched(pointToStrike) && pointToStrike.isInThisGrid(GRID_SIZE)) {
			System.out.println("Valid point found !");
			return pointToStrike;
		} else {
			System.out.println("Not valid point found");
			return tryToDeterminateAxeOfBoatWithNotTriedDirectionInSinkModeList();
		}
	}

	private boolean isPointInMapTriedPointAndIsPointTouched(PointBean pointToSearch) {
		for (PointBean point : triedPointAndIsPointTouchedMap.keySet()) {
			if (point.haveSamePosition(pointToSearch)) {
				return true;
			}
		}
		return false;
	}

	private boolean isPointInMapAndWasABoat(PointBean pointToSearch) {
		for (Map.Entry<PointBean, Boolean> entry : triedPointAndIsPointTouchedMap.entrySet()) {
			if (entry.getKey().haveSamePosition(pointToSearch) && entry.getValue()) {
				System.out.println("Find value point with true in map. Point is :");
				System.out.println(entry.getKey().getPosDescription());
				return true;
			}
		}
		return false;
	}

	private PointBean getPointFromThisPointAndDirection(PointBean basePoint, Direction direction) {

		switch (direction) {
		case UP:
			return new PointBean(basePoint.getAxeX(), basePoint.getAxeY() - 1);

		case RIGHT:
			return new PointBean(basePoint.getAxeX() + 1, basePoint.getAxeY());

		case DOWN:
			return new PointBean(basePoint.getAxeX(), basePoint.getAxeY() + 1);

		case LEFT:
			return new PointBean(basePoint.getAxeX() - 1, basePoint.getAxeY());

		default:
			System.out.println(
					"In SinkingBoat, in method getPointFromThisPointAndDirection, switch reach the default case, that's a big problem");
			return null;
		}
	}

	private void determinateAxeWithTwoPoints(PointBean firstPoint, PointBean secondPoint)
			throws CantDeterminateAxeWithThisTwoPointsException {
		if (isAxeHorizontal(firstPoint, secondPoint)) {
			axeOfBoatHunted = AxeBoat.HORIZONTAL;
		} else if (isAxeVertical(firstPoint, secondPoint)) {
			axeOfBoatHunted = AxeBoat.VERTICAL;
		} else {
			throw new CantDeterminateAxeWithThisTwoPointsException(firstPoint, secondPoint);
		}
	}

	private boolean isAxeVertical(PointBean firstPoint, PointBean secondPoint) {
		return (firstPoint.getAxeX() == secondPoint.getAxeX());
	}

	private boolean isAxeHorizontal(PointBean firstPoint, PointBean secondPoint) {
		return (firstPoint.getAxeY() == secondPoint.getAxeY());
	}

	private int getRandomInt(int min, int max) {
		return (int) (((Math.random() * max) - min) + min);
	}

	@Deprecated
	private boolean isDirectionInDirectionList(Direction direction, List<Direction> list) {
		for (Direction directionInList : list) {
			if (direction == directionInList) {
				return true;
			}
		}
		return false;
	}

}
