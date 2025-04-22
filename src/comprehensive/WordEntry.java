package comprehensive;

import java.util.HashMap;

/**
 * This class represents one entry. It acts as a compact way to store multiple
 * values inside the Generator class's HashTable. More specifically, it stores
 * the word, its frequency in the list. AdjacentWords is a HashMap that stores
 * the word as its {@code Key} and its frequency after this word as its
 * {@code value}.
 * 
 * @author Kent Wilkison and Brady Nelson
 * @version 4/22/2025
 */
public class WordEntry {
	private String word;
	private int frequency;
	private HashMap<String, Integer> adjacentWords;

	/**
	 * Basic constructor for a WordEntry.
	 * 
	 * @param word - The word to be associated with this WordEntry
	 */
	public WordEntry(String word) {
		this.word = word;
		this.frequency = 0;
		this.adjacentWords = new HashMap<>();
	}

	/**
	 * Get this word's frequency.
	 * 
	 * @return
	 */
	public int getFrequency() {
		return this.frequency;
	}

	/**
	 * Increment the total number of occurrences of this word.
	 */
	public void incrementOccurrence() {
		this.frequency++;
	}

	/**
	 * Get the adjacent words HashMap for this word.
	 * 
	 * @return the adjacent words HashMap
	 */
	public HashMap<String, Integer> getAdjacentWords() {
		return this.adjacentWords;
	}
	
	/**
	 * Get the word associated with this WordEntry.
	 * 
	 * @return the word
	 */
	public String getWord() {
		return word;
	}

	/**
	 * This function takes a word and tries to put it into the AdjacentWord HashMap.
	 * If it exists, it just increments the frequency count of that word instead of adding a new entry.
	 * 
	 * @param word - the word that comes after the first word, to be added to the
	 * HashMap.
	 */
	public void addAdjacentWord(String word) {
		adjacentWords.put(word, adjacentWords.getOrDefault(word, 0) + 1);
	}
}
