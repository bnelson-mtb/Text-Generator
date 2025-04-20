package comprehensive;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import timing.TimingExperiment;

/**
 * Experiment to measure library‐creation time while varying the
 * total length of the input file, with a small fixed vocabulary.
 */
public class LibraryCreationFileLengthTimingExperiment extends TimingExperiment {
    private static final String problemSizeDescription = "file word count";
    private static final int problemSizeMin            =  100000;
    private static final int problemSizeCount          =  10;
    private static final int problemSizeStep           =  100000;
    private static final int experimentIterationCount  =  20;
    // Keep vocabulary small (e.g., 10 distinct words)
    private static final int VOCAB_SIZE = 10;

    private String filePath;
    private Generator generator;

    public static void main(String[] args) {
        new LibraryCreationFileLengthTimingExperiment().printResults();
    }

    public LibraryCreationFileLengthTimingExperiment() {
        super(problemSizeDescription,
              problemSizeMin, problemSizeCount, problemSizeStep,
              experimentIterationCount);
    }

    @Override
    protected void setupExperiment(int problemSize) {
        // Generate a file of `problemSize` words cycling through 1..VOCAB_SIZE
        filePath = "length_" + problemSize + ".txt";
        try (PrintWriter out = new PrintWriter(new File(filePath))) {
            for (int i = 0; i < problemSize; i++) {
                out.print((i % VOCAB_SIZE + 1) + " ");
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
