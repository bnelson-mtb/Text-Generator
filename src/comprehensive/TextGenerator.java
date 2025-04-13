package comprehensive;

import java.util.HashMap;

public class TextGenerator {

	// takes 4 arguments: input text file, the "seed" word, k number of words to generate as output, and "probable", "deterministic", and/or "random"
	public static void main(String[] args) {
		
		HashMap<String, WordEntry> library = GeneratorLogic.createLibraryFromFile(args[0]);
		
		System.out.println(GeneratorLogic.generateText(library, args[1], Integer.valueOf(args[2]), args[3]));
	}
}
