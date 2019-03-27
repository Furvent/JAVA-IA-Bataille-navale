package ai;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import adrar.barbeverte.PointBean;
import adrar.barbeverte.enums.AxeBoat;
import adrar.barbeverte.enums.Direction;
import adrar.barbeverte.enums.ModeAI;
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

public class Core {
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
			lastPointSentToPlayer = determinateAPointToStrikeInSinkMode();
			break;
		}
		if (lastPointSentToPlayer == null) {
			throw new NoPointDeterminateToGiveItBackToPlayer();
		} else {
			return lastPointSentToPlayer;
		}
	}

	public void getFeedBackAboutPointSentToPlayer(boolean pointWasTouched) {
		triedPointAndIsPointTouchedMap.put(lastPointSentToPlayer, pointWasTouched);
		if (pointWasTouched) {
			pointSentToPlayerTouchedABoat();
		} else {
			pointSentToPlayerDidntTouchABoat();
		}
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
			pointToStrike = continueToShootInTheAxeFound();
		}
		// TODO : REMOVE return
		return null;
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
	 * Appel deux méthodes différentes selon l'axe mais qui ont le même
	 * fonctionnement. Une extrémité est choisie aléatoirement, et si un point déjà
	 * touché ou si les limites de la grille sont atteintes, l'autre côté est
	 * exploré jusqu'à renvoyer un point non essayé
	 *
	 * @return
	 * @throws NoAxeException
	 */
	private PointBean continueToShootInTheAxeFound() throws NoAxeException {
		if (axeOfBoatHunted == AxeBoat.HORIZONTAL) {
			System.out.println("Continue to shoot in horizontal axe");
			Direction direction = (getRandomInt(1, 2) == 1) ? Direction.LEFT : Direction.RIGHT;
			return choosePointInHorizontalAxe(lastPointSentToPlayer, direction);
		} else if (axeOfBoatHunted == AxeBoat.VERTICAL) {
			System.out.println("Continue to shoot in vertical axe");
			Direction direction = (getRandomInt(1, 2) == 1) ? Direction.UP : Direction.DOWN;
			return choosePointInVerticalAxe();
		} else {
			throw new NoAxeException();
		}
		// TODO remove return null
		return null;
	}

	private PointBean choosePointInHorizontalAxe(PointBean basePoint, Direction direction) {
		PointBean pointToStrike = getPointFromThisPointAndDirection(basePoint, direction);
		if (pointToStrike.isInThisGrid(GRID_SIZE) && isPointInMapTriedPointAndIsPointTouched(pointToStrike)) {
			System.out.println("Point is in grid and not shoot yet");
			 if (pointToStrike)

		} else {
			System.out.println("Point searched was out of grid or already tried, look into opposite direction");
			direction.getOppositeDirection();
			choosePointInHorizontalAxe(pointToStrike, direction);
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
		notTriedDirectionInSinkModeList.remove(indexRandom);// Au fur et à mesure de l'appel de cette fonction la List
															// diminue.
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

	private boolean isPointInMapWasABoat(PointBean pointToSearch) {
		for (PointBean point : triedPointAndIsPointTouchedMap.keySet()) {
			if (point.haveSamePosition(pointToSearch) && ) {
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
		return (int) ((Math.random() * max - min) + min);
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
