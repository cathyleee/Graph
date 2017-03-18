import java.util.*;

/**
 * A sparse, list-based implementation of a graph class,
 * parameterised by 2 generic types
 * 
 * @author Cathy Lee
 * @version CSC 212 Stage 1 Final, 3rd December 2016
 *
 * @param <V> specifies the generic type utilised by nodes
 * @param <E> specifies the generic type utilised by edges
 */
public class Graph<V, E> {
	/** An ArrayList of nodes */
	public ArrayList<Node> nodeList;

	/** An ArrayList of edges */
	public ArrayList<Edge> edgeList;
	
	/** HashSet of seen nodes for DFT */
	public HashSet<Node> DFTseen = new HashSet<Node>();
	
	/** Output ArrayList of nodes for DFT */
	public ArrayList<Node> DFToutput = new ArrayList<Node>();

	/** Constructor for graph class */
	public Graph() {
		nodeList = new ArrayList<Node>();
		edgeList = new ArrayList<Edge>();
	}

	/** Adds a node */
	public void addNode(V data) {
		Node n = new Node(data);
		nodeList.add(n);
	}

	/** Adds an edge */
	public void addEdge(E data, Node head, Node tail) {
		Edge e = new Edge(data, head, tail);
		head.addEdgeRef(e);
		tail.addEdgeRef(e);
		edgeList.add(e);
	}

	/** Accessor for node */
	public Node getNode(int i) {
		return nodeList.get(i);
	}
	
	/** Accessor for list of nodes */
	public ArrayList<Node> getNodes() {
		return nodeList;
	}
	
	/** Accessor for list of edges */
	public ArrayList<Edge> getEdges() {
		return edgeList;
	}

	/** Accessor for nodes that are endpoints of a list of edges */
	public HashSet<Node> endpoints(HashSet<Edge> edges) {
		HashSet<Node> nodeSet = new HashSet<Node>();
		for (Edge e: edges) {
			nodeSet.add(e.getHead());
			nodeSet.add(e.getTail());
		}
		return nodeSet;
	}

	/** Accessor for nodes not on a given list */ 
	public HashSet<Node> otherNodes(HashSet<Node> group) {
		HashSet<Node> nodeSet = new HashSet<Node>();
		for (Node n: nodeList){
			if (!group.contains(n)){
				nodeSet.add(n);
			}
		}
		return nodeSet;	
	}

	/** Accessor for edge */
	public Edge getEdge(int i) {
		return edgeList.get(i);
	}

	/** Accessor for specific edge */
	public Edge getEdgeRef(Node head, Node tail) {
		return head.edgeTo(tail);
	}

	/** Counts the number of nodes */
	public int numNodes() {
		return nodeList.size();
	}

	/** Counts the number of edges */
	public int numEdges() {
		return edgeList.size();
	}

	/** Removes a node */
	public void removeNode(Node node) {
		// Remove the node's edge references
		//Edge edge;
		while (!node.getEdgeRefs().isEmpty()) {
			removeEdge(node.getEdgeRefs().get(0));
		}
		nodeList.remove(node);
	}

	/** Removes an edge */
	public void removeEdge(Edge edge) {
		// Remove the edge's edge references
		Node head = edge.getHead();
		Node tail = edge.getTail();
		head.removeEdgeRef(edge);
		tail.removeEdgeRef(edge);
		edgeList.remove(edge);
	}

	/** Removes an edge */
	public void removeEdge(Node head, Node tail) {
		Edge edge = head.edgeTo(tail);
		head.removeEdgeRef(edge);
		tail.removeEdgeRef(edge);
		edgeList.remove(edge);
	}
	
	/** Breadth-first traversal of graph */
	public ArrayList<Node> BFT(Node start) {
		LinkedList<Node> BFTqueue = new LinkedList<Node>();
		HashSet<Node> BFTseen = new HashSet<Node>();
		ArrayList<Node> BFToutput = new ArrayList<Node>();
		
		// Add starting node to queue & mark as seen
		BFTqueue.add(start);
		BFTseen.add(start);
		while (!BFTqueue.isEmpty()) {
			Node removed = BFTqueue.remove();
			BFToutput.add(removed);
			for (Node neighbor: removed.getNeighbors()) {
				if (!BFTseen.contains(neighbor)) {
					BFTqueue.add(neighbor);
					BFTseen.add(neighbor);
					// Add nodes to output ArrayList
					BFToutput.add(neighbor);
				}
			}
		}
		return BFToutput;
	}
	
	/** Prints the nodes accessible from the starting node using BFT */
	public void printBFT(ArrayList<Node> BFToutput) {
		for (Node n: BFToutput) {
			System.out.println(n.getData()+" ");
		}
	}
	
	/** Depth-first traversal of graph */
	public ArrayList<Node> DFT(Node start) {
		// Add starting node to output ArrayList & mark as seen
		DFToutput.add(start);
		DFTseen.add(start);
		for (Node neighbor: start.getNeighbors()) {
			if (!DFTseen.contains(neighbor)) {
				DFT(neighbor);
			}
		}
		return DFToutput;
	}
	
	/** Prints the nodes accessible from the starting node using DFT */
	public void printDFT(ArrayList<Node> DFToutput) {
		for (Node n: DFToutput) {
			System.out.println(n.getData()+" ");
		}
	}
	
