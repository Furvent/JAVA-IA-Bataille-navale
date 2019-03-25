package adrar.barbeverte;

import factory.FleetFactory;

public class App {

	public static void main(String[] args) {
		FleetFactory fleetFactory = new FleetFactory(10);
		FleetBean fleet = fleetFactory.generateFleet();
		fleet.getDescription();
		System.out.println("Size: " + fleet.getSize());
		fleet.getGraphicRepresentation();
	}
}
