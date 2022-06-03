import java.util.Arrays;
public class Autocomplete {
   private Term[] elements;
   
	/** Constructor.
	 * Initializes a data structure from the given array of terms.
	 * This method throws a NullPointerException if terms is null.
    *
    * @param terms - an array of object of type Term.
	 */
	public Autocomplete(Term[] terms) { 
      if (terms == null) throw new NullPointerException();
      this.elements = terms;
      Arrays.sort(elements);
   }

	/**
	 * Returns all terms that start with the given prefix, in descending order of weight. 
	 * This method throws a NullPointerException if prefix is null.
    *
    * @param prefix - the prefix to search the field elements for.
    * @return matches - an array of matching Term objects.
	 */
	public Term[] allMatches(String prefix) { 
      if (prefix == null) throw new NullPointerException();
      Term prefixTerm = new Term(prefix, 0);
      int lengthOfPrefix = prefix.length();
      int firstMatch = BinarySearch.<Term>firstIndexOf(this.elements, prefixTerm,
                       Term.byPrefixOrder(lengthOfPrefix));
                       
      int lastMatch = BinarySearch.<Term>lastIndexOf(this.elements, prefixTerm,
                       Term.byPrefixOrder(lengthOfPrefix));
      
      Term[] matches = new Term[lastMatch - firstMatch + 1];           
      for (int i = firstMatch; i < lastMatch + 1; i++) {
           matches[i - firstMatch] = elements[i];
      }
      Arrays.sort(matches, Term.byDescendingWeightOrder());
      return matches;
   }
}
