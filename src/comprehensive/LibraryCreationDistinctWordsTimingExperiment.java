package comprehensive;

import timing.TimingExperiment;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Experiment to measure library‐creation time while varying the number
 * of distinct words in the input file.
 */
public class LibraryCreationDistinctWordsTimingExperiment extends TimingExperiment {
    private static final String problemSizeDescription = "distinct word count";
    private static final int problemSizeMin            = 100000;
    private static final int problemSizeCount          = 10;
    private static final int problemSizeStep           = 100000;
    private static final int experimentIterationCount  = 20;

    private String filePath;
    private Generator generator;

    public static void main(String[] args) {
        new LibraryCreationDistinctWordsTimingExperiment().printResults();
    }

    public LibraryCreationDistinctWordsTimingExperiment() {
        super(problemSizeDescription,
              problemSizeMin, problemSizeCount, problemSizeStep,
              experimentIterationCount);
    }

    @Override
    protected void setupExperiment(int problemSize) {
        // Generate a file with `problemSize` unique numeric "words"
        filePath = "distinct_" + problemSize + ".txt";
        try (PrintWriter out = new PrintWriter(new File(filePath))) {
            for (int i = 1; i <= problemSize; i++) {
                out.print(i + " ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        generator = new Generator();
    }

    @Override
    protected void runComputation() {
        try {
            generator.createLibraryFromFile(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
