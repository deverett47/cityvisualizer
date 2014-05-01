package autocomplete;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

/**
 * This class does most of the heavy lifting -- when its methods are called, it can generate
 * an ArrayList of suggestions and sort that list. It has multiple booleans referring to the
 * different features that can be turned on or off, as dictated by the user's command line inputs.
 * 
 * It also is home to two private classes, both of which implement Comparator. One of the
 * comparators allows me to sort the suggestions based on the guidelines in the handout, while
 * the second comparator allows me to use my "smart" ranking--essentially a slight variation
 * on the ranking guidelines that I think might be helpful.
 * @author deverett
 *
 */
public class Ranker {

	private Trie _trie;
	private TrieNode _root;
	private boolean _LED, _white, _prefix, _bigram, _smart;
	private Bigrams _bigrams;
	private int _distance;
	private SpaceFinder _spaceFinder;
	
	public Ranker () {
		_LED = false;
		_white = false;
		_prefix = false;
		_bigram = false;
		_smart = false;
	}
	
	/**
	 * Called by App to pass a reference to the trie.
	 * @param t
	 */
	public void setTrie(Trie t) {
		_trie = t;
		_root = _trie.getRoot();
	}
	
	/**
	 * Called by App to pass a reference to the bigrams structure.
	 * @param b
	 */
	public void setBigrams(Bigrams b) {
		_bigrams = b;
	}
	
	/**
	 * Called by App to pass in a reference to the space finder, which suggests
	 * potential way to split one word into two.
	 * @param s
	 */
	public void setSpaceFinder(SpaceFinder s) {
		_spaceFinder = s;
	}
	
	/**
	 * Turns on Levenshtein edit distance-based suggestions.
	 */
	public void turnOnLED(int dist) {
		_LED = true;
		_distance = dist;
	}
	
	/**
	 * Turns on word splitting/white space-based suggestions.
	 */
	public void turnOnWhite() {
		_white = true;
	}
	
	/**
	 * Turns on prefix-matching based suggestions.
	 */
	public void turnOnPrefix() {
		_prefix = true;
	}
	
	/**
	 * Turns on bigram-based suggestions.
	 */
	public void turnOnBigram() {
		_bigram = true;
	}
	
	/**
	 * Turns on smart ordering.
	 */
	public void turnOnSmart() {
		_smart = true;
	}
	
	
	
	/**
	 * Given the user's most recent input, generates suggestions based on whether or not
	 * prefix and LED have been enabled by the user.
	 * @param curr
	 * @return
	 */
	public ArrayList<String> getSuggestions(String curr) {
		ArrayList<String> suggestions = new ArrayList<String>();
		
		if (_prefix == true) {  // get suggestions based on prefix matching
			suggestions.addAll(_trie.getPrefixMatches(curr));
		}
		
		if (_LED == true) { // get suggestions based on LED 
			suggestions.addAll(_trie.getLEDMatches(curr, _distance));
		}
		
		if (_white == true) { // get suggestions based on word splitting
			suggestions.addAll(_spaceFinder.separateWords(curr));
		}

		// this next bit eliminates any duplicate elements
		Set<String> temp = new HashSet<String>(suggestions);
		temp.addAll(suggestions);
		suggestions.clear();
		suggestions.addAll(temp);
		
		return suggestions;
	}
	
	/**
	 * Given the list of suggestions that has been generated, first sorts the suggestions. 
	 * Then handles the whitespace aspect, checking to see if the user's input could have been
	 * two words that need to be split. Then, it checks to see if after all that, no suggestions
	 * at all were generated -- though this should probably indicate that the user simply
	 * neglected to turn on LED, prefix matching, or whitespace. If that's the case, checks to
	 * see if the user's input word is at least a real word (according to the trie), and then adds it
	 * to the suggestion list if it is. Then returns the sorted list of suggestions.
	 * @param curr
	 * @param prev
	 * @param suggestions
	 * @return
	 */
	public ArrayList<String> rankSuggestions(String curr, String prev, ArrayList<String> suggestions) {
		if (_smart == true) {
			Collections.sort(suggestions, new smartRankComparator(curr, prev));
		}
		
		else {
			Collections.sort(suggestions, new rankComparator(curr, prev));
		}

		
		if (suggestions.size() == 0) { //if there are no suggestions at all, probably indicating that the user turned on none of the algorithms
			TrieNode word = _trie.find(_root, curr);
			if (word != null) { // this means the input word was actually in the trie/is a real word, so just return it
				suggestions.add(curr);
			}
		}
		
		return suggestions;		
	}
	
	/**
	 * Given two words, loops through the words to determine the size of
	 * the biggest substring they both contain. For example, calling
	 * substringSize("tests", "testss") should return 5. I implemented this for my smartRanking
	 * because I found that my suggestions would sometimes return words that were technically
	 * correct in terms of edit distance, but didn't seem too likely as what the user was looking
	 * for -- for example, it's more likely that the user meant "tests" instead of "bests" when they
	 * accidentally typed "testss."
	 * @param word1
	 * @param word2
	 * @return
	 */
	public int substringSize (String word1, String word2) {
		int result = 0;
		
		for (int i = 0; i < word1.length(); i++) {
			char currentChar = word1.charAt(i);
			int temp = 0;
			for (int z = 0; i < word2.length(); i++) {
				try {
					currentChar = word1.charAt(i+z);
					if (currentChar == word2.charAt(z)) {
						temp += 1;
					}
				}
				catch (IndexOutOfBoundsException e){
				}
			}
			
			if (temp > result) {
				result = temp;
			}
		}
		
		
		return result;
	}
	
	
	

	
	
