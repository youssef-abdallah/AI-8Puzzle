package ai.assignment1;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;



public class BFSSolver implements Solver{
	

	private static int[][] grid;
	private static List<String> pathToGoal = new ArrayList<String>();
	private static int costOfPath, nodesExpanded, depthOfSearch, maxDepth;
	private static double runtime;
	private static HashSet<Integer> frontier = new HashSet<>();
	private static HashSet<Integer> explored = new HashSet<Integer>();
	private static Printer printer = new Printer();
	
	// checking that the grid matches the winning state
	private static boolean testGoalState() {
		int cnt = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (grid[i][j] != cnt) return false; 
				cnt++;
			}
		}
		return true;
	}
	
	private static boolean isValid(Node p) {
		return p.x >= 0 && p.x < 3 && p.y >= 0 && p.y < 3;
	}
	
	private static void swap(Node target) {
		int encodedState = target.encodedState;
		for (int i = 2; i >= 0; i--) {
			for (int j = 2; j >= 0; j--) {
				grid[i][j] = encodedState % 10 - 1;
				encodedState /= 10;
			}
		}
	}
	
	private static void printBoard() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				System.out.print(grid[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	// encode the grid into a single int
	private static int encodeNextState(Node next) {
		int[][] newGrid = new int[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) newGrid[i][j] = grid[i][j];
		}
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (newGrid[i][j] == 0) {
					newGrid[i][j] = newGrid[next.x][next.y];
					newGrid[next.x][next.y] = 0;
				}
			}
		}
		return encodeBoard(newGrid);
	}
	
	private static int encodeBoard(int[][] board) {
		int encodedState = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				encodedState = (encodedState * 10) + (board[i][j] + 1);
			}
		}
		return encodedState;
	}
	
	private static String getActionString(int i) {
		if (i == 3) return "UP";
		if (i == 2) return "DOWN";
		if (i == 1) return "LEFT";
		return "RIGHT";
	}

	@Override
	public void solve(int[][] board) {
		printer.clearResultsFile();
		double startTime = System.currentTimeMillis();
		int[] dr = {0, 0, 1, -1};
		int[] dc = {1, -1, 0, 0};
		Node initial = new Node();
		grid = board;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (grid[i][j] == 0) {
					initial = new Node(i, j, 0, 0);
				}
			}
		}
		Queue<Node> q = new LinkedList<>();
		
		initial.action = "INITIAL";
		q.add(initial);
		initial.encodedState = encodeBoard(grid);
		frontier.add(initial.encodedState);
		while (!q.isEmpty()) {
			Node cur = q.remove();
			maxDepth = Math.max(maxDepth, cur.depth);
			swap(cur);
			frontier.remove(cur.encodedState);
			explored.add(cur.encodedState);
			printBoard();
			if (testGoalState()) {
				depthOfSearch = cur.depth;
				costOfPath = -1;
				List<Node> nodesToGoal = new ArrayList<Node>();
				while (cur != null) {
					costOfPath++;
					pathToGoal.add(cur.action);
					nodesToGoal.add(cur);
					cur = cur.parent;
				}
				Collections.reverse(pathToGoal);
				printer.printGridToFile(nodesToGoal);
				System.out.println("success");
				break;
			}
			for (int i = 0; i < 4; i++) {
				Node next = new Node();
				next.x = cur.x + dr[i];
				next.y = cur.y + dc[i];
				next.depth = cur.depth + 1;
				next.cost = cur.cost + 1;
				
				if (isValid(next)) {
					int nextState = encodeNextState(next);
					if (!explored.contains(nextState) && !frontier.contains(nextState)) {
						next.encodedState = nextState;
						next.action = getActionString(i);
						next.parent = cur;
						q.add(next);
						frontier.add(nextState);
					}
				}
			}
		}
		nodesExpanded = explored.size() - 1;
		double endTime = System.currentTimeMillis();
		runtime = (endTime - startTime) / 1000;
		printer.printResults(getRunningTime(), getNodesExpanded(), getSearchDepth(), getPathToGoal(),
				getMaxSearchDepth(), getCostOfPath());
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
		return maxDepth;
	}

	@Override
	public double getRunningTime() {
		return runtime;
	}
	

	@Override
	public int getSearchDepth() {
		return depthOfSearch;
	}



}
