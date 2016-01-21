import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import javax.management.Query;

public class Graph {

  // Keep a fast index to nodes in the map
  protected Map<String, Vertex> vertices;
  protected Map<Double,String> pair;
  /**
   * Construct an empty Graph.
   */
  public Graph() {
    vertices = new HashMap<String, Vertex>();
  }

  public void addVertex(String name) {
    Vertex v = new Vertex(name);
    addVertex(v);
  }

  public void addVertex(Vertex v) {
    if (vertices.containsKey(v.name))
      throw new IllegalArgumentException(
          "Cannot create new vertex with existing name.");
    vertices.put(v.name, v);
  }

  public Collection<Vertex> getVertices() {
    return vertices.values();
  }

  public Vertex getVertex(String s) {
    return vertices.get(s);
  }

  /**
   * Add a new edge from u to v. Create new nodes if these nodes don't exist
   * yet. This method permits adding multiple edges between the same nodes.
   * 
   * @param u
   *          the source vertex.
   * @param w
   *          the target vertex.
   */
  public void addEdge(String nameU, String nameV, Double cost) {
    if (!vertices.containsKey(nameU))
      addVertex(nameU);
    if (!vertices.containsKey(nameV))
      addVertex(nameV);
    Vertex sourceVertex = vertices.get(nameU);
    Vertex targetVertex = vertices.get(nameV);
    Edge newEdge = new Edge(sourceVertex, targetVertex, cost);
    sourceVertex.addEdge(newEdge);
  }

  /**
   * Add a new edge from u to v. Create new nodes if these nodes don't exist
   * yet. This method permits adding multiple edges between the same nodes.
   * 
   * @param u
   *          unique name of the first vertex.
   * @param w
   *          unique name of the second vertex.
   */
  public void addEdge(String nameU, String nameV) {
    addEdge(nameU, nameV, 1.0);
  }


  /****************************
   * Your code follow here.   *
   ****************************/ 

  public void addUndirectedEdge(String s, String t, double cost) {
	  if(!vertices.containsKey(s))
		  addVertex(s);
	  if(!vertices.containsKey(t))
		  addVertex(t);
	  Vertex sourceVertex = vertices.get(s);
	  Vertex targetVertex = vertices.get(t);
	  Edge fstEdge = new Edge(sourceVertex,targetVertex,cost);
	  Edge secEdge = new Edge(targetVertex,sourceVertex,cost);
	  sourceVertex.addEdge(fstEdge);
	  targetVertex.addEdge(secEdge);
	  
  }

  public double computeEuclideanCost(double ux, double uy, double vx, double vy) {
    return Math.sqrt(Math.pow(ux-vx,2) +  Math.pow(uy-vy,2)); 
  }

  public void computeAllEuclideanCosts() {
	  for(Vertex v:vertices.values()){
		  for(Edge e:v.getEdges()){
			  e.cost=computeEuclideanCost(v.posX, v.posY, e.targetVertex.posX, e.targetVertex.posY);
		  }
	  }
    return; 
  }

  /** BFS */
  public void doBfs(String s) {
	  Queue<Vertex> q = new LinkedList<Vertex>();
	  
	  for(Vertex v: vertices.values()){
		 v.visited=false;
		 v.cost = Integer.MAX_VALUE;
	  }
	  vertices.get(s).cost = 0;
	  vertices.get(s).visited = true;	  
	  q.add(vertices.get(s));
	  while(!q.isEmpty()){
		  Vertex v = q.poll();
		  for(Edge e:v.getEdges()){
			  Vertex w = e.targetVertex;
			  if(w.visited==false){
				  w.visited =true;
				  w.backpointer = v;
				  w.cost = v.cost +1;
				  q.add(w);
			  }
		  }
	  }
	  
    return; 
  }
  
  public Graph getUnweightedShortestPath(String s, String t) {
	  doBfs(s);
	  Graph testing = new Graph();

	  testing.addVertex(new Vertex(t, this.vertices.get(t).posX,this.vertices.get(t).posY));
	  Vertex va = this.vertices.get(t).backpointer;
	  while( va!=vertices.get(s) ){
		  testing.addVertex(new Vertex(va.name, va.posX,va.posY));	
		  va = va.backpointer;		
	 }

	 testing.addVertex(new Vertex(va.name, va.posX,va.posY));
	 for(Vertex v:testing.vertices.values()){
		 testing.addEdge(v.name, v.backpointer.name);
	 }

    return testing;
  }

