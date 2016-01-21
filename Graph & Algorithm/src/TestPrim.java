import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class TestPrim {

  public static void main(String[] args) {
//	  Graph graph = new Graph();
//
//	  graph = readGraph(args[0], args[1]);
//	  graph = graph.getMinimumSpanningTree(args[2]);
//	  graph.printAdjacencyList();
//	  DisplayGraph display = new DisplayGraph(graph );
//		display.setVisible(true);
	  StringBuilder errors = new StringBuilder();
	    int sectionScoreMax = 13;
	    try {
	      Graph g = MapReader.readGraph("ttrvertices_test.txt", "ttredges_test.txt");

	      g.computeAllEuclideanCosts();
	      g.doPrim("Denver");

	      double sum = 0.0;
	      HashMap<String, HashSet<String>> tree = new HashMap<>();
	      String vname;
	      String pname;
	      for (Vertex v : g.getVertices()) {
	        vname = v.name;
	        if (v.backpointer != null) {
	          pname = v.backpointer.name;
	          sum = sum + v.cost;
	          if (!tree.containsKey(pname))
	        	  tree.put(pname, new HashSet<String>());
//	            tree.put(pname, new HashSet<>());
	          tree.get(pname).add(vname);
	        }
	      }

	      Set<String> seen = new HashSet();
	      LinkedList<String> stack = new LinkedList<>();
	      stack.push("Denver");
	      String next;
	      boolean has_cycle = false;

	      while (!stack.isEmpty()) {
	        next = stack.pop();
	        seen.add(next);
	        if (tree.containsKey(next))
	          for (String child : tree.get(next)) {
	            if (seen.contains(child))
	              has_cycle = true;
	            stack.push(child);
	          }
	      }
	      if (has_cycle || (seen.size() < g.vertices.size())) {
	        System.out.println("Result is not a spanning tree.");
	        return;
	      }
	      if (sum > 1675.40589624302) {
	       System.out.println( "Spanning tree is not minimal.");
	        return;
	      }
	    System.out.println(sectionScoreMax);
	    } catch (Exception e) {
	     System.out.println("Exception");
	    }
	}

}
