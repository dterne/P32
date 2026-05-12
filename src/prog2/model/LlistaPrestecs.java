package prog2.model;

import java.io.Serializable;

/**
 * Classe que representa una col·lecció específica per a la gestió de préstecs.
 * Hereta de la classe genèrica {@link Llista} i s'especialitza en objectes de tipus {@link Prestec}.
 * Aquesta estructura permet emmagatzemar i gestionar tot l'historial de préstecs de la biblioteca.
 * * @author Ternero David, Tribo Miquel
 * @version 1.0
 */
public class LlistaPrestecs extends Llista<Prestec> implements Serializable {

    /**
     * Constructor de la llista de préstecs.
     * Invoca el constructor de la superclasse {@link Llista} per inicialitzar
     * l'estructura de dades interna.
     */
    public LlistaPrestecs() {
        super();
    }

}