import java.util.Comparator;
public class Term implements Comparable<Term> {
   private String query;
   private long weight;
   
    /** CONSTRUCTOR
     * Initialize a term with the given query and weight.
     * This method throws a NullPointerException if query is null,
     * and an IllegalArgumentException if weight is negative.
     *
     * @param query - the word in the word, weight pair.
     * @param weight - records the total number of ocurrences of the
     *     associated word in some large English text.
     */
   public Term(String query, long weight) {
      if (query == null) {
         throw new NullPointerException();
      }
      if (weight < 0) {
         throw new IllegalArgumentException();
      }
      this.query = query;
      this.weight = weight;
    }

    /**
     * Compares the two terms in descending order of weight.
     *
     * @return a comparator that sorts elements in descending order of weight.
     */
   public static Comparator<Term> byDescendingWeightOrder() {
      return new ComparatorDescendingWeightOrder(); 
   }

    /**
     * Compares the two terms in ascending lexicographic order of query,
     * but using only the first length characters of query. This method
     * throws an IllegalArgumentException if length is less than or equal
     * to zero.
     *
     * @param length - the number of letters in the prefix.
     * @return a comparator with a type parameter of type Term.
     */
   public static Comparator<Term> byPrefixOrder(int length) {
      if (length <= 0) {
         throw new IllegalArgumentException();
      }
      return new ComparatorPrefixOrder(length);
   }

    /**
     * Compares this term with the other term in ascending lexicographic order
     * of query.
     *
     * @param other - the other object of type Term used for comparison.
     * @return an integer representing greater than, less than or equal to.
     */
   @Override
   public int compareTo(Term other) { 
      return this.query.compareTo(other.query);
   } 

    /**
     * Returns a string representation of this term in the following format:
     * query followed by a tab followed by weight
     *
     * @return a string representation of Term.
     */
   @Override
   public String toString(){
      return this.query + "\t" + this.weight;
   }
   
   /**
   * This class defines a comparator used to establish a total ordering based on
   * query weights.
   */
   private static class ComparatorDescendingWeightOrder implements Comparator<Term> {
      @Override
      public int compare(Term t1, Term t2) {
         if (t2.weight < t1.weight) return -1;
         if (t2.weight > t1.weight) return 1;
         return 0;
      }
   }
   
   /**
   * This class defines a comparator used to establish a total ordering based
   * prefix length.
   */
   private static class ComparatorPrefixOrder implements Comparator<Term> {
      private int length;
      private ComparatorPrefixOrder(int length) {
         this.length = length;
      }
      @Override
      public int compare(Term t1, Term t2) {
         String thisPrefix, otherPrefix;
         if (t1.query.length() < length) {
            thisPrefix = t1.query;
         } else {
            thisPrefix = t1.query.substring(0, length);
         }
         
         if (t2.query.length() < length) {
            otherPrefix = t2.query;
         } else {
            otherPrefix = t2.query.substring(0, length);
         }
         return thisPrefix.compareTo(otherPrefix);
      }
   }

}