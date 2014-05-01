package autocomplete;

import java.util.Hashtable;

/**
 * This class is used to help keep track of the bigrams in the input text files.
 * It consists of a hashtable that takes in entries of type <String, Hashtable<String, Integer>>.
 * The first string is the "before" word, and then it maps to its own hashtable, which
 * stores the "after" words as strings that map to their frequency relative to the "before"
 * word.
 * @author deverett
 *
 */
public class Bigrams {

private Hashtable<String, Hashtable<String, Integer>> _bigrams;

	public Bigrams() {
		_bigrams = new Hashtable<String, Hashtable<String, Integer>>();
	}
	
	/**
	 * Given a before/after pair, inserts the strings into the structure.
	 * @param curr
	 * @param next
	 */
	public void insert(String curr, String next) {
		/* If curr is not yet in the _bigrams hashtable at all, it will 
		 * have to be added, along with its own hashtable to keep its 
		 * adjacent words.
		 */
		if (_bigrams.get(curr) == null) {
			Hashtable<String, Integer> newTable = new Hashtable<String, Integer>();
			newTable.put(next, 1);
			_bigrams.put(curr, newTable);
		}
		
		/*
		 * If curr is already in _bigrams, and the word after it is already
		 * part of curr's hashtable, simply increment the frequency of this word pairing.
		 */
		else if (this.getFrequency(curr, next) != 0) {
			this.incrementFrequency(curr, next);
		}
		
		/*
		 * The case where curr is already in the _bigrams hashtable, but next is 
		 * not, so curr's hashtable is accessed and next is inserted with frequency 1.
		 */
		else {
			Hashtable<String, Integer> currsTable = _bigrams.get(curr);
			currsTable.put(next, 1);
			_bigrams.put(curr, currsTable);
			
		}
		
		
	}
	
	/**
	 * Returns the frequency count of this before-after pairing (indicating how many times
	 * the second word appears after the first in the text).
	 * @param curr
	 * @param next
	 * @return
	 */
	public int getFrequency(String curr, String next) {
		if (_bigrams.get(curr) == null || _bigrams.get(curr).get(next) == null) {
			return 0;
		}
		else {
			return _bigrams.get(curr).get(next);
		}
	}
	
	/**
	 * Increments the frequency count of this before-after pairing.
	 * @param curr
	 * @param next
	 */
	private void incrementFrequency(String curr, String next) {
		_bigrams.get(curr).put(next, _bigrams.get(curr).get(next) +1);
	}
}
