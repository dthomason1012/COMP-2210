import java.io.File;
import java.io.IOException;

/**
 * TextGenerator.java. Creates an order K Markov model of the supplied source
 * text, and then outputs M characters generated according to the model.
 *
 * @author     Daniel Thomason (dht0009@auburn.edu)
 * @author     Dean Hendrix (dh@auburn.edu)
 * @version    2018-04-17
 *
 */
public class TextGenerator {

   /** Drives execution. */
   public static void main(String[] args) {

      if (args.length < 3) {
         System.out.println("Usage: java TextGenerator k length input");
         return;
      }

      // No error checking! You may want some, but it's not necessary.
      int K = Integer.parseInt(args[0]);
      int M = Integer.parseInt(args[1]);
      if ((K < 0) || (M < 0)) {
         System.out.println("Error: Both K and M must be non-negative.");
         return;
      }

      File text;
      try {
         text = new File(args[2]);
         if (!text.canRead()) {
            throw new Exception();
         }
      }
      catch (Exception e) {
         System.out.println("Error: Could not open " + args[2] + ".");
         return;
      }


      // instantiate a MarkovModel with the supplied parameters and
      // generate sample output text ...

      MarkovModel out = new MarkovModel(K, text);
      String kgram = out.getRandomKgram();

      String output = "" + kgram;
      while (output.length() < M) {
          char c = out.getNextChar(kgram);
          output += c;
          kgram = kgram.substring(1, kgram.length()) + c;
      }
      if (output.length() > M) {
          output = output.substring(0, M);
      }
      System.out.println(output);
  }
}
