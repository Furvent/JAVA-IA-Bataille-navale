package ai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adrar.barbeverte.PointBean;
import adrar.barbeverte.Util;
import adrar.barbeverte.enums.AxeBoat;
import adrar.barbeverte.enums.Direction;
import adrar.barbeverte.enums.ModeAI;
import adrar.barbeverte.enums.ShotFeedback;
import adrar.barbeverte.exceptions.AllDirectionsTestedException;
import adrar.barbeverte.exceptions.AlreadySendPointException;
import adrar.barbeverte.exceptions.CantDeterminateAxeWithThisTwoPointsException;
import adrar.barbeverte.exceptions.NoAxeException;
import adrar.barbeverte.exceptions.NoPointDeterminateToGiveItBackToPlayer;
import adrar.barbeverte.exceptions.bothPointAtExtrimityOfAxeHorizontalAreInvalidException;
import adrar.barbeverte.exceptions.bothPointAtExtrimityOfAxeVerticalAreInvalidException;

/**
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
	private List<PointBean> sinkModePointOfBoatHuntedList;
	private AxeBoat axeOfBoatHunted;

	/**
	 * Cr�� la liste de direction pas encore essay�e, et la liste de points o� le
	 * bateau chass� a �t� touch�. Donc le premier point est rajout�.
	 */
	private void initSinkMode() {
		mode = ModeAI.SINK;
		firstPointTouchedWhenInSinkMode = lastPointSentToPlayer;

		notTriedDirectionInSinkModeList = new ArrayList<>();
		notTriedDirectionInSinkModeList.add(Direction.UP);
		notTriedDirectionInSinkModeList.add(Direction.RIGHT);
		notTriedDirectionInSinkModeList.add(Direction.DOWN);
		notTriedDirectionInSinkModeList.add(Direction.LEFT);

		sinkModePointOfBoatHuntedList = new ArrayList<>();
		sinkModePointOfBoatHuntedList.add(lastPointSentToPlayer);
		axeOfBoatHunted = AxeBoat.UNDEFINED;
	}

	private void resetSinkModeDate() {
		firstPointTouchedWhenInSinkMode = null;

		if (notTriedDirectionInSinkModeList != null) {
			notTriedDirectionInSinkModeList.clear();
		}

		if (sinkModePointOfBoatHuntedList != null) {
			sinkModePointOfBoatHuntedList.clear();
		}
		axeOfBoatHunted = AxeBoat.UNDEFINED;
	}

	private Map<PointBean, Boolean> triedPointAndPointWasBoatMap;

	/**
	 * Classe pour l'instant, pas encore incorpor� dans Core
	 */

	private PointBean lastPointSentToPlayer;
	private ModeAI mode;

	// ===========================================================
	// Constructors
	// ===========================================================
	public Core(int gridSize) {
		triedPointAndPointWasBoatMap = new HashMap<>();
		GRID_SIZE = gridSize;
		mode = ModeAI.SEARCH;
		axeOfBoatHunted = AxeBoat.UNDEFINED;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================
	public PointBean dearIAGiveMeAPoint() throws NoPointDeterminateToGiveItBackToPlayer, AlreadySendPointException {
		System.out.println("In AI, Player asked me a point and i'm in " + mode.getModeDescription() + " MODE.");
		switch (mode) {
		case SEARCH:
			lastPointSentToPlayer = debugGiveRandomePoint(0);
			System.out.println("Point renvoy� au player : " + lastPointSentToPlayer.getPosDescription());
			break;
		case SINK:
			lastPointSentToPlayer = determinateAPointToStrikeInSinkMode();
			System.out.println("Point renvoy� au player : " + lastPointSentToPlayer.getPosDescription());
			break;
		}
		if (lastPointSentToPlayer == null) {
			throw new NoPointDeterminateToGiveItBackToPlayer();
		} else if (isPointInMapTriedPoint(lastPointSentToPlayer)) {
			throw new AlreadySendPointException();
		} else {
			return lastPointSentToPlayer;
		}
	}

	public void initAllData() {
		System.out.println("Init all data in AI");
		resetSinkModeDate();
		mode = ModeAI.SEARCH;
		triedPointAndPointWasBoatMap.clear();
		lastPointSentToPlayer = null;
	}

	/**
	 * A diviser en deux fonctions
	 *
	 * @param feedback
	 */
	public void getFeedBackAboutPointSentToPlayer(ShotFeedback feedback) {
		boolean isPointTouched = (feedback == ShotFeedback.MISSED) ? false : true;
		triedPointAndPointWasBoatMap.put(lastPointSentToPlayer, isPointTouched);

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

	private PointBean debugGiveRandomePoint(int numberOfTry) {
		if (numberOfTry > 50) {
			System.exit(0);
		}
		numberOfTry++;
		PointBean pointToSend = new PointBean(Util.getRandomInt(1, GRID_SIZE), Util.getRandomInt(1, GRID_SIZE));
		if (isPointInMapTriedPoint(pointToSend)) {
			// DEBUG
			System.out.println("Point choisi al�atoirement a d�j� �t� touch�, retour visuel pour debug: ");
			debugGetVisualGridOfPointTried();
			System.out.println("Un nouveau point al�atoire va �tre choisi.");
			return debugGiveRandomePoint(numberOfTry);

		} else {
			System.out.println("Point al�atoire choisi : " + pointToSend.getPosDescription());
			return pointToSend;
		}
	}

	private void pointSentToPlayerSunkABoat() {
		System.out.println("Boat was sink !");
		mode = ModeAI.SEARCH;
		resetSinkModeDate();
	}

	private PointBean determinateAPointToStrikeInSinkMode() {
		System.out.println("In sinking mode, core ai asked me to determinate a point.");
		PointBean pointToStrike = null;
		if (axeOfBoatHunted == AxeBoat.UNDEFINED) {
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
//		switch (mode) {
//		case SEARCH:
//			break;
//		case SINK:
//			break;
//		}

	}

	private void getFeedbackSuccessfullyTouchedAnotherPointInSinkMode() {
		// Instruction suivante tr�s importante, permet de continuer � chasser le bateau
		sinkModePointOfBoatHuntedList.add(lastPointSentToPlayer);
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
	 * Appel une m�thode o� diff�re seulement l'axe (horizontal ou vertical). Une
	 * extr�mit� est choisie al�atoirement, et si un point d�j� touch� ou si les
	 * limites de la grille sont atteintes, l'autre c�t� est explor� jusqu'�
	 * renvoyer un point non essay�
	 *
	 * @return
	 * @throws NoAxeException
	 */
	private PointBean continueToShootInTheAxeFound() throws NoAxeException {
		PointBean pointToSend = null;
		if (axeOfBoatHunted == AxeBoat.HORIZONTAL) {
			System.out.println("Continue to shoot in horizontal axe");
//			Direction direction = (Util.getRandomInt(1, 2) == 1) ? Direction.LEFT : Direction.RIGHT;
//			return choosePointInAxe(lastPointSentToPlayer, direction);
			try {
				pointToSend = shootInHorizontalAxe();
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(0);
			}

		} else if (axeOfBoatHunted == AxeBoat.VERTICAL) {
			System.out.println("Continue to shoot in vertical axe");
//			Direction direction = (Util.getRandomInt(1, 2) == 1) ? Direction.UP : Direction.DOWN;
//			return choosePointInAxe(lastPointSentToPlayer, direction);
			try {
				pointToSend = shootInVerticalAxe();
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(0);
			}
		} else {
			throw new NoAxeException();
		}
		return pointToSend;
	}

	private PointBean shootInVerticalAxe() throws bothPointAtExtrimityOfAxeVerticalAreInvalidException {
		// On regarde � quelle position X sont situ�s les points d�j� trouv�s et on
		// retourne le plus petit et le plus grand X.
		int smallerOrdinate = getSmallerOrdinateFromTouchedPointofHuntingBoat();
		int higherOrdinate = getHigherOrdinateFromTouchedPointOfHuntingBoat();

		if (thisPointIsAvailable(new PointBean(lastPointSentToPlayer.getAxeX(), smallerOrdinate - 1))) {
			return new PointBean(lastPointSentToPlayer.getAxeX(), smallerOrdinate - 1);
		} else if (thisPointIsAvailable(new PointBean(lastPointSentToPlayer.getAxeX(), higherOrdinate + 1))) {
			return new PointBean(lastPointSentToPlayer.getAxeX(), higherOrdinate + 1);
		} else {
			throw new bothPointAtExtrimityOfAxeVerticalAreInvalidException(smallerOrdinate, higherOrdinate);
		}
	}

	private int getHigherOrdinateFromTouchedPointOfHuntingBoat() {
		int higherOrdinate = 0;
		for (PointBean point : sinkModePointOfBoatHuntedList) {
			if (point.getAxeY() > higherOrdinate) {
				higherOrdinate = point.getAxeY();
			}
		}
		return higherOrdinate;
	}

	private int getSmallerOrdinateFromTouchedPointofHuntingBoat() {
		int smallerOrdinate = GRID_SIZE + 1;
		for (PointBean point : sinkModePointOfBoatHuntedList) {
			if (point.getAxeY() < smallerOrdinate) {
				smallerOrdinate = point.getAxeY();
			}
		}
		return smallerOrdinate;
	}

	private PointBean shootInHorizontalAxe() throws bothPointAtExtrimityOfAxeHorizontalAreInvalidException {
		// On regarde � quelle position X sont situ�s les points d�j� trouv�s et on
		// retourne le plus petit et le plus grand X.
		int smallerAbscissa = getSmallerAbscissaFromTouchedPointofHuntingBoat();
		int higherAbscissa = getHigherAbscissaFromTouchedPointOfHuntingBoat();

		if (thisPointIsAvailable(new PointBean(smallerAbscissa - 1, lastPointSentToPlayer.getAxeY()))) {
			return new PointBean(smallerAbscissa - 1, lastPointSentToPlayer.getAxeY());
		} else if (thisPointIsAvailable(new PointBean(higherAbscissa + 1, lastPointSentToPlayer.getAxeY()))) {
			return new PointBean(higherAbscissa + 1, lastPointSentToPlayer.getAxeY());
		} else {
			throw new bothPointAtExtrimityOfAxeHorizontalAreInvalidException(smallerAbscissa, higherAbscissa);
		}
	}

	private boolean thisPointIsAvailable(PointBean point) {
		return !isPointInMapTriedPoint(point) && point.isInThisGrid(GRID_SIZE);
	}

	private int getHigherAbscissaFromTouchedPointOfHuntingBoat() {
		int higherAbscissa = 0;
		for (PointBean point : sinkModePointOfBoatHuntedList) {
			if (point.getAxeX() > higherAbscissa) {
				higherAbscissa = point.getAxeX();
			}
		}
		return higherAbscissa;
	}

	private int getSmallerAbscissaFromTouchedPointofHuntingBoat() {
		int smallerAbscissa = GRID_SIZE + 1;
		for (PointBean point : sinkModePointOfBoatHuntedList) {
			if (point.getAxeX() < smallerAbscissa) {
				smallerAbscissa = point.getAxeX();
			}
		}
		return smallerAbscissa;
	}

	/**
	 * Recursive. Gros risque d'appel infini, � absolument contr�ler. Effectivement,
	 * en plein debug, c'est le cas. Solutions possibles : changer la logique, peut
	 * �tre cr�er une classe. Ou enlever la r�cursivit�. Voir avec la fonction qui
	 * appelle celle ci (continueToShootInAxeFound)
	 *
	 * @deprecated
	 *
	 * @param basePoint
	 * @param direction
	 * @return
	 */
	@Deprecated
	private PointBean choosePointInAxe(PointBean basePoint, Direction direction) {
		System.out.println("Je cherche un point dans cette direction : " + direction.getDirectionDescription());
		PointBean pointToStrike = getPointFromThisPointAndDirection(basePoint, direction);

		// Si le point choisi a d�j� �t� touch� et qu'une partie du bateau avait �t�
		// r�v�l�
		if (isPointInMapAndWasABoat(pointToStrike)) {
			System.out.println(
					"Point found is another point already touched of the boat. Trying another point in the same axe and the same direction");
			System.out.println("Point is: " + pointToStrike.getPosDescription());
			return choosePointInAxe(pointToStrike, direction);

			// Si le point choisi a d�j� �t� touch� mais qu'aucune partie du bateau ne s'y
			// trouvait
		} else if (isPointInMapTriedPoint(pointToStrike)) {
			System.out.println("Point found was already shoot and no boat was here. Look into opposite direction");
			System.out.println("Point is: " + pointToStrike.getPosDescription());
			Direction newDirection = Direction.getOppositeDirection(direction);
			return choosePointInAxe(pointToStrike, newDirection);

			// Si le point choisi est en dehors de la grille
		} else if (!pointToStrike.isInThisGrid(GRID_SIZE)) {
			System.out.println("Point found is out of grid. Look into opposite direction");
			System.out.println("Point is: " + pointToStrike.getPosDescription());
			Direction newDirection = Direction.getOppositeDirection(direction);
			return choosePointInAxe(pointToStrike, newDirection);

			// Et enfin, si le point choisi est encore compl�tement inconnu
		} else {
			System.out.println("Point found was never shoot, so we can try it.");
			System.out.println("Point is: " + pointToStrike.getPosDescription());
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

		int indexRandom = Util.getRandomInt(0, notTriedDirectionInSinkModeList.size() - 1);
		Direction direction = notTriedDirectionInSinkModeList.get(indexRandom);
		pointToStrike = getPointFromThisPointAndDirection(firstPointTouchedWhenInSinkMode, direction);
		// Au fur et � mesure de l'appel de cette fonction la Liste diminue.
		notTriedDirectionInSinkModeList.remove(indexRandom);

		if (!isPointInMapTriedPoint(pointToStrike) && pointToStrike.isInThisGrid(GRID_SIZE)) {
			System.out.println("Valid point found !");
			return pointToStrike;
		} else {
			System.out.println("Not valid point found");
			return tryToDeterminateAxeOfBoatWithNotTriedDirectionInSinkModeList();
		}
	}

	private boolean isPointInMapTriedPoint(PointBean pointToSearch) {
		for (PointBean point : triedPointAndPointWasBoatMap.keySet()) {
			if (point.haveSamePosition(pointToSearch)) {
				return true;
			}
		}
		return false;
	}

	private boolean isPointInMapAndWasABoat(PointBean pointToSearch) {
		for (Map.Entry<PointBean, Boolean> entry : triedPointAndPointWasBoatMap.entrySet()) {
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
		System.out.println("Axe found is: " + axeOfBoatHunted.getAxeDescription());
	}

	private boolean isAxeVertical(PointBean firstPoint, PointBean secondPoint) {
		return (firstPoint.getAxeX() == secondPoint.getAxeX());
	}

	private boolean isAxeHorizontal(PointBean firstPoint, PointBean secondPoint) {
		return (firstPoint.getAxeY() == secondPoint.getAxeY());
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

	private void debugGetVisualGridOfPointTried() {

		// On cr�� la repr�sentation graphique de la grille
		for (int x = 1; x <= GRID_SIZE; x++) {
			String line = "";

			for (int y = 1; y <= GRID_SIZE; y++) {
				PointBean point = new PointBean(x, y);
				if (isPointInMapTriedPoint(point)) {
					line += " + ";
				} else {
					line += " - ";
				}
			}
			System.out.println(line);
		}
		System.out.println("Fin du debug visuel");
	}

}
