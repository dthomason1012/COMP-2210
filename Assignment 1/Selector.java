import java.util.Arrays;

/**
* Defines a library of selection methods
* on arrays of ints.
*
* @author   Daniel Thomason (DHT0009@auburn.edu)
* @author   Dean Hendrix (dh@auburn.edu)
* @version  5 September 2019
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
    * Selects the minimum value from the array a. This method
    * throws IllegalArgumentException if a is null or has zero
    * length. The array a is not changed by this method.
    */
   public static int min(int[] a) {
      if (a == null) {
         throw new IllegalArgumentException();
      }
      if (a.length == 0) {
         throw new IllegalArgumentException();
      }
      int min = a[0];
      for (int i = 1; i < a.length; i++) {
         if (a[i] < min) {
            min = a[i];
         }
      }
      return min;
   }


   /**
    * Selects the maximum value from the array a. This method
    * throws IllegalArgumentException if a is null or has zero
    * length. The array a is not changed by this method.
    */
   public static int max(int[] a) {
      if (a == null) {
         throw new IllegalArgumentException();
      }
      if (a.length == 0) {
         throw new IllegalArgumentException();
      }
      int max = a[0];
      for (int i = 1; i < a.length; i++) {
         if (a[i] > max) {
            max = a[i];
         }
      }
      return max;
   }


   /**
    * Selects the kth minimum value from the array a. This method
    * throws IllegalArgumentException if a is null, has zero length,
    * or if there is no kth minimum value. Note that there is no kth
    * minimum value if k < 1, k > a.length, or if k is larger than
    * the number of distinct values in the array. The array a is not
    * changed by this method.
    */
   public static int kmin(int[] a, int k) {
      if (a == null) {
         throw new IllegalArgumentException();
      }
      if (a.length == 0) {
         throw new IllegalArgumentException();
      }
      if (k < 1) {
         throw new IllegalArgumentException();
      }
      if (k > a.length) {
         throw new IllegalArgumentException();
      }
      int[] a2 = Arrays.copyOf(a, a.length);
      Arrays.sort(a2);
      int dpls = 0;
      int unq = 0;
      
      for (int i = 0; i < a2.length - 1; i++) {
         if (a2[i] == a2[i + 1]) {
            dpls++;
         }
      }
      
      unq = a2.length - dpls;
      
      if (k > unq) {
         throw new IllegalArgumentException();
      }
      
      int[] uniqueValues = Arrays.copyOf(a2, a2.length);
      int j = 0;
      int i = 1;
      
      while (i < uniqueValues.length) {
         if (uniqueValues[i] == uniqueValues[j]) {
            i++;
         }
         else {
            j++;
            uniqueValues[j] = uniqueValues[i];
            i++;
         }
      }
      
      return uniqueValues[k - 1];
   }


   /**
    * Selects the kth maximum value from the array a. This method
    * throws IllegalArgumentException if a is null, has zero length,
    * or if there is no kth maximum value. Note that there is no kth
    * maximum value if k < 1, k > a.length, or if k is larger than
    * the number of distinct values in the array. The array a is not
    * changed by this method.
    */
   public static int kmax(int[] a, int k) {
      if (a == null) {
         throw new IllegalArgumentException();
      }
      if (a.length == 0) {
         throw new IllegalArgumentException();
      }
      if (k < 1) {
         throw new IllegalArgumentException();
      }
      if (k > a.length) {
         throw new IllegalArgumentException();
      }
      int[] a2 = Arrays.copyOf(a, a.length);
      Arrays.sort(a2);
      int dpls = 0;
      int unq = 0;
      
      for (int i = 0; i < a2.length - 1; i++) {
         if (a2[i] == a2[i + 1]) { 
            dpls++;
         }
      }
      
      unq = a2.length - dpls;
      
      if (k > unq) {
         throw new IllegalArgumentException();
      }
      
      int[] uniqueValues = Arrays.copyOf(a2, a2.length);
      int j = 0;
      int i = 1;
      
      while (i < uniqueValues.length) {
         if (uniqueValues[i] == uniqueValues[j]) {
            i++;
         }
         else {
            j++;
            uniqueValues[j] = uniqueValues[i];
            i++;
         }
      }
      
      return uniqueValues[unq - k];
   }


   /**
    * Returns an array containing all the values in a in the
    * range [low..high]; that is, all the values that are greater
    * than or equal to low and less than or equal to high,
    * including duplicate values. The length of the returned array
    * is the same as the number of values in the range [low..high].
    * If there are no qualifying values, this method returns a
    * zero-length array. Note that low and high do not have
    * to be actual values in a. This method throws an
    * IllegalArgumentException if a is null or has zero length.
    * The array a is not changed by this method.
    */
   public static int[] range(int[] a, int low, int high) {
      if (a == null) {
         throw new IllegalArgumentException();
      }
      if (a.length == 0) {
         throw new IllegalArgumentException();
      }
      
      int[] a2 = Arrays.copyOf(a, a.length);
      int j = 0;
      //creates the new length array of values between high & low
      for (int i = 0; i < a.length; i++) {
         if (a[i] >= low && a[i] <= high) {
            a2[j] = a2[i];
            j++;
         }
      }
      
      if (j == 0) {
         int[] b = {};
         return b;
      }
      //returns the new array of length j
      else {
         int[] a3 = Arrays.copyOf(a2, j);
         return a3;
      }
   }


   /**
    * Returns the smallest value in a that is greater than or equal to
    * the given key. This method throws an IllegalArgumentException if
    * a is null or has zero length, or if there is no qualifying
    * value. Note that key does not have to be an actual value in a.
    * The array a is not changed by this method.
    */
   public static int ceiling(int[] a, int key) {
      if (a == null) {
         throw new IllegalArgumentException();
      }
      if (a.length == 0) {
         throw new IllegalArgumentException();
      }
      
      int ceil = 0;
      int j = 0;
      
      for (int i = 0; i < a.length; i++) {
         ceil += Math.abs(a[i]);
      }
      
      for (int i = 0; i < a.length; i++) {
         if (a[i] >= key && a[i] <= ceil) {
            ceil = a[i];
            j++;
         }
      }
      
      if (j == 0) {
         throw new IllegalArgumentException();
      }
      
      return ceil;
   }


   /**
    * Returns the largest value in a that is less than or equal to
    * the given key. This method throws an IllegalArgumentException if
    * a is null or has zero length, or if there is no qualifying
    * value. Note that key does not have to be an actual value in a.
    * The array a is not changed by this method.
    */
   public static int floor(int[] a, int key) {
      if (a == null) {
         throw new IllegalArgumentException();
      }
      if (a.length == 0) {
         throw new IllegalArgumentException();
      }
   
      int floor = 0;
      int j = 0;
         
      for (int i = 0; i < a.length; i++) {
         floor -= Math.abs(a[i]);
      }
      
      for (int i = 0; i < a.length; i++) {
         if (a[i] <= key && a[i] >= floor) {
            floor = a[i];
            j++;
         }
      }
      
      if (j == 0) {
         throw new IllegalArgumentException();
      }
      return floor;
   }

}
