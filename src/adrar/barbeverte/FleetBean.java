package adrar.barbeverte;

import java.util.ArrayList;

import interfaces.FleetInterface;

public final class FleetBean implements FleetInterface {

	// ===========================================================
	// Fields
	// ===========================================================
	private ArrayList<BoatBean> boatList;

	// ===========================================================
	// Constructors
	// ===========================================================
	public FleetBean(ArrayList<BoatBean> boatList) {
		this.boatList = boatList;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public ArrayList<BoatBean> getBoatList() {
		return boatList;
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	@Override
	public boolean isThereABoatAtThisPoint(PointBean point) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addBoat(BoatBean boat) {
		// TODO Auto-generated method stub

	}

	// ===========================================================
	// Methods
	// ===========================================================
}
