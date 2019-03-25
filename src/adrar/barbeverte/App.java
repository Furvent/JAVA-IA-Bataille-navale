package adrar.barbeverte;

public class App {

	public static void main(String[] args) {
//		FleetFactory fleetFactory = new FleetFactory(10);
//		FleetBean fleet = fleetFactory.generateFleet();
//		fleet.getDescription();
//		System.out.println("Size: " + fleet.getSize());
//		fleet.getGraphicRepresentation();

		IArena arena = new IArena();
		arena.launchTest();
	}
}
