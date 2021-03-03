import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class GraphEditor extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String AUTHOR = "Autor: Adam Lekan\nData: styczen 2020";
	public static final String INSTRUCTION =
			"                  O P I S   P R O G R A M U \n\n" + 
	        "Aktywna klawisze:\n" +
			"   strzałki ==> przesuwanie wszystkich kół\n" +
			"ponadto, gdy kursor wskazuje koło:\n" +
			"   DEL   ==> kasowanie koła\n" +
			"   +, -   ==> powiększanie, pomniejszanie koła\n" +
			"   r,g,b ==> zmiana koloru koła\n\n" +
			"Operacje myszka:\n" +
			"   przeciąganie ==> przesuwanie wszystkich kół\n" +
			"   PPM ==> tworzenie nowego koła w niejscu kursora\n" +
	        "ponadto gdy kursor wskazuje koło:\n" +
	        "   przeciąganie ==> przesuwanie koła\n"
	        + "przeciąganie + PPM na inne koło==> tworzenie krawędzi  " +
			"   PPM ==> zmiana koloru koła\n" +
	        "   zmiane nazwy lub usuwanie koła\n";
	
	JMenuBar menuBar = new JMenuBar();
	JMenu menuFile = new JMenu("Plik");
	JMenu menuGraph = new JMenu("Graf");
	JMenu menuHelp = new JMenu("Pomoc");
	JMenuItem itemNew = new JMenuItem("Nowy");
	JMenuItem itemLoad = new JMenuItem("Wczytaj");
	JMenuItem itemSave = new JMenuItem("Zapisz");
	JMenuItem itemExample = new JMenuItem("Przykład");
	JMenuItem itemExit = new JMenuItem("Wyjdz");
	JMenuItem itemNodeList = new JMenuItem("Lista węzłów");
	JMenuItem itemEdgeList = new JMenuItem("Lista krwawędzi");
	JMenuItem itemAuthor = new JMenuItem("Autor");
	JMenuItem itemInstruction = new JMenuItem("Instrukcja");
	GraphPanel panel = new GraphPanel();
	
	
	public static void main(String[] args) {
		
		new GraphEditor();
		
	}

	public GraphEditor() {
		
		setTitle("Graficzny edytor grafów");
		setSize(500,500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		setJMenuBar(menuBar);
		menuBar.add(menuFile);
		menuBar.add(menuGraph);
		menuBar.add(menuHelp);
		 
		menuFile.add(itemNew);
		menuFile.add(itemLoad);
		menuFile.add(itemSave);
		menuFile.add(itemExample);
		menuFile.add(itemExit);
		 
		menuGraph.add(itemNodeList);
		menuGraph.add(itemEdgeList);
		 
		menuHelp.add(itemAuthor);
		menuHelp.add(itemInstruction);
	
		itemNew.addActionListener(this);
		itemLoad.addActionListener(this);
		itemSave.addActionListener(this);
		itemExample.addActionListener(this);
		itemExit.addActionListener(this);
		itemNodeList.addActionListener(this);
		itemEdgeList.addActionListener(this);
		itemAuthor.addActionListener(this);
		itemInstruction.addActionListener(this);
		 
		showExample();
		setContentPane(panel);
		setVisible(true);
	}
	private void showExample() {
		Graph graph = new Graph();


		Node node1 = new Node(100,100,20,Color.BLUE);
		Node node2 = new Node(150,150,10,Color.RED);
		Node node3 = new Node(222,333,15,Color.YELLOW);

		node1.setName("A");
		node2.setName("B");

		Edge edge1 = new Edge(node1,node2,Color.GREEN);
		Edge edge2 = new Edge(node3,node2,Color.BLACK);

		graph.addNode(node1);
		graph.addNode(node2);
		graph.addNode(node3);

		graph.addEdge(edge1);
		graph.addEdge(edge2);

		
		panel.setGraph(graph);	
	}

	
	public void showListOfNodes(Graph graph) {
		Node array[] = graph.getNodes();
		int i = 0;
		StringBuilder sb = new StringBuilder("Liczba węzłów: " + array.length + "\n");
		for (Node node : array) {
			sb.append(node + "    ");
			if (++i % 2 == 0)
				sb.append("\n");
		}
		JOptionPane.showMessageDialog(null, sb," Lista węzłów", JOptionPane.PLAIN_MESSAGE);
	}
	public void showListOfEdges(Graph graph) {
		Edge array[] = graph.getEdges();
		int i = 0;
		StringBuilder sb = new StringBuilder("Liczba krawędzi: " + array.length + "\n");
		for (Edge edge : array) {
			++i;
			sb.append(i+ ".");
			sb.append(edge + "\n");
		}
		JOptionPane.showMessageDialog(null, sb," Lista krawędzi", JOptionPane.PLAIN_MESSAGE);
	}
	public void saveToFile(Graph graph) {
			
		try {
			FileOutputStream fileOutput = new FileOutputStream("graph.bin");
			ObjectOutputStream output = new ObjectOutputStream(fileOutput);
			output.writeObject(graph);
			output.close(); 
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	public void loadFromFile() {
			  
		try {
			FileInputStream fileInput = new FileInputStream("graph.bin");
			try {		   
				ObjectInputStream input = new ObjectInputStream(fileInput);
			    Graph graph = (Graph) input.readObject();
			    input.close();
			    panel.setGraph(graph);

			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		  
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if(source==itemNew) {
			panel.setGraph(new Graph());
			repaint();
		}
		if(source==itemLoad) {
			loadFromFile();
			repaint();
		}
		if(source==itemSave) {
			saveToFile(panel.getGraph());
		}
		if(source==itemExample) {
			showExample();
			repaint();
		}
		if(source==itemExit) {
			System.exit(0);			
		}
		if(source==itemNodeList) {
			showListOfNodes(panel.getGraph());			
		}
		if(source==itemEdgeList) {
			showListOfEdges(panel.getGraph());
		}
		if(source==itemAuthor) {
			JOptionPane.showMessageDialog(null, AUTHOR,"Autor", JOptionPane.PLAIN_MESSAGE);
		}
		if(source==itemInstruction) {
			JOptionPane.showMessageDialog(null, INSTRUCTION,"Instrukcja", JOptionPane.PLAIN_MESSAGE);
			
		}
	}
}


