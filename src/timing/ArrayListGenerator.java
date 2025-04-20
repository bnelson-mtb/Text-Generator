package timing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 * This class contains methods for generating ArrayLists<> of various sizes and orderings.
 * 
 * @author CS 2420 course staff and Kent Wilkison
 * @version 2/18/25
 */
public class ArrayListGenerator {

	private static final Random rng = new Random();

	/**
	 * Generates an array with problemSize random integers in nearly ascending order.
	 *
	 * @implNote calls generateAscendingArray and then swaps a small number of
	 *           random pairs of nearby elements
	 * @param problemSize - size of the array
	 */
	public static ArrayList<Integer> generateNearlyAscendingArray(int problemSize) {
		ArrayList<Integer> array = generateAscendingArray(problemSize);
		slightlyShuffleArray(array);
		return array;
	}

	/**
	 * Generates an array with problemSize random integers in a permuted order.
	 *
	 * @implNote calls generateAscendingArray and then shuffles the contents of the
	 *           array
	 * @param problemSize - size of the array
	 */
	public static ArrayList<Integer> generatePermutedArray(int problemSize) {
		ArrayList<Integer> array = generateAscendingArray(problemSize);
		shuffleArray(array);
		return array;
	}

	/**
	 * Generates an array with problemSize random integers in descending order.
	 *
	 * @implNote calls generateAscendingArray and then reverses the contents of the
	 *           array
	 * @param problemSize - size of the array
	 */
	public static ArrayList<Integer> generateDescendingArray(int problemSize) {
		ArrayList<Integer> array = generateAscendingArray(problemSize);
		ArrayList<Integer> arrayFlipped = new ArrayList<>(problemSize);
		
		for (int i = 0; i < array.size(); i++)
			arrayFlipped.add(null);

		for (int i = 0; i < array.size(); i++)
			arrayFlipped.set(array.size() - i - 1, array.get(i));
		
		return arrayFlipped;
		
	}

	/**
	 * Generates an array with problemSize random integers in ascending order.
	 *
	 * @implNote integers are bounded between 0 and 20 + 10 * problemSize
	 * @param problemSize - size of the array
	 */
	static ArrayList<Integer> generateAscendingArray(int problemSize) {
		ArrayList<Integer> array = new ArrayList<Integer>(problemSize);
		int currentElement = rng.nextInt(20);
		
		for(int i = 0; i < problemSize; i++) {
			array.add(currentElement);
			currentElement += rng.nextInt(10);
		}
		
		return array;
	}

	/**
	 * Slightly shuffles the contents of the given array, such that it is in nearly
	 * ascending order, by swapping a small number of random pairs of nearby
	 * elements. The number of swaps is small, assuming the array contains a large
	 * number elements.
	 * 
	 * @param array to be shuffled slightly
	 */
	private static void slightlyShuffleArray(ArrayList<Integer> array) {
		// Choose a random number of pairs to swap, between 5 and 19
		int swapCount = 5 + rng.nextInt(15);
		for(int i = 0; i < swapCount; i++) {
			// Choose a random index, excluding the final 11 indices
			int idx1 = rng.nextInt(array.size() - 11);
			// Choose an index between 1 and 10 to the right of idx1
			int idx2 = idx1 + 1 + rng.nextInt(10);
			// Swap the entries at those two indices
			swapArrayListElements(array, idx1, idx2);
		}
	}

	/**
	 * Shuffles the contents of the given array.
	 * 
	 * @param array to be shuffled
	 */
	private static void shuffleArray(ArrayList<Integer> array) {
		Collections.shuffle(array);
	}

	/**
	 * Swaps two elements in the given array.
	 *
	 * @param array       with elements to be swapped
	 * @param firstIndex  to swap
	 * @param secondIndex to swap
	 * @throws IndexOutOfBoundsException if either index is out of bounds
	 */
	private static void swapArrayListElements(ArrayList<Integer> array, int firstIndex, int secondIndex) {
		if(firstIndex < 0 || firstIndex >= array.size() || secondIndex < 0 || secondIndex >= array.size()) {
			throw new IndexOutOfBoundsException();
		}

		int temp = array.get(firstIndex);
		array.set(firstIndex, array.get(secondIndex));
		array.set(secondIndex, temp);
	}
	
	/**
	 * Generates an ArrayList of problemSize random integers in nearly ascending order, with each element unique.
	 *
	 * @implNote calls generateAscendingArrayListWithoutDuplicates and then swaps a small number of random pairs of nearby elements
	 * @param problemSize - size of the list
	 */
	public static ArrayList<Integer> generateNearlyAscendingArrayListWithoutDuplicates(int problemSize) {
		ArrayList<Integer> array = generateAscendingArrayListWithoutDuplicates(problemSize);
		slightlyShuffleArray(array);
		return array;
	}
		
	/**
	 * Generates an ArrayList of problemSize random integers in a permuted order, with each element unique.
	 *
	 * @implNote calls generateAscendingArrayListWithoutDuplicates and then shuffles the contents of the list
	 * @param problemSize - size of the list
	 */
	public static ArrayList<Integer> generatePermutedArrayListWithoutDuplicates(int problemSize) {
		ArrayList<Integer> array = generateAscendingArrayListWithoutDuplicates(problemSize);
		shuffleArray(array);
		return array;
	}

	/**
	 * Generates an ArrayList of problemSize random integers in ascending order, with each element unique.
	 *
	 * @param problemSize - size of the list
	 */
	private static ArrayList<Integer> generateAscendingArrayListWithoutDuplicates(int problemSize) {
	    ArrayList<Integer> list = new ArrayList<Integer>();
	    int currentElement = rng.nextInt(20);
	    for(int i = 0; i < problemSize; i++) {
	        list.add(currentElement);
	        currentElement += rng.nextInt(1, 10);
	    }
	    return list;
	}
	
	/**
	 * Generates an ArrayList of problemSize random integers in a descending, delta-sorted order.
	 * 
	 * @implNote calls generateDescendingArray and then shuffles sub-lists of size delta + 1.
	 * @param problemSize - size of the list
	 * @param delta - parameter for delta sorting
	 */
	public static ArrayList<Integer> generateDescendingDeltaSortedArrayList(int problemSize, int delta) {
	    ArrayList<Integer> list = generateDescendingArray(problemSize);
	    for(int start = 0; start < problemSize; start += delta + 1) {
	        int end = Math.min(start + delta + 1, problemSize);
	        for(int i = start; i < end; i++)
	            swapArrayListElements(list, i, rng.nextInt(start, end));
	    }
	    return list;
	}
}