/**
 * ListFactory.java.
 * Implements the factory method pattern (https://en.wikipedia.org/wiki/Factory_method_pattern)
 * for lists in this assignment.
 *
 * @author Daniel Thomason (dht0009auburn.edu)
 * @author Dean Hendrix (dh@auburn.edu)
 * @version 2019-10-20
 */
public class ListFactory {

   /**
    * Return an instance of a class that implements RandomizedList.
    */
   public static <T> RandomizedList<T> makeRandomizedList() {
      // Replace the following return statement with one that returns
      // an instance of the class you wrote to implement the
      // RandomizedList interface.
      return new RandomArrays<T>();
   }

   /**
    * Return an instance of a class that implements DoubleEndedList.
    */
   public static <T> DoubleEndedList<T> makeDoubleEndedList() {
      // Replace the following return statement with one that returns
      // an instance of the class you wrote to implement the
      // DoubleEndedList interface.
      return new NodeDoubleEndedList<T>();
   }

}
