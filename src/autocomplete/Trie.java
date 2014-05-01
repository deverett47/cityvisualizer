package autocomplete;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 * This is my implementation of a trie. Most of its methods are self-explanatory or have been 
 * explained where I felt necessary. I kept the prefix-matching method here so that it would have
 * easy access to the trie. I also put my LED calculating method here, although in retrospect it
 * really wasn't necessary; I could've just put in the Ranker class.
 * @author deverett
 *
 */
public class Trie {

private TrieNode _root;
private ArrayList<String> _suggestions;

	public Trie() {
		_root = new TrieNode("");
		
	}
	
	/**
	 * Returns the root node of the trie.
	 * @return
	 */
	public TrieNode getRoot() {
		return _root;
	}
	
	
	/**
	 * Inserts a word into the trie letter-by-letter.
	 * @param node
	 * @param word
	 */
	public void insert(TrieNode node, String word) {
		word = word.toLowerCase();
		
		if (word.length() == 0) { //if this is the end of the word
			node.setWordEnd(true);		
			node.incrementFrequency(); 
		}
		
		/*
		 * If you have not reached the end of the word, access the first character of the
		 * string. If the current node does not already have this character as a child, 
		 * add it as child. Then instantiate an updated string that no longer has the first
		 * character, and recursively call insert to finish inserting the rest of the string.
		 */
		else {
			
			String first = word.charAt(0) + ""; // access first character and convert to string
			if (node.checkChildren(first) == false) {
				node.addChild(first);				
			}
			
			String updatedWord = word.substring(1); // removes the first character
			this.insert(node.getChild(first), updatedWord); 
			
		}
		
	}
	
	/**
	 * Given a node of the trie and a word to search for, it returns the final node
	 * of the word in the trie, or null if the word is not in the trie. When called by any
	 * other class, it should be passed a reference to the root node; after that, the
	 * function calls itself recursively and passes a reference to the last node it checked. 
	 * @param node
	 * @param word
	 * @return
	 */
	public TrieNode find(TrieNode node, String word) {
		if (word.length() == 0) { // if you've reached the end of the word
			return node;
		}
		
		else {
			String first = word.charAt(0) + ""; // access first character and convert to string
			if (node.checkChildren(first) == false) { // if this simply doesn't exist in the trie
				return null; 
			}
			
			else {
				String updatedWord = word.substring(1);
				return find(node.getChild(first), updatedWord);
			}
		}
	}
	
	/**
	 * Given two strings, computes and returns the Levenshtein edit distance.
	 * @param word1
	 * @param word2
	 * @return
	 */
	public int levenshteinDist(String word1, String word2) {
	    
		if (word1 == word2) {
			return 0;
		}
	    
	    else {
	        word1 = " " + word1;
	        word2 = " " + word2;
	        
	        int numRows = word1.length();
	        int numCols = word2.length();

	        
	        // initalizes the table that will be used to compute the distance
	        int[][] T = new int[numRows][numCols];
	        for (int r = 0; r < numRows; r++){ 
	            T[r][0] = r;     
	        }
	        for (int c = 0; c < numCols; c++) {
	            T[0][c] = c;
	        }
	        
	        /*
	         * Loops through the table, updating each cell according to the
	         * rules of Levenshtein edit distances. When the loop finishes, the 
	         * function returns the lower-right corner of the table, which
	         * will be the final edit distance tally.
	         */
	        for (int r = 1; r < numRows; r++) {
	            for (int c = 1; c < numCols; c++) {
	            	int differential = 0;
	            	if (word1.charAt(r) != word2.charAt(c)) {
	            		differential = 1;
	            	}

            		int tempMin = Math.min(T[r-1][c] + 1, T[r][c-1] + 1);
            		int realMin = Math.min(tempMin, T[r-1][c-1] + differential);
            		T[r][c] = realMin;
	            }
	        }
	        return T[numRows-1][numCols-1];
	    }
	}
	
	
	
	/**
	 * Given a prefix (wordSoFar), first checks to see if this prefix itself is a word according
	 * to the trie. Then recursively calls getPrefixMatches(...) on each of the prefix's children
	 * to generate a full list of suggestions.
	 * @param wordSoFar
	 * @return
	 */
	public ArrayList<String> getPrefixMatches(String wordSoFar) {
		ArrayList<String> suggestions = new ArrayList<String>();
		TrieNode curr = this.find(_root, wordSoFar); // finds the deepest node of the prefix
		
		// add this prefix to the suggestions list if it is already a word
		if (curr != null && curr.isWordEnd() == true) {
			suggestions.add(wordSoFar);
		}
		
		// gets an iterator for all the children of curr, iterates over them to generate more suggestions
		if (curr != null) {
			Iterator<Entry<String, TrieNode>> it = curr.getIterator();
			while (it.hasNext()) {
				String nextLetter = it.next().getValue().getLetter();
				ArrayList<String> childSuggestions = this.getPrefixMatches((wordSoFar + nextLetter));
				suggestions.addAll(childSuggestions);
			}
		}
		
		return suggestions;
	}
	
	
	/**
	 * Given what has been typed so far, finds suggestions in the trie that could match
	 * the wordSoFar with the specified number of maximum edits. Also returns
	 * wordSoFar as a suggestion if it is already a word.
	 * @param wordSoFar
	 * @param dist
	 * @return
	 */
	public ArrayList<String> getLEDMatches(String wordSoFar, int dist) {
		ArrayList<String> suggestions = new ArrayList<String>();
		
		// adds wordSoFar to the suggestion list if it is a valid word, according to the trie
		if (this.find(_root, wordSoFar) != null && this.find(_root, wordSoFar).isWordEnd()) {
			suggestions.add(wordSoFar);
		}
		
		Iterator<Entry<String, TrieNode>> it = _root.getIterator(); // gets an iterator for the root's children
		while(it.hasNext()) {
			String nextLetter = it.next().getValue().getLetter();
			ArrayList<String> childSuggs = this.recursiveLEDMatches(wordSoFar, nextLetter, dist);
			suggestions.addAll(childSuggs);
		}
		
		return suggestions;
	}
	
	/**
	 * Called by getLEDMatches to recursively find matches that are within the specified
	 * LED. I broke this off from the original function so that I could pass an original word
	 * reference and calculate the edit distance off of that, instead of having to deal with a sort
	 * of relative edit distance that I would otherwise need.
	 * @param original
	 * @return
	 */
	private ArrayList<String> recursiveLEDMatches(String original, String wordSoFar, int dist) {
		ArrayList<String> suggestions = new ArrayList<String>();
		TrieNode curr = this.find(_root, wordSoFar);

		if (curr != null) {
			// adds wordSoFar to the suggestion list if it's a valid word and is close enough to original word
			if (curr.isWordEnd() && this.levenshteinDist(original, wordSoFar) <= dist) {
				suggestions.add(wordSoFar);
			}		
		
			// Iterates through the current node's children and recursively calls this method
			Iterator<Entry<String, TrieNode>> it = curr.getIterator();
			while (it.hasNext()) {
				String nextLetter = it.next().getValue().getLetter();
				
				int origLength = original.length();
				int wordSoFarLength = wordSoFar.length();
				int buffer = origLength - wordSoFarLength;				
				
				if (this.levenshteinDist(original, (wordSoFar + nextLetter)) <= dist + buffer) {
					ArrayList<String> childSuggs = this.recursiveLEDMatches(original, (wordSoFar + nextLetter), dist);
					suggestions.addAll(childSuggs);
				}
			}
		}
		
		return suggestions;
	}
	
}

