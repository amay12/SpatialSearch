/**
 * File: KDTreeOperations.java
 * Created By: amaykadre
 * Created On: 2019-11-25
 * Type: KDTreeOperations
 */
package SearchOperations;

import SearchImplementation.KDTreeNode;

/**
 * @author amaykadre
 *
 */
public class KDTreeOperations {

	
	/**
	 * Initial Depth: 0
	 */
	 private static KDTreeNode insertNode(KDTreeNode root, int[] dataPoint, int depth) {
		if(root == null)
			return new KDTreeNode(dataPoint);
		
		int currentDimension = depth % KDTreeNode.DIMENSION;
		
		 if (dataPoint[currentDimension] < (root.dataPoint[currentDimension])) 
		        root.left  = insertNode(root.left, dataPoint, depth + 1); 
		    else
		        root.right = insertNode(root.right, dataPoint, depth + 1); 
		return root;
	}
	
	public static KDTreeNode insert(KDTreeNode root, int[] dataPoint) 
	{ 
	    return insertNode(root, dataPoint, 0); 
	}
	
	public static boolean arePointsEqual(int point1[], int point2[]) 
	{ 

	    for (int i = 0; i < KDTreeNode.DIMENSION; ++i) 
	        if (point1[i] != point2[i]) 
	            return false; 
	  
	    return true; 
	}
	
	public static boolean search(KDTreeNode root, int[] dataPoint) 
	{ 
	    return searchNode(root, dataPoint, 0); 
	}
	
	public static boolean searchNode(KDTreeNode root, int[] dataPoint, int depth) {
		
		if (root == null) 
	        return false;
	    if (arePointsEqual(root.dataPoint, dataPoint))
	        return true;
	    
	    int currentDimension = depth % KDTreeNode.DIMENSION;
	    
	    if(dataPoint[currentDimension] < root.dataPoint[currentDimension])
	    	return searchNode(root.left, dataPoint, depth+1);
	    else
	    	return searchNode(root.right, dataPoint, depth+1);

	}
	public static KDTreeNode nearestNeighbour(KDTreeNode root, int[] dataPoint) 
    { 
        return searchNearestNeighbour(root, dataPoint, Integer.MAX_VALUE, root);
    }
	public static KDTreeNode searchNearestNeighbour(KDTreeNode root, int[] dataPoint, double minDist, KDTreeNode bestNode) {
        if(root == null)
            return bestNode;

        double distanceFromNode = euclidianDistance(root.dataPoint, dataPoint);
        if(euclidianDistance(root.dataPoint, dataPoint) < minDist) {
            minDist = distanceFromNode;
            bestNode = root;
        }
        if(root.left == null)
            return searchNearestNeighbour(root.right, dataPoint, minDist, bestNode);
        if(root.right == null)
            return searchNearestNeighbour(root.left, dataPoint, minDist, bestNode);

        if (euclidianDistance(root.left.dataPoint, dataPoint) < euclidianDistance(root.right.dataPoint, dataPoint))
            bestNode = searchNearestNeighbour(root.left, dataPoint, minDist, bestNode);
        else
            bestNode = searchNearestNeighbour(root.right, dataPoint, minDist, bestNode);
        return bestNode;
    }

    public static double euclidianDistance(int[] a, int[] b) {
        if(a == null || b == null)
            return Integer.MAX_VALUE;
        return Math.sqrt((b[1] - a[1]) * (b[1] - a[1]) + (b[0] - a[0]) * (b[0] - a[0]));
    }


}
