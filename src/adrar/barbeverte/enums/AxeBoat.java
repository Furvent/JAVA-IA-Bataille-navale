package adrar.barbeverte.enums;

public enum AxeBoat {
	HORIZONTAL(0), VERTICAL(1), UNDEFINED(2);

	private int value;

	AxeBoat(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
