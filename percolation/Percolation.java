package percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final boolean [] sites;
    private final WeightedQuickUnionUF uf1;
    private final WeightedQuickUnionUF uf2;
    private final int n;
    private int opened;
    
    public Percolation(int n) {               // create n-by-n grid, with all sites blocked
        if (n < 1)
            throw new IllegalArgumentException ();
        this.n = n;
        uf1 = new WeightedQuickUnionUF(n*n+2);
        uf2 = new WeightedQuickUnionUF(n*n+2);
        sites = new boolean[n*n+2];
        opened = 0;
    }
    private int xyTo1D(int x, int y) {
        return (x-1)*n+(y-1);
    }
    public    void open(int row, int col) {    // open site (row, col) if it is not open already
        if (row < 1 || row > n || col < 1 || col > n)
            throw new IllegalArgumentException(); 
        if (this.isOpen(row, col))
            return;
        
        int thisID = xyTo1D(row,col);
        sites[thisID] = true;
        if (row == 1) {
            uf1.union(thisID, n*n);
            uf2.union(thisID, n*n);
        }
        if (row == n)
            uf1.union(thisID, n*n+1);
        if (row > 1) {
            if (sites[thisID-n]) {
                uf1.union(thisID, thisID-n);
                uf2.union(thisID, thisID-n);
            }
        }
        if (row < n) { 
            if (sites[thisID+n]) {
                uf1.union(thisID, thisID+n);
                uf2.union(thisID, thisID+n);       
            }
        }
        if (col > 1) {
            if (sites[thisID-1]) {
                uf1.union(thisID, thisID-1);
                uf2.union(thisID, thisID-1);
            }
        }
        if (col < n) {
            if (sites[thisID+1]) {
                uf1.union(thisID, thisID+1);
                uf2.union(thisID, thisID+1);
            }
        }
        opened++;  
    }
    public boolean isOpen(int row, int col) {  // is site (row, col) open?
        if (row < 1 || row > n || col < 1 || col > n)
            throw new IllegalArgumentException(); 
        return sites[xyTo1D(row,col)];
    }
    public boolean isFull(int row, int col) {  // is site (row, col) full?
        if (row < 1 || row > n || col < 1 || col > n)
            throw new IllegalArgumentException(); 
        return uf2.connected(xyTo1D(row,col), n*n);
    }
    public int numberOfOpenSites() {       // number of open sites
        return opened;
    }
    public boolean percolates() {             // does the system percolate?
        return uf1.connected(n*n, n*n+1);
    }

    public static void main(String[] args) {}  // test client (optional)
 }
