package ai.assignment1;
import java.awt.Point;
import java.lang.Math;
import java.util.*;

public class AStarSolverEuc implements Solver{
	
	private static int[][] grid;
	private static boolean success = false;
	
	private static List<String> pathToGoal = new ArrayList<String>();
	private static int costOfPath = 0;
	private static int nodesExpanded = 0;
	private static int maxDepthSearch = 0;
	private static double runningTime = 0;
	
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
			long startTime = System.currentTimeMillis();
			int[] dr = {0, 0, 1, -1};
			int[] dc = {1, -1, 0, 0};
			String[] directions = {"Right", "Left", "Down", "Up"};
			HashSet<Integer> explored = new HashSet<Integer>();
			PriorityQueue<qEntry> pQueue = new PriorityQueue<>();
			
			grid = board;
			pQueue.add(new qEntry(grid, getEuclideanDistance(grid), 0, null, null, 0));
			
			int exploredStates = 0;
			
			while(!pQueue.isEmpty()) {
				exploredStates++;
				qEntry current = pQueue.poll();
				grid = current.board;
				addToExplored(explored);
				maxDepthSearch = Math.max(maxDepthSearch, current.depth); 
				if(isGoalState()) {
					nodesExpanded = exploredStates;
					long endTime = System.currentTimeMillis();
					runningTime = endTime - startTime;
					int steps = traceSolution(current);
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
							pQueue.add(new qEntry(newGrid, getEuclideanDistance(newGrid) + current.cost, current.cost + 1, current, directions[i], current.depth+1));
						}
					}
				}
				
			}
			if(!success) {
				System.out.println("Failed to get a solution using Euclidean distance");
			}
		}

		
		
		
		private static int traceSolution(qEntry entry) {
			int steps = 0;
			Stack<int[][]> stack = new Stack<>();
			stack.add(entry.board);
			while(entry.parent != null) {
				steps++;
				pathToGoal.add(0, entry.direction);
				entry = entry.parent;
				stack.add(entry.board);
			}
			
			costOfPath = stack.size()-1;
			while(!stack.isEmpty()) {
				printGrid(stack.pop());
			}
			for(int i = 0; i < pathToGoal.size(); i++) {
				System.out.print(pathToGoal.get(i) + " , ");
			}
			
			return steps;
		}

		@Override
		public List<String> getPathToGoal() {
			return pathToGoal;
		}

		@Override
		public int getCostOfPath() {
			return costOfPath;
		}

		@Override
		public int getNodesExpanded() {
			return nodesExpanded;
		}

		@Override
		public int getMaxSearchDepth() {
			return maxDepthSearch;
		}

		@Override
		public double getRunningTime() {
			return runningTime;
		}

		@Override
		public int getSearchDepth() {
			return costOfPath;
		}
	
	
}
