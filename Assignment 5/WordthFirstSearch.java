import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.Iterator;
import java.util.Arrays;
import java.util.ArrayList;

/**
 * Creates a word search game.
 *
 * @author Daniel Thomason (dht0009@auburn.edu)
 * @version 2019-10-30
 *
 */
public class WordthFirstSearch implements WordSearchGame {
    private String[][] board;
    private boolean[][] visited;
    private TreeSet<String> lexicon;
    private static final int MAX_NEIGHBORS = 8;
    private boolean loaded;
    private int length;
    private int height;
    private int width;
    private SortedSet<String> validWords;
    private List<Integer> path;
    private List<Integer> actualPath;
    private ArrayList<Position> wordPath;
    private String buildingWord;

    public WordthFirstSearch() {
        //Default gameboard from the intstructions if another one is not loaded.
        board = new String[4][4];
        board[0][0] = "E";
        board[0][1] = "E";
        board[0][2] = "C";
        board[0][3] = "A";
        board[1][0] = "A";
        board[1][1] = "L";
        board[1][2] = "E";
        board[1][3] = "P";
        board[2][0] = "H";
        board[2][1] = "N";
        board[2][2] = "B";
        board[2][3] = "O";
        board[3][0] = "Q";
        board[3][1] = "T";
        board[3][2] = "T";
        board[3][3] = "Y";
    }

    /**
     * Loads the lexicon into a data structure for later use.
     *
     * @param fileName A string containing the name of the file to be opened.
     * @throws IllegalArgumentException if fileName is null
     * @throws IllegalArgumentException if fileName cannot be opened.
     */
    @Override
    public void loadLexicon(String fileName) {
        lexicon = new TreeSet<String>();
        if (fileName == null) {
            throw new IllegalArgumentException();
        }
        Scanner scan;
        String letter;
        try {
            scan = new Scanner(new BufferedReader(new FileReader(
            new File(fileName))));
            while (scan.hasNext()) {
                letter= scan.next();
                letter = letter.toUpperCase();
                lexicon.add(letter);
                scan.nextLine();
            }
        }
        catch (java.io.IOException i) {
            throw new IllegalArgumentException();
        }
        loaded = true;
    }
    /**
     * Stores the incoming array of Strings in a data structure that will make
     * it convenient to find words.
     *
     * @param letterArray This array of length N^2 stores the contents of the
     *     game board in row-major order. Thus, index 0 stores the contents of board
     *     position (0,0) and index length-1 stores the contents of board position
     *     (N-1,N-1). Note that the board must be square and that the strings inside
     *     may be longer than one character.
     * @throws IllegalArgumentException if letterArray is null, or is  not
     *     square.
     */
    @Override
    public void setBoard(String[] letterArray) {
        if (letterArray == null) {
            throw new IllegalArgumentException();
        }
        int dimension = (int) Math.sqrt(letterArray.length);
        if ((dimension * dimension) != letterArray.length) {
            throw new IllegalArgumentException();
        }
        else {
            height = dimension;
            width = dimension;
            int position = 0;
            board = new String[dimension][dimension];
            for (int h = 0; h < height; h++) {
                for (int w = 0; w < width; w++) {
                    board[h][w] = letterArray[position];
                    position++;
                }
            }
        }
    }

