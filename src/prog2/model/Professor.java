package prog2.model;

import java.io.Serializable;

/**
 * Classe que representa un professor dins del sistema de gestió de préstecs.
 * Hereta de la classe {@link Usuari} i implementa la interfície {@link Serializable}.
 *
 * Un professor té límits específics de préstecs normals i llargs.
 *
 * @author Ternero, David, Tribo, Miquel
 * @version 1.0
 */
public class Professor extends Usuari implements Serializable {

    /**
     * Constructor que crea una nova instància de Professor.
     * Hereta els atributs de la superclasse Usuari (email, nom, adreça).
     *
     * @param email l'adreça de correu electrònic del professor
     * @param nom el nom del professor
     * @param adreca l'adreça del professor
     */
    public Professor(String email, String nom, String adreca) {
        super(email, nom, adreca);
    }

    /**
     * Retorna el nombre màxim de préstecs normals que pot tenir un professor.
     *
     * @return el nombre màxim de préstecs normals (2)
     */
    @Override
    public int getMaxPrestecsNormals() {
        return 2;
    }

    /**
     * Retorna el nombre màxim de préstecs llargs que pot tenir un professor.
     *
     * @return el nombre màxim de préstecs llargs (2)
     */
    @Override
    public int getMaxPrestecsLlargs() {
        return 2;
    }

    /**
     * Retorna una representació en cadena de text de l'objecte Professor.
     *
     * @return una cadena que contén el tipus d'usuari (Professor) i la informació de la superclasse
     */
    @Override
    public String toString() {
        return "Tipus =" + "Professor" +  super.toString();
    }

    /**
     * Retorna el tipus de client al qual pertany aquest usuari.
     *
     * @return la cadena "Professor"
     */
    @Override
    public String tipusClient() {
        return "Professor";
    }

}


