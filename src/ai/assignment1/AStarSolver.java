package ai.assignment1;
import java.awt.Point;
import java.lang.Math;
import java.util.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class AStarSolver implements Solver{
	
	private static int[][] grid;
	private static boolean success = false;
	
	private static boolean isGoalState() {
		int counter = 0;
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				if(grid[i][j] == counter) {
					counter++;
				}
				else {
					return false;
				}
			}
		}
		return true;
	}
	
	 private static int getManhattanDistance(int[][] board) {
		int distance = 0;
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				if(board[i][j] != 0) {
					distance += Math.abs(i - board[i][j] / 3) + Math.abs(j - board[i][j] % 3);
				}
			}
		}
		return distance;
	}
	 
	 private static Point getBlankLocation() {
		 for(int i = 0; i < 3; i++) {
			 for(int j = 0; j < 3; j++) {
				 if(grid[i][j] == 0) {
					 return new Point(i, j);
				 }
			 }
		 }
		return new Point(0, 0);
	 }
	 
	 private static boolean checkValidMove(int x, int y) {
		 if(x >=0 && x <3 && y >=0 && y <3) {
			 return true;
		 }
		 return false;
	 }
	 
	 private static void printGrid() {
		 for(int i = 0; i < 3; i++) {
				for(int j = 0; j < 3; j++) {
					System.out.print(grid[i][j]);
				}
				System.out.println();
			}
		 System.out.println();
	 }
	 private static void addToExplored(Set<Integer> set) {
			ArrayList<Integer> state = new ArrayList<Integer>();
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					state.add(grid[i][j]);
				}
			}
			int encodedState = 0;
			for (Integer elem : state) {
				encodedState = (encodedState * 10) + (elem + 1);
			}
			set.add(encodedState);
		}
		
		private static boolean wasExplored(Set<Integer> set, int[][] board) {
			ArrayList<Integer> state = new ArrayList<Integer>();
			int[][] newGrid = board;

			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					state.add(newGrid[i][j]);
				}
			}
			int encodedState = 0;
			for (Integer elem : state) {
				encodedState = (encodedState * 10) + (elem + 1);
			}
			return set.contains(encodedState);
		}
		
		private static int[][] getNewGrid(Point oldBlank, Point newBlank){
			int[][] newGrid = new int[3][3];
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					newGrid[i][j] = grid[i][j];
				}
			}
			newGrid[oldBlank.x][oldBlank.y] = grid[newBlank.x][newBlank.y];
			newGrid[newBlank.x][newBlank.y] = 0;
			return newGrid;
		}
	
	@Override
	public void solve(int[][] board) {
		int[] dr = {0, 0, 1, -1};
		int[] dc = {1, -1, 0, 0};
		HashSet<Integer> explored = new HashSet<Integer>();
		PriorityQueue<qEntry> pQueue = new PriorityQueue<>();
		
		grid = board;
		pQueue.add(new qEntry(grid, getManhattanDistance(grid)));
		
		
		while(!pQueue.isEmpty()) {
			grid = pQueue.poll().board;
			printGrid();
			addToExplored(explored);
			
			if(isGoalState()) {
				System.out.println("Success");
				success = true;
				break;
			}
			Point blankLocation = getBlankLocation();
			for(int i = 0; i < 4 ; i++) {
				Point newBlank = new Point();
				newBlank.x = blankLocation.x + dr[i];
				newBlank.y = blankLocation.y + dc[i];
				if(checkValidMove(newBlank.x, newBlank.y)) {
					int[][] newGrid = getNewGrid(blankLocation, newBlank);
					if(!wasExplored(explored, newGrid)) {
						pQueue.add(new qEntry(newGrid, getManhattanDistance(newGrid)));
					}
				}
			}
			
		}
		if(!success) {
			System.out.println("Failed to get a solution");
		}
		
	}
	
	
}

class qEntry implements Comparable<qEntry> {

	int[][] board;
	int distance;
	
	qEntry(int[][] board, int distance){
		this.board = board;
		this.distance = distance;
	}
	
	@Override
	public int compareTo(qEntry other) {
		if(distance < other.distance) {
			return -1;
		}
		else if(distance == other.distance) {
			return 0;
		}
		return 1;
	}
	
}



