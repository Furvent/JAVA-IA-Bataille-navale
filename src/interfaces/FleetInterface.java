package interfaces;
import adrar.barbeverte.BoatBean;
import adrar.barbeverte.PointBean;

public interface FleetInterface {
	public boolean isThereABoatAtThisPoint(PointBean point);

	public void addBoat(BoatBean boat);
}
