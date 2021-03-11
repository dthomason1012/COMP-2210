import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Extractor.java. Implements feature extraction for collinear points in
 * two dimensional data.
 *
 * @author  Daniel Thomason (DHT0009@auburn.edu)
 * @author  Dean Hendrix (dh@auburn.edu)
 * @version 2019-10-03
 *
 */
public class Extractor {

   /** raw data: all (x,y) points from source data. */
   private Point[] points;

   /** lines identified from raw data. */
   private SortedSet<Line> lines;

   /**
    * Builds an extractor based on the points in the file named by filename.
    */
   public Extractor(String filename) {

      try {
         Scanner sc = new Scanner(new File(filename));
         int n = sc.nextInt();
         points = new Point[n];

         for (int i = 0; i < n; i++) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            points[i] = new Point(x, y);
         }
      } catch (java.io.IOException e) {
         System.out.println("Error reading file.");
      }
   }

   /**
    * Builds an extractor based on the points in the Collection named by pcoll.
    *
    * THIS METHOD IS PROVIDED FOR YOU AND MUST NOT BE CHANGED.
    */
   public Extractor(Collection<Point> pcoll) {
      points = pcoll.toArray(new Point[]{});
   }

   /**
    * Returns a sorted set of all line segments of exactly four collinear
    * points. Uses a brute-force combinatorial strategy. Returns an empty set
    * if there are no qualifying line segments.
    */
   public SortedSet<Line> getLinesBrute() {
      lines = new TreeSet<Line>();
      /*Four nested for loops are used to find all possible combinations
        of the four points */
      for (int i = 0; i < points.length; i++) {
         for (int j = i + 1; j < points.length; j++) {
            for (int k = j + 1; k < points.length; k++) {
               for (int l = k + 1; l < points.length; l++) {
                  if (points[i].slopeTo(points[j])
                          == points[j].slopeTo(points[k])
                          && points[j].slopeTo(points[k])
                          == points[k].slopeTo(points[l])) {
                     Line ln = new Line();
                     ln.add(points[i]);
                     ln.add(points[j]);
                     ln.add(points[k]);
                     ln.add(points[l]);
                     lines.add(ln);
                  }
               }
            }
         }
      }
      return lines;
   }

   /**
    * Returns a sorted set of all line segments of at least four collinear
    * points. The line segments are maximal; that is, no sub-segments are
    * identified separately. A sort-and-scan strategy is used. Returns an empty
    * set if there are no qualifying line segments.
    */
   public SortedSet<Line> getLinesFast() {
      lines = new TreeSet<Line>();
      Line ln = new Line();
      Point[] points2 = Arrays.copyOf(points, points.length);
      for (int i = 0; i < points.length; i++) {
         Arrays.sort(points2, points[i].slopeOrder);
         for (int j = 0; j < points.length; j++) {
            ln.add(points2[0]);
            if (ln.add(points2[j]) == false) {
               if (ln.length() >= 4) {
                  lines.add(ln);
               }
               ln = new Line();
               ln.add(points2[j]);
            }
         }
      }
      return lines;
   }
}
