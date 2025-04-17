package comprehensive;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GeneratorTester {

	@BeforeEach
	void setUp() throws Exception {
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
	
	

}
