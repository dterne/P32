package prog2.model;

import java.util.Date;

public interface InPrestec {
    void setExemplar(Exemplar exemplar);

    Exemplar getExemplar();

    void setUsuari(Usuari usuari);

    Usuari getUsuari();

    void setDataCreacio(Date data);

    Date getDataCreacio();

    void setDataLimitRetorn(Date data);

    Date getDataLimitRetorn();

    String tipusPrestec();

    void setRetornat(boolean retornat);

    boolean getRetornat();

    /**
     * Retornar prestec. Llança excepció si el prestec ja es vaig retornar
     */
    void retorna();

    /**
     * Retornar durada prestec. La durada del prestec depen del tipus de prestec
     */
    long duradaPrestec();

    /**
     * Retornar true si el prestec està endarrerit per a la data actual
     */
    boolean prestecEndarrerit();

    @Override
    String toString();
}