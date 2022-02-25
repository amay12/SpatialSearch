package models.quad;

public class QuadPoint {

	public int[] coordinates;

	public QuadPoint() {
		coordinates = new int[2];
		coordinates[0] = 0;
		coordinates[1] = 0;
	}

	public QuadPoint(int x, int y) {
		coordinates = new int[2];
		coordinates[0] = x;
		coordinates[1] = y;
	}
}
