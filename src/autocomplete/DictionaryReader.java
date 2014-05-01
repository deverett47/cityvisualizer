package autocomplete;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

 /**
 * This classes parses the input text files and inserts every word into the appropriate
 * data structures. It is instantiated by the App class at the beginning of the program's execution
 * so that it can get everything set up.
 * @author deverett
 *
 */
public class DictionaryReader {


private Trie _trie;
private TrieNode _root;
private Bigrams _bigrams;
	
	public DictionaryReader() {
	}
	
	/**
	 * Called by App to pass in a reference to the trie.
	 * @param trie
	 */
	public void setTrie(Trie trie){
		_trie = trie;
		_root = _trie.getRoot();
	}
	
	/**
	 * Called by APp to pass in a reference to the bigrams structure.
	 * @param b
	 */
	public void setBigrams(Bigrams b) {
		_bigrams = b;
	}
	
	/**
	 * When called by the App class, it is passed an ArrayList of fileReaders, which
	 * are then used to instantiated bufferedReaders. The current line is updated by
	 * looping through the buffered reader's list of lines. The strings on each line
	 * are edited for case, spacing, punctuation, etc.  It then inserts each word into
	 * the bigram tracker and the trie.
	 * @param fileReaders
	 * @throws IOException 
	 */
	public void readFile(File f) throws IOException {
		
		RandomAccessFile raf = new RandomAccessFile(f, "r");
		raf.seek(0);
		raf.readLine();
		long currPointer = raf.getFilePointer();
		long length = raf.length();
		while (currPointer < length) {
			String[] currLine = raf.readLine().split("\t");
			String words = currLine[0];
			String[] splitWords = words.split(" ");
			
			for (int i = 0; i < splitWords.length; i++) {
			try {
				_trie.insert(_root, splitWords[i]);
				if (i > 0 ) {
					_bigrams.insert(splitWords[i-1], splitWords[i]);
				}
				}
				catch (NullPointerException e) {		
				}
			}
			currPointer = raf.getFilePointer();
			// alternatively, could split words and separate on a word-by-word basis instead of on a street-by-street basis
			
		}
		raf.close();
	}
}
