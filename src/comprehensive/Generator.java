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
 * This class breaks down the input file passed into main and creates
 * a hash table out of it. The hash table stores words as keys and WordEntries 
 * as its values (see the documentation of WordEntry for more about this class!). Additionally,
 * this class provides 3 unique methods for generating text based on the probability
 * of a certain word coming after the first. They include random, which randomly chooses
 * a word based on its probability (for example, a word that occurs 50% of the time 
 * has a higher chance of being chosen than a word that occurs 25% of the time.)
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
	    String seedLower = seed.toLowerCase();
	    String currentWord = seedLower;
	    
	    // Check if seed exists in library
	    if (!library.containsKey(currentWord)) {
	        return "Error: Seed word '" + currentWord + "' not found in the input text.";
	    }
	    
		// If mode is "probable", call separate method (logic is different)
		if (mode.equalsIgnoreCase("probable")) {
			return getNextProbableWord(seed, k);
		}
	    
		// If other modes, start creating generated string
		StringBuilder output = new StringBuilder(currentWord);
	    
	    // Generate "k" words, 1 at a time, using random or deterministic methods
		for (int i = 0; i < k - 1; i++) {
			
			// Instantiates the data of the word we will use on this iteration
			WordEntry currentEntry = library.get(currentWord);
			
			// If word has no adjacent words, restart from seed
			if (currentEntry == null || currentEntry.getAdjacentWords().isEmpty()) {
				output.append(" ").append(seed);
				i++;
				currentEntry = library.get(seedLower);
	            
	            // If even the seed has no adjacent words, we have to break
	            if (currentEntry == null || currentEntry.getAdjacentWords().isEmpty()) {
	                break;
	            }
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
					nextWord = null;
					for (Map.Entry<String,Integer> e : adjWords.entrySet()) {
					  int freq = e.getValue();
					  String word  = e.getKey();
					  if (freq > maxFreq) {
					    maxFreq = freq;
					    nextWord = word;
					  }
					  else if (freq == maxFreq && word.compareTo(nextWord) < 0) {
					    nextWord = word;
					  }
					}
					break;
					
				default:
					throw new IllegalArgumentException("Unknown mode: " + mode);
			}
			
			
			output.append(" ").append(nextWord);
			currentWord = nextWord;
		}
		
		return output.toString().trim();
	}
	
	/**
	 * Returns the first k words that are the most probable to come after the
	 * seed. 
	 * 
	 * If the size of seed's adjacency list is smaller than k, only 
	 * produce up to the size of the adjacency list. 
	 * 
	 * @param seed
	 * @param k
	 * @return
	 */
	private String getNextProbableWord(String seed, Integer k) {
		
		// Get the adjacent words list from the seed word
		WordEntry seedValue = library.get(seed);
		HashMap<String, Integer> adjacentList = seedValue.getAdjacentWords();
		List<Map.Entry<String, Integer>> entryList = new ArrayList<>(adjacentList.entrySet());
		
		// Sort the list
		Collections.sort(entryList, (a, b) -> b.getValue().compareTo(a.getValue()));
		
		// Build resulting string according to the 'k' specified
		StringBuilder result = new StringBuilder();

		if (adjacentList.size() < k) {
			k = adjacentList.size();
		}
		for (int i = 0; i < k; i++) {
			result.append(entryList.get(i).getKey());
			if (i < k - 1)
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
		return "";
	}

}