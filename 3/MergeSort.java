public class MergeSort {
  private int[] merge(int[] left, int[] right) {
      int size = left.length + right.length; 
      int[] merge = new int[size];
      int mIndex = 0;
      int lIndex = 0;
      int rIndex = 0;
      
      while(mIndex < size) {
          if(lIndex < left.length && rIndex < right.length) {
            int lItem = left[lIndex];
            int rItem = right[rIndex];
            if(lItem <= rItem) {
              merge[mIndex] = lItem;
              lIndex++;
            } else {
              merge[mIndex] = rItem;
              rIndex++;
            }
          } else if(lIndex < left.length) {
              int lItem = left[lIndex];
              merge[mIndex] = lItem;
              lIndex++;
          } else {
              int rItem = right[rIndex];
              merge[mIndex] = rItem;
              rIndex++;
          }
          mIndex++;
      }
      
      return merge;
  }
   
  public int[] sort(int[] array) {
    if(array.length == 1) {
      return array;
    } else {
      int middle = array.length / 2;
      int[] left = this.copy(array, 0, middle);
      int[] right = this.copy(array, middle, array.length);
      int[] sortedL = this.sort(left);
      int[] sortedR = this.sort(right);
      return this.merge(sortedL, sortedR);
    }
  }
  
  private int[] copy(int[] array, int from, int to) {
    int length = to - from;
    int[] result = new int[length];
    
    for(int i = from; i < to; i++) {
      result[i - from] = array[i];
    }
    
    return result;
  }
  
  private void show(int[] array) {
    for(int i = 0; i < array.length; i++) {
      System.out.print(array[i]);
    }
    System.out.println();
  }
  
  public static void main(String[] args) {
    MergeSort merge = new MergeSort();

    int[] arr = new int[]{ 6, 3, 4, 1, 5, 2, 7 };
    int[] sorted = merge.sort(arr);
    
    merge.show(sorted);
  }
}