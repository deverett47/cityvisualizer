package gui;

import javax.swing.JFrame;

public class GUI extends JFrame {
	
	public GUI() {
		super("City Visualizer");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.add(new MainPanel());
		this.setVisible(true);
		this.pack();	
	}
}
