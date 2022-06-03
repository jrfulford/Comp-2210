import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Provides an implementation of the Set interface.
 * A doubly-linked list is used as the underlying data structure.
 * Although not required by the interface, this linked list is
 * maintained in ascending natural order. In those methods that
 * take a LinkedSet as a parameter, this order is used to increase
 * efficiency.
 *
 * @author Dean Hendrix (dh@auburn.edu)
 * @author YOUR NAME (you@auburn.edu)
 *
 */
public class LinkedSet<T extends Comparable<T>> implements Set<T> {

    //////////////////////////////////////////////////////////
    // Do not change the following three fields in any way. //
    //////////////////////////////////////////////////////////

    /** References to the first and last node of the list. */
    Node front;
    Node rear;

    /** The number of nodes in the list. */
    int size;

    /////////////////////////////////////////////////////////
    // Do not change the following constructor in any way. //
    /////////////////////////////////////////////////////////

    /**
     * Instantiates an empty LinkedSet.
     */
    public LinkedSet() {
        front = null;
        rear = null;
        size = 0;
    }


    //////////////////////////////////////////////////
    // Public interface and class-specific methods. //
    //////////////////////////////////////////////////

    ///////////////////////////////////////
    // DO NOT CHANGE THE TOSTRING METHOD //
    ///////////////////////////////////////
    /**
     * Return a string representation of this LinkedSet.
     *
     * @return a string representation of this LinkedSet
     */
    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        }
        StringBuilder result = new StringBuilder();
        result.append("[");
        for (T element : this) {
            result.append(element + ", ");
        }
        result.delete(result.length() - 2, result.length());
        result.append("]");
        return result.toString();
    }


    ///////////////////////////////////
    // DO NOT CHANGE THE SIZE METHOD //
    ///////////////////////////////////
    /**
     * Returns the current size of this collection.
     *
     * @return  the number of elements in this collection.
     */
    public int size() {
        return size;
    }

    //////////////////////////////////////
    // DO NOT CHANGE THE ISEMPTY METHOD //
    //////////////////////////////////////
    /**
     * Tests to see if this collection is empty.
     *
     * @return  true if this collection contains no elements, false otherwise.
     */
    public boolean isEmpty() {
        return (size == 0);
    }


    /**
     * Ensures the collection contains the specified element. Neither duplicate
     * nor null values are allowed. This method ensures that the elements in the
     * linked list are maintained in ascending natural order.
     *
     * @param  element  The element whose presence is to be ensured.
     * @return true if collection is changed, false otherwise.
     */
    public boolean add(T element) {
        if (element == null) {
           throw new IllegalArgumentException();
        }
        
        Node n = new Node(element);
        Node i = this.front;
                
        if (this.contains(element)) {
           return false;
        }
        
        if (isEmpty()) {
           this.front = n;
           this.rear = n;
           this.front.prev = null;
           this.rear.next = null;
           size++;
           return true;
        }
        else if (element.compareTo(front.element) < 0)
        {
           i.prev = n;
           n.next = i;
           front = n;
           size++;
           return true;
        }
        else {
           i = nodeBefore(element);
        }
           if (i.next != null) {
              n.prev = i;
              n.next = i.next;
              i.next.prev = n;
              i.next = n;
              size++;
           }
           else
           {
              n.prev = i;
              n.next = i.next;
              i.next = n;
              rear = n;
              size++;
           }
           return true;
    }
    
    /**
     * Ensures the collection does not contain the specified element.
     * If the specified element is present, this method removes it
     * from the collection. This method, consistent with add, ensures
     * that the elements in the linked lists are maintained in ascending
     * natural order.
     *
     * @param   element  The element to be removed.
     * @return  true if collection is changed, false otherwise.
     */
    public boolean remove(T element) {
        if (element == null) {
           throw new IllegalArgumentException();
        }
    
        if (!this.contains(element)) {
           return false;
        }
                
        if (this.size() == 1) {
           this.front = null;
           this.rear = null;
           size--;
           return true;
        }
        
        Node currentNode = search(element);
        Node prevNode = currentNode.prev;
        Node nextNode = currentNode.next;

        if (prevNode == null) {
           this.front = nextNode;
           nextNode.prev = prevNode;
           currentNode = prevNode;
           currentNode = prevNode;
           size--;
           return true;
        }
        else if (nextNode == null) {
           this.rear = prevNode;
           prevNode.next = nextNode;
           currentNode.prev = nextNode;
           currentNode = nextNode;
           size--;
           return true;
        }
        else {
           nextNode.prev = prevNode;
           prevNode.next = nextNode;
           currentNode.next = null;
           currentNode.prev = null;
          // currentNode.prev = null;
           size--;
           return true;
        }
    }


    /**
     * Searches for specified element in this collection.
     *
     * @param   element  The element whose presence in this collection is to be tested.
     * @return  true if this collection contains the specified element, false otherwise.
     */
    public boolean contains(T element) {
        if (element == null) {
           throw new IllegalArgumentException();
        }
        Node i = front;
        while (i != null) {
           if (i.element == element) {
              return true;
           }
           i = i.next;
        }
        return false;
    }


    /**
     * Tests for equality between this set and the parameter set.
     * Returns true if this set contains exactly the same elements
     * as the parameter set, regardless of order.
     *
     * @return  true if this set contains exactly the same elements as
     *               the parameter set, false otherwise
     */
    public boolean equals(Set<T> s) {
        if (s == null) {
           throw new IllegalArgumentException();
        }
        
        if (s.size() != this.size()) {
           return false;
        }
        
        if (this.complement(s).size() == 0) {
           return true;
        }
                
        return false;
    }


    /**
     * Tests for equality between this set and the parameter set.
     * Returns true if this set contains exactly the same elements
     * as the parameter set, regardless of order.
     *
     * @return  true if this set contains exactly the same elements as
     *               the parameter set, false otherwise
     */
    public boolean equals(LinkedSet<T> s) {
        if (s == null) {
           throw new IllegalArgumentException();
        }
        
        if (this.size() != s.size()) {
           return false;
        }
        
        Node thisNode = this.front;
        Node thatNode = s.front;
        
        while ( (thisNode != null) && (thatNode != null) ) {
           if (thisNode.element != thatNode.element) {
              return false;
           }
           thisNode = thisNode.next;
           thatNode = thatNode.next;
        }
        return true;
    }


    /**
     * Returns a set that is the union of this set and the parameter set.
     *
     * @return  a set that contains all the elements of this set and the parameter set
     */
    public Set<T> union(Set<T> s){
       if (s == null) {
          throw new IllegalArgumentException();
       }
       
       LinkedSet<T> unionSet = new LinkedSet<>();
       
       Iterator<T> itr = s.iterator();
       
       while (itr.hasNext()) {
          unionSet.add(itr.next());
       }
       
       Node i = front;
       
       while (i != null) {
          unionSet.add(i.element);
          i = i.next;
       }
       
        return unionSet;
    }


    /**
     * Returns a set that is the union of this set and the parameter set.
     *
     * @return  a set that contains all the elements of this set and the parameter set
     */
    public Set<T> union(LinkedSet<T> s){
        if (s == null) {
           throw new IllegalArgumentException();
        }
        Node thisNode = this.front;
        Node thatNode = s.front;
        LinkedSet<T> unionSet = new LinkedSet<>();
        
        
        while ( (thisNode != null) || (thatNode != null) ) {
           if (thisNode == null) {
              unionSet.addToTheEnd(thatNode.element);
              thatNode = thatNode.next;
           }
           else if (thatNode == null) {
              unionSet.addToTheEnd(thisNode.element);
              thisNode = thisNode.next;
           }
           else if (thatNode.element.compareTo(thisNode.element) < 0) {
              unionSet.addToTheEnd(thatNode.element);
              thatNode = thatNode.next;
           }
           else if (thatNode.element.compareTo(thisNode.element) > 0) {
              unionSet.addToTheEnd(thisNode.element);
              thisNode = thisNode.next;
           }
           else {
              unionSet.addToTheEnd(thisNode.element);
              thisNode = thisNode.next;
              thatNode = thatNode.next;
           }
        }
        
        return unionSet;
    }


    /**
     * Returns a set that is the intersection of this set and the parameter set.
     *
     * @return  a set that contains elements that are in both this set and the parameter set
     */
    public Set<T> intersection(Set<T> s) {
        if (s == null) {
           throw new IllegalArgumentException();
        }
        
        Node i = this.front;
        LinkedSet<T> interSet = new LinkedSet<>();
        
        while (i != null) {
           if (s.contains(i.element)) {
              interSet.add(i.element);
           }
           i = i.next;
        }
        
        return interSet;
    }

    /**
     * Returns a set that is the intersection of this set and
     * the parameter set.
     *
     * @return  a set that contains elements that are in both
     *            this set and the parameter set
     */
    public Set<T> intersection(LinkedSet<T> s) {
        if (s == null) {
           throw new IllegalArgumentException();
        }
        
        Node thisNode = this.front;
        Node thatNode = s.front;
        
        LinkedSet<T> interSet = new LinkedSet<>();
        
        while ( (thisNode != null) && (thatNode != null) ) {
           if (thisNode.element == thatNode.element) {
              interSet.addToTheEnd(thisNode.element);
              thisNode = thisNode.next;
              thatNode = thatNode.next;
           }
           else if (thatNode.element.compareTo(thisNode.element) < 0) {
              thatNode = thatNode.next;
           }
           else {
              thisNode = thisNode.next;
           }
        }
        return interSet;
    }


    /**
     * Returns a set that is the complement of this set and the parameter set.
     *
     * @return  a set that contains elements that are in this set but not the parameter set
     */
    public Set<T> complement(Set<T> s) {
       if (s == null) {
          throw new IllegalArgumentException();
       }
    
       LinkedSet<T> complementElements = new LinkedSet<>();
       
       Node i = this.front;
       while (i != null) {
          if (!s.contains(i.element)) {
             complementElements.add(i.element);
          }
          i = i.next;
       }
        return complementElements;
    }


    /**
     * Returns a set that is the complement of this set and
     * the parameter set.
     *
     * @return  a set that contains elements that are in this
     *            set but not the parameter set
     */
    public Set<T> complement(LinkedSet<T> s) {
        if (s == null) {
           throw new IllegalArgumentException();
        }
        
        LinkedSet<T> complementSet = new LinkedSet<>();
        Node thisNode = this.front;
        Node thatNode = s.front;
        
        while (thisNode != null) {
           if (thatNode == null) {
              complementSet.<T>addToTheEnd(thisNode.element);
              thisNode = thisNode.next;
           }
           else if (thatNode.element.compareTo(thisNode.element) < 0) {
              thatNode = thatNode.next;
           }
           else if (thatNode.element.compareTo(thisNode.element) > 0) {
              complementSet.<T>addToTheEnd(thisNode.element);
              thisNode = thisNode.next;
           }
           else {
              thisNode = thisNode.next;
              thatNode = thatNode.next;
           }
        }
        
        return complementSet;
    }


    /**
     * Returns an iterator over the elements in this LinkedSet.
     * Elements are returned in ascending natural order.
     *
     * @return  an iterator over the elements in this LinkedSet
     */
    public Iterator<T> iterator() {
        return new LinkedSetIterator();
    }


    /**
     * Returns an iterator over the elements in this LinkedSet.
     * Elements are returned in descending natural order.
     *
     * @return  an iterator over the elements in this LinkedSet
     */
    public Iterator<T> descendingIterator() {
        return new DescendingLinkedSetIterator();
    }


    /**
     * Returns an iterator over the members of the power set
     * of this LinkedSet. No specific order can be assumed.
     *
     * @return  an iterator over members of the power set
     */
    public Iterator<Set<T>> powerSetIterator() {
        return new PowerSetIterator(this.rear, this.size());
    }



    //////////////////////////////
    // Private utility methods. //
    //////////////////////////////

    // Feel free to add as many private methods as you need.

    ////////////////////
    // Nested classes //
    ////////////////////

    //////////////////////////////////////////////
    // DO NOT CHANGE THE NODE CLASS IN ANY WAY. //
    //////////////////////////////////////////////
    
    /**
    * This method adds a node to the end of the linked set structure.
    *
    * @param element - the value of the element to be added.
    * @return returns true/false if the node was successfully added.
    */
    private boolean addToTheEnd(T element) {
       Node i = rear;
       Node newNode = new Node(element);
       
       if (this.size() == 0) {
          this.front = newNode;
          this.rear = newNode;
          size++;
          return true;
       } else{
          rear.next = newNode;
          newNode.prev = rear;
          rear = newNode;
          size++;
          return true;
       }
    }

    /**
    * This is an iterator over all the elements of LinkeSet in
    * ascending order.
    */
    private class LinkedSetIterator implements Iterator<T> {
       private Node n = LinkedSet.this.front;
       
       public boolean hasNext() {
          return (n != null);
       }
       
       public T next() {
          if (!hasNext()) {
             throw new NoSuchElementException();
          }
          
          T nextElement = n.element;
          n = n.next;
          return nextElement;
       }
       
       public void remove() {
          throw new UnsupportedOperationException();
       }
       
    }
    
    /**
    * This is an iterator over all the elements of a LinkedSet in
    * descending order.
    */
    private class DescendingLinkedSetIterator implements Iterator<T> {
       private Node i = rear;
       
       public boolean hasNext() {
          return (i != null);
       }
       
       T previousElement;
       public T next() {
          if (!hasNext()) {
             throw new NoSuchElementException();
          }
          
          previousElement = i.element;
          i = i.prev;
          return previousElement;
       }
       
       public void remove() {
          throw new UnsupportedOperationException();
       }
       
    }
    
       /**
       * This class is used to define a an iterator over all of the
       * power sets of a set.
       */
       private class PowerSetIterator implements Iterator<Set<T>> {
       private Node currentNode;
       private int size;
       private int count;
       private int maxSize;

      // Constructor
      public PowerSetIterator(Node rear,int size) {
         this.currentNode = rear;
         this.size = size;
         this.count = 0;
         this.maxSize = 1 << size;
      }

      /**
      * This method returns true if there is a non-null element
      * after the current element.
      *
      * @return true/false if there is a non-null element after
      *     the current element.
      */
         public boolean hasNext() {
            if (count == 0) {
            return true;
         }
            return ((this.count < maxSize) && (currentNode != null));
         }

      /**
      * This method returns the next element in the power set.
      *
      * @return the next element in the power set iterator.
      */
         public Set<T> next() {
            if (!hasNext()) {
               throw new NoSuchElementException();
            }
            Set<T> powerSet = new LinkedSet<T>();
            if (count == 0) {
               this.count++;
               return powerSet;
            }
            String binaryString = Integer.toBinaryString(count);
            for (int i = binaryString.length() - 1; i >= 0; i--) {
               if (binaryString.charAt(i) == '1') {
                  powerSet.add(currentNode.element);
               }
               this.currentNode = this.currentNode.prev;
               }
            this.count++;
            this.currentNode = rear;
            return powerSet;
         }
  
       /**
       * This method is not supported.
       *
       * @throws UnsupportedOperationException
       */
         public void remove() {
            throw new UnsupportedOperationException();
         }
    }    
    
    /**
    * This method searches the LinkedSet structure for the node
    * with the node with the value of element in its fields.
    */
    private Node search(T element) {
       Node i = front;
       while (i != null) {
          if (i.element.equals(element)) {
             return i;
          }
          i = i.next;
       }
       throw new NoSuchElementException();
    }
        
    /**
    * This method returns the node before the current node.
    *
    *
    * @param element - the value of the element to be searched for.
    *     This method will return the node before the node with the
    *     value of element.
    */
    private Node nodeBefore(T element) {
       Node i = front;
           while (i.next != null) {
              if (element.compareTo(i.next.element) < 0) {
                 break;
              }
              i = i.next;
           }
           return i;
    }

    /**
     * Defines a node class for a doubly-linked list.
     */
    class Node {
        /** the value stored in this node. */
        T element;
        /** a reference to the node after this node. */
        Node next;
        /** a reference to the node before this node. */
        Node prev;

        /**
         * Instantiate an empty node.
         */
        public Node() {
            element = null;
            next = null;
            prev = null;
        }

        /**
         * Instantiate a node that containts element
         * and with no node before or after it.
         */
        public Node(T e) {
            element = e;
            next = null;
            prev = null;
        }
    }

}