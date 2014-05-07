package Buildings;

import java.awt.Graphics2D;

public interface Building {

	void insert(String polarity);

	void repaint(Graphics2D brush);

	int getWidth();
	
	boolean getFloorsFinished();
	
	boolean getDirtFinished();

}
