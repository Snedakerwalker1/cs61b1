package hw2;

import java.util.List;
import java.util.ArrayList;

public class PercolationStats {
    private double mean;
    private double standardDev;
    private int number;

    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException("Arguments must be greater than zero");
        }
        double x = 0;
        double standd = 0;
        this.number = T;
        List<Double> results = new ArrayList<>();
        int row;
        int col;
        double xi;
        for (int i = 0; i < T; i += 1) {
            xi = 0;
            Percolation newPerc = new Percolation(2);
            while (!newPerc.percolates()) {
                row = edu.princeton.cs.introcs.StdRandom.uniform(0, N);
                col = edu.princeton.cs.introcs.StdRandom.uniform(0, N);
                if (!newPerc.isOpen(row, col)) {
                    newPerc.open(row, col);
                    xi += 1;
                }
            }
            results.add(xi);
            x += xi;
        }
        this.mean = x / T;
        for (int i = 0; i < results.size(); i += 1) {
            standd += (results.get(i) - this.mean) * (results.get(i) - this.mean);
        }
        this.standardDev = Math.sqrt(standd / (T - 1));
    }

    public double mean() {
        return this.mean;

    }
    public double stddev() {
        return this.standardDev;
    }

    public double confidenceLow() {
        return this.mean - ((1.96 * standardDev) / (Math.sqrt(this.number)));
    }

    public double confidenceHigh() {
        return this.mean + ((1.96 * standardDev) / (Math.sqrt(this.number)));
    }
}                       
