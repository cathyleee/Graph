import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Implements a GUI for inputting and editing graph nodes and edges.
 * @author Cathy Lee
 * @version CSC 212, 9 December 2016
 */
public class GraphGUI extends Graph<Point,Integer> {
	/** The graph to be displayed */
	private GraphCanvas canvas;
	
	/** Label for the input mode instructions */
	private JLabel instr;
	
	/** The input mode */
	InputMode mode = InputMode.ADD_NODES;
	
	/** Remembers node where last mousedown event occurred */
	Graph<Point,Integer>.Node nodeUnderMouse;
	
	/** Remembers node that is the first point clicked when creating an edge */
	Graph<Point,Integer>.Node headUnderMouse;
	
	/** Schedules a job for the event-displatching thread
	 * creating and showing this application's GUI */
	public static void main(String[] args) {
		final GraphGUI GUI = new GraphGUI();
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GUI.createAndShowGUI();
			}
		});
	}
	
	/** Sets up the GUI window */
	public void createAndShowGUI() {
		// Make sure we have nice window decorations.
		JFrame.setDefaultLookAndFeelDecorated(true);

		// Create and set up the window.
		JFrame frame = new JFrame("Graph GUI");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Add components
		createComponents(frame);

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}
	
	/** Puts content in the GUI window */
	public void createComponents(JFrame frame) {
		// graph display
		Container pane = frame.getContentPane();
		pane.setLayout(new FlowLayout());
		JPanel panel1 = new JPanel();
		panel1.setLayout(new BorderLayout());
		canvas = new GraphCanvas();
		PointMouseListener pml = new PointMouseListener();
		canvas.addMouseListener(pml);
		canvas.addMouseMotionListener(pml);
		panel1.add(canvas);
		instr = new JLabel("Click to add new points; drag to move.");
		panel1.add(instr,BorderLayout.NORTH);
		pane.add(panel1);

		// controls
		JPanel panel2 = new JPanel();
		panel2.setLayout(new GridLayout(2,1));
		// Button for adding or moving nodes
		JButton addNodeButton = new JButton("Add/Move Nodes");
		panel2.add(addNodeButton);
		addNodeButton.addActionListener(new AddNodeListener());
		// Button for removing nodes
		JButton rmvNodeButton = new JButton("Remove Nodes");
		panel2.add(rmvNodeButton);
		rmvNodeButton.addActionListener(new RmvNodeListener());
		// Button for adding edges
		JButton addEdgeButton = new JButton("Add Edges");
		panel2.add(addEdgeButton);
		addEdgeButton.addActionListener(new AddEdgeListener());
		// Button for removing edges
		JButton rmvEdgeButton = new JButton("Remove Edges");
		panel2.add(rmvEdgeButton);
		rmvEdgeButton.addActionListener(new RmvEdgeListener());
		// Button for breadth-first traversal
		JButton BFTButton = new JButton("BFT");
		panel2.add(BFTButton);
		BFTButton.addActionListener(new BFTListener());
		// Button for depth-first traversal
		JButton DFTButton = new JButton("DFT");
		panel2.add(DFTButton);
		DFTButton.addActionListener(new DFTListener());
/*		// Button for loading a previously saved graph
		JButton loadGraphButton = new JButton("Load Graph");
		panel2.add(loadGraphButton);
		loadGraphButton.addActionListener(new LoadGraphListener());*/
		// Button for saving a graph
		JButton saveGraphButton = new JButton("Save Graph");
		panel2.add(saveGraphButton);
		saveGraphButton.addActionListener(new SaveGraphListener());
		pane.add(panel2);
	}
	
	/**
	 * Returns a node found within the drawing radius of the given location,
	 * or null if none
	 * 
	 * @param x specifies the x coordinate of the location
	 * @param y specifies the y coordinate of the location
	 * @return a node from the canvas if there is one covering this location,
	 * or a null reference if not
	 */
	public Graph<Point,Integer>.Node findNearbyNode(int x, int y) {
		for (int i = 0; i < canvas.graph.numNodes(); i++) {
			Point node = (Point)canvas.graph.getNode(i).getData();
			if (node.distance((double)x, (double)y) <= 5) {
				return canvas.graph.getNode(i);
			}
		}
		return null;
	}
	
	/** Constants for recording the input mode */
	enum InputMode {
		ADD_NODES, RMV_NODES, ADD_EDGES, RMV_EDGES, BFT, DFT
	}
	
	/** Listener for AddNode button */
	private class AddNodeListener implements ActionListener {
		/** Event handler for AddNode button */
		public void actionPerformed(ActionEvent e) {
			mode = InputMode.ADD_NODES;
			instr.setText("Click to add new nodes or change their location.");
		}
	}
	
	/** Listener for RmvNode button */
	private class RmvNodeListener implements ActionListener {
		/** Event handler for RmvNode button */
		public void actionPerformed(ActionEvent e) {
			mode = InputMode.RMV_NODES;
			instr.setText("Click to remove nodes.");
		}
	}
	
	/** Listener for AddEdge button */
	private class AddEdgeListener implements ActionListener {
		/** Event handler for AddEdge button */
		public void actionPerformed(ActionEvent e) {
			mode = InputMode.ADD_EDGES;
			instr.setText("Click and drag from one node to another to add an edge between them.");
		}
	}
	
	/** Listener for RmvEdge button */
	private class RmvEdgeListener implements ActionListener {
		/** Event handler for RmvEdge button */
		public void actionPerformed(ActionEvent e) {
			mode = InputMode.RMV_EDGES;
			instr.setText("Click and drag from one node to another to delete an edge between them.");
		}
	}
	
	/** Listener for BFT button */
	private class BFTListener implements ActionListener {
		/** Event handler for BFT button */
		public void actionPerformed(ActionEvent e) {
			mode = InputMode.BFT;
			instr.setText("Click on any node to start a breadth-first traversal of the graph.");
		}
	}
	
	/** Listener for DFT button */
	private class DFTListener implements ActionListener {
		/** Event handler for DFT button */
		public void actionPerformed(ActionEvent e) {
			mode = InputMode.DFT;
			instr.setText("Click on any node to start a depth-first traversal of the graph.");
		}
	}
	
