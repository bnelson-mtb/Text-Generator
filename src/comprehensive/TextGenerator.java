package comprehensive;

import java.util.HashMap;

public class TextGenerator {

	/**
	 * Takes 4 arguments from command line to generate text: input text file, the
	 * "seed" word, k number of words to generate as output, and "probable",
	 * "deterministic", and/or "random".
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Generator generator = new Generator();
		generator.createLibraryFromFile(args[0]);
		System.out.println(generator.generateText(args[1], Integer.valueOf(args[2]), args[3]));
	}
}
