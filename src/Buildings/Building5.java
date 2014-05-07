package Buildings;

import gfx.Rectangle;
import gui.DrawingPanel;
import gui.MainPanel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Stack;

import javax.imageio.ImageIO;

public class Building5 implements Building {

	private Stack<Rectangle> _floors, _dirt;
	private int _maxFloors, _currFloors, _currDirt;
	private MainPanel _mp;
	private int _baseX, _baseY;
	private int _currY, _currDirtY;
	private boolean _finished = false;
	private DrawingPanel _dp;
	private Stack<BufferedImage> _floorsNew, _dirtNew;
	private BufferedImage _roof, _dirtRoof;
	private int _numFloors = 0;
	private int _numDirt = 0;
	
	public Building5(int baseX, DrawingPanel _dp) {
		//_mp = mp;
		_maxFloors = (new Random()).nextInt(40) + 11;
		_baseY = 400;
		_baseX = baseX;
		_currY = _baseY;
		_currDirtY = _baseY;
		_floors = new Stack<Rectangle>();
		_dirt = new Stack<Rectangle>();
		
		_floorsNew = new Stack<BufferedImage>();
		_dirtNew = new Stack<BufferedImage>();
	}
	
	
	public void repaint(Graphics2D g) {
		
		int height = _baseY - 24;

		for (BufferedImage b: _floorsNew) {
			g.drawImage(b, _baseX, height, null);
			height -= 24;
		}
		
		if (_roof != null) {
			g.drawImage(_roof, _baseX, 40, null);
		}

		int depth = _baseY;
		for (BufferedImage b: _dirtNew) {
			g.drawImage(b, _baseX, depth, null);
			depth += 24;
		}
		
		if (_dirtRoof != null) {
			g.drawImage(_dirtRoof, _baseX, 688, null);
		}
		
	}
	
	
	public void insert(String s) {
		if (s.equals("neutral")) {
			if (_numFloors <= 12) {
				this.addFloorNew();
			}
		}
		
		else if (s.equals("positive")) {
			if (_numFloors <= 12) {
				this.addFloorNew();
				this.addFloorNew();
			}
		}
		
		else if (s.equals("negative")) {
		if (_numDirt <= 12) {
			this.addDirtNew();
			this.addDirtNew();
		}
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
			if (_numFloors < 13) {
				int rand = new Random().nextInt(5) +1;
				System.out.println(rand);
				BufferedImage newImage = ImageIO.read(new File("building5/floor"+rand+".png"));
				_floorsNew.add(newImage);
				_numFloors++;
			}
			
			else {
				System.out.println("roof!!!!!!!!");
				_roof = ImageIO.read(new File("building5/roof.png"));
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
	
	private void addDirtNew() {
		try {
			if (_numDirt < 13) {
				int rand = new Random().nextInt(5) +1;
				System.out.println(rand);
				BufferedImage newImage = ImageIO.read(new File("building5/dirt"+rand+".png"));
				_dirtNew.add(newImage);
				_numDirt++;
			}
			
			else {
				System.out.println("dirt roof!!!!!!!!");
				_dirtRoof = ImageIO.read(new File("building5/dirtroof.png"));
				_numDirt++;
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



}
