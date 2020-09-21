/******************************************************************************
* Author: Frederik De Brouwer
*  
* Given an array of nn buckets, each containing a red, white, or blue pebble, sort them by color. The allowed operations are:
* swap(i, j)swap(i,j): swap the pebble in bucket ii with the pebble in bucket jj.
* color(i)color(i): determine the color of the pebble in bucket ii.
* The performance requirements are as follows:
*
* At most nn calls to color()color().
* At most nn calls to swap()swap().
* Constant extra space.
*
* Note: without QuickSort, using 3 way partitioning
******************************************************************************/

package dutch_national_flag;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;

public class DutchNationalFlag {
    private final int RED = 0;
    private final int WHITE = 1;
    private final int BLUE = 2;
    private int[] buckets;
    private int n;  // amount of buckets

    public DutchNationalFlag(int[] buckets) {
        this.n = buckets.length;
        this.buckets = buckets;
    }

    private void swap(int i, int j) {
        int temp = buckets[i];
        buckets[i] = buckets[j];
        buckets[j] = temp;
    }

    private int color(int i) {
        return buckets[i];
    }

    public void sort() {
        int low = 0;        // pointer for bucket just after highest 'red bucket'
        int high = n-1;     // pointer for bucket just below lowest 'blue bucket'
        int current = low;  // pointer for current bucket when looping through array

        while(current <= high) {
            switch(color(current)) {
                case RED:
                    if (current != low) {
                        swap(current, low); // set current bucket as bucket just after highest 'red bucket'
                    }
                    current++;
                    low++;
                    break;
                case WHITE:
                    current++; // if bucket should be in the 'middle' (= white), leave it
                    break;
                case BLUE:
                    swap(current, high); // set current bucket as bucket just below lowest 'blue bucket'
                    high--;
                    break;
            }
        }
    }

    // Testing client
    public static void main(String[] args) {
        int[] flag_buckets = new int[20]; // Initialize array with length 20
        for (int i = 0 ; i < flag_buckets.length ; i++) {
            flag_buckets[i] = StdRandom.uniform(3); // Populate array with random integers between 0 and 3 (not incl.)
        }
        StdOut.println(Arrays.toString(flag_buckets));
        DutchNationalFlag foo = new DutchNationalFlag(flag_buckets);
        foo.sort();
        StdOut.println(Arrays.toString(flag_buckets));
    }
}
