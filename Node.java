import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

public class Node implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int x,y,r;
	private Color color;
	String name;
	
	public Node(int x, int y) {
		
		this.x = x;
		this.y = y;
		this.r = 10;
		this.color = Color.DARK_GRAY;
	} 
	public Node(int x, int y, int r, Color color) {
		
		this.x = x;
		this.y = y;
		this.r = r;
		this.color = color;
	} 
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getR() {
		return r;
	}
	public void setR(int r) {
		this.r = r;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	 
	@Override
	public String toString() {
		return "Node [nazwa=" + name + ", x=(" + x + ", y=" + y + "), color=" + color+ "]";
	}
	void draw(Graphics g) {
		g.setColor(color);
		g.fillOval(x-r, y-r, 2*r, 2*r);
	}	
	public boolean isMouseOver(int mx, int my) {
		return (x-mx)*(x-mx)+(y-my)*(y-my)<=r*r;	
	}
	
}
