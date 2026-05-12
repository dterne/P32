package prog2.model;

import prog2.vista.BiblioException;

import java.util.ArrayList;

public interface InLlista<T> {
    int getSize();

    void afegir(T t) throws BiblioException;

    void esborrar(T t);

    T getAt(int position);

    void clear();

    boolean isEmpty();

    ArrayList<T> getArrayList();
}