import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;

public class SelectionSort {
  private int[] array;
  private int size;
  
  private int[] initArray(int size) {
    int[] array = new int[size];
    for(int i = 0; i < size; i++) {
      array[i] = StdRandom.uniform(10);
    }
    return array;
  }
  
  public SelectionSort(int size) {
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
    for(int i = 0; i < this.size - 1; i++) {
      int current = this.array[i];
      int minIndex = i;
      
      for(int j = i + 1; j < this.size; j++) {
        if(this.array[j] < current) {
          current = this.array[j];
          minIndex = j;
        }
      }
      
      if(i != minIndex) {
        int temp = this.array[i];
        this.array[i] = this.array[minIndex];
        this.array[minIndex] = temp;
      }
    }
  }
  
  public static void main(String[] args) { 
        Integer n = 10;
        SelectionSort sort = new SelectionSort(n);
        
        sort.show();
        sort.sort();
        sort.show();
    }
}