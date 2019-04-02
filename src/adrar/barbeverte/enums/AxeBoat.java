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

	public String getAxeDescription() {
		switch (value) {
		case 0:
			return "HORIZONTAL";
		case 1:
			return "VERTICAL";
		case 2:
			return "UNDEFINED";
		default:
			return "ERROR, NO AXE";
		}
	}
}
