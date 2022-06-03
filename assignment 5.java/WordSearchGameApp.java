import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.TreeSet;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.io.BufferedReader;

/**
 * Defines the methods needed to play a word search game.
 *
 */
public class WordSearchGameApp implements WordSearchGame {

   //Private Fields
   private TreeSet<String> lexicon;
   private String[][] gameBoard;
   private static final int MAX_PROX = 8;
   private int height;
   private int width;
   private boolean[][] visited;
   private SortedSet<String> allWords;
   private ArrayList<Integer> path;
   private ArrayList<Position> path2;
   private String semiWord;
   
   /** Constructor. **/
   public WordSearchGameApp() {
      lexicon = null;
      
      gameBoard = new String[4][4];
      gameBoard[0][0] = "E"; 
      gameBoard[0][1] = "E"; 
      gameBoard[0][2] = "C"; 
      gameBoard[0][3] = "A"; 
      gameBoard[1][0] = "A"; 
      gameBoard[1][1] = "L"; 
      gameBoard[1][2] = "E"; 
      gameBoard[1][3] = "P"; 
      gameBoard[2][0] = "H"; 
      gameBoard[2][1] = "N"; 
      gameBoard[2][2] = "B"; 
      gameBoard[2][3] = "O"; 
      gameBoard[3][0] = "Q"; 
      gameBoard[3][1] = "T"; 
      gameBoard[3][2] = "T"; 
      gameBoard[3][3] = "Y";  
        
      height = gameBoard[0].length;  
      width = gameBoard.length;
   }
   
   
   /**
    * Loads the lexicon into a data structure for later use. 
    * 
    * @param fileName A string containing the name of the file to be opened.
    * @throws IllegalArgumentException if fileName is null
    * @throws IllegalArgumentException if fileName cannot be opened.
    */
   public void loadLexicon(String fileName) {
      if (fileName == null) {
         throw new IllegalArgumentException();
      }
      lexicon = new TreeSet<String>();
      try {
         Scanner s = 
                new Scanner(new BufferedReader(new FileReader(new File(fileName))));
         while (s.hasNext()) {
            String str = s.next();
            boolean added = lexicon.add(str.toUpperCase());
            s.nextLine();
         }
      }
      catch (Exception e) {
         throw new IllegalArgumentException("Error loading word list: " + fileName + ": " + e);
      }
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
   public void setBoard(String[] letterArray) {
      if (letterArray == null) {
         throw new IllegalArgumentException();
      }
      int boardSqrt = (int) Math.sqrt(letterArray.length);
      if (boardSqrt * boardSqrt != letterArray.length) {
         throw new IllegalArgumentException();
      }
      
      gameBoard = new String[boardSqrt][boardSqrt];
      height = boardSqrt;
      width = boardSqrt;
      int spot = 0;
      for (int i = 0; i < height; i++) {
         for (int j = 0; j < width; j++) {
            gameBoard[i][j] = letterArray[spot];
            spot++;
         }
      }
   }
   
   /**
    * Creates a String representation of the board, suitable for printing to
    *   standard out. Note that this method can always be called since
    *   implementing classes should have a default board.
    */
   public String getBoard() {
      String board = "";
     
      for (int i = 0; i < height; i++) {
         for (int j = 0; j < width; j++) {
            board += gameBoard[i][j] + " ";
         }
         board += "\n";
      }
   
      return board;
   }
   
   /**
    * Retrieves all scorable words on the game board, according to the stated game
    * rules.
    * 
    * @param minimumWordLength The minimum allowed length (i.e., number of
    *     characters) for any word found on the board.
    * @return java.util.SortedSet which contains all the words of minimum length
    *     found on the game board and in the lexicon.
    * @throws IllegalArgumentException if minimumWordLength < 1
    * @throws IllegalStateException if loadLexicon has not been called.
    */
   public SortedSet<String> getAllScorableWords(int minimumWordLength) {
      if (minimumWordLength < 1) {
         throw new IllegalArgumentException();
      }
      if (lexicon == null) {
         throw new IllegalStateException();
      }
      
      path2 = new ArrayList<Position>();
      allWords = new TreeSet<String>();
      semiWord = "";
      
      for (int i = 0; i < height; i++) {
         for (int j = 0; j < width; j ++) {
            semiWord = gameBoard[i][j];
            if (isValidWord(semiWord) && semiWord.length() >= minimumWordLength) {
               allWords.add(semiWord);
            }
            if (isValidPrefix(semiWord)) {
               Position p = new Position(i,j);
               path2.add(p);
               dfs2(i, j, minimumWordLength); 
               path2.remove(p);
            }
         }
      }
      
      return allWords;
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
   public int getScoreForWords(SortedSet<String> words, int minimumWordLength) {
      if (minimumWordLength < 1) {
         throw new IllegalArgumentException();
      }
      if (lexicon == null) {
         throw new IllegalStateException();
      }
      
      int score = 0;
      Iterator<String> itr = words.iterator();
      
      while (itr.hasNext()) {
         String word = itr.next();
         if (word.length() >= minimumWordLength && isValidWord(word) && !isOnBoard(word).isEmpty()) {
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
   public boolean isValidWord(String wordToCheck) {
      if (wordToCheck == null) {
         throw new IllegalArgumentException();
      }
      if (lexicon == null) {
         throw new IllegalStateException();
      }
      wordToCheck = wordToCheck.toUpperCase();
      if (lexicon.contains(wordToCheck)) {
         return true;
      }
      return false;
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
   public boolean isValidPrefix(String prefixToCheck) {
      if (prefixToCheck == null) {
         throw new IllegalArgumentException();
      }
      if (lexicon == null) {
         throw new IllegalStateException();
      }
     
      prefixToCheck = prefixToCheck.toUpperCase();
      String word = lexicon.ceiling(prefixToCheck);
      if (word != null) {
         if (word.startsWith(prefixToCheck)) {
            return true;
         }
      }
      return false;
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
   public List<Integer> isOnBoard(String wordToCheck) {
      if (wordToCheck == null) {
         throw new IllegalArgumentException();
      }
      
      if (lexicon == null) {
         throw new IllegalStateException();
      }
      
      path2 = new ArrayList<Position>();
      wordToCheck = wordToCheck.toUpperCase();
      semiWord = "";
      path = new ArrayList<Integer>();
      
      for (int i = 0; i < height; i++) {
      
         for (int j = 0; j < width; j ++) {
         
            if (wordToCheck.equals(gameBoard[i][j])) {
               path.add(i * width + j);
               return path;
            }
            
            if (wordToCheck.startsWith(gameBoard[i][j])) {
               Position p = new Position(i, j);
               path2.add(p);
               semiWord = gameBoard[i][j];
               dfs(i, j, wordToCheck);
               
               if (!wordToCheck.equals(semiWord)) {
                  path2.remove(p);
               }
               else {
                  for (Position pos: path2) {
                     path.add((pos.x * width) + pos.y);
                  } 
                  
                  return path;
               }
            }
         }
      }
      return path;
   }
   
   private void dfs(int x, int y, String wordToCheck) {
   
      Position start = new Position(x, y);
      allUnvisited();
      pathVisited();
      
      for (Position p: start.proximity()) {
      
         if (!isVisited(p)) {
            visit(p);
            
            if (wordToCheck.startsWith(semiWord + gameBoard[p.x][p.y])) {
               semiWord += gameBoard[p.x][p.y];
               path2.add(p);
               dfs(p.x, p.y, wordToCheck);
               
               if (wordToCheck.equals(semiWord)) {
                  return;
               }
               else {
                  path2.remove(p);
               
                  int endIndex = semiWord.length() - gameBoard[p.x][p.y].length();
                  semiWord = semiWord.substring(0, endIndex);
               }
            }
         }
      }
      allUnvisited();
      pathVisited();
   }
   
  /**
   * Depth-First Search.
   * @param x x-value
   * @param y y-value
   * @param wordToCheck word to check for.
   */
   
   private void dfs2(int x, int y, int min) {
      Position start = new Position(x, y);
      allUnvisited();
      pathVisited();
      
      for (Position p : start.proximity()) {
      
         if (!isVisited(p)) {
            visit(p);
            
            if (isValidPrefix(semiWord + gameBoard[p.x][p.y])) {
               semiWord += gameBoard[p.x][p.y];
               path2.add(p);
               
               if (isValidWord(semiWord) && semiWord.length() >= min) {
                  allWords.add(semiWord);
               }
               
               dfs2(p.x, p.y, min);
               path2.remove(p);
               int endIndex = semiWord.length() - gameBoard[p.x][p.y].length();
               semiWord = semiWord.substring(0, endIndex);
            }
         }
      }
      
      allUnvisited();
      pathVisited();
   }

   /**
   * Marks unvisited positions.
   */
   
   private void allUnvisited() {
      visited = new boolean[width][height];
      for (boolean[] row : visited) {
         Arrays.fill(row, false);
      }
   }
   
   /**
   * Creates a visited path.
   */
   
   private void pathVisited() {
      for (int i = 0; i < path2.size(); i ++) {
         visit(path2.get(i));
      }
   }
   
   /** Class that creates position and can be referenced for position. */
   private class Position {
      int x;
      int y;
   
      public Position(int x, int y) {
         this.x = x;
         this.y = y;
      }
   
      public String toString() {
         return "(" + x + ", " + y + ")";
      }
   
      public Position[] proximity() {
      
         Position[] proxValues = new Position[MAX_PROX];
         int count = 0;
         Position p;
         
         for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
               if (!((i == 0) && (j == 0))) {
                  p = new Position(x + i, y + j);
                  if (isValid(p)) {
                     proxValues[count++] = p;
                  }
               }
            }
         }
         return Arrays.copyOf(proxValues, count);
      }
   }

   private boolean isValid(Position p) {
      return (p.x >= 0) && (p.x < width) && (p.y >= 0) && (p.y < height);
   }

   private boolean isVisited(Position p) {
      return visited[p.x][p.y];
   }

   private void visit(Position p) {
      visited[p.x][p.y] = true;
   }


}