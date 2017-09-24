public class MergeSort {
//  public int[] sort(int[] array) {
//    if(array.length == 1) {
//      return array;
//    } else {
//      int middle = array.length / 2;
//      int[] left 
//    }
//  }
  
  private int[] copy(int[] array, int from, int to) {
    int length = to - from;
    int[] result = new int[length];
    
    for(int i = from; i < from + to; i++) {
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
    int[] left = merge.copy(arr, 0, 4);
    int[] right = merge.copy(arr, 4, 7);
    
    merge.show(arr);
    merge.show(left);
    merge.show(right);
  }
}