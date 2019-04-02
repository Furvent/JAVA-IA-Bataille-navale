package adrar.barbeverte.enums;

public enum ShotFeedback {
	MISSED(0), TOUCHED(1), SUNK(2);

	private int value;

	ShotFeedback(int value) {
		this.value = value;
	}

	public String getShotfeedbackDescription() {
		switch (value) {
		case 0:
			return "MISSED";
		case 1:
			return "TOUCHED";
		case 2:
			return "SUNK";
		default:
			return "ERROR, NO FEEDBACK";
		}
	}
}
