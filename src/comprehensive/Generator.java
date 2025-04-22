package comprehensive;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
 * this class provides 3 unique modes for generating text based on the probability
 * of a certain word coming after the first.
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

		// Create the delimiter pattern to properly split the text
		Pattern delim = Pattern.compile("[^\\p{L}\\p{N}_']+");
		
		try (Scanner scanner = new Scanner(new File(filePath))) {
			// Apply the delimiter pattern
			scanner.useDelimiter(delim);

			// Initialize previousWord pointer
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

				// If we have a previous word, update its adjacent words
				if (previousWord != null) {
					WordEntry prevEntry = library.get(previousWord);
					prevEntry.addAdjacentWord(word);
				}

				// Current word becomes the previous word for the next iteration
				previousWord = word;
			}
		}
	}

	/**
	 * Generates text based on a given seed and given generation specifications.
	 *
	 * @param seed - The seed/starting word to generate from
	 * @param k - Number of words to be generated
	 * @param mode - Mode of generation: "random" randomly chooses
	 * a word based on its probability (for example, a word that occurs 50% of the time 
	 * has a higher chance of being chosen than a word that occurs 25% of the time), "probable" returns
	 * a list of the k most probable elements in sorted order. "deterministic" generates the most probable
	 * word after each previous word.
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
	        return getProbableWordsList(seedLower, k);
	    }
	    
		// If other modes, start creating generated string
		StringBuilder output = new StringBuilder(currentWord);
	    
	    // Main logic loop: generates "k" words, 1 at a time, using random or deterministic methods
		for (int i = 0; i < k - 1; i++) {
			
			// Instantiates the data of the word we will use on this iteration
			WordEntry currentEntry = library.get(currentWord);
			
			// If word has no adjacent words, restart from seed
			if (currentEntry == null || currentEntry.getAdjacentWords().isEmpty()) {
				output.append(" ").append(seedLower);
				i++;
				currentEntry = library.get(seedLower);
	            
	            // If even the seed has no adjacent words, we have to break
	            if (currentEntry == null || currentEntry.getAdjacentWords().isEmpty()) {
	                break;
	            }
	        }
			
			// Resets nextWord and gets adjacent words list, since it now is known to exist
			HashMap<String, Integer> adjWords = currentEntry.getAdjacentWords();
			String nextWord = null;
			
			// Decide which mode to use
			switch (mode.toLowerCase()) {
				case "random":
					nextWord = getRandomNextWord(adjWords);
					break;
					
				case "deterministic":
					// Optimization loop that find the word with highest frequency
					int maxFreq = -1;
					nextWord = null;
					for (Map.Entry<String,Integer> entry : adjWords.entrySet()) {
					    int freq = entry.getValue();
					    String word = entry.getKey();
					    if (freq > maxFreq ||
					        (freq == maxFreq && (nextWord == null || word.compareTo(nextWord) < 0))) {
					        maxFreq = freq;
					        nextWord = word;
					    }
					}
					break;
					
				default:
					throw new IllegalArgumentException("Unknown mode: " + mode);
			}
			
			// Appends the generated word "nextWord" and iteratively feeds it back into the loop by
			// updating the value
			output.append(" ").append(nextWord);
			currentWord = nextWord;
		}
		
		// Returns generated output by converting the StringBUilder to a String
		return output.toString().trim();
	}
	
	/**
	 * Returns the first k words that are the most probable to come after the
	 * seed. 
	 * 
	 * If the size of seed's adjacency list is smaller than k, only 
	 * produce up to the size of the adjacency list. 
	 * 
	 * @param seed - Desired seed word
	 * @param k - Number of words to display
	 * @return String showing the words in descending order based on frequency
	 */
	private String getProbableWordsList(String seed, Integer k) {
		// Get the adjacent words HashMap from the seed word
		WordEntry seedWord = library.get(seed);
		HashMap<String, Integer> adjacentList = seedWord.getAdjacentWords();
		List<Map.Entry<String, Integer>> entryList = new ArrayList<>(adjacentList.entrySet());
		
		// Convert the HashMap and sort it accordingly
		entryList.sort((a, b) -> {
		    int valueCompare = b.getValue().compareTo(a.getValue()); // Sort by frequency value (descending)
		    if (valueCompare != 0) {
		        return valueCompare;
		    }
		    return a.getKey().compareTo(b.getKey()); // Sort by key alphabetically (ascending) if values are equal
		});
		
		// Build and return the output using a StringBuilder
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
		int totalFreq = 0;
		for (int freq : adjacentList.values()) {
			totalFreq += freq;
		}

		// Pick a random value between [0, totalFreq]
		int rand = new Random().nextInt(totalFreq) + 1;
		
		// The loop iterates over each word and subtracts its frequency from rand. When rand drops to 0 
		// or below, it means the random number has landed in the range belonging to this word.
		for (Map.Entry<String, Integer> entry : adjacentList.entrySet()) {
			rand = rand - entry.getValue();
			if (rand <= 0) {
				return entry.getKey();
			}
		}
		return "";
	}

}