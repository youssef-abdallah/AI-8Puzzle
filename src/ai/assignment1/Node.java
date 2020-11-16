package ai.assignment1;


public class Node {
		public int x, y, depth, cost, encodedState;
		
		public String action;
		public Node parent;
		
		public Node() {
			
		}
		
		public Node(int x, int y, int depth, int cost) {
			this.x = x;
			this.y = y;
			this.depth = depth;
			this.cost = cost;
			this.parent = null;
		}


}
