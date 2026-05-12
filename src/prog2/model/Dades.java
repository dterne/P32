package prog2.model;

import prog2.vista.BiblioException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Classe Dades que gestiona tota la informació de la biblioteca.
 * Implementa la interfície InDades i és serializable per a persistència de dades.
 *
 * Aquesta classe manté tres llistes principals:
 * - Exemplars: els llibres de la biblioteca
 * - Usuaris: els estudiants i professors que utilitzen la biblioteca
 * - Préstecs: els registres de préstecs dels exemplars
 *
 * @author Ternero, David, Tribo, Miquel
 * @version 1.0
 */
public class Dades implements InDades, Serializable {

    private final LlistaExemplars exemplars;
    private final LlistaUsuaris usuaris;
    private final LlistaPrestecs prestecs;

    /**
     * Constructor de la classe Dades.
     * Inicialitza les tres llistes principals (exemplars, usuaris i préstecs) buides.
     */
    public Dades() {
        this.exemplars = new LlistaExemplars();
        this.usuaris = new LlistaUsuaris();
        this.prestecs = new LlistaPrestecs();
    }



    /**
     * Afegeix un nou exemplar a la biblioteca.
     *
     * Comprovacions:
     * - L'exemplar no pot estar duplicat (id únic)
     *
     * @param id identificador únic de l'exemplar
     * @param titol títol del llibre
     * @param autor autor del llibre
     * @param admetPrestecLlarg indica si l'exemplar permet préstecs llargs
     *
     * @throws BiblioException si l'exemplar ja existeix (duplicat)
     */
    public void afegirExemplar(String id, String titol, String autor, boolean admetPrestecLlarg)
            throws BiblioException {

        if (exemplars.contains(id)) {
            throw new BiblioException("Exemplar duplicat");
        }

        exemplars.afegir(new Exemplar(id, titol, autor, admetPrestecLlarg));
    }

    /**
     * Recupera la llista completa d'exemplars de la biblioteca.
     *
     * @return ArrayList amb tots els exemplars registrats
     */
    public ArrayList<Exemplar> recuperaExemplars() {
        return exemplars.getArrayList();
    }



    /**
     * Afegeix un nou usuari a la biblioteca.
     *
     * Comprovacions:
     * - L'usuari no pot estar duplicat (email únic)
     *
     * Segons el paràmetre esEstudiant, es crea:
     * - Un objecte de tipus Estudiant si esEstudiant és cert
     * - Un objecte de tipus Professor si esEstudiant és fals
     *
     * @param email correu electrònic únic de l'usuari
     * @param nom nom complet de l'usuari
     * @param adreca adreça de l'usuari
     * @param esEstudiant true si és estudiant, false si és professor
     *
     * @throws BiblioException si l'usuari ja existeix (email duplicat)
     */
    public void afegirUsuari(String email, String nom, String adreca, boolean esEstudiant)
            throws BiblioException {

        if (usuaris.contains(email)) {
            throw new BiblioException("Usuari duplicat");
        }

        Usuari u;

        if (esEstudiant) {
            u = new Estudiant(email, nom, adreca);
        } else {
            u = new Professor(email, nom, adreca);
        }

        usuaris.afegir(u);
    }

    /**
     * Recupera la llista completa d'usuaris de la biblioteca.
     *
     * @return ArrayList amb tots els usuaris registrats (estudiants i professors)
     */
    public ArrayList<Usuari> recuperaUsuaris() {
        return usuaris.getArrayList();
    }



    /**
     * Crea un nou préstec per a un exemplar a un usuari.
     *
     * Comprovacions que es realitzen:
     * 1. L'exemplar ha d'estar disponible
     * 2. L'usuari no pot tenir préstecs endarrerits
     * 3. No ha de superar el límit de préstecs normals (si és préstec normal)
     * 4. No ha de superar el límit de préstecs llargs (si és préstec llarg)
     * 5. L'exemplar ha d'admetre préstec llarg (si és préstec llarg)
     *
     * Segons el tipus de préstec, es crea:
     * - Un objecte de tipus PrestecLlarg si esLlarg és cert
     * - Un objecte de tipus PrestecNormal si esLlarg és fals
     *
     * Després de crear el préstec:
     * - S'incrementa el contador corresponent de l'usuari
     * - L'exemplar es marca com no disponible
     *
     * @param exemplarPos posició de l'exemplar en la llista
     * @param usuariPos posició de l'usuari en la llista
     * @param esLlarg true per a préstec llarg, false per a préstec normal
     *
     * @throws BiblioException si l'exemplar no està disponible, l'usuari té préstecs
     *         endarrerits, es supera algun límit, o l'exemplar no permet préstec llarg
     */
    public void afegirPrestec(int exemplarPos, int usuariPos, boolean esLlarg)
            throws BiblioException {

        Exemplar e = exemplars.getAt(exemplarPos);
        Usuari u = usuaris.getAt(usuariPos);

        // 1. disponible
        if (!e.isDisponible()) {
            throw new BiblioException("Exemplar no disponible");
        }

        // 2. préstecs endarrerits
        for (Prestec p : prestecs.getArrayList()) {
            if (p.getUsuari().equals(u) && p.prestecEndarrerit()) {
                throw new BiblioException("Usuari amb préstecs endarrerits");
            }
        }

        // 3. límits normals
        if (!esLlarg &&
                u.getNumPrestecsNormals() >= u.getMaxPrestecsNormals()) {
            throw new BiblioException("Límit normals superat");
        }

        // 4. límits llargs
        if (esLlarg &&
                u.getNumPrestecsLlargs() >= u.getMaxPrestecsLlargs()) {
            throw new BiblioException("Límit llargs superat");
        }

        // 5. tipus préstec
        Prestec p;
        if (esLlarg) {
            if (!e.getAdmetPrestecLlarg()) {
                throw new BiblioException("No admet préstec llarg");
            }
            p = new PrestecLlarg(e, u, new Date());
            u.setNumPrestecsLlargs(u.getNumPrestecsLlargs()+1);
        } else {
            p = new PrestecNormal(e, u, new Date());
            u.setNumPrestecsNormals(u.getNumPrestecsNormals()+1);
        }

        prestecs.afegir(p);
        e.setDisponible(false);
    }



    /**
     * Retorna un préstec marcat-lo com retornat.
     *
     * Comprovacions:
     * - El préstec no pot estar ja retornat
     *
     * @param pos posició del préstec en la llista
     *
     * @throws BiblioException si el préstec ja havia estat retornat
     */
    public void retornarPrestec(int pos) throws BiblioException {
        Prestec p = prestecs.getAt(pos);

        if (p.getRetornat()) {
            throw new BiblioException("Ja retornat");
        }

        p.retorna();
    }



    /**
     * Recupera la llista completa de préstecs de la biblioteca.
     *
     * @return ArrayList amb tots els préstecs registrats (retornats i no retornats)
     */
    public ArrayList<Prestec> recuperaPrestecs() {
        return prestecs.getArrayList();
    }

    /**
     * Recupera la llista de préstecs no retornats.
     *
     * Filtra la llista de préstecs per a retornar només aquells que no han estat retornats.
     *
     * @return ArrayList amb els préstecs actius (no retornats)
     */
    public ArrayList<Prestec> recuperaPrestecsNoRetornats() {
        ArrayList<Prestec> res = new ArrayList<>();

        for (Prestec p : prestecs.getArrayList()) {
            if (!p.getRetornat()) {
                res.add(p);
            }
        }

        return res;
    }
}