/*	*//** Listener for LoadGraph button *//*
	private class LoadGraphListener implements ActionListener {
		*//** Event handler for LoadGraph button *//*
		public void actionPerformed(ActionEvent e) {
			BufferedReader input = null;
			try {
				input = new BufferedReader(new FileReader("graph.txt"));
			} catch (IOException exception) {
				System.out.println("Problem trying to load graph from file");
			}
		}
	}*/
	
	/** Listener for SaveGraph button */
	private class SaveGraphListener implements ActionListener {
		/** Event handler for SaveGraph button */
		public void actionPerformed(ActionEvent e) {
			try {
				PrintWriter out = new PrintWriter(new FileWriter("graph.txt"));
				//out.print(canvas.graph.print());
				for (Graph<Point,Integer>.Node node: canvas.graph.nodeList) {
					out.print(node.getData()+" ");
				}
				for (Graph<Point,Integer>.Edge edge: canvas.graph.edgeList) {
					out.print(edge.getData()+" ");
				}
				out.close();
			} catch (IOException exception) {
				System.out.println("Problem trying to write output into graph.txt");
				System.exit(-1);
			}
		}
	}
	
	/** Mouse listener for GraphCanvas element */
	private class PointMouseListener extends MouseAdapter
	implements MouseMotionListener {
		/** Responds to click event depending on mode */
		@SuppressWarnings("unchecked")
		public void mouseClicked(MouseEvent e) {
			switch (mode) {
			case ADD_NODES:
				if (findNearbyNode(e.getX(), e.getY()) == null) {
					Point newNode = new Point(e.getX(), e.getY());
					canvas.graph.addNode(newNode);
				} else {
					Toolkit.getDefaultToolkit().beep();
				}
				break;
			case RMV_NODES:
				if (findNearbyNode(e.getX(), e.getY()) != null) {
					canvas.graph.removeNode(findNearbyNode(e.getX(),e.getY()));
				} else {
					Toolkit.getDefaultToolkit().beep();
				}
				break;
			case ADD_EDGES:
				if (findNearbyNode(e.getX(), e.getY()) == null) {
					Toolkit.getDefaultToolkit().beep();
				}
				break;
			case RMV_EDGES:
				if (findNearbyNode(e.getX(), e.getY()) == null) {
					Toolkit.getDefaultToolkit().beep();
				}
				break;
			case BFT:
				if (findNearbyNode(e.getX(), e.getY()) != null) {
					System.out.println("Nodes accessible using breadth-first traversal: ");
					printBFT(BFT(findNearbyNode(e.getX(), e.getY())));
				} else {
					Toolkit.getDefaultToolkit().beep();
				}
				break;
			case DFT:
				if (findNearbyNode(e.getX(), e.getY()) != null) {
					System.out.println("Nodes accessible using depth-first traversal: ");
					printBFT(BFT(findNearbyNode(e.getX(), e.getY())));
				} else {
					Toolkit.getDefaultToolkit().beep();
				}
				break;
			}
			canvas.repaint();
		}
		
		/** Records point under mousedown event in anticipation of possible drag */
		public void mousePressed(MouseEvent e) {
			nodeUnderMouse = findNearbyNode(e.getX(), e.getY());
		}
		
		/** Responds to mouseup event */
		public void mouseReleased(MouseEvent e) {
			if (nodeUnderMouse != null) {
				nodeUnderMouse = null;
			}
		}
		
		/** Responds to mouse drag event */
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public void mouseDragged(MouseEvent e) {
			// Moving nodes
			if (mode == InputMode.ADD_NODES && nodeUnderMouse != null) {
				Point node = new Point(e.getX(), e.getY());
				nodeUnderMouse.setData(node);
				canvas.repaint();
			}
			// Adding edges
			if (mode == InputMode.ADD_EDGES && nodeUnderMouse != null) {
				if (headUnderMouse == null) {
					headUnderMouse = nodeUnderMouse;
				} else {
					Graph.Node tail = findNearbyNode(e.getX(), e.getY());
					canvas.graph.addEdge(null, headUnderMouse, tail);
					headUnderMouse = null;
				}
				canvas.repaint();
			}
			// Remove edges
			if (mode == InputMode.RMV_EDGES && nodeUnderMouse != null) {
				if (headUnderMouse == null) {
					headUnderMouse = nodeUnderMouse;
				} else {
					Graph.Node tail = findNearbyNode(e.getX(), e.getY());
					canvas.graph.removeEdge(headUnderMouse.edgeTo(tail));
					headUnderMouse = null;
				}
			}
		}
		
		// Empty but necessary to comply with MouseMotionListener interface
		public void mouseMoved(MouseEvent e) {
			
		}
	}
}
