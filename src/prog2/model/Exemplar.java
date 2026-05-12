package prog2.model;

import java.io.Serializable;

/**
 * Classe que representa un exemplar d'una obra de biblioteca.
 *
 * Un exemplar conté informació sobre l'identificador únic, el títol, l'autor
 * i les condicions de préstec (si admet préstec llarg). Cada exemplar pot estar
 * disponible o no per a ser prestat.
 *
 * @author Ternero, David Tribo Miquel
 * @version 1.0
 */
public class Exemplar implements InExemplar, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identificador únic de l'exemplar.
     */
    private String id;

    /**
     * Títol de l'obra.
     */
    private String titol;

    /**
     * Autor de l'obra.
     */
    private String autor;

    /**
     * Indica si l'exemplar admet préstec llarg.
     */
    private boolean admetPrestecLlarg;

    /**
     * Indica la disponibilitat de l'exemplar. <code>true</code> si està disponible,
     * <code>false</code> en cas contrari.
     */
    private boolean disponible;

    /**
     * Constructor de la classe Exemplar.
     *
     * Inicialitza un nou exemplar amb l'identificador, títol, autor i tipus de préstec
     * especificats. Per defecte, es considera que l'exemplar està disponible.
     *
     * @param id                    l'identificador únic de l'exemplar
     * @param titol                 el títol de l'obra
     * @param autor                 l'autor de l'obra
     * @param admetPrestecLlarg     <code>true</code> si admet préstec llarg,
     *                              <code>false</code> en cas contrari
     */
    public Exemplar(String id, String titol, String autor, boolean admetPrestecLlarg) {
        this.id = id;
        this.titol = titol;
        this.autor = autor;
        this.admetPrestecLlarg = admetPrestecLlarg;
        this.disponible = true;
    }

    // Setter i getters atributs de Exemplar

    /**
     * Estableix l'identificador de l'exemplar.
     *
     * @param id l'identificador únic de l'exemplar
     */
    public void setId(String id){
        this.id = id;
    }

    /**
     * Retorna l'identificador de l'exemplar.
     *
     * @return l'identificador únic de l'exemplar
     */
    public String getId(){
        return id;
    }

    /**
     * Estableix el títol de l'obra.
     *
     * @param titol el títol de l'obra
     */
    public void setTitol(String titol){
        this.titol = titol;
    }

    /**
     * Retorna el títol de l'obra.
     *
     * @return el títol de l'obra
     */
    public String getTitol(){
        return titol;
    }

    /**
     * Estableix l'autor de l'obra.
     *
     * @param autor l'autor de l'obra
     */
    public void setAutor(String autor){
        this.autor = autor;
    }

    /**
     * Retorna l'autor de l'obra.
     *
     * @return l'autor de l'obra
     */
    public String getAutor(){
        return autor;
    }

    /**
     * Estableix si l'exemplar admet préstec llarg.
     *
     * @param admetPrestecLlarg <code>true</code> si admet préstec llarg,
     *                          <code>false</code> en cas contrari
     */
    public void setAdmetPrestecLlarg(boolean admetPrestecLlarg){
        this.admetPrestecLlarg = admetPrestecLlarg;
    }

    /**
     * Retorna si l'exemplar admet préstec llarg.
     *
     * @return <code>true</code> si l'exemplar admet préstec llarg,
     *         <code>false</code> en cas contrari
     */
    public boolean getAdmetPrestecLlarg(){
        return admetPrestecLlarg;
    }

    /**
     * Estableix la disponibilitat de l'exemplar.
     *
     * @param disponible <code>true</code> si l'exemplar està disponible,
     *                   <code>false</code> en cas contrari
     */
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    /**
     * Indica si l'exemplar està disponible per a ser prestat.
     *
     * @return <code>true</code> si l'exemplar està disponible,
     *         <code>false</code> en cas contrari
     */
    public boolean isDisponible() {
        // Mètode que retorna si l'exemplar està disponible o no.
        return disponible;
    }

    /**
     * Retorna una representació en cadena de caràcters de l'exemplar.
     *
     * Inclou tots els atributs de l'exemplar: identificador, títol, autor,
     * si admet préstec llarg i disponibilitat.
     *
     * @return una cadena de caràcters amb la informació de l'exemplar
     */
    @Override
    public String toString() {
        // toString que retorna els atributs en String
        return "Id=" + id +
                ", Títol=" + titol +
                ", Autor=" + autor +
                ", Admet Préstec Llarg=" + admetPrestecLlarg +
                ", Disponible=" + disponible;
    }
}
