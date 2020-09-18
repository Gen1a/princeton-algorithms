/******************************************************************************
 * Author: Frederik De Brouwer
 * 
 * Dequeue. A double-ended queue or deque (pronounced “deck”) is a generalization 
 * of a stack and a queue that supports adding and removing items from either 
 * the front or the back of the data structure. 
 * Create a generic data type Deque that implements the following API.
 ******************************************************************************/
package queues;
import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
    
    private Node first, last;   // keep a pointer for first and last item
    private int size;   // number of items in deque

    private class Node { // Linked List Implementation instead of Array due to program specifications
        Item item;
        Node next;
        Node previous;
    }

    // construct an empty deque
    public Deque() {
        this.size = 0;
        this.first = null;
        this.last = null;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return this.size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return this.size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException("Can't add null to deque.");
        // Save old first node
        Node oldFirst = first;
        // Build new node + avoid loitering
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        first.previous = null;
        // Check if first node added
        if (isEmpty()) last = first;
        else {
            oldFirst.previous = first;
        }
        // Increase deque size
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException("Can't add null to deque.");
        // Save old last node
        Node oldLast = last;
        // Build new node + avoid loitering
        last = new Node();
        last.item = item;
        last.next = null;
        last.previous = oldLast;
        // Check if first node added
        if (isEmpty()) first = last;
        else {
            oldLast.next = last;
        }
        // Increase deque size
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("Cannot remove item (deque is empty).");
        // Save item to return
        Item item = first.item;
        // Change pointers
        first = first.next;
        // Change deque size
        size--;
        // Check if items remain
        if (isEmpty()) last = first;
        else {
            first.previous = null;
        }
        return item;

    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("Cannot remove item (deque is empty).");
        // Save item to return
        Item item = last.item;
        // Change pointers
        last = last.previous;
        // Change deque size
        size--;
        // Check if items remain
        if (isEmpty()) first = last;
        else {
            last.next = null;
        }
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        
        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("No more items to return.");
            // Save current item
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException("This operation is not supported.");
        } 
    }

    // unit testing (required)
    public static void main(String[] args){
        Deque<Integer> d = new Deque<Integer>();
        StdOut.println(d.isEmpty() == true);
        StdOut.println(d.size() == 0);
        d.addFirst(1);
        d.addLast(2);
        d.addLast(3);
        StdOut.println(d.isEmpty() == false);
        StdOut.println(d.size() == 3);
        int counter = 1;
        for (int i : d) {
            StdOut.println("Item #" + counter + ": " + i);  // Test iterator
        }
        StdOut.println(d.removeFirst() == 1);
        StdOut.println(d.removeLast() == 3);
        StdOut.println(d.removeLast() == 2);
        StdOut.println(d.size() == 0);
    }
}
