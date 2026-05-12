package prog2.model;

import prog2.vista.BiblioException;

/**
 * Classe que representa una llista d'usuaris.
 * Estén la classe genèrica Llista amb tipus d'element Usuari.
 * Proporciona mètodes per afegir usuaris i buscar-los per email.
 *
 * @author Ternero, David, Tribo, Miquel
 * @version 1.0
 */
public class LlistaUsuaris extends Llista<Usuari> {

    /**
     * Afegeix un usuari a la llista.
     * Verifica que no existeixi un altre usuari amb el mateix email.
     *
     * @param usuari L'usuari que es desitja afegir a la llista
     * @throws BiblioException Si ja existeix un usuari amb el mateix email
     */
    @Override
    public void afegir(Usuari usuari) throws BiblioException {
        if (contains(usuari.getEmail())) {
            throw new BiblioException("Ja existeix un usuari amb aquest email");
        }
        super.afegir(usuari);
    }

    /**
     * Busca si existeix un usuari amb l'email especificat a la llista.
     *
     * @param email L'email a buscar
     * @return true si existeix un usuari amb l'email especificat, false en cas contrari
     */
    public boolean contains(String email) {
        for (Usuari u : llista) {
            if (u.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }
}