    /**
     * Creates a String representation of the board, suitable for printing to
     *   standard out. Note that this method can always be called since
     *   implementing classes should have a default board.
     */
    @Override
    public String getBoard() {
        String boardString = "";
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                boardString += board[i][j] += " ";
            }
        }
        return boardString;
    }

    /**
     * Retrieves all valid words on the game board, according to the stated game
     * rules.
     *
     * @param minimumWordLength The minimum allowed length (i.e., number of
     *     characters) for any word found on the board.
     * @return java.util.SortedSet which contains all the words of minimum length
     *     found on the game board and in the lexicon.
     * @throws IllegalArgumentException if minimumWordLength < 1
     * @throws IllegalStateException if loadLexicon has not been called.
     */
    @Override
    public SortedSet<String> getAllValidWords(int minimumWordLength) {
        if (minimumWordLength < 1) {
            throw new IllegalArgumentException();
        }
        if (!loaded) {
            throw new IllegalStateException();
        }
        wordPath = new ArrayList<Position>();
        validWords = new TreeSet<String>();
        buildingWord = "";
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                buildingWord = board[i][j];
                if (isValidWord(buildingWord) && buildingWord.length()
                   >= minimumWordLength) {
                   validWords.add(buildingWord);
               }
               if (isValidPrefix(buildingWord)) {
                   Position p = new Position(i,j);
                   wordPath.add(p);
                   depth(i, j, minimumWordLength);
                   wordPath.remove(p);
               }
            }
        }
        return validWords;
    }

    /**
     * Computes the cummulative score for the scorable words in the given set.
     * To be scorable, a word must (1) have at least the minimum number of characters,
     * (2) be in the lexicon, and (3) be on the board. Each scorable word is
     * awarded one point for the minimum number of characters, and one point for
     * each character beyond the minimum number.
     *
     * @param words The set of words that are to be scored.
     * @param minimumWordLength The minimum number of characters required per word
     * @return the cummulative score of all scorable words in the set
     * @throws IllegalArgumentException if minimumWordLength < 1
     * @throws IllegalStateException if loadLexicon has not been called.
     */
    @Override
    public int getScoreForWords(SortedSet<String> words, int minimumWordLength) {
        if (minimumWordLength < 1) {
            throw new IllegalArgumentException();
        }
        if (!loaded) {
            throw new IllegalStateException();
        }
        int score = 0;
        Iterator<String> itr = words.iterator();
        while (itr.hasNext()) {
            String word = itr.next();
            if (word.length() >= minimumWordLength && isValidWord(word)
            && !isOnBoard(word).isEmpty()) {
                score += (word.length() - minimumWordLength) + 1;
            }
        }
        return score;
    }

    /**
     * Determines if the given word is in the lexicon.
     *
     * @param wordToCheck The word to validate
     * @return true if wordToCheck appears in lexicon, false otherwise.
     * @throws IllegalArgumentException if wordToCheck is null.
     * @throws IllegalStateException if loadLexicon has not been called.
     */
    @Override
    public boolean isValidWord(String wordToCheck) {
        if (wordToCheck == null) {
            throw new IllegalArgumentException();
        }
        if (!loaded) {
            throw new IllegalStateException();
        }
        return lexicon.contains(wordToCheck.toUpperCase());
    }

    /**
     * Determines if there is at least one word in the lexicon with the
     * given prefix.
     *
     * @param prefixToCheck The prefix to validate
     * @return true if prefixToCheck appears in lexicon, false otherwise.
     * @throws IllegalArgumentException if prefixToCheck is null.
     * @throws IllegalStateException if loadLexicon has not been called.
     */
    @Override
    public boolean isValidPrefix(String prefixToCheck) {
        if (prefixToCheck == null) {
            throw new IllegalArgumentException();
        }
        if (!loaded) {
            throw new IllegalStateException();
        }
        return lexicon.ceiling(prefixToCheck).startsWith(prefixToCheck);
    }

    /**
     * Determines if the given word is in on the game board. If so, it returns
     * the path that makes up the word.
     * @param wordToCheck The word to validate
     * @return java.util.List containing java.lang.Integer objects with  the path
     *     that makes up the word on the game board. If word is not on the game
     *     board, return an empty list. Positions on the board are numbered from zero
     *     top to bottom, left to right (i.e., in row-major order). Thus, on an NxN
     *     board, the upper left position is numbered 0 and the lower right position
     *     is numbered N^2 - 1.
     * @throws IllegalArgumentException if wordToCheck is null.
     * @throws IllegalStateException if loadLexicon has not been called.
     */
    @Override
    public List<Integer> isOnBoard(String wordToCheck) {
        if (wordToCheck == null) {
            throw new IllegalArgumentException();
        }
        if (!loaded) {
            throw new IllegalStateException();
        }
        wordPath = new ArrayList<Position>();
        wordToCheck = wordToCheck.toUpperCase();
        buildingWord = "";
        path =  new ArrayList<Integer>();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (wordToCheck.equals(board[i][j])) {
                    path.add(i * width + j);
                    return path;
                }
                if (wordToCheck.startsWith(board[i][j])) {
                    Position p = new Position(i, j);
                    wordPath.add(p);
                    buildingWord = board[i][j];
                }
            }
        }
        return path;
    }

    /**
     * Marks all positions on the board as unvisited.
     */
    private void markAllUnvisited() {
        visited = new boolean[width][height];
        for (boolean[] row : visited) {
            Arrays.fill(row, false);
        }
    }

    /**
     * Marks all positions on the word path as visited.
     */
    private void markPathVisited() {
        for (int i = 0; i < wordPath.size(); i++) {
            visit(wordPath.get(i));
        }
    }

    /**
     * Performs a depth-first search for the getAllValidWords method.
     *
     * @param x - "x" coordinate
     * @param y - "y" coordinate
     * @param minLength - mininum length of a word
     */
    private void depth(int x, int y, int minLength) {
        Position start = new Position(x, y);
        markAllUnvisited();
        markPathVisited();
        for (Position p : start.neighbors()) {
            if (!isVisited(p)) {
                visit(p);
                if (isValidPrefix(buildingWord + board[p.x][p.y])) {
                    buildingWord += board[p.x][p.y];
                    wordPath.add(p);
                    if (isValidWord(buildingWord) && buildingWord.length()
                       >= minLength) {
                        validWords.add(buildingWord);
                    }
                    depth(p.x, p.y, minLength);
                    wordPath.remove(p);
                    int end = buildingWord.length() - board[p.x][p.y].length();
                    buildingWord = buildingWord.substring(0, end);
                }
            }
        }
        markAllUnvisited();
        markPathVisited();
    }

    /**
     * Performs a depth-first search for the isOnBoard method.
     *
     * @param x - "x" coordinate
     * @param y - "y" coordinate
     * @param word - the word that is being checked to see if it is
     * on the board
     */
    private void depth2(int x, int y, String word) {
        Position start = new Position(x, y);
        markAllUnvisited();
        markPathVisited();
        for (Position p : start.neighbors()) {
            if (word.startsWith(buildingWord + board[p.x][p.y])) {
                buildingWord += board[p.x][p.y];
                wordPath.add(p);
                depth2(p.x, p.y, word);
            }
            if (word.equals(buildingWord)) {
                return;
            }
            else {
                wordPath.remove(p);
                int end = buildingWord.length() - board[p.x][p.y].length();
                buildingWord = buildingWord.substring(0, end);
            }
        }
        markAllUnvisited();
        markPathVisited();
    }

    /**
     * Determines if the position is actually a position on the board.
     *
     * @param p - the position to be checked
     * @return true if on board, false if not
     */
    private boolean isValid(Position p) {
        return (p.x >= 0) && (p.x < width) && (p.y >= 0) && (p.y < height);
    }

    /**
     * Determines if the position is being visited.
     *
     * @return true if it is being visited, false if not
     */
    private boolean isVisited(Position p) {
        return visited[p.x][p.y];
    }

    /**
     * Physically visits the position on the board.
     */
    private void visit(Position p) {
        visited[p.x][p.y] = true;
    }

    /**
     * Class that defines a Position object to represent points on the game
     * board.
     */
    private class Position {
        int x;
        int y;

        /**
         * Constructs a Position object.
         *
         * @param x - "x" coordinate
         * @param y - "y" coordinate
         */
        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        /**
         * Represents the Position as a coordinate point.
         *
         * @return a String representation of the Position
         */
        @Override
        public String toString() {
            return "(" + x + ", " + ")";
        }

        /**
         * Creates an array of the neighbors of the current Position.
         *
         * @return an array containing the neighbors of the Position.
         */
        public Position[] neighbors() {
            Position[] nbrs = new Position[MAX_NEIGHBORS];
            int count = 0;
            Position p;
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (!((i == 0) && (j == 0))) {
                        p = new Position (x + i, y +j);
                        if (isValid(p)) {
                            nbrs[count++] = p;
                        }
                    }
                }
            }
            return Arrays.copyOf(nbrs, count);
        }
    }
}
