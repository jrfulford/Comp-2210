import java.util.Arrays;

/**
* Defines a library of selection methods
* on arrays of ints.
*
* @author   Jack Ryan Fulford (jrf0067@auburn.edu)
* @version  01/20/22
*
*/
public final class Selector {

    /**
     * Can't instantiate this class.
     *
     * D O   N O T   C H A N G E   T H I S   C O N S T R U C T O R
     *
     */
    private Selector() { }
/**
   * The min method returns the minimum element found in an array
   * of ints.
   * @param a - an array of integers.
   * @return min - the minimum elements of the array.
   */
   public static int min(int[] a) {
      if (a == null || a.length == 0) {
         throw new IllegalArgumentException();
      }
      
      int min = a[0];
      for (int element : a) {
         if (element < min) {
            min = element;
         }
      }
      return min;
   }
   
   
   /**
   * The max method returns the maximum element found in an array
   * of ints.
   * @param a - an array of integers.
   * @return max - the maximum elements of the array.
   */
   public static int max(int[] a) {
      if (a == null || a.length == 0) {
         throw new IllegalArgumentException();
      }
      int max = a[0];
      for (int element : a) {
         if (max < element) {
            max = element;
         }
      }
      return max;
   }
   
   /**
   * The unique method is a private method that find the number of
   * unique elements in an array of integers.
   * @param a - the array of integers.
   * @return numUnique - the number of unique elements.
   */
   private static int unique(int[] a) {
      int numUnique = 1;
      int n = a.length;
      for (int i = 1; i < n; i++) {
         if (a[i - 1] == a[i]) {
            continue;
         }
         else
         {
            numUnique++;
         }
      }
      return numUnique;
   }
   
   /**
   * The kmin method selects the k-th minimum value from a given array. A
   * value is the k-th minimum if and only if there are exactly k - 1 distinct
   * values less than it in the array.
   * @param a - the array of integers.
   * @param k - the value of k.
   * @return kMin - the kMin of the array a.
   */
   public static int kmin(int[] a, int k) {
      if (a == null) {
         throw new IllegalArgumentException();
      }
      
      if (a.length == 0) {
         throw new IllegalArgumentException();
      }
      
      if (a.length < k) {
         throw new IllegalArgumentException();
      }
      
      if (k < 1) {
         throw new IllegalArgumentException();
      }
      
      int[] copyA = Arrays.copyOf(a, a.length);
      
      Arrays.sort(copyA);
      
      int distinctValues = unique(copyA);
      
      if (distinctValues < k) {
         throw new IllegalArgumentException();
      }
   
      int kCount = 1;
      int counter = 1;
      int kMin = copyA[0];
      while ( (kCount < k) && (counter < copyA.length)) {
         if (copyA[counter] > kMin) {
            kMin = copyA[counter];
            kCount++;
         }
         counter++;
      }          
      return kMin;
   }
   
   /**
   * The kmax method selects the k-th maximum value from a given array. A
   * value is the k-th minimum if and only if there are exactly k - 1 distinct
   * values greater than it in the array.
   * @param a - the array of integers.
   * @param k - the value of k.
   * @return kMax - the k-th maximum value of the array a.
   */
   public static int kmax(int[] a, int k) {
      if (a == null) {
         throw new IllegalArgumentException();
      }
      
      if (a.length == 0) {
         throw new IllegalArgumentException();
      }
      
      if (a.length < k) {
         throw new IllegalArgumentException();
      }
      
      if (k < 1) {
         throw new IllegalArgumentException();
      }
      
      int[] copyA = Arrays.copyOf(a, a.length);
      
      Arrays.sort(copyA);
      int distinctValues = unique(copyA);
      
      if (distinctValues < k) {
         throw new IllegalArgumentException();
      }
      
      int kCount = 1;
      int counter = copyA.length - 1;
      int kMax = copyA[copyA.length  - 1];
      while ( (kCount < k) && (counter > -1) ) {
         if (copyA[counter] < kMax) {
            kMax = copyA[counter];
            kCount++;
         }
         counter--;
      }
      return kMax;
   }
   
   /**
   * The range method selects all values from a given array that
   * are greater than or equal to low and less than or equal to high.
   *
   * @param a - the array of integers.
   * @param low - the lowerbound of the range.
   * @param high - the upperbound of the range.
   * @param arrWithRange - the array that holds all of the elements
   *        of a between low and high.
   */
   public static int[] range(int[] a, int low, int high) {
      if ( (a == null) || (a.length ==0) ) {
         throw new IllegalArgumentException();
      }
   
      int limit = 0;
      
      for (int element : a) {
         if (low <= element && element <= high) {
            limit++;
         }
      }
      
      if (limit < 1) {
         int arrWithRange[] = {};
         return arrWithRange;
      }
      
      int arrWithRange[] = new int[limit];
      
      int counter = 0;
      for (int element : a) {
         if (low <= element && element <= high) {
            arrWithRange[counter] = element;
            counter++;
         }
      }
      return arrWithRange;
   }

   /**
   * The ceiling method selects from a given array the smallest value that
   * is greater than or equal to key.
   * @param a - the array of integers.
   * @param key - the lower bound.
   * @return the smallest value that is greater than or equal to key.
   */
   public static int ceiling(int[] a, int key) {
      if ( (a == null) || (a.length == 0) ) {
         throw new IllegalArgumentException();
      }
      
      int count = 0;
      for (int element : a) {
         if (key <= element) {
            count++;
         }
      }
      
      int greaterThanKey[] = new int[count];
      count = 0;
      for (int element : a) {
         if (key <= element) {
            greaterThanKey[count] = element;
            count++;
         }
      }
      return min(greaterThanKey);
   }
   
   /**
   * The floor method selects from a given array the largest value
   * that is less than or equal to key.
   * @param a - the array of integers.
   * @param key - the upper bound
   * @return the largest value that is less than or equal to key.
   */
   public static int floor(int[] a, int key) {
      if ( (a == null) || (a.length == 0) ) {
         throw new IllegalArgumentException();
      }
      
      int count = 0;
      for (int element : a) {
         if (element <= key) {
            count++;
         }
      }
      
      int lessThanKey[] = new int[count];
      count = 0;
      for (int element : a) {
         if (element <= key) {
            lessThanKey[count] = element;
            count++;
         }
      }
      return max(lessThanKey);
   }
}