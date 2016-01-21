import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

public class MapReader {
	 protected Map<String, Vertex> vertices;
  @SuppressWarnings("deprecation")
public static Graph readGraph(String vertexfile, String edgefile) {
	  Graph graph = new Graph();
	  try {
		 
		DataInputStream vertexStream = new DataInputStream(new BufferedInputStream(new FileInputStream(vertexfile)));
		DataInputStream edgeStream = new DataInputStream(new BufferedInputStream(new FileInputStream(edgefile)));
		String line;
		while((line = vertexStream.readLine())!=null){
			String[] word = line.trim().split(",");
			graph.addVertex(new Vertex(word[0], Integer.parseInt(word[1]), Integer.parseInt(word[2])));
		}
		while((line = edgeStream.readLine())!=null){
			String[] word = line.trim().split(",");
			double diff =  Math.pow(graph.getVertex(word[0]).posX-graph.getVertex(word[1]).posX,2)
					+  Math.pow(graph.getVertex(word[0]).posY-graph.getVertex(word[1]).posY,2);
			
	//		graph.addUndirectedEdge(word[0], word[1], Math.sqrt(diff));
			graph.addUndirectedEdge(word[0], word[1],1);
		}
	//	graph.printAdjacencyList();
	//	DisplayGraph display = new DisplayGraph(graph);
//		display.setVisible(true);
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
	  
    return graph; // TODO 
  }

  public static void main(String[] args) {

	  readGraph(args[0], args[1]);
  }

}
