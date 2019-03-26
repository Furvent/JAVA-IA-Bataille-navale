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
}
