package prog2.model;

import java.io.Serializable;

/**
 * Classe abstracta que representa un usuari del sistema.
 * Implementa la interfície {@link InUsuari} i és serialitzable.
 * Aquesta classe no pot ser instanciada directament; ha de ser estesa
 * per les seves subclasses com ara Estudiant o Professor.
 *
 * @author
 * @version 1.0
 */
public abstract class Usuari implements InUsuari, Serializable {

    // Atributs necessaris que guarden la informació de l'usuari.
    private String email;
    private String nom;
    private String adreca;
    private int numPrestecsNormals;
    private int numPrestecsLlargs;

    /**
     * Constructor que inicialitza tots els atributs de l'usuari.
     * Els atributs de préstec es inicialitzen a 0.
     *
     * @param email el correu electrònic de l'usuari
     * @param nom el nom complet de l'usuari
     * @param adreca l'adreça de residència de l'usuari
     */
    public Usuari(String email, String nom, String adreca){
        this.email = email;
        this.nom = nom;
        this.adreca = adreca;
        this.numPrestecsNormals =  0;
        this.numPrestecsLlargs = 0;
    }

    /**
     * Modifica el correu electrònic de l'usuari.
     *
     * @param email el nou correu electrònic
     */
    public void setEmail(String email) {
        this.email = email;
    }


    /**
     * Obté el correu electrònic de l'usuari.
     *
     * @return el correu electrònic de l'usuari
     */
    public String getEmail() {
        return email;
    }


    /**
     * Modifica el nom de l'usuari.
     *
     * @param nom el nou nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }


    /**
     * Obté el nom de l'usuari.
     *
     * @return el nom de l'usuari
     */
    public String getNom(){
        return nom;
    }

    /**
     * Modifica l'adreça de l'usuari.
     *
     * @param adreca la nova adreça
     */
    public void setAdreca(String adreca) {
        this.adreca = adreca;
    }

    /**
     * Obté l'adreça de l'usuari.
     *
     * @return l'adreça de l'usuari
     */
    public String getAdreca() {
        return adreca;
    }

    /**
     * Retorna el tipus de client/usuari.
     * Aquest mètode és definit a la classe abstracta per a ser
     * sobreescrit en les subclasses.
     *
     * @return una cadena que represente el tipus d'usuari
     */
    public String tipusClient() {
        return " ";
    }

    /**
     * Modifica el nombre de préstecs normals de l'usuari.
     *
     * @param numPrestecsNormals el nou nombre de préstecs normals
     */
    public void setNumPrestecsNormals(int numPrestecsNormals) {
        this.numPrestecsNormals = numPrestecsNormals;
    }


    /**
     * Obté el nombre actual de préstecs normals de l'usuari.
     *
     * @return el nombre de préstecs normals
     */
    public int getNumPrestecsNormals() {
        return numPrestecsNormals;
    }


    /**
     * Modifica el nombre de préstecs llargs de l'usuari.
     *
     * @param numPrestecsLlargs el nou nombre de préstecs llargs
     */
    public void setNumPrestecsLlargs(int numPrestecsLlargs) {
        this.numPrestecsLlargs = numPrestecsLlargs;
    }

    /**
     * Obté el nombre actual de préstecs llargs de l'usuari.
     *
     * @return el nombre de préstecs llargs
     */
    public int getNumPrestecsLlargs(){
        return numPrestecsLlargs;
    }

    /**
     * Obté el nombre màxim de préstecs normals permesos.
     * Aquest mètode retorna 0 a la classe abstracta i ha de ser
     * sobreescrit en les subclasses.
     *
     * @return el nombre màxim de préstecs normals
     */
    public int getMaxPrestecsNormals(){
        return 0;
    }

    /**
     * Obté el nombre màxim de préstecs llargs permesos.
     * Aquest mètode retorna 0 a la classe abstracta i ha de ser
     * sobreescrit en les subclasses.
     *
     * @return el nombre màxim de préstecs llargs
     */
    public int getMaxPrestecsLlargs(){
        return 0;
    }


    /**
     * Retorna una representació en cadena de text de la informació de l'usuari.
     * Inclou el correu electrònic, nom, adreça i nombres de préstecs normals i llargs.
     *
     * @return una cadena que representa la informació de l'usuari
     */
    @Override
    public String toString() {
        return "Email=" + email +
                ", Nom=" + nom +
                ", Adreça=" + adreca + "Num préstecs normals=" + numPrestecsNormals + "Num préstecs llargs=" + numPrestecsLlargs;
    }
}