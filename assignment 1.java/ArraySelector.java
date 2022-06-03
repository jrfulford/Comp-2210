import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Defines a library of selection methods on Collections.
 *
 * @author  Jack Ryan Fulford (jrf0067@auburn.edu)
 * @author  Dean Hendrix (dh@auburn.edu)
 * @version 09 Feburary 2022
 *
 */
public final class Selector {

   /**
   * Can't instantiate this class.
   */
   private Selector() {};
   
    /**
     * Returns the minimum value in the Collection coll as defined by the
     * Comparator comp. If either coll or comp is null, this method throws an
     * IllegalArgumentException. If coll is empty, this method throws a
     * NoSuchElementException. This method will not change coll in any way.
     *
     * @param coll    the Collection from which the minimum is selected
     * @param comp    the Comparator that defines the total order on T
     * @return        the minimum value in coll
     * @throws        IllegalArgumentException as per above
     * @throws        NoSuchElementException as per above
     */   public static <T> T min(Collection<T> coll, Comparator<T> comp) {
      if (coll == null) {
         throw new IllegalArgumentException();
      }
      
      if (comp == null) {
         throw new IllegalArgumentException();
      }
      
      if (coll.isEmpty()) {
         throw new NoSuchElementException();
      }
      
      Iterator<T> itr = coll.iterator();
      T min = itr.next();
      T item;
      while(itr.hasNext()) {
         item = itr.next();
         if (comp.compare(item, min) < 0) {
            min = item;
         }
      }
      
      return min;
   }
   
    /**
     * Selects the maximum value in the Collection coll as defined by the
     * Comparator comp. If either coll or comp is null, this method throws an
     * IllegalArgumentException. If coll is empty, this method throws a
     * NoSuchElementException. This method will not change coll in any way.
     *
     * @param coll    the Collection from which the maximum is selected
     * @param comp    the Comparator that defines the total order on T
     * @return        the maximum value in coll
     * @throws        IllegalArgumentException as per above
     * @throws        NoSuchElementException as per above
     */   public static <T> T max(Collection<T> coll, Comparator<T> comp) {
      if (coll == null) {
         throw new IllegalArgumentException();
      }
      
      if (comp == null) {
         throw new IllegalArgumentException();
      }
      
      if (coll.isEmpty()) {
         throw new NoSuchElementException();
      }
      
      Iterator<T> itr = coll.iterator();
      T max = itr.next();
      T item;
      while(itr.hasNext()) {
         item = itr.next();
         if (comp.compare(item, max) > 0) {
            max = item;
         }
      }
      return max;
   }
   
   /**
   * Counts the number of distinct object in the Collection<T>.
   *
   * @param coll   The collection with the objects inside
   * @return       an int representing the distinct number of objects.
   */
   public static <T> int unique(Collection<T> coll) {
      int numUnique = 0;
      int n = coll.size();
      T previousElement = null;
      for(T element : coll) {
         if (!element.equals(previousElement)) {
            numUnique++;
         }
         previousElement = element;
      }
      return numUnique;
      
   }
   
    /**
     * Selects the kth minimum value from the Collection coll as defined by the
     * Comparator comp. If either coll or comp is null, this method throws an
     * IllegalArgumentException. If coll is empty or if there is no kth minimum
     * value, this method throws a NoSuchElementException. This method will not
     * change coll in any way.
     *
     * @param coll    the Collection from which the kth minimum is selected
     * @param k       the k-selection value
     * @param comp    the Comparator that defines the total order on T
     * @return        the kth minimum value in coll
     * @throws        IllegalArgumentException as per above
     * @throws        NoSuchElementException as per above
     */
     public static <T> T kmin(Collection<T> coll, int k, Comparator<T> comp) {
      if (coll == null) {
         throw new IllegalArgumentException();
      }
      
      if (comp == null) {
         throw new IllegalArgumentException();
      }
      
      if (coll.isEmpty()) {
         throw new NoSuchElementException();
      }
   
      if (coll.size() < k) {
         throw new NoSuchElementException();
      }
      
      if (k < 1) {
         throw new NoSuchElementException();
      }
      
      List<T> collCopy = new ArrayList<>(coll);
      
      collCopy.sort(comp);
      Iterator<T> itr = collCopy.iterator();
      int numberOfUniqueElements = unique(collCopy);
      
      if (numberOfUniqueElements < k) {
         throw new NoSuchElementException();
      }
      
      int kCount = 1;
      T kMin = itr.next();
      T currentElement = null;
      while(kCount < k) {
         currentElement = itr.next();
         //if (comp.compare(currentElement, kMin) > 0) {
         if (!currentElement.equals(kMin)) {
            kMin = currentElement;
            kCount++;
         }
      }
      
      return kMin;
   }
   
