
import java.util.*;
import java.io.*;
public class DirectedGraph  {
    ArrayList<DirectedNodeList> dGraph;
    int numVertex;
   boolean [] marked;
    
    
    public DirectedGraph() {
        dGraph = new ArrayList<>();
        numVertex=0;
        
    }
    
    public DirectedGraph(int n) {
      numVertex =n;
      dGraph = new ArrayList<>(n);
      marked= new boolean[n];
      for (int i=0;i<numVertex;i++)
       dGraph.add(new DirectedNodeList());
    }
    
    public void addEdge(int u, int v) {
       // assume all vertices are created
       // directed edge u to v will cause outdegree of u to go up and indegree of v to go up.
       
       if (u>=0 && u<numVertex && v>=0 && v<numVertex) { 
	      if (u!=v) {
           getNeighborList(u).addToOutList(v);
           getNeighborList(v).addToInList(u);
		  }
        }
        else throw new IndexOutOfBoundsException();
    }
    
    public DirectedNodeList getNeighborList(int u) {
        return dGraph.get(u);
    }
    
    public void printAdjacency(int u) {
       DirectedNodeList dnl = getNeighborList(u);
       System.out.println ("vertices going into "+u+"  "+dnl.getInList());
       System.out.println ("vertices going out of "+u+"  "+dnl.getOutList());
       System.out.println();
    }
    
    public void postOrderDepthFirstTraversal() {
       for (int i=0;i<numVertex;i++) 
       if (!marked[i])
           postOrderDFT (i);
       
    }
    public void postOrderDFT(int v){
        
        marked[v]=true;
        
        for (Integer u:dGraph.get(v).getOutList())
        if (!marked[u]) postOrderDFT(u);
       System.out.println(v);
    }
    
    public void depthFirstTraversal() {
       for (int i=0;i<numVertex;i++) 
       if (!marked[i])
           dFT (i);
       
    }
    public void dFT(int v){
        System.out.println(v);
        marked[v]=true;
        
        for (Integer u:dGraph.get(v).getOutList())
        if (!marked[u]) dFT(u);
       
    }
    
    
    
    public static void main(String[] args) {
        int n =6;
        DirectedGraph dg = new DirectedGraph(n);
        dg.addEdge(0,1);
        dg.addEdge(0,2);
        dg.addEdge(2,4);
        dg.addEdge(1,4);
        dg.addEdge(2,3);
        dg.addEdge(3,5);
        dg.addEdge(4,5);
        
        
        
        

        dg.addEdge(0,1);
        dg.addEdge(0,2);
        dg.addEdge(1,2);
        dg.addEdge(1,3);
        dg.addEdge(2,4);
        dg.addEdge(2,3);
        dg.addEdge(3,1);
        dg.addEdge(4,1);
        dg.addEdge(4,3);
        dg.addEdge(4,0);
        dg.addEdge(3,0);

        
        for (int i=0;i<dg.numVertex;i++)
        dg.printAdjacency(i);
        System.out.println ("regular depth first traversal");
        dg.depthFirstTraversal();
        System.out.println ();
        dg.marked= new boolean[n];
        System.out.println("post order depth first traversal");
        dg.postOrderDepthFirstTraversal();
        
        
        
    }
    

   
}
