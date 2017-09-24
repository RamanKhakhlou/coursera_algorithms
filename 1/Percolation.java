import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
  private final int size;
  private boolean[] field;
  private final WeightedQuickUnionUF union;

  public Percolation(int n) {               // create n-by-n grid, with all sites blocked
    if (n <= 0) throw new IllegalArgumentException("Size can not be less than 1");

    this.size = n;
    this.field = new boolean[n * n];
    this.union = new WeightedQuickUnionUF(n * n);
  }
  
  private int getIndex(int row, int col) {
    return row * this.size + col;
  }

  public void open(int row, int col) {   // open site (row, col) if it is not open already
    if (row < 1 || row > this.size) throw new IllegalArgumentException("Row is outside of boundaries");
    if (col < 1 || col > this.size) throw new IllegalArgumentException("Column is outside of boundaries");
    
    if (this.isOpen(row, col)) return;

    int rowIndex = row - 1;
    int colIndex = col - 1;
    
    int currentIndex = this.getIndex(rowIndex, colIndex);
    this.field[currentIndex] = true;

    int topRow = rowIndex - 1;
    if (topRow >= 0 && this.isOpen(topRow + 1, colIndex + 1)) {
        int topIndex = this.getIndex(topRow, colIndex);
        this.union.union(topIndex, currentIndex);
    }

    int leftColumn = colIndex - 1;
    if (leftColumn >= 0 && this.isOpen(rowIndex + 1, leftColumn + 1)) {
        int leftIndex = this.getIndex(rowIndex, leftColumn);
        this.union.union(leftIndex, currentIndex);
    }

    int bottomRow = rowIndex + 1;
    if (bottomRow < this.size && this.isOpen(bottomRow + 1, colIndex + 1)) {
        int bottomIndex = this.getIndex(bottomRow, colIndex);
        this.union.union(bottomIndex, currentIndex);
    }

    int rightColumn = colIndex + 1;
    if (rightColumn < this.size && this.isOpen(rowIndex + 1, rightColumn + 1)) {
        int rightIndex = this.getIndex(rowIndex, rightColumn);
        this.union.union(rightIndex, currentIndex);
    }
  }

  public boolean isOpen(int row, int col) { // is site (row, col) open?
    if (row < 1 || row > this.size) throw new IllegalArgumentException("Row is outside of boundaries");
    if (col < 1 || col > this.size) throw new IllegalArgumentException("Column is outside of boundaries");

    int index = this.getIndex(row - 1, col - 1);
    return this.field[index];
  }

  public boolean isFull(int row, int col) { // is site (row, col) full?
    if (row < 1 || row > this.size) throw new IllegalArgumentException("Row is outside of boundaries");
    if (col < 1 || col > this.size) throw new IllegalArgumentException("Column is outside of boundaries");
    
    if (!this.isOpen(row, col)) return false;
    
    int cellIndex = this.getIndex(row - 1, col - 1);
    for (int i = 0; i < this.size; i++) {
       int topIndex = this.getIndex(0, i);
        
       if (this.isOpen(1, i + 1)) {
         if(this.union.connected(topIndex, cellIndex)) {
           return true;
         }
       }
    }
    return false;
  }

  public int numberOfOpenSites() {      // number of open sites
    int number = 0;
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        int index = this.getIndex(i, j);
        if (this.field[index]) {
          number++;
        }
      }
    }
    return number;
  }

  public boolean percolates() {             // does the system percolate?
    for (int i = 0; i < this.size; i++) {
      if (this.isFull(this.size, i + 1)) {
        return true;
      }
    }
    
    return false;
  }

  public static void main(String[] args) {  // test client (optional)
    int size = 6;
    Percolation p = new Percolation(size);

    // StdOut.println(p.isFull(1, 1));
    // StdOut.println(p.isOpen(1, 1));
   
    p.open(1, 1);
    
    for(int i = 1; i <= size; i++) {
      for(int j = 1; j <= size; j++) {
        StdOut.println(p.isOpen(i, j));
        StdOut.println(p.isFull(i, j));
        StdOut.println(p.percolates());
      }
    }
    
    p.open(6, 1);
    p.open(1, 6);
    p.open(6, 6);
    
    StdOut.println(p.isFull(1, 1));
    StdOut.println(p.isFull(2, 2));
    StdOut.println(p.isFull(3, 3));
    StdOut.println(p.isFull(3, 5));
    StdOut.println(p.percolates());
  }
}