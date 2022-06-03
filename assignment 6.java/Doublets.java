import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.Arrays;
import java.util.Queue;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;

import java.util.stream.Collectors;

/**
 * Provides an implementation of the WordLadderGame interface. 
 *
 * @author Your Name (you@auburn.edu)
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
    private HashSet<String> lexicon;

    /**
     * Instantiates a new instance of Doublets with the lexicon populated with
     * the strings in the provided InputStream. The InputStream can be formatted
     * in different ways as long as the first string on each line is a word to be
     * stored in the lexicon.
     */
    public Doublets(InputStream in) {
        lexicon = new HashSet<>();
        try {
            //////////////////////////////////////
            // INSTANTIATE lexicon OBJECT HERE  //
            //////////////////////////////////////
            Scanner s =
                new Scanner(new BufferedReader(new InputStreamReader(in)));
            while (s.hasNext()) {
                String str = s.next();
                this.lexicon.<String>add(str.toLowerCase());
                /////////////////////////////////////////////////////////////
                // INSERT CODE HERE TO APPROPRIATELY STORE str IN lexicon. //
                /////////////////////////////////////////////////////////////
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
     * Returns the total number of words in the current lexicon.
     *
     * @return number of words in the lexicon
     */
    public int getWordCount() {
        return this.lexicon.size();
    }
    
    /**
     * Checks to see if the given string is a word.
     *
     * @param  str the string to check
     * @return     true if str is a word, false otherwise
     */
    public boolean isWord(String str) {
        return this.lexicon.contains(str);
    }
    
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
    public int getHammingDistance(String str1, String str2) {
        if (str1.length() != str2.length()) {
           return -1;
        }
        int hammingDistance = 0;
        for (int i = 0; i < str1.length(); i++) {
            if (str1.charAt(i) != str2.charAt(i)) {
                hammingDistance++;
            }
        }
        return hammingDistance;
    }
    
    /**
     * Returns all the words that have a Hamming distance of one relative to the
     * given word.
     *
     * @param  word the given word
     * @return      the neighbors of the given word
     */
    public List<String> getNeighbors(String word) {
        int wordLength = word.length();
        int j = 0;
        List<String> result = new ArrayList<>();
        for (int i = 0; i < wordLength; i++) {
            StringBuilder myString = this.setStringBuilder(word);
            for (char character = 'a'; character <= 'z'; character++) {
               myString.replace(i, i + 1, Character.toString(character));
               if (this.lexicon.contains(myString.toString()) &&
                   this.getHammingDistance(word, myString.toString()) == 1) {
                   result.<String>add(myString.toString());
               }
            }
            
        }
        return result;
    }
    
    /**
     * Checks to see if the given sequence of strings is a valid word ladder.
     *
     * @param  sequence the given sequence of strings
     * @return          true if the given sequence is a valid word ladder,
     *                       false otherwise
     */
    public boolean isWordLadder(List<String> sequence) {
        if (sequence.isEmpty()) {
            return false;
        }
        String word1 = "";
        String word2 = "";
        for (int i = 0; i < sequence.size() - 1; i++) {
            word1 = sequence.get(i);
            word2 = sequence.get(i + 1);
            if (!this.lexicon.contains(word1) ||
                !this.lexicon.contains(word2)) {
                return false;
            }
            
            if (this.getHammingDistance(word1, word2) != 1) {
                return false;
            }
        }
        return true;
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
    public List<String> getMinLadder(String start, String end) {
        start = start.toLowerCase();
        end = end.toLowerCase();
        List<String> revList = new ArrayList<>();
        List<String> ladder = new ArrayList<>();
        
        if (start.equals(end)) {
            ladder.add(end);
            return ladder;
        }
        
        if (this.getHammingDistance(start, end) == -1) {
            return new ArrayList<>();
        }
        
        if (this.isWord(start) && this.isWord(end)) {
            revList = bfs(start, end);
        }
        
        if (revList.isEmpty()) {
            return new ArrayList<>();
        }
        
        for (int i = revList.size() - 1; i > -1; i--) {
            ladder.add(revList.get(i));
        }
        return ladder;
    }
    
    /**
    * This method sets a StringBuilder's initial value equal to the
    * the characters in str.
    *
    * @parma str - the initial value of the StringBuilder.
    * @return a StringBuilder.
    */
    private StringBuilder setStringBuilder(String str) {
        int wordLength = str.length();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < wordLength; i++) {
            result.append(str.charAt(i));
        }
        return result;
    }
    
    /**
    * This method implement breadth-first search.
    *
    * @param start - the first word in the word ladder.
    * @param end - the second word in the word ladder.
    * @return result<String> - a list of strings representing the word ladder.
    */
    private List<String> bfs(String start, String end) {
        Deque<Node> bfsDeque = new ArrayDeque<>();
        List<String> result = new LinkedList<>();
        HashSet<String> visitedNeighbors = new HashSet<>();
        List<String> neighbors = new ArrayList<>();
        Node element = new Node(start, null);
        Node endNode = new Node(end, null);
        
        bfsDeque.add(element);
        visitedNeighbors.add(start);
        outerloop:
        while (!bfsDeque.isEmpty()) {
           element = bfsDeque.removeFirst();
           neighbors = this.getNeighbors(element.getWord());
           for (String neighbor : neighbors) {
               if (!visitedNeighbors.contains(neighbor)) {
                   visitedNeighbors.add(neighbor);
                   bfsDeque.add(new Node(neighbor, element));
               }
               if (neighbor.equals(end)) {
                   endNode.setPrevious(element);
                   break outerloop;
               }
           }
        }

        if (endNode.getPrevious() == null) {
            return result;
        }        
        Node i = endNode;
        while (i != null) {
            result.add(i.word);
            i = i.getPrevious();
        }
        
        return result;
    }
    
    /**
    * This class defines a Node.
    */
    private class Node {
        private String word;
        private Node previous;
        
        public Node(String wordIn, Node previousIn) {
            this.word = wordIn;
            this.previous = previousIn;
        }
        
        public String getWord() {
            return this.word;
        }
        
        public Node getPrevious() {
            return this.previous;
        }
        
        public void setPrevious(Node previousIn) {
            this.previous = previousIn;
        }
        
    } 
}