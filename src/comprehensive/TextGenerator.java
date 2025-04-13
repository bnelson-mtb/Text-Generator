package comprehensive;

public class TextGenerator {

	// takes 4 arguments: input text file, the "seed" word, k number of words to generate as output, and "probable", "deterministic", and/or "random"
	public static void main(String[] args) {
		// hello world!
		LibraryLogic.feedFile(args[0]);
		
		System.out.println(LibraryLogic.generateText());		
	}
}
