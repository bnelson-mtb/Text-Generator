package comprehensive;

import java.io.File;
import java.util.HashMap;

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
	 * the current word (current) and one to the next word (next). Stores the processed words in
	 * this.library.
	 * 
	 * As the file is scanned, the following steps are performed:
	 * 	1. Inspect the word current points to:
	 * 	  - If the word is not in the library, create a new WordEntry for it.
	 * 	  - If it already exists, increment its occurrence count.
	 *  2. Inspect the word next points to:
	 *    - Within AdjacentWords, apply the same logic as in step 1.
	 * 
	 * After processing, the current and next pointers advance to the next pair of
	 * words in the list.
	 * 
	 * @param fileName
	 * @return
	 */
	public void createLibraryFromFile(String fileName) {
		HashMap<String, WordEntry> constructedLibrary = new HashMap<String, WordEntry>();

		this.library = constructedLibrary;
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

		String output = "";

		// from library, calculate the frequency of words in the library and from there
		// generate the next word.

		return output;
	}

}
