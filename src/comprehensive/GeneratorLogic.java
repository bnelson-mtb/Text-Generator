package comprehensive;

import java.io.File;
import java.util.HashMap;

/**
 * A class that uses a HashMap to store words as keys and {@code WordEntry} objects as values.
 * <p>
 * <strong>How the Class Works:</strong>
 * <p>
 * This class processes words from a file using two pointers:
 * one pointing to the current word ({@code current}) and one to the next word ({@code next}).
 * <p>
 * As the file is scanned, the following steps are performed:
 * <ol>
 *   <li>
 *     Inspect the word {@code current} points to:
 *     <ul>
 *       <li>If the word is not in the library, create a new {@code WordEntry} for it.</li>
 *       <li>If it already exists, increment its occurrence count.</li>
 *     </ul>
 *   </li>
 *   <li>
 *     Inspect the word {@code next} points to:
 *     <ul>
 *       <li>Within {@code AdjacentWords}, apply the same logic as in step 1.</li>
 *     </ul>
 *   </li>
 * </ol>
 * After processing, the {@code current} and {@code next} pointers advance to the next pair of words in the list.
 *
 * @author Kent Wilkison and Brady Nelson
 * @version 4/13/25
 */

public class GeneratorLogic {
	
	/**
	 * 
	 * @param fileName
	 * @return
	 */
	public static HashMap<String, WordEntry> createLibraryFromFile(String fileName) {
		// generates and constructs the library, which is to be used inside GeneratorLogic.
		//this.library = parseFile(fileName);\
		HashMap<String, WordEntry> library = new HashMap<String, WordEntry>();
		
		
		return library;
	}
	
	/**
	 * 
	 * 
	 * @param library
	 * @param seed
	 * @param k
	 * @param mode
	 * @return
	 */
	public static String generateText(HashMap<String, WordEntry> library, String seed, Integer k, String mode) {
		String output = "";
		
		// from library, calculate the frequency of words in the library and from there generate the next word.
		
		return output;
	}
	
}
