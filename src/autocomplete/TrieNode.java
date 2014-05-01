package autocomplete;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

/**
 * This is the most basic class, modeling the nodes that each letter of the words
 * inserted into the trie will be stored in. It has a boolean that lets it keep track
 * of whether it marks the end of a valid word, and it contains a hashtable _children that
 * lets it keep track of all the nodes descending from it.
 * @author deverett
 *
 */
public class TrieNode {

private String _letter;
private Hashtable<String, TrieNode> _children;
private boolean _isWordEnd;
private int _frequency;

	public TrieNode(String letter) {
		_letter = letter;
		_isWordEnd = false;
		_children = new Hashtable<String, TrieNode>();
		_frequency = 0; 
	}
	
	/**
	 * Given a boolean input, sets the _isWordEnd variable, which indicates whether or not
	 * this node (and its parents) form a word.
	 * @param b
	 */
	public void setWordEnd(boolean b) {
		_isWordEnd = b;
	}
	
	/**
	 * Returns the boolean _isWordEnd to help with insertion.
	 * @return boolean
	 */
	public boolean isWordEnd(){
		return _isWordEnd;
	}
	
	/**
	 * Returns the letter stored at this node as string.
	 * @return String
	 */
	public String getLetter() {
		return _letter;
	}
	
	/**
	 * Checks to see if this node has any children matching the input letter. 
	 * Returns true if the hashtable maps to something for the given key, false otherwise.
	 * @param letter
	 * @return boolean
	 */
	public boolean checkChildren(String letter) {
		if (_children.get(letter) == null) {
			return false;
		}
		
		else {
			return true;
		}
	}
	
	/**
	 * When called, maps the input letter to a new TrieNode.
	 * @param letter
	 */
	public void addChild(String letter) {
		_children.put(letter, new TrieNode(letter));
	}
	
	/**
	 * Returns the node that the children hashtable maps to for 
	 * request letter.
	 * @param letter
	 * @return TrieNode
	 */
	public TrieNode getChild(String letter) {
		return _children.get(letter);
	}
	
	/**
	 * Returns an iterator that allows you to iterate over this node's
	 * set of children.
	 * @return
	 */
	
	public Iterator<Entry<String, TrieNode>> getIterator(){
		Set<Entry<String, TrieNode>> childSet = _children.entrySet();
		return childSet.iterator();
	}
	
	/**
	 * Returns the int _frequency, which indicates how many times this node has 
	 * been visited as part of a word.
	 * @return int
	 */
	public int getFrequency() {
		return _frequency;
	}
	
	/**
	 * Increases the frequency value by 1.
	 */
	public void incrementFrequency() {
		_frequency++;
	}
	

}
