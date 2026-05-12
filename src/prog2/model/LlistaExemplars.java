package prog2.model;

import prog2.vista.BiblioException;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Classe que representa una llista d'exemplars amb funcionalitats específiques.
 * Aquesta classe estén la clase genèrica Llista i afegeix mètodes per a gestionar
 * exemplars amb validacions addicionals com la detecció de duplicats i la verificació
 * de nul·litat.
 *
 * @author Ternero David Tribo Miquel
 * @version 1.0
 * @see Llista
 * @see Exemplar
 */
public class LlistaExemplars extends Llista<Exemplar> implements Serializable {


    /**
     * Afegeix un exemplar a la llista després de comprovar que no sigui null
     * ni estigui duplicat per ID.
     *
     * @param exemplar l'exemplar a afegir a la llista
     * @throws BiblioException si l'exemplar és null o si ja existeix un exemplar
     *                         amb el mateix ID a la llista
     * @see #contains(String)
     */
    @Override
    public void afegir(Exemplar exemplar) throws BiblioException {
        // Mètode que afegeix un exemplar a la llista després de comprovar que ni sigui null o estigui duplicat
        if (exemplar == null) {
            throw new BiblioException("Exemplar null");
        }

        if (contains(exemplar.getId())) {
            throw new BiblioException("ID duplicat");
        }

        super.afegir(exemplar);
    }

    /**
     * Comprova si un exemplar amb un ID específic ja existeix a la llista.
     *
     * @param id l'identificador únic de l'exemplar a buscar
     * @return {@code true} si existeix un exemplar amb l'ID especificat,
     *         {@code false} en cas contrari
     */
    public boolean contains(String id) {
        // Mètode que comprova si un exemplar amb un ID específic ja existeix a la llista
        for (Exemplar e : llista) {
            if (e.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Esborra un exemplar de la llista comparant els identificadors dels exemplars.
     *
     * @param exemplar l'exemplar a esborrar de la llista
     */
    public void esborrar(Exemplar exemplar) {
        // Mètode que esborra un exemplar de la llista comparant els ID dels exemplars
        for (Exemplar e : llista) {
            if (e.getId().equals(exemplar.getId())) {
                llista.remove(e);
                return;
            }
        }
    }

    /**
     * Retorna l'ArrayList que conté tots els exemplars de la llista.
     *
     * @return un ArrayList amb els exemplars emmagatzemats
     * @see ArrayList
     */
    @Override
    public ArrayList<Exemplar> getArrayList() {
        // Mètode que retorna la llista d'exemplars
        return super.getArrayList();
    }
}