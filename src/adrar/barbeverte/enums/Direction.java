package adrar.barbeverte.enums;

public enum Direction {
	UP(0), RIGHT(1), DOWN(2), LEFT(3);

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
		default:
			value = 0;
			break;
		}
	}

	public void getOppositeDirection() {
		switch (value) {
		case 0:
			value = 2;
			break;
		case 1:
			value = 3;
			break;
		case 2:
			value = 0;
			break;
		case 3:
			value = 1;
			break;

		default:
			break;
		}
	}

}
