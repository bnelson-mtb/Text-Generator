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
	void testBuildLibrary() throws IOException {
		Generator actual = new Generator();
				
		actual.createLibraryFromFile("C:\\Users\\kentw\\Downloads\\abc.txt");
		// assertThrows();
	}

}
