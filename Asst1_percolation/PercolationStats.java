import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
    private final double [] records;
    private final int n;
    private final int trials;
    private final double sd;
    private final double m;
    public PercolationStats(int n, int trials) {   // perform trials independent experiments on an n-by-n grid
        if (n < 1 || trials < 1)
            throw new IllegalArgumentException ();
        this.n= n;
        this.trials = trials;
        records = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation perc = new Percolation(n);
            while (!perc.percolates()) {
                perc.open(StdRandom.uniform(n)+1, StdRandom.uniform(n)+1);
            }
            records[i] = (double)(perc.numberOfOpenSites())/(n*n);
        }
        m = StdStats.mean(records);
        sd = StdStats.stddev(records);
    }
    public double mean() {                          // sample mean of percolation threshold
       return m;
    }
    public double stddev() {                        // sample standard deviation of percolation threshold
        if (trials == 1)
            return 0;
        return sd;
    }
    public double confidenceLo() {                  // low  endpoint of 95% confidence interval
        return m-(1.96*sd)/Math.sqrt(trials);
    }
    public double confidenceHi() {                  // high endpoint of 95% confidence interval
        return m+(1.96*sd)/Math.sqrt(trials);
    }

    public static void main(String[] args) {        // test client (described below)
        PercolationStats stat = new PercolationStats(150,150);
        System.out.printf("n:%d trials:%d mean:%f sd:%f, uc:%f, lc:%f", 
                stat.n, stat.trials, stat.m, stat.sd, stat.confidenceHi(), stat.confidenceLo());
    }
 }
