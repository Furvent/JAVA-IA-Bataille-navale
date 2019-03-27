package ai;

import adrar.barbeverte.PointBean;
import adrar.barbeverte.enums.AxeBoat;

public class AxeBoatHunted {
	// ===========================================================
	// Constants
	// ===========================================================
	private final AxeBoat axe;
	private final PointBean firstPoint;
	private final PointBean secondPoint;

	// ===========================================================
	// Fields
	// ===========================================================
	private boolean firstExtrimityIsEmpty;
	private boolean secondExtremityIsEmpty;

	// ===========================================================
	// Constructors
	// ===========================================================
	public AxeBoatHunted(AxeBoat axe, PointBean firstPoint, PointBean secondPoint) {
		this.axe = axe;
		hasTriedFirstExtremity = false;
		hasTriedSecondExtremity = false;
		this.firstPoint = firstPoint;
		this.secondPoint = secondPoint;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	public boolean isHasTriedFirstExtremity() {
		return hasTriedFirstExtremity;
	}

	public void setHasTriedFirstExtremity(boolean hasTriedFirstExtremity) {
		this.hasTriedFirstExtremity = hasTriedFirstExtremity;
	}

	public boolean isHasTriedSecondExtremity() {
		return hasTriedSecondExtremity;
	}

	public void setHasTriedSecondExtremity(boolean hasTriedSecondExtremity) {
		this.hasTriedSecondExtremity = hasTriedSecondExtremity;
	}

	public PointBean getFirstPoint() {
		return firstPoint;
	}

	public PointBean getSecondPoint() {
		return secondPoint;
	}

	public AxeBoat getAxe() {
		return axe;
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================
}
