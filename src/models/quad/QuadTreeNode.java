package models.quad;

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