	/**
	 * A private class that implements the comparator interface. Used to compare
	 * suggestions according to the guidelines in the assignment handout.
	 * @author deverett
	 *
	 */
	private class rankComparator implements Comparator<String> {
		private String _curr, _prev;
		
		public rankComparator(String curr, String prev) {
			_curr = curr.replace("\\s+", "");
			_prev = prev;
			if (_prev != null) {
			_prev = _prev.replace("\\s+", "");
			}
		}
		
		
		/**
		 * Given two words (from the list of suggestions), first checks to see if either word is the
		 * same as the one as the user has already typed (and returns -1 or 1 accordingly). Then checks 
		 * the bigram frequency of each word compared to word preceding the word that needs to be corrected
		 * (assuming the user has typed two or more words). This returns -1 or 1 depending on which word
		 * has a higher bigram frequency w/ _prev. Should both of these frequencies be the same, the compare
		 * method resorts to comparing unigram frequency. Should both words also have the same unigram frequency
		 * the very last resort is comparing by alphabetical order.
		 */
		public int compare(String word1, String word2) {
			int result = 0;		
			
			if (word1 == _curr) { // if word1 is the word that the user has typed
				result = -1;
			}
			
			else if (word2 == _curr) { // if word2 is the word that the user has typed
				result = 1;
			}
			
			else {
				int freq1 = 0;
				int freq2 = 0;
				
				if (_prev != null) { // if there is actually a previous word
					freq1 = _bigrams.getFrequency(_prev, word1); // get frequency of word1 appearing after _prev
					freq2 = _bigrams.getFrequency(_prev, word2); // get frequency of words2 appearing after _prev
				}
		
				
				if (freq1 > freq2) {
					result = -1;
				}
				
				else if (freq2 > freq1) {
					result = 1;
				}
				
				else { // if they have the same bigram frequency
					int overallFreq1;
					int overallFreq2;
					try {
						overallFreq1 = _trie.find(_root, word1).getFrequency(); // gets overall freq of word1 in trie
						overallFreq2 = _trie.find(_root, word2).getFrequency(); // overall freq of word2 in trie
					}
					catch (NullPointerException e) {
						overallFreq1 = 0;
						overallFreq2 = 0;
					}
					
					if (overallFreq1 > overallFreq2) {
						result = -1;
					}
					
					else if (overallFreq2 > overallFreq1) {
						result = 1;
					}
					
					else if (overallFreq1 == overallFreq2) { // if they have the same unigram frequency
						result = word1.compareTo(word2); // returns neg int if word1 is first alphabetically, pos if word 2 is first
					}
				}
			}	
			return result;
		}
		
	}
	
	
	
	
	
	
	
	/**
	 * A private class that implements the comparator interface. Used to compare
	 * suggestions according to the guidelines in the assignment handout, with
	 * a slight modification for what I thought of for the "smart" ranking.
	 * @author deverett
	 *
	 */
	private class smartRankComparator implements Comparator<String> {
		private String _curr, _prev;
		
		public smartRankComparator(String curr, String prev) {
			_curr = curr;
			_prev = prev;
		}	
		
		/**
		 * Almost entirely the same as the regular comparator's compare method,
		 * except it checks which word has the smaller LED distance from the user's
		 * input before moving on to unigram frequency. This essentially banks on the
		 * assumption that, were a user to type a word wrong, they'd be more likely
		 * to miss only one or two characters instead of most of the string.
		 */

		public int compare(String word1, String word2) {
			int result = 0;		
			
			if (word1 == _curr) { // if word1 is the word that the user has typed
				result = -1;
			}
			
			else if (word2 == _curr) { // if word2 is the word that the user has typed
				result = 1;
			}
			
			else {
				int freq1 = 0;
				int freq2 = 0;
				
				if (_prev != null) { // if there is actually a previous word
					freq1 = _bigrams.getFrequency(_prev, word1); // get frequency of word1 appearing after _prev
					freq2 = _bigrams.getFrequency(_prev, word2); // get frequency of words2 appearing after _prev
				}
		
				
				if (freq1 > freq2) {
					result = -1;
				}
				
				else if (freq2 > freq1) {
					result = 1;
				}
				
				else {
					int lev1 = _trie.levenshteinDist(word1, _curr);
					int lev2 = _trie.levenshteinDist(word2, _curr);
					
					if (lev1 < lev2) {
						return -1;
					}
					
					else if (lev2 < lev1) {
						return 1;
					}
					
					else { // if they have the same bigram frequency and same LED from curr!
						int overallFreq1 = _trie.find(_root, word1).getFrequency(); // gets overall freq of word1 in trie
						int overallFreq2 = _trie.find(_root, word2).getFrequency(); // overall freq of word2 in trie
					
						if (overallFreq1 > overallFreq2) {
							result = -1;
						}
					
						else if (overallFreq2 > overallFreq1) {
							result = 1;
						}
					
						else if (overallFreq1 == overallFreq2) { // if they have the same unigram frequency
							result = word1.compareTo(word2); // returns neg int if word1 is first alphabetically, pos if word 2 is first
						}
					}
				}	
			}
				return result;
		}
		
		
	}
	
	
	
}
