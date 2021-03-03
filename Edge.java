import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;


public class Edge implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Node nodeA,nodeB;
	Color color;
	
	public Edge(Node nodeA, Node nodeB, Color color) {
	
		this.nodeA = nodeA;
		this.nodeB = nodeB;
		this.color = color;
	}

	

	public Node getNodeA() {
		return nodeA;
	}

	public void setNodeA(Node nodeA) {
		this.nodeA = nodeA;
	}

	public Node getNodeB() {
		return nodeB;
	}

	public void setNodeB(Node nodeB) {
		this.nodeB = nodeB;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return "Edge [color=" + color + "]";
	}
	
	void draw(Graphics g) {
		
		g.setColor(color);
		g.drawLine(nodeA.getX(),nodeA.getY(),nodeB.getX(),nodeB.getY());
		
		
	}

	public boolean isMouseOver(int x, int y) {
		
	    if (x>=Math.min(nodeA.getX(), nodeB.getX())&& x<=Math.max(nodeA.getX(), nodeB.getX()) 
	   		&& y>Math.min(nodeA.getY(), nodeB.getY()) && y<=Math.max(nodeA.getY(), nodeB.getY())) 
	    	return true;
	    else
	    	return false;

	    }



	

}
