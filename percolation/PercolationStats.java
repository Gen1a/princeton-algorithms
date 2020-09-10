/******************************************************************************
 * Author: Frederik De Brouwer
 * 
 * Performs a series of computational experiments.
 * Takes two command-line arguments n and T, performs T independent computational experiments on an n-by-n grid.
 ******************************************************************************/

package percolation;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
// import edu.princeton.cs.algs4.Stopwatch;


public class PercolationStats {
    private final double[] values;
    private final int T; // amount of required trials

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n and trials need to be larger than 0.");
        }
        
        this.T = trials;
        this.values = new double[T];

        for (int i = 0; i < trials; i++) {
            values[i] = (double) executeTrial(n) / (n * n);
        }
    }

    private int executeTrial(int n) {
        Percolation p = new Percolation(n);

        while (!(p.percolates())) {
            // Get random site coordinates
            int row = StdRandom.uniform(1, n + 1);
            int col = StdRandom.uniform(1, n + 1);
            // Open the site
            p.open(row, col);
        }
        return p.numberOfOpenSites();
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(values);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        if (T == 1)
            return Double.NaN;
        return StdStats.stddev(values);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return this.mean() - (1.96 * this.stddev() / Math.sqrt(T));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.mean() + (1.96 * this.stddev() / Math.sqrt(T));
    }

   // test client (see below)
    public static void main(String[] args) {
        // Stopwatch stopwatch = new Stopwatch();
        PercolationStats test = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.println("Mean                    = " + test.mean());
        System.out.println("Stddev                  = " + test.stddev());
        System.out.println("95% confidence interval = [" + test.confidenceLo() + ", " + test.confidenceHi() + "]");
        // System.out.println("Elapsed time: " + stopwatch.elapsedTime());
    }
}