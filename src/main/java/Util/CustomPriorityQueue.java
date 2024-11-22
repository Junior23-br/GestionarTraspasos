package Util;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

public class CustomPriorityQueue<E> implements Iterable<E> {
    private ListaEnlazada<E> heap;
    private Comparator<E> comparator;

    public CustomPriorityQueue(Comparator<E> comparator) {
        this.heap = new ListaEnlazada<>();
        this.comparator = comparator;
    }

    public void add(E element) {
        ListaEnlazada<E> tempHeap = new ListaEnlazada<>();
        boolean added = false;

        for (int i = 0; i < heap.size(); i++) {
            E current = heap.get(i);
            if (!added && comparator.compare(element, current) <= 0) {
                tempHeap.add(element);
                added = true;
            }
            tempHeap.add(current);
        }

        if (!added) {
            tempHeap.add(element);
        }

        heap = tempHeap;
    }


    public E poll() {
        if (heap.isEmpty()) {
            throw new NoSuchElementException();
        }
        E result = heap.get(0);
        heap.remove(0);
        return result;
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    public int size() {
        return heap.size();
    }

    @Override
    public Iterator<E> iterator() {
        return heap.iterator();
    }

    public void forEach(Consumer<? super E> action) {
        for (E element : heap) {
            action.accept(element);
        }
    }
}