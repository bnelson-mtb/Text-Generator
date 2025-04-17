package comprehensive;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GeneratorTester {
	String catPath;
	
	
	@BeforeEach
	void setUp() throws Exception {
		catPath = "C:\\Users\\brady\\Downloads\\cat.txt";
	}

	@Test
	void testBuildLibraryAbc() throws IOException {
		Generator generator = new Generator();
				
		generator.createLibraryFromFile("C:\\Users\\brady\\Downloads\\abc.txt");
	}
	
	@Test
	void testBuildLibraryTricky() throws IOException {
		Generator generator = new Generator();
				
		generator.createLibraryFromFile("C:\\Users\\brady\\Downloads\\trickyFormatting.txt");
	}
	
	@Test
	void testBuildLibraryHelloWord() throws IOException {
		Generator generator = new Generator();
				
		generator.createLibraryFromFile("C:\\Users\\brady\\Downloads\\helloWorld.txt");
	}
	
	@Test
	void testProbable() throws IOException {
		Generator generator = new Generator();
		generator.createLibraryFromFile(catPath);
		
		String expected = "cat mat";
		String actual = generator.generateText("the", 10, "probable");
		
		assertEquals(expected, actual);
	}
	
	@Test
	void testDeterministic() throws IOException {
		Generator generator = new Generator();
		generator.createLibraryFromFile(catPath);
		
		String expected = "the cat ate a bat the cat ate a bat";
		String actual = generator.generateText("the", 10, "deterministic");
		
		assertEquals(expected, actual);
	}
	
	
	
	

}
