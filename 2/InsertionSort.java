import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;

public class InsertionSort {
  private int[] array;
  private int size;
  
  private int[] initArray(int size) {
    int[] array = new int[size];
    for(int i = 0; i < size; i++) {
      array[i] = StdRandom.uniform(10);
    }
    return array;
  }
  
  public InsertionSort(int size) {
    this.size = size;
    this.array = this.initArray(size);
  }
  
  public void show() {
    for(int i = 0; i < this.size; i++) {
      StdOut.print(this.array[i]);
    }
    StdOut.println();
  }
  
  public void sort() {
    for(int i = 0; i < this.size; i++) {
      for(int j = i; j > 0; j--) {
        if(this.array[j] < this.array[j - 1]) {
          int temp = this.array[j - 1];
          this.array[j - 1] = this.array[j];
          this.array[j] = temp;
        }
      }
    }
  }
  
  public static void main(String[] args) { 
        Integer n = 10;
        InsertionSort sort = new InsertionSort(n);
        
        sort.show();
        sort.sort();
        sort.show();
    }
}