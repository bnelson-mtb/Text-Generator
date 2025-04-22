package comprehensive;

import java.io.IOException;

/**
 * Driver class that takes 4 arguments from command line to generate text: input text file, the
 * "seed" word, k number of words to generate as output, and "probable",
 * "deterministic", and/or "random".
 * 
 * @param args
 */
public class TextGenerator {

	public static void main(String[] args) {
		Generator generator = new Generator();
		
		try {
			generator.createLibraryFromFile(args[0]);
		} catch (IOException fileError) {
			fileError.printStackTrace();
		}
		
		System.out.println(generator.generateText(args[1], Integer.valueOf(args[2]), args[3]));
	}
}
