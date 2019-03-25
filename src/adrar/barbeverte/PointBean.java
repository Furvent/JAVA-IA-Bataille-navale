package adrar.barbeverte;

public final class PointBean {

	private int axeX;
	private int axeY;

	// ---------------------
	// ---GETTER SETTER-----
	// ---------------------

	public PointBean(int axeX, int axeY) {
		super();
		this.axeX = axeX;
		this.axeY = axeY;
	}

	public int getAxeX() {
		return axeX;
	}

	public void setAxeX(int axeX) {
		this.axeX = axeX;
	}

	public int getAxeY() {
		return axeY;
	}

	public void setAxeY(int axeY) {
		this.axeY = axeY;
	}

	public boolean haveSamePosition(PointBean point) {
		return point.getAxeX() == axeX && point.getAxeY() == axeY;
	}

	public String getPosDescription() {
		return "x:" + axeX + "/y:" + axeY;
	}

}