  /** Dijkstra's */
  // When we go from SantaFe to LosAngeles, the BST is choosing from  SantaFe->ElPaso->LosAngeles; 
  // By using Dijkstra to find the path from SantaFe to LosAngeles, it is choosing from SantaFe->Phoenix->LosAngeles;
  public void doDijkstra(String s) {
	  PriorityQueue<Pair<Double,String >> q = new PriorityQueue<Pair<Double,String>>();
	  for(Vertex v:vertices.values()){
	
		  v.cost = Integer.MAX_VALUE;
		  v.visited = false;
	  }
	  vertices.get(s).cost =0.0;	 
	  q.add(new Pair<Double,String>(0.0,s));
	  while(!q.isEmpty()){
		  Pair<Double,String> pair =q.remove();
		  Vertex u = vertices.get(pair.name);
		  if(u.visited==false){
			 u.visited = true;
			  for(Edge v: u.getEdges()){
				  Vertex w = v.targetVertex;
				  if(w.visited==false){
					  double cost = computeEuclideanCost(u.posX, u.posY, w.posX, w.posY);
					  if(pair.cost+cost<w.cost){
						  w.cost = cost +pair.cost;
						  w.backpointer = u;
						  q.add(new Pair<Double,String>(w.cost,w.name));
					  }
				  }
			  }
		  }
	  }
	  
    return; // TODO
  }

  public Graph getWeightedShortestPath(String s, String t) {
	  doDijkstra(s);
	  Graph testing = new Graph();
	  testing.addVertex(new Vertex(t, this.vertices.get(t).posX,this.vertices.get(t).posY));
	  Vertex va = this.vertices.get(t).backpointer;
	  while( va!=vertices.get(s) ){
		  testing.addVertex(new Vertex(va.name, va.posX,va.posY));	
		  va = va.backpointer;
	 }
	 testing.addVertex(new Vertex(va.name, va.posX,va.posY));
	 for(Vertex v:testing.vertices.values()){
		 double cost = computeEuclideanCost(v.posX, v.posY, v.backpointer.posX, v.backpointer.posY);
		 testing.addEdge(v.name, v.backpointer.name, cost);
	 }
    return testing;
  }

  /** Prim's */
  public void doPrim(String s) {
	  PriorityQueue<Pair<Double,String>> q = new PriorityQueue<>();
	  int num =0;
	  for(Vertex v:vertices.values()){
		  v.cost = Integer.MAX_VALUE;
		  v.visited = false;
	  }
	
	  vertices.get(s).cost =0.0;
	  q.add(new Pair<Double,String>(0.0,s));
	  Vertex vv=null;
	  while(!q.isEmpty()){
		  Pair<Double,String> pair = q.remove();  
		  Vertex u = vertices.get(pair.name);
		  for(Edge w: u.getEdges()){
			  vv = w.targetVertex;
			  double cost = computeEuclideanCost(u.posX, u.posY, vv.posX, vv.posY);
			  if(vv.visited==false && cost<vv.cost){
				  vv.cost = cost;
				  vv.backpointer = u;  
				  q.add(new Pair<Double,String>(vv.cost,vv.name));
			  }
		  }
		  u.visited =true;	  
	  }

	  
	  
	  
    return; 
  }

  public Graph getMinimumSpanningTree(String s) {
	  doPrim(s);
	  Graph testing = new Graph();
	  testing.addVertex(new Vertex(s, vertices.get(s).posX,vertices.get(s).posY));
	  Vertex vt = vertices.get(s);
	  while(vt!=null &&testing.vertices.size()!=this.vertices.size()){
		  edgeRec(vt,testing);
	  }
	  for(Vertex v:vertices.values()){
		  if(v.backpointer!=null){
			  testing.addUndirectedEdge(v.name, v.backpointer.name, computeEuclideanCost(v.posX, v.posY,v.backpointer.posX , v.backpointer.posY));
		  }
	  }
    return testing;
  }

 



private void edgeRec(Vertex vt, Graph testing) {
	if(vt ==null) {
		if(vt.backpointer==null) return;
		vt = vt.backpointer;
	}
	for(Edge w:vt.getEdges()){
		Vertex v = w.targetVertex;
		if(v==null) continue;
		if(!testing.vertices.containsKey(v.name)){
			testing.addVertex(new Vertex(v.name, v.posX,v.posY));		
			edgeRec(v,testing);
		}
	}
	return;
}

/*************************/

  public void printAdjacencyList() {
    for (String u : vertices.keySet()) {
      StringBuilder sb = new StringBuilder();
      sb.append(u);
      sb.append(" -> [ ");
      for (Edge e : vertices.get(u).getEdges()) {
        sb.append(e.targetVertex.name);
        sb.append("(");
        sb.append(e.cost);
        sb.append(") ");
      }
      sb.append("]");
      System.out.println(sb.toString());
    }
  }

  public static void main(String[] args) {
	
 
  }

}
