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

    public void incrementOccurrence() {
        this.frequency++;
    }

    public HashMap<String, Integer> getAdjacentWords() {
        return this.adjacentWords;
    }
    
    /**
     * This function takes a word and tries to put it into the AdjacentWord hashmap. 
     * If it exists, use the value the word has, otherwise set its default value to 0.
     * After you do that, increment the value by 1.
     * 
     * This method uses a special get method, "getOrDefault" which is like if .get() had a built-in 
     * if statement that checks if the value is null or not, and then acts accordingly. This prevents "NullPointerException" errors.
     * 
     * @param word - the word that comes after the first word, to be added to the hashmap.
     */
    public void addAdjacentWord(String word) {
    	adjacentWords.put(word, adjacentWords.getOrDefault(word, 0) + 1);
    }
}
