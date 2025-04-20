package comprehensive;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import timing.TimingExperiment;

/**
 * Experiment to measure text‐generation time (deterministic mode)
 * as the number of words requested (k) increases.
 * 
 * DOES NOT WORK CURRENTLY
 */
public class NextKWordsTimingExperiment extends TimingExperiment {
    private static final String problemSizeDescription = "k (words generated)";
    private static final int problemSizeMin            =   100000000;
    private static final int problemSizeCount          =   100;
    private static final int problemSizeStep           =   100000000;
    private static final int experimentIterationCount  =   50;
    // Fixed‐size input for library building:
    private static final int INPUT_WORD_COUNT  = 50_000;
    private static final int INPUT_VOCAB_SIZE  =    500;
    private int currentProblemSize;
    
    private Generator generator;
    private String filePath;

    public static void main(String[] args) {
        new NextKWordsTimingExperiment().printResults();
    }

    public NextKWordsTimingExperiment() {
        super(problemSizeDescription,
              problemSizeMin, problemSizeCount, problemSizeStep,
              experimentIterationCount);
    }

    @Override
    protected void setupExperiment(int k) {
    	this.currentProblemSize = k;

        // 1) Generate a fixed‐size input file once per iteration:
        filePath = "fixed_input.txt";
        try (PrintWriter out = new PrintWriter(new File(filePath))) {
            for (int i = 0; i < INPUT_WORD_COUNT; i++) {
                out.print((i % INPUT_VOCAB_SIZE + 1) + " ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 2) Build the library (not timed)
        generator = new Generator();
        try {
            generator.createLibraryFromFile(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void runComputation() {
        // Time how long it takes to generate k words in specified mode
    	for(int i = 0; i < 5_000; i++) {
    		  generator.generateText("1", 1_000, "probable");
    		}
        generator.generateText("1", currentProblemSize, "probable");
}
    
}
