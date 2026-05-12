package prog2.model;

import java.io.Serializable;

/**
 * Classe que representa un Estudiant al sistema de gestió de préstecs de la biblioteca.
 * Estén la classe {@link Usuari} i implementa {@link Serializable}.
 *
 * Un estudiant té drets específics de préstec:
 * - Pot tenir un màxim de 2 préstecs normals.
 * - Pot tenir un màxim de 1 préstec llarg.
 *
 * @author Ternero David, Tribo Miquel
 * @version 1.0
 */
public class Estudiant extends Usuari implements Serializable {

    /**
     * Constructor de la classe Estudiant.
     *
     * @param email L'adreça de correu electrònic de l'estudiant.
     * @param nom El nom complet de l'estudiant.
     * @param adreca L'adreça postal de l'estudiant.
     */
    public Estudiant(String email, String nom, String adreca) {
        super(email, nom, adreca);
    }

    /**
     * Obté el nombre màxim de préstecs normals que pot tenir un estudiant.
     *
     * @return El nombre màxim de préstecs normals: 2
     */
    @Override
    public int getMaxPrestecsNormals() {
        return 2;
    }

    /**
     * Obté el nombre màxim de préstecs llargs que pot tenir un estudiant.
     *
     * @return El nombre màxim de préstecs llargs: 1
     */
    @Override
    public int getMaxPrestecsLlargs() {
        return 1;
    }

    /**
     * Retorna el tipus de client que representa aquesta classe.
     *
     * @return Una cadena de text identificant el tipus de client com a "Estudiant".
     */
    @Override
    public String tipusClient() {
        return "Estudiant";
    }

    /**
     * Retorna una representació en forma de cadena de text de l'estudiant.
     *
     * @return Una cadena de text que inclou el tipus de client ("Estudiant")
     *         i tots els atributs heretats de la classe {@link Usuari}.
     */
    @Override
    public String toString() {
        return "Tipus =" + "Estudiant" +  super.toString();
    }

}