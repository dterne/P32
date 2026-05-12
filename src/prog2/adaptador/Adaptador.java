package prog2.adaptador;

import prog2.model.Dades;
import prog2.vista.BiblioException;

import java.io.*;

/**
 * Classe que actua com a prog2.adaptador per gestionar la persistència de l'aplicació.
 * Permet desar i carregar l'estat complet del prog2.model de dades en un fitxer binari
 * mitjançant la serialització d'objectes.
 * * @author Ternero David, Tribo Miquel
 * @version 1.0
 */
public class Adaptador implements Serializable {

    /**
     * Instància del prog2.model de dades que conté tota la informació de la biblioteca.
     */
    private Dades dades;

    /**
     * Constructor per defecte. Inicialitza un nou objecte {@link Dades} buit.
     */
    public Adaptador() {
        this.dades = new Dades();
    }

    /**
     * Guarda l'estat actual de les dades en un fitxer extern.
     * Utilitza la classe {@link ObjectOutputStream} per escriure l'objecte {@code dades}
     * de forma binària en el camí especificat.
     * * @param fitxer Camí o nom del fitxer on es volen desar les dades.
     * @throws BiblioException Si es produeix qualsevol error d'entrada/sortida durant l'escriptura.
     */
    public void guardaDades(String fitxer) throws BiblioException {
        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream(fitxer))) {

            out.writeObject(dades);

        } catch (IOException e) {
            throw new BiblioException("Error guardant dades: " + e.getMessage());
        }
    }

    /**
     * Carrega les dades de l'aplicació des d'un fitxer existent.
     * Utilitza {@link ObjectInputStream} per llegir i deserialitzar l'objecte {@link Dades}.
     * Després de la càrrega, l'atribut {@code dades} d'aquesta classe s'actualitza amb la nova informació.
     * * @param fitxer Camí o nom del fitxer des d'on es volen recuperar les dades.
     * @throws BiblioException Si el fitxer no existeix, el format és incorrecte o hi ha un error de lectura.
     */
    public void carregaDades(String fitxer) throws BiblioException {
        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream(fitxer))) {

            dades = (Dades) in.readObject();

        } catch (IOException | ClassNotFoundException e) {
            throw new BiblioException("Error carregant dades: " + e.getMessage());
        }
    }

    /**
     * Obté l'objecte que conté totes les dades gestionades per l'prog2.adaptador.
     * * @return L'objecte {@link Dades} actual.
     */
    public Dades getDades() {
        return dades;
    }
}