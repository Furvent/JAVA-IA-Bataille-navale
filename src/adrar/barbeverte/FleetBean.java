package adrar.barbeverte;

import java.util.ArrayList;
import java.util.List;

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
		for (BoatBean boat : boatList) {
			if (boat.isAPointOfBoat(point)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void addBoat(BoatBean boat) {
		boatList.add(boat);
	}

	// ===========================================================
	// Methods
	// ===========================================================
	public void getDescription() {
		for (BoatBean boat : boatList) {
			System.out.println("Boat : ");
			System.out.println(boat.getListOfPointsDescription());
		}
	}

	public int getSize() {
		return boatList.size();
	}

	/*
	 * Use to debug with graphic
	 */
	public void getGraphicRepresentation() {
		List<PointBean> allBoatPointList = new ArrayList<>();

		// On met tous les points de bateaux à afficher dans une liste
		for (BoatBean boat : boatList) {
			for (PointBean point : boat.getPointMap().keySet()) {
				allBoatPointList.add(point);
			}
		}

		// On créé la représentation graphique de la grille
		for (int x = 1; x <= 10; x++) {
			String line = "";

			for (int y = 1; y <= 10; y++) {
				PointBean pointGrid = new PointBean(x, y);
				if (debugPointIsInList(allBoatPointList, pointGrid)) {
					line += " + ";
				} else {
					line += " - ";
				}
			}
			System.out.println(line);
		}
	}

	private boolean debugPointIsInList(List<PointBean> list, PointBean point) {
		for (PointBean pointBoat : list) {
			if (point.haveSamePosition(pointBoat)) {
				return true;
			}
		}
		return false;
	}
}
