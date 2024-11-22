package Util;


import Model.Nodo;
import java.util.*;

public class ListaEnlazada<T> implements Iterable<T> {
    private Nodo<T> cabeza;
    private Nodo<T> cola;
    private int size;

    public void add(T data) {
        Nodo<T> nuevoNodo = new Nodo<>(data);
        if (cabeza == null) {
            cabeza = nuevoNodo;
            cola = nuevoNodo;
        } else {
            cola.setNext(nuevoNodo);
            cola = nuevoNodo;
        }
        size++;
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Nodo<T> actual = cabeza;
        for (int i = 0; i < index; i++) {
            actual = actual.getNext();
        }
        return actual.getData();
    }

    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) {
            cabeza = cabeza.getNext();
            if (cabeza == null) {
                cola = null;
            }
        } else {
            Nodo<T> actual = cabeza;
            for (int i = 0; i < index - 1; i++) {
                actual = actual.getNext();
            }
            actual.setNext(actual.getNext().getNext());
            if (index == size - 1) {
                cola = actual;
            }
        }
        size--;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        cabeza = null;
        cola = null;
        size = 0;
    }

    public boolean contains(T data) {
        Nodo<T> actual = cabeza;
        while (actual != null) {
            if (actual.getData().equals(data)) {
                return true;
            }
            actual = actual.getNext();
        }
        return false;
    }

    public void printList() {
        Nodo<T> actual = cabeza;
        while (actual != null) {
            System.out.println(actual.getData());
            actual = actual.getNext();
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new IteradorCustom<>(cabeza);
    }
}

