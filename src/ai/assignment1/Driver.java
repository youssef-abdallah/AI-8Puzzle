package ai.assignment1;


public class Driver {

	public static void main(String[] args) {
		//int[][] grid = parseInput(args);
		int[][] grid = {{1,2,5},{3,4,0},{6,7,8}};
		
		BFSSolver dfsSolver = new BFSSolver();
		dfsSolver.solve(grid);
		
		System.out.println("running time in seconds: " + dfsSolver.getRunningTime());
		System.out.println("No. of nodes expanded:" + dfsSolver.getNodesExpanded());
		System.out.println("Search depth: " + dfsSolver.getSearchDepth());
		System.out.println("Path to goal: " + dfsSolver.getPathToGoal());
		System.out.println("Max search depth: " + dfsSolver.getMaxSearchDepth());
		System.out.println("Cost of path: " + dfsSolver.getCostOfPath());
		
		//AStarSolverEuc aStarSolver = new AStarSolverEuc();
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
