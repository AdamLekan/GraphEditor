import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Graph implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Node> nodes ;
	private List<Edge> edges ;

	
	public Graph() {
		this.nodes= new ArrayList<Node>();
		this.edges= new ArrayList<Edge>();

	}
	public void addNode(Node node) {
		
		nodes.add(node);
		
	}public void addEdge(Edge edge) {
		
		edges.add(edge);
		
	}
	public void removeNode(Node node) {
		
		for(Edge edge : edges) {
			if(edge.nodeA ==node ||edge.nodeB ==node )
				removeEdge(edge);
		}
		
		nodes.remove(node);
		
	}
	public void removeEdge(Edge edge) {
		
		edges.remove(edge);
		
	}
	public Node [] getNodes() {
		
		Node [] array = nodes.toArray(new Node[0]);
		
		return array ;
		
	}
public Edge [] getEdges() {
		
		Edge [] array = edges.toArray(new Edge[0]);
		
		return array ;
		
	}
	public void draw(Graphics g) {
		
		for(Node node : nodes) {
			node.draw(g);
		}
		for(Edge edge : edges) {
			edge.draw(g);
		}
				
	}	
	
	
	

}