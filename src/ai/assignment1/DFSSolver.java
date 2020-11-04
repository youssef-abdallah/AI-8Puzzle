package ai.assignment1;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class DFSSolver implements Solver {
	
	private static int[][] grid;
	
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
	
	private static boolean isValid(Point p) {
		return p.x >= 0 && p.x < 3 && p.y >= 0 && p.y < 3;
	}
	
	private static Point swap(Point target) {
		Point org = new Point();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (grid[i][j] == 0) {
					org.x = i;
					org.y = j;
					grid[i][j] = grid[target.x][target.y];
					grid[target.x][target.y] = 0;
					i = 4;
					j = 4;
				}
			}
		}
		return org;
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
	
	private static boolean wasExplored(Set<Integer> set, Point next) {
		ArrayList<Integer> state = new ArrayList<Integer>();
		int[][] newGrid = new int[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) newGrid[i][j] = grid[i][j];
		}
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (newGrid[i][j] ==  0) {
					newGrid[i][j] = newGrid[next.x][next.y];
					newGrid[next.x][next.y] = 0; 
					j = 3;
					i = 3;
				}
			}
		}
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

	@Override
	public void solve(int[][] board) {
		int[] dr = {0, 0, 1, -1};
		int[] dc = {1, -1, 0, 0};
		HashSet<Integer> explored = new HashSet<Integer>();
		Point initial = new Point();
		grid = board;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (grid[i][j] == 0) {
					initial = new Point(i, j);
				}
			}
		}
		Stack<Point> st = new Stack<>();
		st.push(initial);
		while (!st.empty()) {
			Point cur = st.pop();
			Point org = swap(cur);
			printBoard();
			st.push(org);
			addToExplored(explored);
			if (testGoalState()) {
				System.out.println("success");
				break;
			}
			for (int i = 0; i < 4; i++) {
				Point next = new Point();
				next.x = cur.x + dr[i];
				next.y = cur.y + dc[i];
				if (isValid(next) && !wasExplored(explored, next)) {
					st.push(next);
				}
			}
		}
	}

}
