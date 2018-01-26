import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int size;
    private Node first;
    private Node last;
    private class Node{
        private Item item;
        private Node prev;
        private Node next;
        private Node(Item item, Node prev, Node next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }
    public Deque() {                           // construct an empty deque
        size = 0;
        first = null;
        last = null;
    }
    public boolean isEmpty() {                 // is the deque empty?
        return size == 0;
    }
    public int size() {                        // return the number of items on the deque
        return size;
    }
    public void addFirst(Item item) {          // add the item to the front
        if (item == null)
            throw new IllegalArgumentException ();
        if (size == 0) {
            first = new Node(item, null, null);
            last = first;
        }
        else {
            first = new Node(item, null, first);
            first.next.prev = first;
         }
        size++;
    }
    public void addLast(Item item) {           // add the item to the end
        if (item == null)
            throw new IllegalArgumentException ();
        if (size == 0) {
            first = new Node(item, null, null);
            last = first;
        }
        else {
            last = new Node(item, last, null);
            last.prev.next = last;
        }
        size++;
    }
    public Item removeFirst() {                // remove and return the item from the front
        if (this.isEmpty())
            throw new NoSuchElementException();
        Item item = first.item;
        if (size == 1) {
            first = last = null;
        }
        else {
            first = first.next;
            first.prev = null;
        }
        size--;
        return item;
    }
    public Item removeLast() {                 // remove and return the item from the end
        if (this.isEmpty())
            throw new NoSuchElementException();
        Item item = last.item;
        if (size == 1) {
            first = last = null;
        }
        else {
            last = last.prev;
            last.next = null;
        }
        size--;
        return item;
    }
    public Iterator<Item> iterator() {         // return an iterator over items in order from front to end
        return new DequeIterator();
    }
    private class DequeIterator implements Iterator<Item>{
        private Node cur;
        private DequeIterator(){
            cur = first;
        }
        @Override
        public boolean hasNext() {
            // TODO Auto-generated method stub
            return cur != null;
        }

        @Override
        public Item next() {
            // TODO Auto-generated method stub
            if (!hasNext())
                throw new NoSuchElementException();
            Item item = cur.item;
            cur = cur.next;
            return item;
        }
        
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
    }
    public static void main(String[] args) {  // unit testing (optional)
        Deque<Integer> d = new Deque<Integer>();
        System.out.println(d.isEmpty());
        d.addLast(1);
        System.out.println(d.isEmpty());
        d.addFirst(0);
        d.addLast(2);
        d.addLast(3);
        Iterator<Integer> it= d.iterator();
        while (it.hasNext())
            System.out.print(it.next()+" ");
        System.out.println();
        d.removeFirst();
        d.removeLast();
        it= d.iterator();
        while (it.hasNext())
            System.out.print(it.next()+" ");
        System.out.println();
        d.removeFirst();
        it= d.iterator();
        while (it.hasNext())
            System.out.print(it.next()+" ");
        System.out.println();
        d.removeFirst();
        System.out.println(d.isEmpty());
    }
 }
