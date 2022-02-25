package driver;

import models.KDTreeNode;
import operations.QuadTree;
import models.quad.QuadPoint;
import models.quad.QuadTreeNode;
import operations.KDTree;
import util.Generator;

import java.util.List;
import java.util.ArrayList;

public class Search {

	static int min = 0;
	static int max = 100000;
	static int numPointsGenerated = 1000;
	static int numTestCases = 100;

	public static void main(String[] args) {
		
		System.out.println("2D Plane coordinates: ("+min+","+min+") to ("+max+","+max+")");
		System.out.println("Number of Generated Points: "+ numPointsGenerated);
		Generator test = new Generator();
		
		List<int[]> listOfPoints = test.generateTestCases(min, max, numPointsGenerated, 0);
		List<int[]> listOfTestPoints = new ArrayList<>();
		//List<int[]> listOfTestPoints = test.generateTestCases(min/4, max/4, numTestCases, 0);
		int[] rootPoint = test.generateRandomPoint(min, max);
		listOfPoints.add(rootPoint);

		//Uncomment this for Generating testcases with 50% points which lie in the data structures and 50% random points
		/*for(int i = 0; i < numTestCases; i++) {
			int randomIndex = (int)Math.random() * numPointsGenerated;
			int[] randomAvailablePoint = listOfPoints.get(randomIndex);
			listOfTestPoints.add(randomAvailablePoint);
		}*/
		/*for(int i = 0; i < numTestCases; i++) {
			//int[] point = test.generateRandomPoint(min, max);
			int[] point = test.generateRandomPoint(min/100, max/100);
			listOfTestPoints.add(point);
		}*/
		for(int i = 0; i < numTestCases; i++) {
			//int[] point = test.generateRandomPoint(min, max);
			int[] point = test.generateRandomPoint(min, max);
			listOfTestPoints.add(point); 
		}

		//-----------------------KD Tree-------------------------------
		KDTreeNode root = new KDTreeNode(rootPoint);
		System.out.println("KD-Tree Creation Time taken: "+kDTreeConstructionTime(root, rootPoint, listOfPoints));

		//KD Tree search on test points
		System.out.println("KD-Tree Search Time taken: "+kDTreeSearch(root, listOfTestPoints));

		//KD Tree nearest neighbor
		System.out.println("KD-Tree nearest neighbor search time: "+kDTreeNearestNeighbor(root, listOfTestPoints));
		
		//------------------------------Quad Tree-------------------------------
		//Insertion in quad tree
		QuadTree center = new QuadTree(new QuadPoint(min, min), new QuadPoint(max, max));
		System.out.println("Quad-Tree Creation Time taken: "+quadTreeConstructionTime(center, rootPoint, listOfPoints));
		
		//Quad Tree search on test points
		System.out.println("Quad-Tree Search Time taken: "+quadTreeSearch(center, listOfPoints));
		
		//Quad tree nearest neighbour
		System.out.println("Quad-Tree nearest neighbor search time: "+quadTreeNearestNeighbor(center, listOfPoints));
	
	}

	public static long kDTreeConstructionTime(KDTreeNode root, int[] rootPoint, List<int[]> listOfPoints) {
		long startTimeKDIns = System.nanoTime();
		//Insertion in KD Tree
		for(int[] point : listOfPoints)
			root = KDTree.insert(root, point);
		long endTimeKDIns = System.nanoTime();
		return (endTimeKDIns - startTimeKDIns);
	}
	public static long kDTreeSearch(KDTreeNode root, List<int[]> listOfTestPoints) {
		long startTimeKDSearch = System.nanoTime();
		for(int[] point : listOfTestPoints)
			KDTree.search(root, point);
		long endTimeKDSearch = System.nanoTime();
		long durationKDSearch = (endTimeKDSearch - startTimeKDSearch)/listOfTestPoints.size();
		return durationKDSearch;
	}
	public static long kDTreeNearestNeighbor(KDTreeNode root,List<int[]> listOfTestPoints) {
		long startTimeKDNearest = System.nanoTime();
		for(int[] u: listOfTestPoints) {
			KDTreeNode closest = KDTree.nearestNeighbour(root,u);
		}
		long endTimeKDNearest = System.nanoTime();
		long durationKDNearest = (endTimeKDNearest - startTimeKDNearest)/listOfTestPoints.size();
		return durationKDNearest;
	}
	
	public static long quadTreeConstructionTime(QuadTree center, int rootPoint[], List<int[]> listOfPoints) {
		long startTimeQuadIns = System.nanoTime();
	    
		for(int[] point : listOfPoints) {
			QuadTreeNode quadPoint = new QuadTreeNode(new QuadPoint(point[0], point[1]), 1);
			center.insert(quadPoint);
		}
		long endTimeQuadIns = System.nanoTime();
		long durationQuadIns = (endTimeQuadIns - startTimeQuadIns);
		return durationQuadIns;
	}
	public static long quadTreeSearch(QuadTree center, List<int[]> listOfTestPoints) {
		long startTimeQuadSearch = System.nanoTime();
		for(int[] point : listOfTestPoints)
			center.search(new QuadPoint(point[0], point[1]));
		long endTimeQuadSearch = System.nanoTime();
		long durationQuadSearch = (endTimeQuadSearch - startTimeQuadSearch)/listOfTestPoints.size();
		return durationQuadSearch;
	}
	public static long quadTreeNearestNeighbor(QuadTree center, List<int[]> listOfTestPoints) {
		int[] testWindowBot = {min/100,min/100};
		int[] testWindowTop = {max/100,max/100};
		
		long startTimeQuadNN = System.nanoTime();
		for(int[] x: listOfTestPoints) {
			QuadTreeNode nearest = center.nearestNeighbor(new QuadPoint(x[0], x[1]), testWindowBot, testWindowTop);
		}
		long endTimeQuadNN = System.nanoTime();
		long durationQuadNN = (endTimeQuadNN - startTimeQuadNN)/listOfTestPoints.size();
		return durationQuadNN;
	}
}
