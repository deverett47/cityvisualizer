package gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import Buildings.Building;
import Buildings.Building1;
import Buildings.Building2;
import Buildings.Building3;
import Buildings.Building4;
import Buildings.Building5;
import Buildings.Building6;
import Buildings.Building7;

import gfx.Rectangle;


public class DrawingPanel extends javax.swing.JPanel {

	private Building1 _b;
	private Rectangle _dirt;
	private ArrayList<Building> _buildings;


      public DrawingPanel() {
	    super();
	    java.awt.Dimension size = new java.awt.Dimension(800, 800);
	    this.setPreferredSize(size);
	    this.setSize(size);
	    this.setBackground(new java.awt.Color(181, 223, 255));
	    this.setFocusable(false);
	    
	    _dirt = new Rectangle(this);
		_dirt.setLocation(0, 400);
		_dirt.setColor(new Color(131,104,52));
		_dirt.setSize(800,400);
		_dirt.setVisible(true);
	    
	    
		
//		_buildings = new ArrayList<Building>(20);
//		for (int i = 0; i < 20; i++) {
//			_buildings.add(i, new Building(i*40, this));
//		}
		
		_buildings = new ArrayList<Building>(1);
		for (int i = 0; i < 2; i++) {
			_buildings.add(i, new Building2(i*60, this));
		}
		
		

	    _b = new Building1(0, this);

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
		int randIndex = (new Random()).nextInt(2);
//		Building curr = _buildings.get(randIndex);
		Building curr = _buildings.get(randIndex);
		curr.insert(polarity);
		this.repaint();
	}


}