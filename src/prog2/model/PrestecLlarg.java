package prog2.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Classe que representa un préstec de llarga durada.
 * Hereta de la classe {@link Prestec} i implementa una lògica específica per a
 * préstecs amb un termini de retorn estès i el seu control de quota d'usuari.
 * * @author Ternero, David, Tribo, Miquel
 * @version 1.0
 */
public class PrestecLlarg extends Prestec implements Serializable {

    /**
     * Constant que defineix la durada predeterminada del préstec llarg en mil·lisegons.
     * Fixada en 140.000 ms (el doble que un préstec normal).
     */
    private static final long DURADA = 140_000L;

    /**
     * Constructor per crear un nou préstec de llarga durada.
     * Inicialitza el préstec calculant la data límit a partir de la constant {@code DURADA}
     * i marca l'exemplar associat com a no disponible.
     * * @param exemplar L'objecte {@link Exemplar} que es deixa en préstec.
     * @param usuari L'objecte {@link Usuari} que realitza la petició.
     * @param dataCreacio La data d'inici del préstec.
     */
    public PrestecLlarg(Exemplar exemplar, Usuari usuari, Date dataCreacio) {
        super(exemplar, usuari, dataCreacio);

        // Calculem la data límit sumant la durada específica a la data de creació
        setDataLimitRetorn(new Date(dataCreacio.getTime() + DURADA));

        // Actualitzem l'estat de l'exemplar
        exemplar.setDisponible(false);
    }

    /**
     * Identifica el tipus de préstec.
     * * @return Una cadena de text amb el valor "Llarg".
     */
    @Override
    public String tipusPrestec() {
        return "Llarg";
    }

    /**
     * Gestiona la devolució de l'exemplar per a préstecs llargs.
     * Crida al mètode de la superclasse per alliberar l'exemplar i, addicionalment,
     * decrementa el comptador de préstecs llargs actius de l'usuari.
     */
    @Override
    public void retorna() {
        // Executem la lògica general de devolució (marcar com retornat i disponible)
        super.retorna();

        // Decrementem el comptador específic de préstecs llargs de l'usuari
        Usuari u = getUsuari();
        u.setNumPrestecsLlargs(u.getNumPrestecsLlargs() - 1);
    }
}
