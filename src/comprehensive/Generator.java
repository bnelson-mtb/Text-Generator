package comprehensive;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * 
 *
 * @author Kent Wilkison and Brady Nelson
 * @version 4/13/25
 */

public class Generator {
	private HashMap<String, WordEntry> library;

	public Generator() {
		this.library = new HashMap<String, WordEntry>();
	}

	/**
	 * This method processes words from a file using two pointers: one pointing to
	 * the current word (current) and one to the next word (next). Stores the
	 * processed words in this.library.
	 * 
	 * As the file is scanned, the following steps are performed: 1. Inspect the
	 * word current points to: - If the word is not in the library, create a new
	 * WordEntry for it. - If it already exists, increment its occurrence count. 2.
	 * Inspect the word next points to: - Within AdjacentWords, apply the same logic
	 * as in step 1.
	 * 
	 * After processing, the current and next pointers advance to the next pair of
	 * words in the list.
	 * 
	 * @param filePath Path to the text file to process
	 * @throws IOException If there's an error reading the file
	 */
	public void createLibraryFromFile(String filePath) throws IOException {
		// Clear any existing library data
		library.clear();

		// Define a pattern that splits on whitespace and punctuation except underscores
		// and apostrophes
		// This pattern means: any whitespace OR any punctuation except underscore and
		// apostrophe
		Pattern wordSplitPattern = Pattern.compile("[\\s]|[\\p{Punct}&&[^_']]");

		try (Scanner scanner = new Scanner(new File(filePath))) {
			// Set the delimiter pattern
			scanner.useDelimiter(wordSplitPattern);

			String previousWord = null;

			// Process one word at a time
			while (scanner.hasNext()) {
				String word = scanner.next().trim();

				// Skip empty strings
				if (word.isEmpty()) {
					continue;
				}

				// Convert to lowercase for consistency
				word = word.toLowerCase();

				// Add or update this word in our library
				WordEntry entry = library.computeIfAbsent(word, w -> new WordEntry(w));
				entry.incrementOccurrence();

				// If we have a previous word, update its connections
				if (previousWord != null) {
					WordEntry prevEntry = library.get(previousWord);
					prevEntry.addAdjacentWord(word);
				}

				// Current word becomes the previous word for the next iteration
				previousWord = word;
			}
		}

		System.out.println("Library created with " + library.size() + " unique words.");
	}

	/**
	 * Generates text based on a given seed and given generation specifications.
	 *
	 * @param seed - The seed/starting word to generate from
	 * @param k - Number of words to be generated
	 * @param mode - Mode of generation:
	 * 
	 * @return
	 */
	public String generateText(String seed, Integer k, String mode) {
		// Convert seed to lowercase to match our library keys
	    String currentWord = seed.toLowerCase();
	    
	    // Check if seed exists in library
	    if (!library.containsKey(currentWord)) {
	        return "Error: Seed word '" + currentWord + "' not found in the input text.";
	    }
	    
	    StringBuilder output = new StringBuilder(currentWord);
		
		boolean includeSeed = !mode.equalsIgnoreCase("probable");
		if (includeSeed) {
			output.append(currentWord);
		}
		
		for (int i = 0; i < k; i++) {
			// this creates the general structure/data by which we will work with to find the seed's next word.
			WordEntry entry = library.get(currentWord);
			if (entry == null || entry.getAdjacentWords().isEmpty()) {
				break;
			}
			
			HashMap<String, Integer> adjacent = entry.getAdjacentWords();
			String nextWord = null;
			
			switch (mode.toLowerCase()) {
				case "probable":
					PriorityQueue<String> probs = new PriorityQueue<String>();
					int adjSize = adjacent.size();
					for (String word : adjacent.keySet()) {
						probs.put(word, (adjacent.get(word) / adjSize));
					}
					probs.sort();
						
				
					
					break;
				case "random":
					/// implement
					break;
				case "deterministic":
					int maxFreq = -1;
					for (Map.Entry<String, Integer> e : adjacent.entrySet()) {
						if (e.getValue() > maxFreq || (e.getValue() == maxFreq && e.getKey().compareTo(nextWord) < 0)) {
							maxFreq = e.getValue();
							nextWord = e.getKey();
						}
					}
					break;
				default:
					throw new IllegalArgumentException("Unknown mode: " + mode);
			}
			output.append(" ").append(nextWord);
			currentWord = nextWord;
		}
		
		return output.toString();
	}

}