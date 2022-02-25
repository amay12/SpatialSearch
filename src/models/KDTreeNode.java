package models;

public class KDTreeNode {

	public int[] dataPoint;
	public final static int DIMENSION = 2;
	
	public KDTreeNode left;
	public KDTreeNode right;

	public KDTreeNode(int[] point) {
		dataPoint = new int[DIMENSION];
		System.arraycopy(point, 0, dataPoint, 0, point.length);
		left = null;
		right = null;
	}
	
}
