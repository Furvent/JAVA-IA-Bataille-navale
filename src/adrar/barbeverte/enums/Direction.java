package adrar.barbeverte.enums;

public enum Direction {
	UP(0), RIGHT(1), DOWN(2), LEFT(3), UNDEFINED(4);

	private int value;

	Direction(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void nexDirection() {
		switch (value) {
		case 0:
			value = 1;
			break;
		case 1:
			value = 2;
			break;
		case 2:
			value = 3;
			break;
		case 3:
			value = 0;
			break;
		}
	}

	public static Direction getOppositeDirection(Direction direction) {
		switch (direction) {
		case UP:
			return Direction.DOWN;
		case RIGHT:
			return Direction.LEFT;
		case DOWN:
			return Direction.UP;
		case LEFT:
			return Direction.RIGHT;
		default:
			return Direction.UNDEFINED;
		}
	}

	public String getDirectionDescription() {
		switch (value) {
		case 0:
			return "UP";
		case 1:
			return "RIGHT";
		case 2:
			return "DOWN";
		case 3:
			return "LEFT";
		default:
			return "ERROR, NO DIRECTION";
		}
	}

}
