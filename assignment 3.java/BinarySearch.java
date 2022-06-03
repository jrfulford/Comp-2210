import java.util.Arrays;
import java.util.Comparator;

/**
 * Binary search.
 */
public class BinarySearch {

    /**
     * Returns the index of the first key in a[] that equals the search key, 
     * or -1 if no such key exists. This method throws a NullPointerException
     * if any parameter is null.
     *
     * NOTE: This method uses a modified version of binary search used to
     *     locate the first index of a target value in the presence of
     *     duplicates.
     *
     * @param a - a list of Term objects.
     * @param comparator - a comparator used for sorting a.
     * @return left/an integer used to indicated greater than, equal to,
     *     or less than.
     */
    public static <Key> int firstIndexOf(Key[] a, Key key, Comparator<Key> comparator) { 
       if (a == null) throw new NullPointerException();
       if (key == null) throw new NullPointerException();
       if (comparator == null) throw new NullPointerException();
    
       int left = 0, right = a.length, middle;
       boolean found = false;
       while (left < right) {
          middle = (left + right) / 2;
          if (comparator.compare(a[middle], key) < 0) {
             left = middle + 1;
          } else {
             right = middle;
             if (comparator.compare(a[middle], key) == 0) found = true;
          }
       }
       if (found) {
          return left;
       } else {
          return -1;
       }
    }

    /**
     * Returns the index of the last key in a[] that equals the search key, 
     * or -1 if no such key exists. This method throws a NullPointerException
     * if any parameter is null.
     *
     * NOTE: This method uses a modified version of binary search used to
     *     locate the last index of a target in the presence of duplicates.
     *
     * @param a - a list of Term objects.
     * @param comparator - a comparator used for sorting a.
     * @return left/an integer used to indicated greater than, equal to,
     *     or less than.
     */
    public static <Key> int lastIndexOf(Key[] a, Key key, Comparator<Key> comparator) {
       if (a == null) throw new NullPointerException();
       if (key == null) throw new NullPointerException();
       if (comparator == null) throw new NullPointerException();
    
       int left = 0, right = a.length, middle;
       boolean found = false;
       while (left < right) {
          middle = (left + right) / 2;
          if (comparator.compare(a[middle], key) > 0) {
             right = middle;
          } else {
             left = middle + 1;
             if (comparator.compare(a[middle], key) == 0) found = true;
          }
       }
       if (found) {
          return right - 1;
       } else {
          return -1;
       }
    }
}