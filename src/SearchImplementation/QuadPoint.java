/**
 * File: QuadPoint.java
 * Created By: amaykadre
 * Created On: 2019-11-25
 * Type: QuadPoint
 */
package SearchImplementation;

/**
 * @author amaykadre
 *
 */
public class QuadPoint {

	public int[] coordinates;
	
	public QuadPoint(int x, int y) {
		coordinates = new int[2];
		coordinates[0] = x;
		coordinates[1] = y;
	}
	
	public QuadPoint() {
		coordinates = new int[2];
		coordinates[0] = 0;
		coordinates[1] = 0;
	}
}
