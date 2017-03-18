import java.awt.*;
import javax.swing.*;

/**
 * Implements a graphical canvas that displays nodes and edges.
 * @author Cathy Lee
 * @version CSC 212, 9 December 2016
 */
public class GraphCanvas extends JComponent {
	/** The graph */
	public Graph<Point, Integer> graph;
	
	/** The diameter of each node */
	public static final int DIAM = 30;
	
	/** Constructor */
	public GraphCanvas() {
		graph = new Graph<Point, Integer>();
	}
	
	/**
	 * Paints a blue circle ten pixels in diameter at each point,
	 * and a cyan line representing the edges that connect the points
	 * 
	 * @param g specifies the graphics object to draw with
	 */
	public void paintComponent(Graphics g) {
		g.setColor(Color.BLUE);
		for (int i = 0; i < graph.numNodes(); i++) {
			Point node = (Point)graph.getNode(i).getData();
			g.fillOval((int)node.getX(), (int)node.getY(), 10, 10);
		}
		g.setColor(Color.CYAN);
		for (int j = 0; j < graph.numEdges(); j++) {
			Point head = (Point)graph.getEdge(j).getHead().getData();
			Point tail = (Point)graph.getEdge(j).getTail().getData();
			g.drawLine((int)head.getX(), (int)head.getY(), (int)tail.getX(), (int)tail.getY());
		}
	}
	
	/**
     *  The component will look bad if it is sized smaller than this
     *
     *  @returns The minimum dimension
     */
    public Dimension getMinimumSize() {
        return new Dimension(500,3000);
    }

    /**
     *  The component will look best at this size
     *
     *  @returns The preferred dimension
     */
    public Dimension getPreferredSize() {
        return new Dimension(500,300);
    }
}
