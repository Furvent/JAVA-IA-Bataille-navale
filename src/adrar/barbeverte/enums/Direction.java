package adrar.barbeverte.enums;

public enum Direction {
	UP(0), RIGHT(1), DOWN(2), LEFT(3);

	private int value;

	public int getValue() {
		return value;
	}

	Direction(int value) {
		this.value = value;
	}
}
