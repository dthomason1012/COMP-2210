import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.Arrays;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;

import java.util.stream.Collectors;

/**
 * Provides an implementation of the WordLadderGame interface.
 *
 * @author Daniel Thomason (dht0009@auburn.edu)
 * @author Dean Hendrix (dh@auburn.edu)
 * @version 2019-03-29
 */
public class Doublets implements WordLadderGame {

    // The word list used to validate words.
    // Must be instantiated and populated in the constructor.
    /////////////////////////////////////////////////////////////////////////////
    // DECLARE A FIELD NAMED lexicon HERE. THIS FIELD IS USED TO STORE ALL THE //
    // WORDS IN THE WORD LIST. YOU CAN CREATE YOUR OWN COLLECTION FOR THIS     //
    // PURPOSE OF YOU CAN USE ONE OF THE JCF COLLECTIONS. SUGGESTED CHOICES    //
    // ARE TreeSet (a red-black tree) OR HashSet (a closed addressed hash      //
    // table with chaining).
    /////////////////////////////////////////////////////////////////////////////
    private List<Node> path;
    private HashSet<String> visited;
    private Deque<String> sol;
    private TreeSet<String> lexicon;
    /**
     * Instantiates a new instance of Doublets with the lexicon populated with
     * the strings in the provided InputStream. The InputStream can be formatted
     * in different ways as long as the first string on each line is a word to be
     * stored in the lexicon.
     */
    public Doublets(InputStream in) {
        try {
            //////////////////////////////////////
            // INSTANTIATE lexicon OBJECT HERE  //
            //////////////////////////////////////
            lexicon = new TreeSet<String>();
            Scanner s =
                new Scanner(new BufferedReader(new InputStreamReader(in)));
            while (s.hasNext()) {
                String str = s.next();
                /////////////////////////////////////////////////////////////
                // INSERT CODE HERE TO APPROPRIATELY STORE str IN lexicon. //
                /////////////////////////////////////////////////////////////
                lexicon.add(str.toLowerCase());
                s.nextLine();
            }
            in.close();
        }
        catch (java.io.IOException e) {
            System.err.println("Error reading from InputStream.");
            System.exit(1);
        }
    }


    //////////////////////////////////////////////////////////////
    // ADD IMPLEMENTATIONS FOR ALL WordLadderGame METHODS HERE  //
    //////////////////////////////////////////////////////////////
    /**
     * Returns the Hamming distance between two strings, str1 and str2. The
     * Hamming distance between two strings of equal length is defined as the
     * number of positions at which the corresponding symbols are different. The
     * Hamming distance is undefined if the strings have different length, and
     * this method returns -1 in that case. See the following link for
     * reference: https://en.wikipedia.org/wiki/Hamming_distance
     *
     * @param  str1 the first string
     * @param  str2 the second string
     * @return      the Hamming distance between str1 and str2 if they are the
     *                  same length, -1 otherwise
     */
    @Override
    public int getHammingDistance(String str1, String str2) {
        int hamDis = 0;
        if (str1.length() != str2.length()) {
            return -1;
        }
        else {
            for (int i = 0; i < str1.length(); i++) {
                if (str1.toLowerCase().charAt(i) != str2.toLowerCase().charAt(i)) {
                    hamDis++;
                }
            }
        }
        return hamDis;
    }

    /**
     * Returns a minimum-length word ladder from start to end. If multiple
     * minimum-length word ladders exist, no guarantee is made regarding which
     * one is returned. If no word ladder exists, this method returns an empty
     * list.
     *
     * Breadth-first search must be used in all implementing classes.
     *
     * @param  start  the starting word
     * @param  end    the ending word
     * @return        a minimum length word ladder from start to end
     */
    @Override
    public List<String> getMinLadder(String start, String end) {
        List<String> minLadder = new ArrayList<String>();
        List<String> emptyList = new ArrayList<String>();
        start = start.toLowerCase();
        end = end.toLowerCase();
        Iterator<String> itr = sol.iterator();
        if (start == end) {
            minLadder.add(start);
            minLadder.add(end);
            return minLadder;
        }
        if (isWord(start) && isWord(end)) {
            breadthFirstSearch(start, end, lexicon);
            while (itr.hasNext()) {
                minLadder.add(itr.next());
            }
            return minLadder;
        }
        return emptyList;
    }

    /**
     * Performs breadth-first search over a given set.
     *
     * @param start starting point for the search
     * @param target the element to be found
     * @param set the set that is being searched
     */
    public void breadthFirstSearch(String start, String target, TreeSet<String> set) {
        Deque<Node> queue = new ArrayDeque<Node>();
        path = new ArrayList<Node>();
        visited = new HashSet<String>();
        visited.add(start); //always add the first element to visited
        queue.addLast(new Node(start, null));
        Node neighbor = null;
        while (!queue.isEmpty()) {
            Node n = queue.removeFirst();
            String current = n.word;
            //looks at the neighbors of the current word
            for (String s : getNeighbors(current)) {
                neighbor = new Node(s, n);
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor.word);
                    queue.addLast(neighbor);
                    if (neighbor.word.equalsIgnoreCase(target)) {
                        path.add(neighbor);
                        queue.clear();
                        break;
                    }
                }
            }
        }
        //adds word ladders to the solution set
        sol = new ArrayDeque<String>();
        while (neighbor.parent != null) {
            sol.addFirst(neighbor.word);
            neighbor = neighbor.parent;
        }
        sol.addFirst(start);
    }

    /**
     * Returns all the words that have a Hamming distance of one relative to the
     * given word.
     *
     * @param  word the given word
     * @return      the neighbors of the given word
     */
    @Override
    public List<String> getNeighbors(String word) {
        List<String> neighbors = new ArrayList<String>();
        Iterator<String> itr = lexicon.iterator();
        while (itr.hasNext()) {
            String str = itr.next();
            if (getHammingDistance(word, str) == 1) {
                neighbors.add(str);
            }
        }
        return neighbors;
    }

    /**
     * Returns the total number of words in the current lexicon.
     *
     * @return number of words in the lexicon
     */
    @Override
    public int getWordCount() {
        return lexicon.size();
    }

    /**
     * Checks to see if the given string is a word.
     *
     * @param  str the string to check
     * @return     true if str is a word, false otherwise
     */
    @Override
    public boolean isWord(String str) {
        return lexicon.contains(str.toLowerCase());
    }

    /**
     * Checks to see if the given sequence of strings is a valid word ladder.
     *
     * @param  sequence the given sequence of strings
     * @return          true if the given sequence is a valid word ladder,
     *                       false otherwise
     */
    @Override
    public boolean isWordLadder(List<String> sequence) {
        if (sequence.isEmpty()) {
            return false;
        }
        for (int i = 0; i < sequence.size() - 1; i++) {
            if (getHammingDistance(sequence.get(i), sequence.get(i + 1)) != 1) {
                return false;
            }
        }
        for (int j = 0; j < sequence.size(); j++) {
            if (!lexicon.contains(sequence.get(j).toLowerCase())) {
                return false;
            }
        }
        return true;
    }

    private class Node {
        String word;
        Node parent;

        public Node(String element, Node pred) {
            word = element;
            parent = pred;
        }
    }
}
