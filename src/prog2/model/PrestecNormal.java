package prog2.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Classe que representa un préstec de tipus normal.
 * Hereta de la classe {@link Prestec} i defineix una durada fixa per al préstec,
 * a més de gestionar el comptador de préstecs normals de l'usuari.
 * * @author Ternero David, Tribo Miquel
 * @version 1.0
 */
public class PrestecNormal extends Prestec implements Serializable {

    /**
     * Constant que defineix la durada predeterminada del préstec normal en mil·lisegons.
     * En aquest cas, fixada en 70.000 ms (70 segons per a proves/simulació).
     */
    private static final long DURADA = 70_000L;

    /**
     * Constructor per crear un nou préstec normal.
     * Calcula automàticament la data límit de retorn sumant la constant {@code DURADA}
     * a la data de creació i marca l'exemplar com a no disponible.
     * * @param exemplar L'objecte {@link Exemplar} que es lliura en préstec.
     * @param usuari L'objecte {@link Usuari} que rep el préstec.
     * @param dataCreacio La data en què s'inicia el préstec.
     */
    public PrestecNormal(Exemplar exemplar, Usuari usuari, Date dataCreacio) {
        super(exemplar, usuari, dataCreacio);

        // Calculem i establim la data límit
        setDataLimitRetorn(new Date(dataCreacio.getTime() + DURADA));

        // Marquem l'exemplar com ocupat
        exemplar.setDisponible(false);
    }

    /**
     * Identifica el tipus de préstec actual.
     * * @return Una cadena de text amb el valor "Normal".
     */
    @Override
    public String tipusPrestec() {
        return "Normal";
    }

    /**
     * Gestiona la devolució de l'exemplar.
     * Estén el mètode {@code retorna()} de la superclasse per actualitzar
     * el comptador de préstecs normals actius de l'usuari (decrementant-lo en 1).
     */
    @Override
    public void retorna() {
        // Cridem al mètode de la superclasse per marcar com retornat i alliberar l'exemplar
        super.retorna();

        // Actualitzem les estadístiques de l'usuari
        Usuari u = getUsuari();
        u.setNumPrestecsNormals(u.getNumPrestecsNormals() - 1);
    }
}