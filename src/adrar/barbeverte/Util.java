package adrar.barbeverte;

public abstract class Util {
	// ===========================================================
	// Methods
	// ===========================================================
	public static int getRandomInt(int min, int max) {
		return (int) (Math.random() * ((max - min) + 1)) + min;
	}
}
