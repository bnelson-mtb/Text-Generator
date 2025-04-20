package comprehensive;

import timing.*;

public class TextGeneratorTimingExperiment extends TimingExperiment {

	private static String problemSizeDescription = "element count";
    private static int problemSizeMin = 10000;
    private static int problemSizeCount = 10;
    private static int problemSizeStep = 10000;
    private static int experimentIterationCount = 100; 
    	
    
    public static void main(String[] args) {
        TimingExperiment timingExperiment = new TextGeneratorTimingExperiment();
        timingExperiment.printResults();
        System.out.println("Finished!");
    }
	
	public TextGeneratorTimingExperiment() {
		super(problemSizeDescription, problemSizeMin, problemSizeCount, problemSizeStep, experimentIterationCount);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void setupExperiment(int problemSize) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void runComputation() {
		// TODO Auto-generated method stub
		
	}

}
