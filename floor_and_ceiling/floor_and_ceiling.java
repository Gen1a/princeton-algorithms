/*****************************************************************************
 * Question: Floor and ceiling. 
 * Given a set of comparable elements, the ceiling of x is the smallest element 
 * in the set greater than or equal to x, and the floor is the largest element 
 * less than or equal to x. Suppose you have an array of N items in ascending order. 
 * Give an O(log N) algorithm to find the floor and ceiling of x.
 *****************************************************************************/

package floor_and_ceiling;
import edu.princeton.cs.algs4.StdOut;

public class floor_and_ceiling {
    
    private static int indexFloor(int[] array, int x) {
        int lowest = 0;
        int highest = array.length - 1;
        int indexFloor = 0;
        while (lowest <= highest){
            int middle = lowest + (highest - lowest) / 2;
            if (array[middle] == x){ // if key is equal to middle element
                indexFloor = middle;
                break;
            }
            else if (array[middle] < x && array[middle+1] > x){ // if key is bigger than previous and smaller than next element
                indexFloor = middle;
                break;

            }
            else if (array[middle] < x){
                lowest = middle + 1;
            }
            else{
                highest = middle - 1;
            }
        }
        return indexFloor;
    }
    
    // Testing client
    public static void main(String[] args){
        int[] array1 = {1, 2, 4, 8, 19, 25, 39, 47, 55, 67, 88, 92, 110};
        int x = 47;
        int indexFloor = indexFloor(array1, x);
        StdOut.println("Floor is: " + array1[indexFloor]);
        if (x == array1[indexFloor]){
            StdOut.println("Ceiling is: " + array1[indexFloor]);
        }
        else{
            StdOut.println("Ceiling is: " + array1[indexFloor+1]);
        }
    }
}
