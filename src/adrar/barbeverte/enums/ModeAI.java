package adrar.barbeverte.enums;

public enum ModeAI {
	SEARCH(0), SINK(1);

	private int value;

	ModeAI(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public String getModeDescription() {
		switch (value) {
		case 0:
			return "SEARCH";
		case 1:
			return "SINK";
		default:
			return "ERROR, NO MODE";
		}
	}
}
