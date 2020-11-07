package ai.assignment1;


public class Driver {

	public static void main(String[] args) {
		//int[][] grid = parseInput(args);
		int[][] grid = {{1,0,2},{7,5,4},{8,6,3}};
		
		DFSSolver dfsSolver = new DFSSolver();
		dfsSolver.solve(grid);
		
		AStarSolver aStarSolver = new AStarSolver();
		aStarSolver.solve(grid);
	}
	
	private static int[][] parseInput(String[] args) {
		int[][] grid = new int[3][3];
		int idx = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				grid[i][j] = Integer.parseInt(args[idx++]);
			}
		}
		return grid;
	}

}
