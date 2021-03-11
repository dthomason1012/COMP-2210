import java.io.File;
import java.util.HashMap;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.ArrayList;
import java.lang.StringBuilder;
import java.util.Iterator;

/**
 * MarkovModel.java Creates an order K Markov model of the supplied source
 * text. The value of K determines the sie of the "kgrams" used to generate
 * the model. A kgram is a sequence of k consecutive characters in the source
 * text.
 *
 * @author     Daniel Thomason (dht0009@auburn.edu)
 * @author     Dean Hendrix (dh@auburn.edu)
 * @version    2018-04-17
 *
 */
public class MarkovModel {

   // Map of <kgram, chars following> pairs that stores the Markov model.
   private HashMap<String, String> model;

   // add other fields as you need them ...
   private int kVal;
   private String input;
   /**
    * Reads the contents of the file sourceText into a string, then calls
    * buildModel to construct the order K model.
    *
    * DO NOT CHANGE THIS CONSTRUCTOR.
    *
    */
   public MarkovModel(int K, File sourceText) {
      model = new HashMap<>();
      try {
         String text = new Scanner(sourceText).useDelimiter("\\Z").next();
         buildModel(K, text);
      }
      catch (IOException e) {
         System.out.println("Error loading source text: " + e);
      }
   }


   /**
    * Calls buildModel to construct the order K model of the string sourceText.
    *
    * DO NOT CHANGE THIS CONSTRUCTOR.
    *
    */
   public MarkovModel(int K, String sourceText) {
      model = new HashMap<>();
      buildModel(K, sourceText);
   }


   /**
    * Builds an order K Markov model of the string sourceText.
    */
   private void buildModel(int K, String sourceText) {
       kVal = K;
       input = sourceText;
       while (input.length() > kVal) {
           String kgram = getFirstKgram();
           String following = "";
           for (int i = 0; i < input.length() - kVal; i++) {
               String inst = input.substring(i, i + kVal);
               if (inst.equals(kgram)) {
                   char add = input.charAt(i + kVal);
                   following += add;
               }
           }
           if (!model.containsKey(kgram)) {
               model.put(kgram, following);
           }
           input = input.substring(1, input.length());
       }
       input = sourceText;
   }


   /** Returns the first kgram found in the source text. */
   public String getFirstKgram() {
       return input.substring(0, kVal);
   }


   /** Returns a kgram chosen at random from the source text. */
   public String getRandomKgram() {
       Random r = new Random();
       int randomIndex = r.nextInt(input.length());
       while ((randomIndex + kVal) > input.length()) {
           randomIndex = new Random().nextInt(input.length());
       }
       return input.substring(randomIndex, randomIndex + kVal);
   }


   /**
    * Returns the set of kgrams in the source text.
    *
    * DO NOT CHANGE THIS METHOD.
    *
    */
    public Set<String> getAllKgrams() {
      return model.keySet();
   }


   /**
    * Returns a single character that follows the given kgram in the source
    * text. This method selects the character according to the probability
    * distribution of all characters that follow the given kgram in the source
    * text.
    */
   public char getNextChar(String kgram) {
       if (kgram.length() == 0 || !model.containsKey(kgram)) {
           return '\u0000';
       }
       String all = model.get(kgram);
       int ind = new Random().nextInt(all.length());
       char next = all.charAt(ind);
       return next;
   }


   /**
    * Returns a string representation of the model.
    * This is not part of the provided shell for the assignment.
    *
    * DO NOT CHANGE THIS METHOD.
    *
    */
    @Override
    public String toString() {
      return model.toString();
   }

}
