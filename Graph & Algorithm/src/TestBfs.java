import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

public class TestBfs {
	
  public static void main(String[] args) {
//	  Graph graph = new Graph();
//
//	  graph = MapReader.readGraph(args[0], args[1]);
//	  graph = graph.getUnweightedShortestPath(args[2], args[3]);
//
//	  graph.printAdjacencyList();
//	  DisplayGraph display = new DisplayGraph(graph );
//	  
//		display.setVisible(true);
//      }
  
	  try {
	      Graph g = MapReader.readGraph("ttrvertices_test.txt", "ttredges_test.txt");

	      StringBuilder errors = new StringBuilder();
	      int score = 13;
	      g.doBfs("Houston");
	      // Test shortest path costs
	      if (g.vertices.get("Houston").cost != 0.0 || g.vertices.get("Montreal").cost != 3.0
	          || g.vertices.get("Seattle").cost != 3.0) {
	        score = score - 8;
	        errors.append("Invalid cost annotations.");
	      }
	      for (Vertex v : g.getVertices())
	        if (!v.visited) {
	          score = score - 8;
	          errors.append(" Unvisited vertices.");
	        }

	      if (g.vertices.get("Houston").backpointer != null
	          || !(g.vertices.get("Montreal").backpointer.name.equals("Denver"))
	          || !(g.vertices.get("Denver").backpointer.name.equals("Nashville"))
	          || (!g.vertices.get("Nashville").backpointer.name.equals("Houston"))) {
	        score = score - 8;
	        errors.append(" Incorrect backpointers.");
	      }

	      g = MapReader.readGraph("ttrvertices.txt", "ttredges.txt");
	      g.doBfs("Calgary");
	      LinkedList<String> path = new LinkedList<String>();
	      Vertex u = g.vertices.get("NewYork");
	      path.addFirst(u.name);
	      while (u.backpointer != null) {
	    	  System.out.println("Loop");
	        u = u.backpointer;
	        path.addFirst(u.name);
	      }
	      String[] goldList = { "Calgary", "Winnipeg", "SaultSaintMarie", "Montreal", "NewYork" };
	      if (!compareCollections(path, Arrays.asList(goldList))) {
	        score = score - 8;
	        errors.append("Incorrect shortest path between Houston and Montreal. Should be " + Arrays.toString(goldList));
	      }   ;
	      System.out.println(errors.toString());
	      if (score < 0)
	        score = 0;
	      System.out.println(errors.toString());
	    } catch (Exception e) {
	      
	    }
  }

	  private static <T> boolean compareCollections(Collection<T> l1, Collection<T> l2) {
	    if (l1.size() != l2.size()) {
	      return false;
	    }
	    T i2;
	    Iterator<T> l2iter = l2.iterator();
	    for (T i1 : l1) {
	      i2 = l2iter.next();
	      if (!(i2.equals(i1))) {
	        return false;
	      }
	    }
	    return true;
	  }
}
  
  
