/*****************************************************************************
 * Question: Local minimum in an array. 
 * Write a program that, given an array a[] of n distinct integers, finds a local minimum: 
 * an index i such that botha[i] < a[i-1] and a[i] < a[i+1] (assuming the neighboring entry is in bounds). 
 * Your program should use ~ 2 lg n compares in the worst case.
 *****************************************************************************/
package local_minimum;
import edu.princeton.cs.algs4.StdOut;

public class local_minimum {

    private static int localMinimum(int[] array) {
        /* Returns index of local minimum
        * Assumptions:
        * If array has 1 element: local minimum is the single element
        * If array has 2 elements: 1 must be the local minimum
        * Corner cases can be seen as a local minimum
        */
        if (array == null) {
            return -1;
        }
        else if (array.length == 1) {
            return 0;
        }
        else if (array.length == 1) {
            return array[0] > array[1] ? 1 : 0;
        }
        int lowest = 0;
        int highest = array.length - 1;

        while (lowest <= highest) {
            int middle = lowest + (highest - lowest) / 2;
            // Corner case upper bound
            if (middle == array.length - 1){
                return highest;
            }
            // Corner case lower bound
            else if (middle == 0){
                return 0;
            }
            // if local minimum found
            if (array[middle - 1] > array[middle] && array[middle + 1] > array[middle]) {
                return middle;
            }
            // if previous value is smaller
            if (array[middle-1] < array[middle]) {
                highest = middle - 1;
            }
            // if next value is smaller
            else{
                lowest = middle + 1;
            }
        }
        return -1;
    }
    
    // Testing client
    public static void main(String[] args){
        int[] array1 = {10, -9, 20, 25, 21, 40, 50, -20};
        int[] array2 = {-4, -3, 9, 4, 10, 2, 20};
        int[] array3 = {5, -3, -5, -6, -7, -8};
        int[] array4 = {5};
        int[] array5 = {10, 20};
        int[] array6 = {7, 20, 30};

        int localMinimum1 = localMinimum(array1);
        int localMinimum2 = localMinimum(array2);
        int localMinimum3 = localMinimum(array3);
        int localMinimum4 = localMinimum(array4);
        int localMinimum5 = localMinimum(array5);
        int localMinimum6 = localMinimum(array6);

        StdOut.println("Local Minimum: " + array1[localMinimum1] + " Expected: -9 or -20");
        StdOut.println("Local Minimum: " + array2[localMinimum2] + " Expected: 4 or -4 or 2");
        StdOut.println("Local Minimum: " + array3[localMinimum3] + " Expected: -8");
        StdOut.println("Local Minimum: " + array4[localMinimum4] + " Expected: 5");
        StdOut.println("Local Minimum: " + array5[localMinimum5] + " Expected: 10");
        StdOut.println("Local Minimum: " + array6[localMinimum6] + " Expected: 7");
    }
}
