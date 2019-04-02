package adrar.barbeverte;

public class App {

	public static void main(String[] args) {
//		FleetFactory fleetFactory = new FleetFactory(10);
//		FleetBean fleet = fleetFactory.generateFleet();
//		fleet.getDescription();
//		System.out.println("Size: " + fleet.getSize());
//		fleet.getGraphicRepresentation();

		IArena arena = new IArena();
		// arena.launchTest();
		arena.debugLaunchTest();
//		for (int i = 0; i < 100; i++) {
//			System.out.println(Util.getRandomInt(0, 3));
//		}

//		PointBean pointBean = new PointBean(1, 1);
//		PointBean secondPointBean = Core.getPointFromThisPointAndDirection(pointBean, Direction.DOWN);
//		System.out.println("First point : " + pointBean.getPosDescription());
//		System.out.println("First point : " + secondPointBean.getPosDescription());
	}
}
