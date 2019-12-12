/**
 * File: Quad.java
 * Created By: amaykadre
 * Created On: 2019-11-25
 * Type: Quad
 */
package SearchImplementation;

import java.util.Stack;

/**
 * @author amaykadre
 *
 */
public class Quad {
    QuadPoint bottomLeft; 
    QuadPoint topRight; 
    
    QuadTreeNode node; 
  
    Quad topLeftTree; 
    Quad topRightTree; 
    Quad botLeftTree; 
    Quad botRightTree; 

	public Quad() {
        bottomLeft = new QuadPoint(0, 0); 
        topRight = new QuadPoint(0, 0); 
        node = null; 
        topLeftTree  = null; 
        topRightTree = null; 
        botLeftTree  = null; 
        botRightTree = null; 
    }
    public Quad(QuadPoint topL, QuadPoint botR) 
    { 
        node = null; 
        topLeftTree  = null; 
        topRightTree = null; 
        botLeftTree  = null; 
        botRightTree = null; 
        bottomLeft = topL; 
        topRight = botR; 
    } 
    
    boolean withinWindow(QuadPoint p) 
    { 
        return (p.coordinates[0] >= bottomLeft.coordinates[0] && 
            p.coordinates[0] <= topRight.coordinates[0] && 
            p.coordinates[1] >= bottomLeft.coordinates[1] && 
            p.coordinates[1] <= topRight.coordinates[1]); 
    }
    
    public void insert(QuadTreeNode node) 
    { 
        if (node == null) 
            return; 
      
        // DataPoint out of window
        if (!withinWindow(node.dataPoint)) 
            return; 
      
        // Quad of unit area. Can not divide further.
        if (Math.abs(bottomLeft.coordinates[0] - topRight.coordinates[0]) <= 1 && 
            Math.abs(bottomLeft.coordinates[1] - topRight.coordinates[1]) <= 1) 
        { 
            if(this.node == null) 
                this.node = node; 
            return; 
        } 
      
        if ((bottomLeft.coordinates[0] + topRight.coordinates[0]) / 2 >= node.dataPoint.coordinates[0]) 
        { 
            // Indicates bottomLeftTree 
            if ((bottomLeft.coordinates[1] + topRight.coordinates[1]) / 2 >= node.dataPoint.coordinates[1]) 
            { 
            	if (botLeftTree == null) 
                    botLeftTree = new Quad( 
                        new QuadPoint(bottomLeft.coordinates[0], bottomLeft.coordinates[1]), 
                        new QuadPoint((bottomLeft.coordinates[0] + topRight.coordinates[0]) / 2, 
                        		(bottomLeft.coordinates[1] + topRight.coordinates[1]) / 2)); 
                botLeftTree.insert(node); 
                
                
            } 
      
            // Indicates toppLeftTree 
            else
            { 
            	if (topLeftTree == null) 
                    topLeftTree = new Quad( new QuadPoint(
	                    						bottomLeft.coordinates[0],
	                    						(bottomLeft.coordinates[1] + topRight.coordinates[1]) / 2
                    						), 
                    						new QuadPoint(
                    							(bottomLeft.coordinates[0] + topRight.coordinates[0]) / 2, 
                    							topRight.coordinates[1]
                    						)
                    						); 
                topLeftTree.insert(node); 
            } 
        } 
        else
        { 
            // Indicates bottomRightTree 
            if ((bottomLeft.coordinates[1] + topRight.coordinates[1]) / 2 >= node.dataPoint.coordinates[1]) 
            { 
            	if (botRightTree == null) 
                    botRightTree = new Quad( new QuadPoint(
                    		(bottomLeft.coordinates[0] + topRight.coordinates[0]) / 2, 
                            bottomLeft.coordinates[1]
                		), 
        				new QuadPoint(
        					topRight.coordinates[0], 
        					(bottomLeft.coordinates[1] + topRight.coordinates[1]) / 2
        				)
        				);
                botRightTree.insert(node); 
            } 
      
            // Indicates toppRightTree 
            else
            { 
            	
            	if (topRightTree == null) 
                    topRightTree =   new Quad( new QuadPoint(
                    		(bottomLeft.coordinates[0] + topRight.coordinates[0]) / 2, 
                    		(bottomLeft.coordinates[1] + topRight.coordinates[1]) / 2
                		), 
            			new QuadPoint(
                    		topRight.coordinates[0], 
                    		topRight.coordinates[1]
                		)
            			); 
                topRightTree.insert(node); 
                
            } 
        } 
    }
    
    
    public QuadTreeNode nearestNeighbor(QuadPoint p, int[] botLeftPoint, int[] topRightPoint) {
    	Stack<Quad> stack = new Stack<>();
        stack.push(this);
        double minDist = Integer.MAX_VALUE;
        QuadTreeNode closestNode = this.node;
        while(!stack.isEmpty()) {
        	Quad poppedNode = stack.pop();
	    	if(poppedNode.topLeftTree != null && checkRectOverlap(poppedNode.topLeftTree, botLeftPoint, topRightPoint)) {
	    		stack.push(poppedNode.topLeftTree);
	    	}
	    	if(poppedNode.topRightTree != null && checkRectOverlap(poppedNode.topRightTree, botLeftPoint, topRightPoint)) {
	    		stack.push(poppedNode.topRightTree);
	    	}
	    	if(poppedNode.botLeftTree != null && checkRectOverlap(poppedNode.botLeftTree, botLeftPoint, topRightPoint)) {
	    		stack.push(poppedNode.botLeftTree);
	    	}
	    	if(poppedNode.botRightTree != null && checkRectOverlap(poppedNode.botRightTree, botLeftPoint, topRightPoint)) {
	    		stack.push(poppedNode.botRightTree);
	    	}
	    	if(poppedNode != null && poppedNode.node != null) {
	    		double dist = euclidianDist(poppedNode.node.dataPoint, p);
		    	if(dist < minDist) {
		    		closestNode = poppedNode.node;
		    		minDist = dist;
		    	}
	    	}
        }
    	return closestNode;
    }
    
