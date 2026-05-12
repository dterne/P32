package prog2.model;

import java.io.Serializable;
import java.util.ArrayList;
import prog2.vista.BiblioException;

/**
 * Classe genèrica que actua com a contenidor per a una llista d'elements.
 * Proporciona mètodes bàsics per a la gestió d'una col·lecció d'objectes,
 * assegurant el control d'errors en les operacions d'inserció i accés.
 * * @param <T> El tipus d'elements que emmagatzemarà aquesta llista.
 * @author Ternero David, Tribo Miquel
 * @version 1.0
 */
public class Llista<T> implements Serializable {

    /**
     * Estructura de dades interna per emmagatzemar els elements.
     */
    protected ArrayList<T> llista;

    /**
     * Constructor per defecte que inicialitza una llista buida.
     */
    public Llista() {
        llista = new ArrayList<>();
    }

    /**
     * Obté el nombre total d'elements emmagatzemats a la llista.
     * * @return El recompte d'elements actuals.
     */
    public int getSize() {
        return llista.size();
    }

    /**
     * Afegeix un nou element a la llista.
     * * @param t L'element de tipus {@code T} que es vol afegir.
     * @throws BiblioException Si s'intenta afegir un element que és {@code null}.
     */
    public void afegir(T t) throws BiblioException {
        if (t == null) {
            throw new BiblioException("No es pot afegir un element null");
        }
        llista.add(t);
    }

    /**
     * Recupera l'element situat en una posició específica.
     * * @param position L'índex de l'element a recuperar (basat en zero).
     * @return L'element de tipus {@code T} en la posició indicada.
     * @throws IndexOutOfBoundsException Si la posició està fora dels límits de la llista.
     */
    public T getAt(int position) {
        if (position < 0 || position >= llista.size()) {
            throw new IndexOutOfBoundsException("Posició fora de rang");
        }
        return llista.get(position);
    }

    /**
     * Elimina tots els elements de la llista, deixant-la buida.
     */
    public void clear() {
        llista.clear();
    }

    /**
     * Comprova si la llista conté elements o no.
     * * @return {@code true} si la llista està buida, {@code false} en cas contrari.
     */
    public boolean isEmpty() {
        return llista.isEmpty();
    }

    /**
     * Retorna una còpia de la llista interna en format {@link ArrayList}.
     * Es retorna una còpia nova per evitar modificacions externes de l'estructura original.
     * * @return Un nou objecte {@link ArrayList} amb els mateixos elements que la llista.
     */
    public ArrayList<T> getArrayList() {
        return new ArrayList<>(llista);
    }
}