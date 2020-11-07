package ai.assignment1;
import java.awt.Point;
import java.lang.Math;
import java.util.*;

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
	 
	 private static int getEuclideanDistance(int[][] board) {
			int distance = 0;
			for(int i = 0; i < 3; i++) {
				for(int j = 0; j < 3; j++) {
					if(board[i][j] != 0) {
						distance += Math.sqrt(Math.pow(Math.abs(i - board[i][j] / 3), 2) + Math.pow(Math.abs(j - board[i][j] % 3), 2));
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
	 
	 private static void printGrid(int[][] board) {
		 for(int i = 0; i < 3; i++) {
				for(int j = 0; j < 3; j++) {
					System.out.print(board[i][j]);
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
		solveWithManhattanDistance(board);
		solveWithEuclideanDistance(board);
	}
	
	private static void solveWithManhattanDistance(int[][] board) {
		int[] dr = {0, 0, 1, -1};
		int[] dc = {1, -1, 0, 0};
		HashSet<Integer> explored = new HashSet<Integer>();
		PriorityQueue<qEntry> pQueue = new PriorityQueue<>();
		
		grid = board;
		pQueue.add(new qEntry(grid, getManhattanDistance(grid), null));
		
		while(!pQueue.isEmpty()) {
			qEntry current = pQueue.poll();
			grid = current.board;
			addToExplored(explored);
			
			if(isGoalState()) {
				int steps = traceSolution(current);
				System.out.println("Success using Manhattan distance in: " + steps + " steps.");
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
						pQueue.add(new qEntry(newGrid, getManhattanDistance(newGrid), current));
					}
				}
			}
			
		}
		if(!success) {
			System.out.println("Failed to get a solution using Manhattan distance");
		}
	}
	
	private static int traceSolution(qEntry entry) {
		int steps = 0;
		Stack<int[][]> stack = new Stack<>();
		stack.add(entry.board);
		while(entry.parent != null) {
			steps++;
			entry = entry.parent;
			stack.add(entry.board);
		}
		while(!stack.isEmpty()) {
			printGrid(stack.pop());
		}
		return steps;
	}
	
	private static void solveWithEuclideanDistance(int[][] board) {
		int[] dr = {0, 0, 1, -1};
		int[] dc = {1, -1, 0, 0};
		HashSet<Integer> explored = new HashSet<Integer>();
		PriorityQueue<qEntry> pQueue = new PriorityQueue<>();
		
		grid = board;
		pQueue.add(new qEntry(grid, getEuclideanDistance(grid), null));
		
		while(!pQueue.isEmpty()) {
			qEntry current = pQueue.poll();
			grid = current.board;
			addToExplored(explored);
			
			if(isGoalState()) {
				int steps = traceSolution(current);
				System.out.println("Success using Euclidean distance in: " + steps + " steps.");
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
						pQueue.add(new qEntry(newGrid, getEuclideanDistance(newGrid), current));
					}
				}
			}
			
		}
		if(!success) {
			System.out.println("Failed to get a solution using Euclidean distance");
		}
	}
	
	
}

class qEntry implements Comparable<qEntry> {

	int[][] board;
	int distance;
	qEntry parent;
	
	qEntry(int[][] board, int distance, qEntry parent){
		this.board = board;
		this.distance = distance;
		this.parent = parent;
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



