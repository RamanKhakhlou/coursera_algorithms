import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats  {
  private final int size;
  private final int trials;
  private final double[] results;
  private final double mean;
  private final double stddev;
  private final double confidenceLo;
  private final double confidenceHi;

  public PercolationStats(int n, int trials) {   // perform trials independent experiments on an n-by-n grid
    if (n <= 0) throw new IllegalArgumentException("Size can not be less than 1");
    if (trials <= 0) throw new IllegalArgumentException("Number of trials can not be less than 1");

    this.size = n;
    this.trials = trials;
    this.results = this.doExperiment();
    this.mean = this.getMean();
    this.stddev = this.getStdDev();
    this.confidenceLo = this.getConfidenceLo();
    this.confidenceHi = this.getConfidenceHi();
  }
  
  private double[] doExperiment() {
    double[] r = new double[this.trials];
    for (int i = 0; i < this.trials; i++) {
      r[i] = this.iterate();
    }
    return r;
  }
  
  private double iterate() {
    Percolation p = new Percolation(this.size);
    
    while (!p.percolates()) {
      int row = StdRandom.uniform(0, size);
      int column = StdRandom.uniform(0, size);
      if (!p.isOpen(row + 1, column + 1)) {
        p.open(row + 1, column + 1);
      }
    }

    return (double) p.numberOfOpenSites() / (double) (size * size);
  }
  
  private double getMean() {
    return StdStats.mean(this.results);
  }
  
  private double getStdDev() {
    return StdStats.stddev(this.results);
  }
  
  private double getConfidenceLo() {
    return this.mean - ((1.96 * this.stddev) / Math.sqrt(this.trials));
  }
  
  private double getConfidenceHi() {
    return this.mean + ((1.96 * this.stddev) / Math.sqrt(this.trials));
  }
  
  public double mean() {                         // sample mean of percolation threshold
    return this.mean;
  }

  public double stddev() {                       // sample standard deviation of percolation threshold
    return this.stddev;
  }

  public double confidenceLo() {                 // low  endpoint of 95% confidence interval
    return this.confidenceLo;
  }

  public double confidenceHi() {                 // high endpoint of 95% confidence interval
    return this.confidenceHi;
  }
  
  public static void main(String[] args) {
    int size = 64;
    PercolationStats ps = new PercolationStats(size, 150);
    StdOut.println(ps.mean());
    StdOut.println(ps.stddev());
    StdOut.println(ps.confidenceLo());
    StdOut.println(ps.confidenceHi());
  }
}