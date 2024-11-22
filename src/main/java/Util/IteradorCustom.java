package Util;
import Model.Nodo;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class IteradorCustom<T> implements Iterator<T> {
    private Nodo<T> current;

    public IteradorCustom(Nodo<T> start) {
        this.current = start;
    }

    @Override
    public boolean hasNext() {
        return current != null;
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        T data = current.getData();
        current = current.getNext();
        return data;
    }
}

