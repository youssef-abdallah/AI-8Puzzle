package ai.assignment1;


public class Driver {

	public static void main(String[] args) {
		int[][] grid = parseInput(args);
		DFSSolver dfsSolver = new DFSSolver();
		dfsSolver.solve(grid);
		System.out.println(dfsSolver.getNodesExpanded());
		System.out.println(dfsSolver.getSearchDepth());
		System.out.println(dfsSolver.getPathToGoal());
		System.out.println(dfsSolver.getMaxSearchDepth());
		System.out.println(dfsSolver.getCostOfPath());
		//AStarSolver aStarSolver = new AStarSolver();
		//aStarSolver.solve(grid);
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
