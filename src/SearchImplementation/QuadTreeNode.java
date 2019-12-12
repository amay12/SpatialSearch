/**
 * File: QuadTreeNode.java
 * Created By: amaykadre
 * Created On: 2019-11-25
 * Type: QuadTreeNode
 */
package SearchImplementation;

/**
 * @author amaykadre
 *
 */
public class QuadTreeNode {

	public QuadPoint dataPoint;
	public int data;

	public QuadTreeNode(QuadPoint dataPoint, int data) {
		this.dataPoint = dataPoint;
		this.data = data;
	}
	public QuadTreeNode() {
		this.data = 0;
	}

}
