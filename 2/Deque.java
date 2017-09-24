import java.util.NoSuchElementException;
import java.util.Iterator;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
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

  public Deque() {                           // construct an empty deque
    this.first = null;
    this.last = null;
    this.size = 0;
  }

  public boolean isEmpty() {                // is the deque empty?
    return this.size == 0;
  }

  public int size() {                       // return the number of items on the deque
    return this.size;
  }

  public void addFirst(Item item) {         // add the item to the front
    if (item == null) throw new IllegalArgumentException("You can not add null value");

    if (this.isEmpty()) {
      Node node = new Node(item, null, null);
      this.first = node;
      this.last = node;
    } else {
      Node node = new Node(item, null, this.first);
      this.first.prev = node;
      this.first = node;
    }
    
    this.size++;
  }

  public void addLast(Item item) {          // add the item to the end
    if (item == null) throw new IllegalArgumentException("You can not add null value");

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

  public Item removeFirst() {               // remove and return the item from the front
    if (this.isEmpty()) throw new NoSuchElementException("You can not remove items from empty queue");

    this.size--;
    Item item = this.first.item;
    this.first = this.first.next;
    if(!this.isEmpty()) {
      this.first.prev = null;
    } else {
      this.last = null;
    }

    return item;
  }

  public Item removeLast() {                // remove and return the item from the end
    if (this.isEmpty()) throw new NoSuchElementException("You can not remove items from empty queue");

    this.size--;
    Item item = this.last.item;
    this.last = this.last.prev;
    if(!this.isEmpty()) {
      this.last.next = null;
    } else {
      this.first = null;
    }

    return item;
  }
  
  public Iterator<Item> iterator() {
    return new DequeIterator(this.first);
  }

  private class DequeIterator implements Iterator<Item> {
    private Node current;
    
    public DequeIterator(Node first) {
      this.current = first;
    }
    
    public boolean hasNext() {
      return this.current != null;
    }
    
    public void remove() {
      throw new UnsupportedOperationException("Does not support remove operation");
    }
    
    public Item next() {
      if (this.current == null) throw new NoSuchElementException("No items to iterate");

      Node node = this.current;
      this.current = node.next;

      return node.item;
    }
  }

  public static void main(String[] args) {  // unit testing (optional)
    Deque<Integer> deque = new Deque<Integer>();

    deque.addFirst(1);
    deque.addLast(2);
    deque.removeFirst();
    deque.removeLast();
    
    Iterator<Integer> i = deque.iterator();
    while (i.hasNext())
    {
      Integer s = i.next();
      StdOut.println(s);
    }
  }
}