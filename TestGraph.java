import java.util.*;

/**
 * Tests the methods written in the Graph class
 * 
 * @author Cathy Lee
 * @version CSC 212, 6th December 2016
 */
public class TestGraph {
	/** Graph of strings */
	private static Graph<String, String> graph;
	
	/** Tests methods */
	public static void main(String[] args) {
		graph = new Graph<String, String>();
		// Add nodes
		graph.addNode("A");
		graph.addNode("B");
		graph.addNode("C");
		graph.addNode("D");
		graph.addNode("E");
		// Consistency check
		System.out.println("Consistency check:");
		graph.check();
		// Add edges
		graph.addEdge("23", graph.getNode(3), graph.getNode(0));
		graph.addEdge("67", graph.getNode(3), graph.getNode(1));
		graph.addEdge("74", graph.getNode(2), graph.getNode(4));
		System.out.println("");
		System.out.println("Printing graph: ");
		graph.print();
		// Remove nodes
		graph.removeNode(graph.getNode(4));
		System.out.println("");
		System.out.println("Printing graph: ");
		graph.print();
		graph.addEdge("51", graph.getNode(1), graph.getNode(2));
		System.out.println("");
		System.out.println("Printing graph: ");
		graph.print();
		// Remove edge
		graph.removeEdge(graph.getEdge(2));
		System.out.println("");
		System.out.println("Printing graph: ");
		graph.print();
		graph.addEdge("51", graph.getNode(1), graph.getNode(2));
		graph.removeEdge(graph.getNode(1), graph.getNode(2));
		System.out.println("");
		System.out.println("Printing graph: ");
		graph.print();
		System.out.println("");
		System.out.println("Number of edges: "+graph.numEdges());
		System.out.println("Number of nodes: "+graph.numNodes());
		System.out.println("");
		// Get endpoint nodes
		HashSet<Graph<String,String>.Edge> edges = new HashSet<Graph<String,String>.Edge>();
		edges.add(graph.getEdge(1));
		edges.add(graph.getEdgeRef(graph.getNode(3), graph.getNode(0)));
		HashSet<Graph<String,String>.Node> endpoints = graph.endpoints(edges);
		System.out.println("Nodes that are endpoints of a given list of edges: ");
		for (Graph<String,String>.Node ep: endpoints) {
			System.out.print(ep.getData());
		}
		System.out.println("");
		// Get nodes not on a given list
		HashSet<Graph<String,String>.Node> nodes = new HashSet<Graph<String,String>.Node>();
		nodes.add(graph.getNode(3));
		nodes.add(graph.getNode(0));
		HashSet<Graph<String,String>.Node> unlisted = graph.otherNodes(nodes);
		System.out.println("Nodes not on a given list");
		for (Graph<String,String>.Node ul: unlisted) {
			System.out.println(ul.getData());
		}
		System.out.println("");
		// Traversals
		System.out.println("Breadth-first traversal starting from node "+graph.getNode(1).getData()+":");
		graph.printBFT(graph.BFT(graph.getNode(1)));
		System.out.println("Depth-first traversal starting from node "+graph.getNode(2).getData()+":");
		graph.printDFT(graph.DFT(graph.getNode(2)));
		System.out.println("");
		// Consistency check
		System.out.println("Consistency check:");
		graph.check();
	}
}