    static boolean checkRectOverlap(Quad node, int[] l2, int[] r2) { 
    	int[] l1 = node.bottomLeft.coordinates; 
    	int[] r1 = node.topRight.coordinates;
        
        if (r1[1] < l2[1] || l1[1] > r2[1]) {
        	return false;
        }
        if (r1[0] < l2[0] || l1[0] > r2[0]) {
        	return false;
        }
  
        return true; 
    } 
    
    static double euclidianDist(QuadPoint a, QuadPoint b) {
    	int[] p1 = a.coordinates;
    	int[] p2 = b.coordinates;
    	return (p1[1] - p2[1])*(p1[1] - p2[1]) + (p1[0] - p2[0]) * (p1[0] - p2[0]);
    }

    
    public QuadTreeNode search(QuadPoint p) 
    { 
        // DataPoint out of window
        if (!withinWindow(p)) 
            return null;
      
        // Quad of unit length. Can not divide further.
        if (this.node != null) 
            return this.node; 
      
        if ((bottomLeft.coordinates[0] + topRight.coordinates[0]) / 2 >= p.coordinates[0]) 
        { 
            // Indicates bottomLeftTree 
            if ((bottomLeft.coordinates[1] + topRight.coordinates[1]) / 2 >= p.coordinates[1]) 
            { 
            	if (botLeftTree == null) 
                    return null; 
                return botLeftTree.search(p);
            } 
      
            // Indicates toppLeftTree 
            else
            { 
            	if (topLeftTree == null) 
                    return null; 
                return topLeftTree.search(p);
                 
            } 
        } 
        else
        { 
            // Indicates bottomRightTree 
            if ((bottomLeft.coordinates[1] + topRight.coordinates[1]) / 2 >= p.coordinates[1]) 
            { 
            	if (botRightTree == null) 
                    return null; 
                return botRightTree.search(p); 
                
            } 
      
            // Indicates toppRightTree 
            else
            { 
            	if (topRightTree == null) 
                    return null; 
                return topRightTree.search(p);
            } 
        } 
    }
}
