package comprehensive;
import java.util.HashMap;

/**
 
This class represents one entry in the CompiledFile class. It
stores the word, its frequency in the list, and a linked list
containing the words that come after the stored word.
<il> AdjacentWords is a HashMap that stores the word as its {@code Key} and its frequency after this word as its {@code value}.
@author Kent Wilkison and Brady Nelson
@version 4/13/2025
*/

public class WordEntry {
	private String word;
    private int frequency;
    private HashMap<String, Integer> adjacentWords;
    
    public WordEntry(String word) {
    	this.word = word;
        this.frequency = 0;
        this.adjacentWords = new HashMap<>();
    }

    public int getFrequency() {
        return this.frequency;
    }

    public void incrementFrequency() {
        this.frequency++;
    }

    public HashMap<String, Integer> getAdjacentWords() {
        return this.adjacentWords;
    }
}
