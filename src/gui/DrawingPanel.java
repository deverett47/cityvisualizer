package gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import gfx.Rectangle;


public class DrawingPanel extends javax.swing.JPanel {

	private Building _b;
	private Rectangle _dirt;
	private ArrayList<Building> _buildings;


      public DrawingPanel() {
	    super();
	    java.awt.Dimension size = new java.awt.Dimension(800, 800);
	    this.setPreferredSize(size);
	    this.setSize(size);
	    this.setBackground(new java.awt.Color(181, 223, 255));
	    
	    _dirt = new Rectangle(this);
		_dirt.setLocation(0, 400);
		_dirt.setColor(new Color(131,104,52));
		_dirt.setSize(1200,400);
		_dirt.setVisible(true);
	    
	    
		
		_buildings = new ArrayList<Building>(20);
		for (int i = 0; i < 20; i++) {
			_buildings.add(i, new Building(i*40, this));
		}
		
		

	    _b = new Building(0, this);

      }

     


      public void paintComponent(java.awt.Graphics g) {
            super.paintComponent(g);
	    java.awt.Graphics2D brush = (java.awt.Graphics2D) g;
	    
	    _dirt.paint(brush);
	    
	    for (Building b: _buildings) {
	    	b.repaint(brush);
	    }
	    
      }




	public void sendUpdate(String polarity) {
		int randIndex = (new Random()).nextInt(20);
		Building curr = _buildings.get(randIndex);
		curr.insert(polarity);
		this.repaint();
	}


}