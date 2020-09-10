/********************************************************************
 * Question: An array is bitonic if it is comprised of an increasing sequence of integers
 * followed immediately by a decreasing sequence of integers. 
 * Write a program that, given a bitonic array of n distinct int values, 
 * determines whether a given integer is in the array. 
 * Your program should use ~ 3 log n compares in the worst case.
 */

package bitonic_search;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;


public class bitonic_search_3lgN {

    private static int bitonicSearch(int[] b, int value){
        // Catch bad input
        if (b == null || b.length == 0){
            return -1;
        }

        // Find the turning point index in the array in with binary search (running time log N)
        int turningPoint = findTurningPoint(b, 0, b.length - 1);

        if (value > b[turningPoint]){
            return -1; // if value is > turning point it can't be in the array
        }
        else if (value == b[turningPoint]){
            return turningPoint;
        }
        else{
            // Run binary search on ascending part (running time log N)
            int lowest = 0;
            int highest = turningPoint - 1;
            int ascendingIndex = ascendingBinarySearch(b, lowest, highest, value);

            if (ascendingIndex != - 1){
                return ascendingIndex;
            }
            else{
                // Run binary search on descending part (running time log N)
                lowest = turningPoint + 1;
                highest = b.length - 1;
                int descendingIndex = descendingBinarySearch(b, lowest, highest, value);
                if (descendingIndex != -1){
                    return descendingIndex;
                }
            }
        }
        return -1;
    }

    private static int descendingBinarySearch(int[] b, int lowest, int highest, int value) {
        while (lowest <= highest){
            int middle = lowest + (highest - lowest) / 2;
            if (value == b[middle]){
                return middle;
            }
            else if (value < b[middle]){
                lowest = middle + 1;
            }
            else{
                highest = middle - 1;
            }
        }
        return -1; // if value not found
    }

    private static int ascendingBinarySearch(int[] b, int lowest, int highest, int value) {
        while (lowest <= highest){
            int middle = lowest + (highest - lowest) / 2;
            if (value == b[middle]){
                return middle;
            }
            else if (value < b[middle]){
                highest = middle - 1;
            }
            else{
                lowest = middle + 1;
            }
        }
        return -1; // if value not found
    }

    private static int findTurningPoint(int[] b, int lowest, int highest) {
        if (lowest == highest){
            return highest;
        }
        int middle = lowest + (highest - lowest) / 2;

        if (middle == 0){
            if (b[middle] < b[middle+1]){
                return findTurningPoint(b, middle + 1, highest);
            }
            else{
                return 0;
            }
        }
        if (b[middle] > b[middle-1] && b[middle] > b[middle+1]){
            return middle;
        }
        else if (b[middle] > b[middle-1] && b[middle] < b[middle+1]){
            lowest = middle + 1;
            return findTurningPoint(b, lowest, highest);
        }
        else {
            highest = middle - 1;
            return findTurningPoint(b, lowest, highest);
        }

    }

    // Testing Client
    public static void main(String [] args){
        int[] array1 = {1, 2, 3, 4, -1, -2, -3};
        int[] array2 = {1, 5, 4, 3, 2, 0};
        int[] array3 = {2, 4, 8, 16, 32, 1};
        int[] array4 = {2, 4, 1};

        // Find Turning Point Test
        int indexTP1 = findTurningPoint(array1, 0, array1.length - 1);
        int indexTP2 = findTurningPoint(array2, 0, array2.length - 1);
        int indexTP3 = findTurningPoint(array3, 0, array3.length - 1);
        int indexTP4 = findTurningPoint(array4, 0, array4.length - 1);
        StdOut.println("Index of turning point: " + indexTP1);
        StdOut.println("Index of turning point: " + indexTP2);
        StdOut.println("Index of turning point: " + indexTP3);
        StdOut.println("Index of turning point: " + indexTP4);

        // Bitonic Search Test
        Stopwatch timer = new Stopwatch();
        int indexOfElement1 = bitonicSearch(array1, -1);
        int indexOfElement2 = bitonicSearch(array2, 5);
        int indexOfElement3 = bitonicSearch(array3, 2);
        int indexOfElement4 = bitonicSearch(array3, 99);
        int indexOfElement5 = bitonicSearch(array4, 1);

        StdOut.println("Index of element: " + indexOfElement1 + " Expected: 4");
        StdOut.println("Index of element: " + indexOfElement2 + " Expected: 1");
        StdOut.println("Index of element: " + indexOfElement3 + " Expected: 0");
        StdOut.println("Index of element: " + indexOfElement4 + " Expected: -1");
        StdOut.println("Index of element: " + indexOfElement5 + " Expected: 2");
        StdOut.println("elapsed time = " + timer.elapsedTime());
    }
}
