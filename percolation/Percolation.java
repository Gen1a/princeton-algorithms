/******************************************************************************
 * Author: Frederik De Brouwer
 * 
 * The problem: In a famous scientific problem, researchers are interested in the following question:
 * if sites are independently set to be open with probability p (and therefore blocked with probability 1 − p),
 * what is the probability that the system percolates?
 * When p equals 0, the system does not percolate; when p equals 1, the system percolates.
 * When n is sufficiently large, there is a threshold value p* such that when p < p* a random n-by-n grid almost never percolates,
 * and when p > p*, a random n-by-n grid almost always percolates. 
 * No mathematical solution for determining the percolation threshold p* has yet been derived.
 * Your task is to write a computer program to estimate p*.
 *
 * By convention: the row and column indices are integers between 1 and n, where (1, 1) is the upper-left site.
 * Performance requirements:  The constructor must take time proportional to n2;
 * all instance methods must take constant time plus a constant number of calls to union() and find().
 * 
 * Note: Backwash problem can be fixed by creating a seperate WeightedQuickUnionUF object for the virtual top and bottom site (not implemented here).
 ******************************************************************************/

package percolation;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // WeightedQuickUnionUF object
    private final WeightedQuickUnionUF unionFind;
    // Grid size
    private final int N;
    // Matrix which will represent open sites
    private boolean[][] open;
    // Virtual top and bottom sites to prevent execution of N² connected queries
    private final int top;
    private final int bottom; 
    // Variable to hold number of open sites
    private int openSites = 0;
    
    // creates n-by-n grid, with all sites initially blocked
    public Percolation (int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be greater than 0.");
        }
        this.N = n;
        this.unionFind = new WeightedQuickUnionUF(n * n + 2); // // initializes unionFind grid of n * n element + 2 virtual sites
        this.open = new boolean[N][N]; // initializes open matrix
        // Define top and bottom virtual site
        this.top = N * N;
        this.bottom = N * N + 1;
        for (int i = 0; i < N; i++) {
            unionFind.union(top, i);
            unionFind.union(bottom, N * (N - 1) + i);
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        // Check if site already open
        if (isOpen(row, col)) return; 
        // Open single site and increment counter;
        open[row-1][col-1] = true;
        openSites++;
        // Link site in question to its open neighbors
        int objectIndex = convertXYTo1D(row, col);
        if (row > 1 && isOpen(row - 1, col)) {
            unionFind.union(objectIndex, convertXYTo1D(row - 1, col));
        }
        if (row < N && isOpen(row + 1, col)) {
            unionFind.union(objectIndex, convertXYTo1D(row + 1, col));
        }
        if (col > 1 && isOpen(row, col - 1)) {
            unionFind.union(objectIndex, convertXYTo1D(row, col - 1));
        }
        if (col < N && isOpen(row, col + 1)) {
            unionFind.union(objectIndex, convertXYTo1D(row, col + 1));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        isInvalidInput(row, col);

        return open[row-1][col-1];
    }

    // is the site (row, col) full?
    // (A full site is an open site that can be connected to an open site in the top row via a chain of neighboring (left, right, up, down) open sites.)
    public boolean isFull(int row, int col) {
        isInvalidInput(row, col);

        return isOpen(row, col) && (unionFind.find(this.top) == unionFind.find(convertXYTo1D(row, col)));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return unionFind.find(this.top) == unionFind.find(this.bottom);
    }

    // Checks for valid row and col input
    private void isInvalidInput(int row, int col) {
        if (row < 1 || col < 1 || row > N || col > N) {
            throw new IllegalArgumentException("Row and column indices must be integers between 1 and n.");
        }
    }

    // Mapping 2D (row, column) pair to 1D array of object indices
    private int convertXYTo1D(int row, int col) {
        isInvalidInput(row, col);

        return (row - 1) * N + (col - 1);
    }

    // test client (optional)
    public static void main(String[] args) {
        // Test if both open sites have top virtual site as a root
        int n = 5;
        Percolation test = new Percolation(n);
        // test.open(1, 1);
        // test.open(1, 2);
        System.out.println(test.isOpen(1, 1));
        System.out.println(test.isFull(1, 1));
        // System.out.println(test.isOpen(1,2));
        // System.out.println(test.unionFind.find(0));
        // System.out.println(test.unionFind.find(0) == test.unionFind.find(1));
    }
}