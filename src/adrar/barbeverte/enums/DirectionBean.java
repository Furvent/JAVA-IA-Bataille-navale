package adrar.barbeverte.enums;

public enum DirectionBean {
	UP(0), RIGHT(1), DOWN(2), LEFT(3);

	private int value;

	public int getValue() {
		return value;
	}

	DirectionBean(int value) {
		this.value = value;
	}
}
