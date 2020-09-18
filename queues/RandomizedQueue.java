/******************************************************************************
 * Author: Frederik De Brouwer
 * 
 * Randomized queue. A randomized queue is similar to a stack or queue, 
 * except that the item removed is chosen uniformly at random among items in 
 * the data structure. Create a generic data type RandomizedQueue that implements 
 * the following API.
 ******************************************************************************/
package queues;
import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int size;   // Number of elements in queue
    private Item[] randomQueue; // Array Implementation instead of Linked List due to program specifications
    private int random;

    // construct an empty randomized queue
    public RandomizedQueue() {
        this.size = 0;
        randomQueue = (Item[]) new Object[1]; // ugly cast
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return this.size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return this.size;
    }

    // Resizes array to the given capacity
    private void resize(int capacity) {
        Item[] temp = (Item[]) new Object[capacity]; // ugly cast
        for (int i = 0 ; i < size ; i++){
            temp[i] = randomQueue[i];
        }
        randomQueue = temp;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("Can't add null to Randomized Queue.");
        // Check if queue is full
        if (randomQueue.length == size) {
            resize(randomQueue.length * 2);
        }
        randomQueue[size++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (this.isEmpty()) throw new NoSuchElementException("Cannot remove random item (queue is empty).");
        Item randomItem = sample();
        if (random != size - 1) {
            randomQueue[random] = randomQueue[size - 1]; // random item can be overwritten now
        }
        randomQueue[size - 1] = null; // avoid loitering
        // Lower size of queue by 1
        size--;
        // Check if queue can be resized
        if (size > 0 && size < randomQueue.length / 4) {
            resize(randomQueue.length / 2);
        }
        return randomItem;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (this.isEmpty()) throw new NoSuchElementException("Cannot return random item (queue is empty).");
        random = StdRandom.uniform(this.size);
        return randomQueue[random];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        
        private Item[] randomQueueCopy = (Item[]) new Object[randomQueue.length]; // copy needed for independent iterators
        private int sizeCopy = size;

        public RandomizedQueueIterator() {
            for (int i = 0 ; i < randomQueue.length ; i++) {
                randomQueueCopy[i] = randomQueue[i];
            }
        }

        @Override
        public boolean hasNext() {
            return sizeCopy > 0;
        }

        // Iterating in random order => item needs to be removed after the iteration to prevent being iterated on again
        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("No more items to return.");
            int random = StdRandom.uniform(sizeCopy);
            Item randomItem = randomQueueCopy[random];
            if (random != sizeCopy - 1) {
                randomQueueCopy[random] = randomQueueCopy[sizeCopy - 1];
            }
            randomQueueCopy[sizeCopy - 1] = null;
            // Lower size of copy by 1
            sizeCopy--;
            return randomItem;
        }
        
        public void remove() {
            throw new UnsupportedOperationException("This operation is not supported.");
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        StdOut.println(rq.size() == 0);
        StdOut.println(rq.isEmpty() == true);
        rq.enqueue(1);
        rq.enqueue(2);
        rq.enqueue(3);
        rq.enqueue(4);
        StdOut.println(rq.size() == 4);
        StdOut.println(rq.isEmpty() == false);
        for (int x : rq){
            StdOut.print(x + " ");
        }
        StdOut.println();
        int size = rq.size();
        for (int i = 0 ; i < size ; i++){
            StdOut.print(rq.dequeue() + " ");
        }
        StdOut.println();
        StdOut.println(rq.size() == 0);
    }
}
