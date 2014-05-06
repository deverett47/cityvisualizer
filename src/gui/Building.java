package gui;

import gfx.Rectangle;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Stack;

import javax.imageio.ImageIO;

public class Building {

	private Stack<Rectangle> _floors, _dirt;
	private int _maxFloors, _currFloors, _currDirt;
	private MainPanel _mp;
	private int _baseX, _baseY;
	private int _currY, _currDirtY;
	private boolean _finished = false;
	private DrawingPanel _dp;
	private Stack<BufferedImage> _floorsNew;
	private BufferedImage _roof;
	private int _numFloors = 0;
	
	public Building(int baseX, DrawingPanel _dp) {
		//_mp = mp;
		_maxFloors = (new Random()).nextInt(40) + 11;
		_baseY = 400;
		_baseX = baseX;
		_currY = _baseY;
		_currDirtY = _baseY;
		_floors = new Stack<Rectangle>();
		_dirt = new Stack<Rectangle>();
		
		_floorsNew = new Stack<BufferedImage>();
	}
	
	
	public void repaint(Graphics2D g) {
		for (Rectangle r: _floors) {
			r.paint(g);
		}
		
		for (Rectangle r: _dirt) {
			r.paint(g);
		}
		
		int height = _baseY - 15;

		for (BufferedImage b: _floorsNew) {
			g.drawImage(b, 0, height, null);
			height -= 15;
		}
		
		if (_roof != null) {
			g.drawImage(_roof, 0, 18, null);
		}
		
	}
	
	
	public void insert(String s) {
		if (s.equals("neutral")) {
			if (_numFloors <= 17) {
				this.addFloorNew();
			}
		}
		
		else if (s.equals("positive")) {
			if (_numFloors <= 17) {
				this.addFloorNew();
				this.addFloorNew();
			}
		}
		
		else if (s.equals("negative")) {
			this.addDirt();
			this.addDirt();
		}
	}
	
	//101x17
	private void addFloor() {
		Rectangle curr = new Rectangle(_dp);
		curr.setLocation(_baseX, _currY -10);
		_currY -= 10;
		curr.setColor(Color.BLACK);
		curr.setSize(40,10);
		curr.setVisible(true);
		_floors.add(curr);
		_numFloors++;
	}
	
	private void addFloorNew() {
		try {
			if (_numFloors < 17) {
				int rand = new Random().nextInt(5) +1;
				System.out.println(rand);
				BufferedImage newImage = ImageIO.read(new File("building1/floor"+rand+".png"));
				_floorsNew.add(newImage);
				_numFloors++;
			}
			
			else {
				System.out.println("roof!!!!!!!!");
				_roof = ImageIO.read(new File("building1/roof.png"));
				_numFloors++;
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