    /**
     * Selects the kth maximum value from the Collection coll as defined by the
     * Comparator comp. If either coll or comp is null, this method throws an
     * IllegalArgumentException. If coll is empty or if there is no kth maximum
     * value, this method throws a NoSuchElementException. This method will not
     * change coll in any way.
     *
     * @param coll    the Collection from which the kth maximum is selected
     * @param k       the k-selection value
     * @param comp    the Comparator that defines the total order on T
     * @return        the kth maximum value in coll
     * @throws        IllegalArgumentException as per above
     * @throws        NoSuchElementException as per above
     */   public static <T> T kmax(Collection<T> coll, int k, Comparator<T> comp) {
      if (coll == null) {
         throw new IllegalArgumentException();
      }
      
      if (comp == null) {
         throw new IllegalArgumentException();
      }
      
      if (coll.isEmpty()) {
         throw new NoSuchElementException();
      }
   
      if (coll.size() < k) {
         throw new NoSuchElementException();
      }
      
      if (k < 1) {
         throw new NoSuchElementException();
      }
      
      List<T> collCopy = new ArrayList<>(coll);;
      
      collCopy.sort(comp);
      int numberOfUniqueElements = unique(collCopy);
      
      if (numberOfUniqueElements < k) {
         throw new NoSuchElementException();
      }
      
      int kCount = 1;
      int counter = collCopy.size() - 1;
      T kMax = collCopy.get(counter);
      T currentElement = null;
      while(kCount < k) {
         //if (comp.compare(currentElement, kMin) > 0) {
         counter--;
         currentElement = collCopy.get(counter);
         if (!currentElement.equals(kMax)) {
            kMax = currentElement;
            kCount++;
         }
      }
      
      return kMax;
   }
   
    /**
     * Returns a new Collection containing all the values in the Collection coll
     * that are greater than or equal to low and less than or equal to high, as
     * defined by the Comparator comp. The returned collection must contain only
     * these values and no others. The values low and high themselves do not have
     * to be in coll. Any duplicate values that are in coll must also be in the
     * returned Collection. If no values in coll fall into the specified range or
     * if coll is empty, this method throws a NoSuchElementException. If either
     * coll or comp is null, this method throws an IllegalArgumentException. This
     * method will not change coll in any way.
     *
     * @param coll    the Collection from which the range values are selected
     * @param low     the lower bound of the range
     * @param high    the upper bound of the range
     * @param comp    the Comparator that defines the total order on T
     * @return        a Collection of values between low and high
     * @throws        IllegalArgumentException as per above
     * @throws        NoSuchElementException as per above
     */   public static <T> Collection<T> range(Collection<T> coll, T low, T high, Comparator<T> comp) {
      if (coll == null) {
         throw new IllegalArgumentException();
      }
      
      if (coll.isEmpty()) {
         throw new NoSuchElementException();
      }
      
      if (comp == null) {
         throw new IllegalArgumentException();
      }
      
      ArrayList<T> rangeList = new ArrayList<>();
      
      for (T element : coll) {
         if ( (0 <= comp.compare(element, low)) && (comp.compare(element, high) <= 0)) {
            rangeList.add(element);
         }
      }
      
      if (rangeList.isEmpty()) {
         throw new NoSuchElementException();
      }
      
      return rangeList;
   }
   
    /**
     * Returns the largest value in the Collection coll that is less than
     * or equal to key, as defined by the Comparator comp. The value of key
     * does not have to be in coll. If coll or comp is null, this method throws
     * an IllegalArgumentException. If coll is empty or if there is no
     * qualifying value, this method throws a NoSuchElementException. This
     * method will not change coll in any way.
     *
     * @param coll    the Collection from which the floor value is selected
     * @param key     the reference value
     * @param comp    the Comparator that defines the total order on T
     * @return        the floor value of key in coll
     * @throws        IllegalArgumentException as per above
     * @throws        NoSuchElementException as per above
     */   public static <T> T floor(Collection<T> coll, T key, Comparator<T> comp) {
      if (coll == null) {
         throw new IllegalArgumentException();
      }
      
      if (comp == null) {
         throw new IllegalArgumentException();
      }
      
      if (coll.isEmpty()) {
         throw new NoSuchElementException();
      }
      
      ArrayList<T> lessThanList = new ArrayList<>();
      
      for (T element : coll) {
         if (comp.compare(element, key) <= 0) {
            lessThanList.add(element);
         }
      }
      
      if (lessThanList.isEmpty()) {
         throw new NoSuchElementException();
      }
      
      return max(lessThanList, comp);
   }
   
    /**
     * Returns the smallest value in the Collection coll that is greater than
     * or equal to key, as defined by the Comparator comp. The value of key
     * does not have to be in coll. If coll or comp is null, this method throws
     * an IllegalArgumentException. If coll is empty or if there is no
     * qualifying value, this method throws a NoSuchElementException. This
     * method will not change coll in any way.
     *
     * @param coll    the Collection from which the ceiling value is selected
     * @param key     the reference value
     * @param comp    the Comparator that defines the total order on T
     * @return        the ceiling value of key in coll
     * @throws        IllegalArgumentException as per above
     * @throws        NoSuchElementException as per above
     */   public static <T> T ceiling(Collection<T> coll, T key, Comparator<T> comp) {
      if (coll == null) {
         throw new IllegalArgumentException();
      }
      
      if (comp == null) {
         throw new IllegalArgumentException();
      }
      
      if (coll.isEmpty()) {
         throw new NoSuchElementException();
      }
      
      ArrayList<T> greaterThanList = new ArrayList<>();
      
      for ( T element : coll) {
         if (comp.compare(element, key) >= 0) {
            greaterThanList.add(element);
         }
      }
      
      if (greaterThanList.isEmpty()) {
         throw new NoSuchElementException();
      }
      
      return min(greaterThanList, comp);
   }
}