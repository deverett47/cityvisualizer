package autocomplete;

import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;


/**
 * This class is the one that pulls it all together. After the App class parses all of the
 * commands line arguments, it instantiates an instance of Corrector and passes it numerous important
 * references. It also passes it a boolean saying whether or not the GUI should be launched.
 * 
 * At the end of the constructor is an instantiation of a scanner, the Java class I found most
 * suited to reading command line input. While there is still input from the user, the readCommandLine()
 * method is called, which puts everything into action in terms of getting suggestions, ranking them,
 * and then printing the top 5 suggestions.
 * 
 * Corrector extends JFrame so that it can support the GUI when required -- see the GUI class for more
 * information.
 * @author deverett
 *
 */



public class Corrector {
	
private Trie _trie;
private TrieNode _root;
private Bigrams _bigrams;
private Ranker _ranker;
private SpaceFinder _spaceFinder;
	
	public Corrector(Trie trie, Bigrams bigrams, Ranker ranker, SpaceFinder spaceFinder) {
		super();
		_trie = trie;
		_root = _trie.getRoot();
		_bigrams = bigrams;
		_ranker = ranker;
		_spaceFinder = spaceFinder;		
	
	}
	
	/**
	 * When called, reads the input line for the next string (could be one word, or a full line of text), 
	 * first strips the string of all punctuation, non-alphanumeric characters, and uppercase letters. Then calls 
	 * the ranker to get suggestions, calls the sort method on those suggestions, and then calls a method to
	 * trim the suggestions down to a five-item list. Returns those five suggestions.
	 * 
	 * 
	 * @param s
	 */
	public ArrayList<String> getSuggestions(String line) {
				String currLine = line;
				String curr = null;
				String prev = null;
			
				currLine = currLine.replaceAll("\\W", " "); // replaces non-words with spaces
				currLine = currLine.replaceAll("\\s+", " "); // eliminates extra spaces
				currLine = currLine.toLowerCase(); // strips line of uppercase characters
				String[] currWords = currLine.split(" ");
				ArrayList<String> result = new ArrayList<String>();
				
				int length = currWords.length;
				
				if (length == 0) {
					return result;
				}
				
				else if (length == 1) {
					curr = currWords[length-1];
					ArrayList<String> suggestions = _ranker.getSuggestions(curr);
					ArrayList<String> sortedSuggestions = _ranker.rankSuggestions(curr, prev, suggestions);
					String[] trimmedSuggs = this.trimSuggestions(sortedSuggestions);
					
					for (String x: trimmedSuggs){
						if (x != null) {
							x = x.substring(0,1) + x.substring(1);
							result.add(x);
						}
					}
				}
			
				else {
					curr = currWords[length-1];
					prev = currWords[length-2];
					ArrayList<String> suggestions = _ranker.getSuggestions(curr);
					ArrayList<String> sortedSuggestions = _ranker.rankSuggestions(curr, prev, suggestions);	
					String[] trimmedSuggs = this.trimSuggestions(sortedSuggestions);
					for (String x: trimmedSuggs){
						if (x != null) {
							String toPrint = "";
							for (int i = 0; i < currWords.length -1; i++) {
								currWords[i] = currWords[i].substring(0,1).toUpperCase() + currWords[i].substring(1);
								toPrint = toPrint + currWords[i] + " ";
							}
							x = x.substring(0,1) + x.substring(1);
							result.add(toPrint+x);
						}
					}	
				}	
				
				return result;

	}
	
	/**
	 * Given the full list of sorted suggestions for this word, grabs the top 5 suggestions
	 * and returns them as a 5-element array.
	 * @param suggs
	 * @return
	 */
	public String[] trimSuggestions(ArrayList<String> suggs) {
		String[] trimmedSuggs = new String[5];
		
		if (suggs.size() > 0) {
			trimmedSuggs[0] = suggs.get(0);
			
			
			// sets the rest of the array of suggestions
			if (suggs.size() > 1) {
				trimmedSuggs[1] = suggs.get(1);
			}
		
			if (suggs.size() > 2) {
				trimmedSuggs[2] = suggs.get(2);
			}
			
			if (suggs.size() > 3) {
				trimmedSuggs[3] = suggs.get(3);
			}
			
			if (suggs.size() > 4) {
				trimmedSuggs[4] = suggs.get(4);
			}
		}
		
		return trimmedSuggs;
	}
	

}
