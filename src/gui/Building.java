package gui;

import gfx.Rectangle;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;
import java.util.Stack;

public class Building {

	private Stack<Rectangle> _floors, _dirt;
	private int _maxFloors, _currFloors, _currDirt;
	private MainPanel _mp;
	private int _baseX, _baseY;
	private int _currY, _currDirtY;
	private boolean _finished = false;
	private DrawingPanel _dp;
	
	public Building(int baseX, DrawingPanel _dp) {
		//_mp = mp;
		_maxFloors = (new Random()).nextInt(40) + 11;
		_baseY = 400;
		_baseX = baseX;
		_currY = _baseY;
		_currDirtY = _baseY;
		_floors = new Stack<Rectangle>();
		_dirt = new Stack<Rectangle>();
	}
	
	
	public void repaint(Graphics2D g) {
		for (Rectangle r: _floors) {
			r.paint(g);
		}
		
		for (Rectangle r: _dirt) {
			r.paint(g);
		}
	}
	
	
	public void insert(String s) {
		if (s.equals("neutral")) {
			this.addFloor();
		}
		
		else if (s.equals("positive")) {
			this.addFloor();
			this.addFloor();
		}
		
		else if (s.equals("negative")) {
			this.addDirt();
			this.addDirt();
		}
	}
	
	
	private void addFloor() {
		Rectangle curr = new Rectangle(_dp);
		curr.setLocation(_baseX, _currY -10);
		_currY -= 10;
		curr.setColor(Color.BLACK);
		curr.setSize(40,10);
		curr.setVisible(true);
		_floors.add(curr);
	}
	
	private void addDirt() {
		Rectangle curr = new Rectangle(_dp);
		curr.setLocation(_baseX, _currDirtY);
		_currDirtY += 10;
		curr.setColor(new Color(78, 62, 28));
		curr.setSize(40,10);
		curr.setVisible(true);
		_floors.add(curr);
	}
	
}
