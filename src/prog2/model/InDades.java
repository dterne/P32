/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prog2.model;

import java.util.ArrayList;

import prog2.vista.BiblioException;


public interface InDades {

    /**
     * Afegeix exemplar. Llança excepció si l'id ja existeix
     */
    public void afegirExemplar(String id, String titol, String autor, boolean admetPrestecLlarg) throws BiblioException;

    public ArrayList<Exemplar> recuperaExemplars();

    /**
     * Afegeix usuari. Llança excepció si l'email ja existeix
     */
    public void afegirUsuari(String email, String nom, String adreca, boolean esEstudiant) throws BiblioException;

    public ArrayList<Usuari> recuperaUsuaris();

    /**
     * Afegeix préstec. Ha de fer diferents comprovacions que poden llançar excepcions.
     * Quan s'afegeix el préstec, s'han de tenir en compte les posicions d'exemplar
     * i usuari dins dels seus ArrayLists
     */
    public void afegirPrestec(int exemplarPos, int usuariPos, boolean esLlarg) throws BiblioException;

    /**
     * Retornar préstec. Llança excepció si el prestec ja es vaig retornar.
     * El préstec s'identifica amb la seva posició dins de l'ArrayList
     */
    public void retornarPrestec(int position) throws BiblioException;

    public ArrayList<Prestec> recuperaPrestecs();

    public ArrayList<Prestec> recuperaPrestecsNoRetornats();
}