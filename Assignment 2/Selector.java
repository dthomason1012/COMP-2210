import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Defines a library of selection methods on Collections.
 *
 * @author  Daniel Thomason (dht0009@auburn.edu)
 * @author  Dean Hendrix (dh@auburn.edu)
 * @version 19 September 2019
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
    */
   public static <T> T min(Collection<T> coll, Comparator<T> comp) {
      if (coll == null || comp == null) {
         throw new IllegalArgumentException();
      }
      if (coll.isEmpty()) {
         throw new NoSuchElementException();
      }

      Iterator<T> itr = coll.iterator();
      T min = itr.next();

      /* Iterates over collection to find the min value
      by comparing two elements of coll at a time. */
      while (itr.hasNext()) {
         T item = itr.next(); //item is the next element after the current
         if (comp.compare(item, min) < 0) {
            min = item; //if item is less than min, min is reassigned
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
    */
   public static <T> T max(Collection<T> coll, Comparator<T> comp) {
      if (coll == null || comp == null) {
         throw new IllegalArgumentException();
      }
      if (coll.isEmpty()) {
         throw new NoSuchElementException();
      }

      Iterator<T> itr = coll.iterator();
      T max = itr.next();

      /* Iterates over collection to find the max value
      by comparing two elements of coll at a time */
      while (itr.hasNext()) {
         T item = itr.next(); //item is the next element after the current
         if (comp.compare(item, max) > 0) {
            max = item; //if item is greater than max, max is then reassigned
         }
      }

      return max;
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

      if (coll == null || comp == null) {
         throw new IllegalArgumentException();
      }

     //Creates an ArrayList out of the Collection coll
      ArrayList<T> collList = new ArrayList<>(coll);
      java.util.Collections.sort(collList, comp);

      if (collList.isEmpty() || k < 1 || k > collList.size()) {
         throw new NoSuchElementException();
      }

     //ArrayList that will contain the unique values in collList
      ArrayList<T> uniqueValues = new ArrayList<>();

      int dpls = 0;
      int unq = 0;

     //Increments dpls if a duplicate value is found in collList
      for (int i = 0; i < collList.size() - 1; i++) {
         if (collList.get(i).equals(collList.get(i + 1))) {
            dpls++;
         }
      }

      //Calculates number of distinct values in collList
      unq = collList.size() - dpls;

      if (k > unq) {
         throw new NoSuchElementException();
      }

      //Adds unique values to the uniqueValues List
      for (int i = 0; i < collList.size(); i++) {
         uniqueValues.add(collList.get(i));
      }

      int i = 1;
      int j = 0;

      while (i < uniqueValues.size()) {
         if (uniqueValues.get(i).equals(uniqueValues.get(j))) {
            i++;
         }

         else {
            j++;
            uniqueValues.get(j).equals(uniqueValues.get(i));
            i++;
         }
      }

      return uniqueValues.get(k - 1);
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
    */

    //Can use ArrayList and sorting
   public static <T> T kmax(Collection<T> coll, int k, Comparator<T> comp) {

      if (coll == null || comp == null) {
         throw new IllegalArgumentException();
      }

     //Creates an ArrayList from the Collection coll
      ArrayList<T> collList = new ArrayList<>(coll);
      java.util.Collections.sort(collList, comp);

      if (coll.isEmpty() || k < 1 || k > collList.size()) {
         throw new NoSuchElementException();
      }

     //ArrayList containing unique values from collList
      ArrayList<T> uniqueValues = new ArrayList<>();

      int dpls = 0;
      int unq = 0;

      //Adds unique values to the uniqueValues List
      for (int i = 0; i < collList.size() - 1; i++) {
         if (collList.get(i).equals(collList.get(i + 1))) {
            dpls++;
         }
      }

      //Calculates number of distinct values in collList
      unq = collList.size() - dpls;

      if (k > unq) {
         throw new NoSuchElementException();
      }

      //Adds distinct values to uniqueValues
      for (int i = 0; i < collList.size(); i++) {
         uniqueValues.add(collList.get(i));
      }

      int i = 1;
      int j = 0;

      while (i < uniqueValues.size()) {
         if (uniqueValues.get(i).equals(uniqueValues.get(j))) {
            i++;
         }

         else {
            j++;
            uniqueValues.get(j).equals(uniqueValues.get(i));
            i++;
         }
      }

      return uniqueValues.get(unq - k);
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
    */

    //Can use ArrayList
   public static <T> Collection<T> range(Collection<T> coll, T low, T high,
                                         Comparator<T> comp) {

      if (coll == null || comp == null) {
         throw new IllegalArgumentException();
      }
      if (coll.isEmpty()) {
         throw new NoSuchElementException();
      }

      //Creates an ArrayList from the Collection coll
      ArrayList<T> collList = new ArrayList<>(coll);
      //Creates an ArrayList that will contain the values within the range
      ArrayList<T> rangeList = new ArrayList<>();

      //Compares values from collList to each other and to high and low
      for (int i = 0; i < collList.size(); i++) {
         if (comp.compare(collList.get(i), low) >= 0
            && comp.compare(collList.get(i), high) <= 0) {
            rangeList.add(collList.get(i));
         }
      }

      if (rangeList.isEmpty()) {
         throw new NoSuchElementException();
      }

      return rangeList;
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
    */
   public static <T> T ceiling(Collection<T> coll, T key, Comparator<T> comp) {
      if (coll == null || comp == null) {
         throw new IllegalArgumentException();
      }
      if (coll.isEmpty()) {
         throw new NoSuchElementException();
      }

      Iterator<T> itr = coll.iterator();
      T ceil = null;

      boolean found = false;

      //Iterates through coll and compares each item to key
      while (itr.hasNext()) {
         T item = itr.next();
         if (found == false && comp.compare(item, key) >= 0) {
            ceil = item;
            found = true;
         }

         else if (comp.compare(item, key) >= 0
                  && comp.compare(item, ceil) <= 0) {
            ceil = item;
         }
      }

      if (found == false) {
         throw new NoSuchElementException();
      }

      return ceil;
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
    */
   public static <T> T floor(Collection<T> coll, T key, Comparator<T> comp) {
      if (coll == null || comp == null) {
         throw new IllegalArgumentException();
      }
      if (coll.isEmpty()) {
         throw new NoSuchElementException();
      }

      Iterator<T> itr = coll.iterator();
      T floor = null;

      boolean found = false;

      //Iterates through coll and compares each item to key
      while (itr.hasNext()) {
         T item = itr.next();
         if (found == false && comp.compare(item, key) <= 0) {
            floor = item;
            found = true;
         }

         else if (comp.compare(item, key) <= 0
                  && comp.compare(item, floor) >= 0) {
            floor = item;
         }
      }
      if (found == false) {
         throw new NoSuchElementException();
      }
      return floor;
   }

}
