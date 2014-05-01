package gui;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import javax.swing.JTextField;

import org.apache.commons.lang3.StringUtils;

import autocomplete.Bigrams;
import autocomplete.Corrector;
import autocomplete.Ranker;
import autocomplete.SpaceFinder;
import autocomplete.Trie;
import autocomplete.TrieNode;


public class SearchField extends JTextField {

	private MainPanel _mp;
	private Corrector _corrector;
	private Trie _trie;
	private TrieNode _root;
	private Bigrams _bigrams;
	
	public SearchField(MainPanel mp) {
		super();
		_mp = mp;
		
		_trie = new Trie();
		_root = _trie.getRoot();
		SpaceFinder spaceFinder = new SpaceFinder(_trie);
		_bigrams = new Bigrams();
		Ranker ranker = new Ranker();
		
		ranker.turnOnBigram();
		ranker.turnOnPrefix();
		//ranker.turnOnWhite();
		ranker.setTrie(_trie);
		ranker.setBigrams(_bigrams);
		ranker.setSpaceFinder(spaceFinder);
		
		_corrector = new Corrector(_trie, _bigrams, ranker, spaceFinder);
		
		this.addKeyListener(new Listener());
		this.setSelectionColor(new Color(85, 172, 238, 160));
	}
	
	
	public void search() {
		this.addToDictionary(this.getText());
		_mp.search();
	}
	
	public void getSuggestions() {
		String text = this.getText();
		String[] textSplit = text.split(" ");
		String curr = textSplit[textSplit.length-1];
		
		if (curr.length() > 1) {
			List<String> suggs = _corrector.getSuggestions(curr);
			if (suggs.size() > 0) {
				String top = suggs.get(0);
				
				String diff = StringUtils.difference(curr, top);				
				String result = curr + diff;
				
				this.setText(result);
				
				this.setSelectionEnd(result.length());
				this.setSelectionStart(result.length() - diff.length());

				//this.setCaretPosition(result.length() - diff.length());

			}
		}
		
	}

	private void addToDictionary(String s) {
		_trie.insert(_root, s);
		
		String[] split = s.split(" ");
		if (split.length > 1) {
			_bigrams.insert(split[split.length-2], split[split.length-1]);
		}	
	}
	
		

	
	
	private class Listener implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {

		}

		@Override
		public void keyPressed(KeyEvent e) {

		}

		@Override
		public void keyReleased(KeyEvent e) {
			int type = Character.getType(e.getKeyChar());
			if (e.getKeyChar() == KeyEvent.VK_ENTER) {
				SearchField.this.search();
			}	
			
			else if (type == 2 || type == 9){
				SearchField.this.getSuggestions();
			}
		}
		
	}

}
