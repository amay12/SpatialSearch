package operations;

import models.quad.QuadPoint;
import models.quad.QuadTreeNode;

import java.util.Stack;

public class QuadTree {
    QuadPoint bottomLeft;
    QuadPoint topRight; 
    
    QuadTreeNode node;
  
    QuadTree topLeftTree;
    QuadTree topRightTree;
    QuadTree bottomLeftTree;
    QuadTree bottomRightTree;

	public QuadTree() {
        bottomLeft = new QuadPoint(0, 0); 
        topRight = new QuadPoint(0, 0); 
        node = null; 
        topLeftTree  = null; 
        topRightTree = null; 
        bottomLeftTree = null;
        bottomRightTree = null;
    }
    public QuadTree(QuadPoint topL, QuadPoint botR)
    { 
        node = null; 
        topLeftTree  = null; 
        topRightTree = null; 
        bottomLeftTree = null;
        bottomRightTree = null;
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
            	if (bottomLeftTree == null)
                    bottomLeftTree = new QuadTree(
                        new QuadPoint(bottomLeft.coordinates[0], bottomLeft.coordinates[1]), 
                        new QuadPoint((bottomLeft.coordinates[0] + topRight.coordinates[0]) / 2, 
                        		(bottomLeft.coordinates[1] + topRight.coordinates[1]) / 2)); 
                bottomLeftTree.insert(node);
                
                
            } 
      
            // Indicates toppLeftTree 
            else
            { 
            	if (topLeftTree == null) 
                    topLeftTree = new QuadTree( new QuadPoint(
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
            	if (bottomRightTree == null)
                    bottomRightTree = new QuadTree( new QuadPoint(
                    		(bottomLeft.coordinates[0] + topRight.coordinates[0]) / 2, 
                            bottomLeft.coordinates[1]
                		), 
        				new QuadPoint(
        					topRight.coordinates[0], 
        					(bottomLeft.coordinates[1] + topRight.coordinates[1]) / 2
        				)
        				);
                bottomRightTree.insert(node);
            } 
      
            // Indicates toppRightTree 
            else
            { 
            	
            	if (topRightTree == null) 
                    topRightTree =   new QuadTree( new QuadPoint(
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
    	Stack<QuadTree> stack = new Stack<>();
        stack.push(this);
        double minDist = Integer.MAX_VALUE;
        QuadTreeNode closestNode = this.node;
        while(!stack.isEmpty()) {
        	QuadTree poppedNode = stack.pop();
	    	if(poppedNode.topLeftTree != null && checkRectOverlap(poppedNode.topLeftTree, botLeftPoint, topRightPoint)) {
	    		stack.push(poppedNode.topLeftTree);
	    	}
	    	if(poppedNode.topRightTree != null && checkRectOverlap(poppedNode.topRightTree, botLeftPoint, topRightPoint)) {
	    		stack.push(poppedNode.topRightTree);
	    	}
	    	if(poppedNode.bottomLeftTree != null && checkRectOverlap(poppedNode.bottomLeftTree, botLeftPoint, topRightPoint)) {
	    		stack.push(poppedNode.bottomLeftTree);
	    	}
	    	if(poppedNode.bottomRightTree != null && checkRectOverlap(poppedNode.bottomRightTree, botLeftPoint, topRightPoint)) {
	    		stack.push(poppedNode.bottomRightTree);
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
    
    static boolean checkRectOverlap(QuadTree node, int[] l2, int[] r2) {
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
            	if (bottomLeftTree == null)
                    return null; 
                return bottomLeftTree.search(p);
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
            	if (bottomRightTree == null)
                    return null; 
                return bottomRightTree.search(p);
                
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