	/** Djikstra's shortest-path algorithm to compute distances to nodes *//*
	public double[] distances(Node start) {
		double[] dist;
		
		
		return dist;
	}*/

	/** Checks for graph consistency */
	public void check() {
		for (Edge e: edgeList) {
			// Check if the head of an edge links back to that edge
			if (!(e.getHead().getEdgeRefs().contains(e))) {
				System.out.println("Head of "+e.getData()+" does not link back to "+e.getData()+".");
			}
			// Check if the tail of an edge links back to that edge
			if (!(e.getTail().getEdgeRefs().contains(e))) {
				System.out.println("Tail of "+e.getData()+" does not link back to "+e.getData()+".");
			}
			// Check if every head / tail of an edge is in central edge list
			if (!edgeList.contains(e.getHead())) {
				System.out.println("Head of edge "+e.getData()+" is not in central edge list.");
			} else if (!edgeList.contains(e.getTail())) {
				System.out.println("Tail of edge "+e.getData()+" is not in central edge list.");
			} else if (!edgeList.contains(e.getHead()) && !edgeList.contains(e.getTail())) {
				System.out.println("Head and tail of edge "+e.getData()+" is not in central edge list.");
			}
		}
	
		for (Node n: nodeList) {
			// Check that every edge referenced by a node is in central node list
			if (!nodeList.contains(n.getEdgeRefs())) {
				System.out.println("Edge referenced by "+n.getData()+" is not in central node list.");
			}
			// Check that every edge listed for a node has that node as head/tail
			for (Edge e2: n.getEdgeRefs()) {
				if ((e2.getHead() != n) || (e2.getTail() != n)) {
					System.out.println("Edge "+e2.getData()+" listed for node "+n.getData()+" does not have "+n.getData()+" as a head or tail.");
				}
			}
		}
	}

	/** Prints a representation of the graph */
	public void print() {
		for (Node n: nodeList) {
			System.out.print(n.getData()+" ");
		}
		for (Edge e: edgeList) {
			System.out.print(e.getData()+" ");
		}
	}

	/**
	 * Nested node class
	 *
	 * @param <V> specifies the generic type utilised by nodes
	 */
	public class Node {
		/** The data of the node */
		V data;
		
		/** A LinkedList containing references to edges */
		private LinkedList<Edge> edgeRefList;

		/** Constructor for nested node class */
		public Node(V data) {
			this.data = data;
			edgeRefList = new LinkedList<Edge>();
		}

		/** Adds an edge to the edge list */
		protected void addEdgeRef(Edge edge) {
			edgeRefList.add(edge);
		}
		
		/** Accessor for LinkedList of edge references */
		public LinkedList<Edge> getEdgeRefs() {
			return edgeRefList;
		}

		/** Returns the edge to a specified node */
		public Edge edgeTo(Node neighbor) {
			Edge edge = null;
			for (Edge e: edgeList) {
				if ((e.head == neighbor) || (e.tail == neighbor)) {
					edge = e;
				}
			}
			return edge;
		}

		/** Returns true if there is an edge to a specified node */
		public boolean isNeighbor(Node node) {
			boolean bool = false;
			if (this.edgeTo(node) != null) {
				bool = true;
			}
			return bool;
		}

		/** Accessor for data */
		public V getData() {
			return this.data;
		}

		/** Returns a list of neighbours */
		public LinkedList<Node> getNeighbors() {
			LinkedList<Node> neighbors = new LinkedList<Node>();
			for (Edge e: edgeRefList) {
				if (e.getHead() == this) {
					neighbors.add(e.getTail());
				} else if (e.getTail() == this) {
					neighbors.add(e.getHead());
				}
			}
			return neighbors;
		}

		/** Removes an edge from the edge list */
		protected void removeEdgeRef(Edge edge) {
			edgeRefList.remove(edge);
		}

		/** Manipulator for data */
		public void setData(V data) {
			this.data = data;
		}
	}

	/**
	 * Nested edge class
	 *
	 * @param <E> specifies the generic type utilised by edges
	 */
	public class Edge {
		/** The data of the edge */
		E data;
		
		/** The head of the edge */ 
		Node head;
		
		/** The tail of the edge */
		Node tail;

		/** Constructor for nested edge class */
		public Edge(E data, Node head, Node tail) {
			this.data = data;
			this.head = head;
			this.tail = tail;
		}

		/** Accessor for data */
		public E getData() {
			return this.data;
		}

		/** Accessor for endpoint #1 */
		public Node getHead() {
			return this.head;
		}

		/** Accessor for opposite node */
		public Node getTail() {
			return this.tail;
		}

		/** Accessor for opposite node */
		public Node oppositeTo(Node node) {
			Node n = null;
			if (node == this.head) {
				n = this.tail;
			} else if (node == this.tail) {
				n = this.head;
			}
			return n;
		}

		/** Manipulator for data */
		public void setData(E data) {
			this.data = data;
		}

		/** Checks if 2 edges connect the same end points */
		@SuppressWarnings("unchecked")
		public boolean equals(Object o) {
			Edge edge = (Edge)o;
			boolean bool = false;
			if ((this.head == edge.head) && (this.tail == edge.tail) 
					|| (this.head == edge.tail) && (this.tail == edge.head)) {
				bool = true;
			}
			return bool;
		}

		/** Redefines hashCode() to match redefined equals() */
		public int hashCode() {
			return this.head.hashCode() + this.head.hashCode();
		}
	}
}
