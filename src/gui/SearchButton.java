package gui;

import javax.swing.JButton;


public class SearchButton extends JButton {

	private MainPanel _mp;
	
	public SearchButton(MainPanel mp) {
		super("Search");
		_mp = mp;
		this.setSize(10,10);
		this.addActionListener(new QuitListener());
	}

	/** 
	 * Creates a private inner action listener class to perform
     * the correct action when the button is clicked 
     */

	private class QuitListener implements java.awt.event.ActionListener{
		
		 public void actionPerformed(java.awt.event.ActionEvent e) {
			  _mp.search();
		}
	}
	
	private static final long serialVersionUID = 647647647;
}
