/**
 * File: KDTree.java
 * Created By: amaykadre
 * Created On: 2019-11-24
 * Type: KDTree
 */
package models;

public class KDTreeNode{

	public int[] dataPoint;
	public final static int DIMENSION = 2;
	
	public KDTreeNode left;
	public KDTreeNode right;

	public KDTreeNode(int[] point) {
		dataPoint = new int[DIMENSION];
		for(int i = 0; i< point.length; i++) {
			dataPoint[i] = point[i]; 
		}
		left = null;
		right = null;
		
	}
	
}
