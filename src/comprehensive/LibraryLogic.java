package comprehensive;

/**
 * This class uses a HashMap to store words as keys and WordEntrys as the values. 
 *
 * << HOW THE CLASS WORKS >>
 * We have two pointers, one pointing to the first word (current) and the next word (next)
 * // As we scan the file, we look at each word. FOr each word:
 * Look at the word 'current' points to.
 * | 1. In the Library, if it does not exist, create a new entry containing the word. Otherwise, increment the number of times the word occurs by 1.
 * Look at the word 'next' points to.
 * | 2. Within the AdjacentWords, repeat the process described step 1.
 * Update the pointers of 'current' and 'next' to be located to the next word that occurs in the list.
 * 
 * @author Kent Wilkison and Brady Nelson
 * @version 4/13/25
 */
public class LibraryLogic {

}
