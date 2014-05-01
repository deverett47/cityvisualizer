package autocomplete;

import java.util.ArrayList;

/**
 * This class, when activated, allows the user to get suggestions like "thisword" --> "this word"
 * It has a reference to the trie so that it can check which words are valid. More information
 * on how I implemented this can be found above the appropriate function.
 * @author deverett
 *
 */
public class SpaceFinder {

	private Trie _trie;
	
	public SpaceFinder(Trie trie) {
		_trie = trie;
	}
	
	
	/**
	 * Given a string that could potentially be two words in need of a space,
	 * generates suggestions that are formatted as "word1 word2" in a list.
	 * 
	 * Loops through the string from the first character, and attempts to find
	 * the first word. If it finds one word, it will check the rest of the string
	 * to make sure that is also a word, and then adds these to the suggestion list.
	 * @param wordToCheck
	 * @return
	 */
	public ArrayList<String> separateWords(String wordToCheck) {
		int length = wordToCheck.length();
		String curr = ""; // instantiates an empty string that will be built on
		TrieNode root = _trie.getRoot();
		ArrayList<String> suggestions = new ArrayList<String>();
		
		for (int i = 0; i < length; i++) {
			String nextLetter = wordToCheck.charAt(i) + ""; // accesses next character and converts to string
			curr = curr + nextLetter;
			
			// if the current potential word is actually in the trie, and is the end of a word
			if (_trie.find(root, curr) != null && _trie.find(root, curr).isWordEnd() == true) {
				String secondWord = "";
				for (int g = i + 1; g < length; g++) {
					secondWord = secondWord + (wordToCheck.charAt(g) + "");
				}
				
				/* if the second generated word is also contained in the trie/recgonized as a real word, 
				 * add the two words to the suggestions separated by space
				 */ 
				if (_trie.find(root, secondWord) != null && _trie.find(root, secondWord).isWordEnd() == true) {
					suggestions.add(curr + " " + secondWord);
				}
			}			
		}
		
		return suggestions;
	}
}
