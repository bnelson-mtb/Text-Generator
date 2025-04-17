package comprehensive;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
	 * As the file is scanned, the following steps are performed:
	 * 
	 * 1. Inspect the word current points to:
	 * - If the word is not in the library, create a new
	 * WordEntry for it.
	 * - If it already exists, increment its occurrence count.
	 * 
	 * 2. Inspect the word next points to:
	 * - Within AdjacentWords, apply similar logic
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

				// Convert to lower-case for consistency
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

		// System.out.println("Library created with " + library.size() + " unique words.");
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
		// Convert seed to lower-case to match our library keys
	    String currentWord = seed.toLowerCase();
	    
	    // Check if seed exists in library
	    if (!library.containsKey(currentWord)) {
	        return "Error: Seed word '" + currentWord + "' not found in the input text.";
	    }
	    
		// If mode is "probable", call separate method (logic is different)
		if (mode.equalsIgnoreCase("probable")) {
			return createProbabilitiesList(seed, k);
		}
	    
		// If other modes, start creating generated string
		StringBuilder output = new StringBuilder(currentWord);
	    
	    // Generate "k" words, 1 at a time, using random or deterministic methods
		for (int i = 0; i < k; i++) {
			
			// Instantiates the data of the word we will use on this iteration
			WordEntry currentEntry = library.get(currentWord);
			
			if (currentEntry == null || currentEntry.getAdjacentWords().isEmpty()) {
				break;
			}
			
			HashMap<String, Integer> adjWords = currentEntry.getAdjacentWords();
			String nextWord = null;
			
			switch (mode.toLowerCase()) {
				case "random":
					nextWord = getRandomNextWord(adjWords);
					break;
					
				case "deterministic":
					// Optimization loop that find the word with highest frequency
					int maxFreq = -1;
					for (Map.Entry<String, Integer> entry : adjWords.entrySet()) {
						if (entry.getValue() > maxFreq || (entry.getValue() == maxFreq && entry.getKey().compareTo(nextWord) < 0)) {
							maxFreq = entry.getValue();
							nextWord = entry.getKey();
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

	private String createProbabilitiesList(String seed, Integer k) {
		
		// Get the adjacent words list from the seed word
		WordEntry seedValue = library.get(seed);
		HashMap<String, Integer> adjacentList = seedValue.getAdjacentWords();
		List<Map.Entry<String, Integer>> entryList = new ArrayList<>(adjacentList.entrySet());
		
		// Sort the list
		Collections.sort(entryList, (a, b) -> b.getValue().compareTo(a.getValue()));
		
		// Build resulting string according to the 'k' specified
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < k; i++) {
			result.append(entryList.get(i).getKey());
			result.append(" ");
		}
		
		return result.toString();
	}
	
	/** 
	 * Computes the next word using a weighted probability algorithm.
	 * @return
	 */
	private String getRandomNextWord(Map<String, Integer> adjacentList) {
		// Add up total frequency
		int total = 0;
		for (int freq : adjacentList.values()) {
			total += freq;
		}

		// Pick a random value between [0, total]
		int rand = new Random().nextInt(total) + 1;
		
		// Iterate through entries and pick the one random falls into.
		for (Map.Entry<String, Integer> entry : adjacentList.entrySet()) {
			rand = rand - entry.getValue();
			if (rand <= 0) {
				return entry.getKey();
			}
		}
		return " ";
	}

}