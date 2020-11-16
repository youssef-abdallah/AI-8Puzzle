package ai.assignment1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;


public class Printer {
	private static int[][] grid = new int[3][3];

	
	private static void swap(Node target) {
		int encodedState = target.encodedState;
		for (int i = 2; i >= 0; i--) {
			for (int j = 2; j >= 0; j--) {
				grid[i][j] = encodedState % 10 - 1;
				encodedState /= 10;
			}
		}
	}
	
	
	public void printGridToFile(List<Node> nodesToGoal) {
		
		try(FileWriter fw = new FileWriter("result.txt", true);
			    BufferedWriter bw = new BufferedWriter(fw);
			    PrintWriter out = new PrintWriter(bw))
			{
			  Collections.reverse(nodesToGoal);
			  for (int n = 0; n < nodesToGoal.size(); n++) {
				  Node node = nodesToGoal.get(n);
				  swap(node);
				  
				  if (n != 0) out.println("move " + node.action + "\r\n");
				  out.println("Step no. : " + n);
		          for (int i = 0; i < 3; i++) {
		        	  out.print("\r\n");
		  			for (int j = 0; j < 3; j++) {
		  				out.print("|  " + grid[i][j] + "  |"); 
		  			}
		  			out.print("\r\n");
		  		  }
		          out.print("\r\n");  
			  }
	      } catch (IOException e) {
	        System.out.println("An error occurred.");
	        e.printStackTrace();
	      }
	}
	
	public void printGridToFileAStar(List<int [][]> grids, List<String> pathToGoal) {
		
		try(FileWriter fw = new FileWriter("result.txt", true);
			    BufferedWriter bw = new BufferedWriter(fw);
			    PrintWriter out = new PrintWriter(bw))
			{
			  for (int n = 0; n < grids.size(); n++) {
				  grid = grids.get(n);
				  out.println("Step no. : " + n);
		          for (int i = 0; i < 3; i++) {
		        	  out.print("\r\n");
		  			for (int j = 0; j < 3; j++) {
		  				out.print("|  " + grid[i][j] + "  |"); 
		  			}
		  			out.print("\r\n");
		  		  }
		          out.print("\r\n");  
		          if (n != grids.size() - 1) out.println("move " + pathToGoal.get(n) + "\r\n");
			  }
	      } catch (IOException e) {
	        System.out.println("An error occurred.");
	        e.printStackTrace();
	      }
	}
	
	public void printResults(double runtime, int nodesExpanded, int searchDepth, List<String> pathToGoal,
			int maxSearchDepth, int costOfPath) {
		try(FileWriter fw = new FileWriter("result.txt", true);
			    BufferedWriter bw = new BufferedWriter(fw);
			    PrintWriter out = new PrintWriter(bw))
			{			
	          out.print("\r\n");  
	          out.println("running time in seconds: " + runtime);
		  	  out.println("No. of nodes expanded:" + nodesExpanded);
		  	  out.println("Search depth: " + searchDepth);
		  	  out.println("Path to goal: " + pathToGoal);
		  	  out.println("Max search depth: " + maxSearchDepth);
		  	  out.println("Cost of path: " + costOfPath);
	      } catch (IOException e) {
	        System.out.println("An error occurred.");
	        e.printStackTrace();
	      }
	}
	
	public void clearResultsFile() {
		try {
		      File myObj = new File("result.txt");
		      if (myObj.createNewFile()) {
		        System.out.println("File created: " + myObj.getName());
		      } else {
		    	new FileWriter("result.txt",false);
		      }
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
	}

}
