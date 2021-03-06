package hw2;                       

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.List;
import java.util.ArrayList;

public class Percolation {
    private int numberOpen;
    private int size;
    private List<Integer> bottoms;
    private boolean[][] openArray;
    private WeightedQuickUnionUF unions;
    private WeightedQuickUnionUF fullunions;
    private int top;
    private int bottom;

    public Percolation(int N) {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException("Arguments must be greater than zero");
        }
        this.numberOpen = 0;
        this.bottoms = new ArrayList<>();
        this.size = N;
        this.top = (N * N) + 3;
        this.bottom = (N * N) + 2;
        this.openArray = new boolean[N][N];
        for (int i = 0; i < N; i += 1) {
            for (int j = 0; j < N; j += 1) {
                openArray[i][j] = false;
            }
        }
        this.unions = new WeightedQuickUnionUF((N * N) + 4);
        this.fullunions = new WeightedQuickUnionUF((N * N) + 4);
    }

    private int rcToInt(int row, int col) {
        return this.size * row + col;
    }
    private List<int[]> adjacent(int row, int col) {
        List<int[]> retLst = new ArrayList<>();
        int[] intarr1 = new int[2];
        int[] intarr2 = new int[2];
        int[] intarr3 = new int[2];
        int[] intarr4 = new int[2];
        intarr1[0] = row;
        intarr1[1] = col + 1;
        intarr2[0] = row + 1;
        intarr2[1] = col;
        intarr3[0] = row;
        intarr3[1] = col - 1;
        intarr4[0] = row - 1;
        intarr4[1] = col;
        if (row == 0) {
            if (col == 0) {
                retLst.add(intarr1);
                retLst.add(intarr2);
            } else if (col == (this.size - 1)) {
                retLst.add(intarr3);
                retLst.add(intarr2);
            } else {
                retLst.add(intarr1);
                retLst.add(intarr2);
                retLst.add(intarr3);
            }
        } else if (row == (this.size - 1)) {
            if (col == 0) {
                retLst.add(intarr1);
                retLst.add(intarr4);
            } else if (col == (this.size - 1)) {
                retLst.add(intarr3);
                retLst.add(intarr4);
            } else {
                retLst.add(intarr1);
                retLst.add(intarr4);
                retLst.add(intarr3);
            }
        } else {
            if (col == 0) {
                retLst.add(intarr1);
                retLst.add(intarr2);
                retLst.add(intarr4);
            } else if (col == (this.size - 1)) {
                retLst.add(intarr3);
                retLst.add(intarr2);
                retLst.add(intarr4);
            } else {
                retLst.add(intarr1);
                retLst.add(intarr2);
                retLst.add(intarr3);
                retLst.add(intarr4);
            }
        }
        return retLst;
    }

    public void open(int row, int col) {
        if (row >= this.size || col >= this.size) {
            throw new IndexOutOfBoundsException("The given index is out of bounds");
        } else if (openArray[row][col]) {
            return;
        } else {
            openArray[row][col] = true;
            if (row == 0) {
                unions.union(rcToInt(row, col), this.top);
                fullunions.union(rcToInt(row, col), this.top);
            }
            if (row == this.size - 1) {
                unions.union(rcToInt(row, col), this.bottom);
            }
            this.numberOpen += 1;
            if (this.size > 1) {
                List<int[]> adjacentPnts = adjacent(row, col);
                for (int i = 0; i < adjacentPnts.size(); i += 1) {
                    if (isOpen(adjacentPnts.get(i)[0], adjacentPnts.get(i)[1])) {
                        unions.union(rcToInt(row, col), rcToInt(adjacentPnts.get(i)[0],
                                adjacentPnts.get(i)[1]));
                        fullunions.union(rcToInt(row, col), rcToInt(adjacentPnts.get(i)[0],
                                adjacentPnts.get(i)[1]));
                    }
                }
            }
        }
    }

    public boolean isOpen(int row, int col) {
        return this.openArray[row][col];
    }

    public boolean isFull(int row, int col) {
        return fullunions.connected(rcToInt(row, col), this.top);
    }

    public int numberOfOpenSites() {
        return this.numberOpen;
    }

    public boolean percolates() {
        return unions.connected(this.bottom, this.top);
    }
}                       
