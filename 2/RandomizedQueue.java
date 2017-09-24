import java.util.NoSuchElementException;
import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
  private Node first;
  private Node last;
  private int size;

  private class Node {
    Item item;
    Node prev;
    Node next;
    
    public Node(Item item, Node prev, Node next) {
      this.item = item;
      this.prev = prev;
      this.next = next;
    }
  }

  public RandomizedQueue() {                           // construct an empty randomized queue
    this.first = null;
    this.last = null;
    this.size = 0;
  }

  public boolean isEmpty() {                // is the queue empty?
    return this.size == 0;
  }

  public int size() {                       // return the number of items on the queue
    return this.size;
  }
  
  public void enqueue(Item item) {          // add the item
    if (item == null) throw new IllegalArgumentException("You can not add null item");

    if (this.isEmpty()) {
      Node node = new Node(item, null, null);
      this.first = node;
      this.last = node;
    } else {
      Node node = new Node(item, this.last, null);
      this.last.next = node;
      this.last = node;
    }

    this.size++;
  }

  public Item dequeue() {                   // remove and return a random item
    if (this.isEmpty()) throw new NoSuchElementException("You can not remove items from empty queue");

    int index = StdRandom.uniform(this.size);
    Node node = this.find(index);
    Node prev = node.prev;
    Node next = node.next;

    this.size--;

    if (prev != null && next != null) {
      prev.next = next;
      next.prev = prev;
    }
    if (prev == null && next != null) {
      this.first = next;
      next.prev = null;
    }
    if (prev != null && next == null) {
      this.last = prev;
      prev.next = null;
    }

    return node.item;
  }

  public Item sample() {                    // return (but do not remove) a random item
    if (this.isEmpty()) throw new NoSuchElementException("You can not remove items from empty queue");
    int index = StdRandom.uniform(this.size);
    return this.find(index).item;
  }
  
  private Node find(int index) {
    int currentIndex = 0;
    Node current = this.first;
    
    while (currentIndex < index) {
      current = current.next;
      currentIndex++;
    }
    
    return current;
  }
  
  public Iterator<Item> iterator() { // return an independent iterator over items in random order
    return new RandomizedQueueIterator(this.first, this.size);
  }

  private class RandomizedQueueIterator implements Iterator<Item> {
    private int size;
    private final Node first;
    private final Item[] sequence;
    private int current;
    
    public RandomizedQueueIterator(Node node, int size) {
      this.current = 0;
      this.size = size;
      this.first = node;
      this.sequence = generateSequence(size);
    }

    private Item[] generateSequence(int s) {
      this.size = s;
      Item[] seq = (Item[]) new Object[s];
      for (int i = 0; i < s; i++) {
        int index = 0;
        Node node = null;
        while (node == null) {
          index = StdRandom.uniform(this.size);
          if (seq[index] == null) {
            node = this.find(i);
          }
        }
        seq[index] = node.item;
      }
      return seq;
    }
    
    private Node find(int index) {
      int currentIndex = 0;
      Node node = this.first;
      
      while (currentIndex < index) {
        node = node.next;
        currentIndex++;
      }
      
      return node;
    }
    
    public boolean hasNext() {
      return this.current < this.size;
    }
    
    public void remove() {
      throw new UnsupportedOperationException("Does not support remove operation");
    }
    
    public Item next() {
      if (this.current >= this.sequence.length) throw new NoSuchElementException("No more items");
      return this.sequence[this.current++];
    }
  }

  public static void main(String[] args) {  // unit testing (optional)
    RandomizedQueue<String> d = new RandomizedQueue<String>();

    d.enqueue("A");
    d.enqueue("B");
    d.enqueue("C");
    d.enqueue("D");
    d.enqueue("E");
    d.enqueue("F");
    d.enqueue("G");
  }
}