package ai.assignment1;

import java.util.List;

public interface Solver {
	void solve(int grid[][]);
	
	List<String> getPathToGoal();
	
	int getCostOfPath();
	
	int getNodesExpanded();
	
	int getMaxSearchDepth();
	
	double getRunningTime();
	
	int getSearchDepth();
}
