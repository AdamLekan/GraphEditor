import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JColorChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

public class GraphPanel extends JPanel implements MouseListener, MouseMotionListener, KeyListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	Graph graph;
	public int cursorX, cursorY ;
	public boolean mouseButtonLeft=false, mouseButtonRight=false;
	private Node nodeUnderCursor = null ,currentNode =null;
	private Edge edgeUnderCursor = null;
	public int mouseCursor = Cursor.DEFAULT_CURSOR;

	
	public GraphPanel() {
		
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
		setFocusable(true);
		requestFocus();
	}
	
	public Node findNode(int x,int y) {
		
		for(Node node: graph.getNodes()) {
			if(node.isMouseOver(x, y)) {
				return node;
			}
		}
		return null;
	}
	public Node findNode(MouseEvent e){
		return findNode(e.getX(), e.getY());
	}
	
	public Edge findEdge(int x,int y) {
		
		for(Edge edge: graph.getEdges()) {
			if(edge.isMouseOver(x, y)) {
				return edge;
			}
		} 
		return null;
	}
	public Edge findEdge(MouseEvent e){
		return findEdge(e.getX(), e.getY());
	}
	public Graph getGraph() {
		return graph;
	}
	public void setGraph(Graph graph) {
		this.graph = graph;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (graph==null || graph.equals(null)) 
			return;
		graph.draw(g);
	}
	public void moveNode(int x, int y,Node node) {
		node.setX(node.getX()+x);
		node.setY(node.getY()+y);
	}
	private void moveAllNodes(int x, int y) {
		for (Node node : graph.getNodes()) {
			moveNode(x, y, node);
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {
		

		if (nodeUnderCursor!=null){
		switch (e.getKeyChar()) {
		case 'r':
			nodeUnderCursor.setColor(Color.RED);
			break;
		case 'g':
			nodeUnderCursor.setColor(Color.GREEN);
			break;
		case 'b':
			nodeUnderCursor.setColor(Color.BLUE);
			break;
		case '+':
			int r = nodeUnderCursor.getR()+10;
			nodeUnderCursor.setR(r);
			break;
		case '-':
			r = nodeUnderCursor.getR()-10;
			if (r>=10) nodeUnderCursor.setR(r);
			break;
		}
		repaint();
		setMouseCursor();
		}		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		 
			switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				moveAllNodes(-20, 0);
				break;
			case KeyEvent.VK_RIGHT:
				moveAllNodes(20, 0);
				break;
			case KeyEvent.VK_UP:
				moveAllNodes(0, -20);
				break;
			case KeyEvent.VK_DOWN:
				moveAllNodes(0, 20);
				break;
			case KeyEvent.VK_DELETE:
				if (nodeUnderCursor != null) {
					graph.removeNode(nodeUnderCursor);
					nodeUnderCursor = null;
				}
				break;
			}
		
		repaint();
		setMouseCursor();		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void setMouseCursor(MouseEvent e) {
		
		nodeUnderCursor = findNode(e);
		edgeUnderCursor = findEdge(e);

		if (nodeUnderCursor != null || edgeUnderCursor !=null ) {
			mouseCursor = Cursor.HAND_CURSOR;
		} 
		else if (mouseButtonLeft) {
			mouseCursor = Cursor.MOVE_CURSOR;
		} 
		else{
			mouseCursor = Cursor.DEFAULT_CURSOR;
		}
	
	
		setCursor(Cursor.getPredefinedCursor(mouseCursor));
		cursorX = e.getX();
		cursorY = e.getY();
	}
	public void setMouseCursor() {
		
		nodeUnderCursor = findNode(cursorX, cursorY);
		edgeUnderCursor = findEdge(cursorX, cursorY);
		
		if (nodeUnderCursor != null || edgeUnderCursor !=null) {
			mouseCursor = Cursor.HAND_CURSOR;
		} 
		else if (mouseButtonLeft) {
			mouseCursor = Cursor.MOVE_CURSOR;
		}
		else{
			mouseCursor = Cursor.DEFAULT_CURSOR;
		}
		
		setCursor(Cursor.getPredefinedCursor(mouseCursor));
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		if(mouseButtonLeft) {
			if(nodeUnderCursor!=null) {
				moveNode(e.getX()- cursorX ,e.getY()- cursorY, nodeUnderCursor );
			}
			else
				moveAllNodes(e.getX()- cursorX ,e.getY()- cursorY);
		}	

		cursorX = e.getX();
		cursorY = e.getY();
		repaint();
	}
	@Override
	public void mouseMoved(MouseEvent e) {

		setMouseCursor(e);
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		
		if (e.getButton() == 3 && nodeUnderCursor != null) {
			createPopupMenu(e, nodeUnderCursor);
		} else if (e.getButton() == 3 && edgeUnderCursor != null) {
			createPopupMenu(e, edgeUnderCursor);
		}else if (e.getButton() == 3 && edgeUnderCursor == null && nodeUnderCursor == null)
		{
			createPopupMenu(e);
		}

		setMouseCursor(e);
	}	
	@Override
	public void mousePressed(MouseEvent e) {
		
		if(e.getButton()==1) {
			mouseButtonLeft = true;
		}
		if(e.getButton()==3) {
			mouseButtonRight = true;
			 if(nodeUnderCursor!=null) {
				 currentNode=nodeUnderCursor;}
		}
		
		setMouseCursor(e);
	}
	@Override
	public void mouseReleased(MouseEvent e) {

		if(e.getButton()==1) {
			mouseButtonLeft = false;
		}
		if(e.getButton()==3) {
			mouseButtonRight = false;
		}
		if(e.getButton()==3 && currentNode!= null ) {
			if(findNode(e)!=null) {
			graph.addEdge(new Edge(currentNode,findNode(e),Color.BLACK ));
			}
			currentNode=null;
			repaint();
		}
		setMouseCursor(e);	
		}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void createPopupMenu(MouseEvent e) {
 
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem menuItem = new JMenuItem("Dodaj węzeł");
		menuItem.addActionListener((action) -> {
			
			graph.addNode(new Node(e.getX(), e.getY()));
			repaint();
		});
	
		popupMenu.add(menuItem);
		popupMenu.show(e.getComponent(), e.getX(), e.getY());
    }
	public void createPopupMenu(MouseEvent e, Node node) {
		
		JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem menuItem= new JMenuItem("Zmień kolor");
		menuItem.addActionListener((a) -> {
			
			Color newColor = JColorChooser.showDialog(this,"Wybierz kolor", node.getColor());
			if (newColor!=null){
				node.setColor(newColor);
			}
			repaint();
		});
		
		popupMenu.add(menuItem);
		menuItem = new JMenuItem("Usuń węzeł");
		menuItem.addActionListener((action) -> {
			
			graph.removeNode(node);
			repaint();
		});
		
		popupMenu.add(menuItem);
		menuItem = new JMenuItem("Zmień nazwę węzła");
		menuItem.addActionListener((action) -> {
			
			String newName = JOptionPane.showInputDialog(this,"Podaj nową nazwe:");
			node.setName(newName);
			repaint();
		});
		
		popupMenu.add(menuItem);
		popupMenu.show(e.getComponent(), e.getX(), e.getY());
	}
public void createPopupMenu(MouseEvent e, Edge edge) {
		
		JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem menuItem= new JMenuItem("Zmień kolor");
		menuItem.addActionListener((a) -> {
			
			Color newColor = JColorChooser.showDialog(this,"Wybierz kolor", edge.getColor());
			if (newColor!=null){
				edge.setColor(newColor);
			}
			repaint();
		});
		
		popupMenu.add(menuItem);
		menuItem = new JMenuItem("Usuń krawędz");
		menuItem.addActionListener((action) -> {
			
			graph.removeEdge(edge);
			repaint();
		});
		
		popupMenu.add(menuItem);
		popupMenu.show(e.getComponent(), e.getX(), e.getY());
	}
}
