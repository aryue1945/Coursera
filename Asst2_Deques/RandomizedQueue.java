import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item [] a;
    private int n;
    
    public RandomizedQueue() {                 // construct an empty randomized queue
        a = (Item[]) new Object[2];
        n = 0;
    }
    public boolean isEmpty() {                 // is the randomized queue empty?
        return n == 0;
    }
    public int size() {                       // return the number of items on the randomized queue
        return n;
    }
    private void resize(int capacity) {
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            temp[i] = a[i];
        }
        a = temp;
    }
    public void enqueue(Item item) {           // add the item
        if (item == null) throw new IllegalArgumentException();
        if (n == a.length) resize(2*a.length);    // double size of array if necessary
        a[n++] = item;                            // add item
    }
    public Item dequeue() {                    // remove and return a random item
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        int t = StdRandom.uniform(n);
        Item item = a[t];
        a[t] = a[n-1];
        a[n-1] = null;                              // to avoid loitering
        n--;
        // shrink size of array if necessary
        if (n > 0 && n == a.length/4) resize(a.length/2);
        return item;
    }
    public Item sample() {                     // return a random item (but do not remove it)
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        return a[StdRandom.uniform(n)];
    }
    private class RandomizedQueueIterator implements Iterator<Item>{
        Item [] items;
        int index;
        int size;
        private RandomizedQueueIterator(){
            size = n;
            index = 0;
            items = (Item[]) new Object[n];
            for (int i = 0; i < n; i++)
                items[i] = a[i];
            StdRandom.shuffle(items);
        }
        @Override
        public boolean hasNext() {
            // TODO Auto-generated method stub
            return index < size;
        }

        @Override
        public Item next() {
            // TODO Auto-generated method stub
            if (!hasNext())
                throw new NoSuchElementException();
            return items[index++];
        }
        
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
    }
    public Iterator<Item> iterator(){         // return an independent iterator over items in random order
        return new RandomizedQueueIterator();
    }
    public static void main(String[] args) {   // unit testing (optional)
        RandomizedQueue<Character> r = new RandomizedQueue<Character>();
        r.enqueue('A');
        r.enqueue('B');
        r.enqueue('C');
        r.enqueue('D');
        System.out.printf("%c%c%c%c",r.dequeue(),r.dequeue(),r.dequeue(),r.dequeue());


    }
 }
