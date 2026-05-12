package prog2.model;

import prog2.adaptador.Adaptador;

import java.io.Serializable;
import java.util.Date;
import java.util.Scanner;

/**
 * Classe abstracta que representa un préstec de biblioteca.
 *
 * Un préstec és l'acte de fer servir temporalment un exemplar d'una biblioteca.
 * Aquesta classe emmagatzema informació sobre l'exemplar prestat, l'usuari que el pren,
 * les dates de creació i límit de retorn, i l'estat de retorn del préstec.
 *
 * @author Ternero, David, Tribo, Miquel
 * @version 1.0
 */
public abstract class Prestec implements InPrestec, Serializable {

    //Atributs necessaris per inicialitzar un objecte de tipus préstec
    private boolean retornat;
    private Exemplar exemplar;
    private Usuari usuari;
    private Date dataCreacio;
    private Date dataLimit;

    /**
     * Constructor per crear una nova instància de préstec.
     *
     * @param exemplar l'exemplar que es presa
     * @param usuari l'usuari que realitza el préstec
     * @param dataCreacio la data en que es crea el préstec
     */
    public Prestec(Exemplar exemplar, Usuari usuari, Date dataCreacio) {
        this.exemplar = exemplar;
        this.usuari = usuari;
        this.dataCreacio = dataCreacio;
        this.retornat = false;
    }

    //Getters i setters de cada atribut.

    /**
     * Estableix l'exemplar del préstec.
     *
     * @param exemplar l'exemplar a establir
     */
    public void setExemplar(Exemplar exemplar) {
        this.exemplar = exemplar;
    }

    /**
     * Obté l'exemplar del préstec.
     *
     * @return l'exemplar associat a aquest préstec
     */
    public Exemplar getExemplar() {
        return exemplar;
    }

    /**
     * Estableix l'usuari del préstec.
     *
     * @param usuari l'usuari a establir
     */
    public void setUsuari(Usuari usuari) {
        this.usuari = usuari;
    }

    /**
     * Obté l'usuari del préstec.
     *
     * @return l'usuari associat a aquest préstec
     */
    public Usuari getUsuari(){
        return usuari;
    }

    /**
     * Estableix la data de creació del préstec.
     *
     * @param data la data de creació
     */
    public void setDataCreacio(Date data) {
        this.dataCreacio = data;
    }

    /**
     * Obté la data de creació del préstec.
     *
     * @return la data en que es va crear el préstec
     */
    public Date getDataCreacio(){
        return dataCreacio;
    }

    /**
     * Estableix la data límit de retorn del préstec.
     *
     * @param data la data límit de retorn
     */
    public void setDataLimitRetorn(Date data){
        this.dataLimit = data;
    }

    /**
     * Obté la data límit de retorn del préstec.
     *
     * @return la data límit en que s'ha de retornar l'exemplar
     */
    public Date getDataLimitRetorn() {
        return dataLimit;
    }

    /**
     * Estableix l'estat de retorn del préstec.
     *
     * @param retornat <code>true</code> si el préstec ha estat retornat, <code>false</code> en cas contrari
     */
    public void setRetornat(boolean retornat) {
        this.retornat = retornat;
    }

    /**
     * Obté l'estat de retorn del préstec.
     *
     * @return <code>true</code> si el préstec ha estat retornat, <code>false</code> en cas contrari
     */
    public boolean getRetornat(){
        return retornat;
    }



    /**
     * Calcula la durada del préstec en mil·lisegons.
     *
     * La durada es calcula com la diferència entre la data límit de retorn
     * i la data de creació del préstec.
     *
     * @return la durada del préstec en mil·lisegons
     */
    public long duradaPrestec(){
        return dataLimit.getTime() - dataCreacio.getTime();
    }

    /**
     * Retorna l'exemplar del préstec, marcant-lo com a disponible.
     *
     * Si el préstec ja ha estat retornat anteriorment, aquest mètode no fa res.
     * En cas contrari, marca el préstec com retornat i posa l'exemplar com a disponible.
     */
    public void retorna() {
        // Mètode que retorna un préstec, després de comprovar que no s'hagi retornat abans.
        if (retornat) return;

        retornat = true;
        exemplar.setDisponible(true);
    }

    /**
     * Comprova si el préstec està endarrerit.
     *
     * Un préstec està endarrerit si la data actual és posterior a la data límit
     * de retorn i el préstec encara no ha estat retornat.
     *
     * @return <code>true</code> si el préstec està endarrerit, <code>false</code> en cas contrari
     */
    public boolean prestecEndarrerit() {
        if (retornat) return false;
        return new Date().after(dataLimit);
    }

    /**
     * Retorna una representació en forma de cadena de text del préstec.
     *
     * La representació inclou el tipus de préstec, el títol de l'exemplar
     * i el nom de l'usuari que ha fet el préstec.
     *
     * @return una cadena amb el format: "tipus - títol - nom_usuari"
     */
    @Override
    public String toString() {
        return tipusPrestec() + " - " +
                exemplar.getTitol() + " - " +
                usuari.getNom();
    }

}