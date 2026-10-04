package sportzone.modele;

import sportzone.exceptions.SceanceCompleteException;

/**
 * Comportement d'un cours dont les places se réservent séance par séance.
 */
public interface Reservable {

    /**
     * Réserve une place pour l'adhérent donné.
     *
     * @param adherent adhérent qui réserve, non null
     * @throws IllegalArgumentException  si adherent est null
     * @throws SceanceCompleteException si le cours a déjà atteint sa
     *                                  capacité maximale
     */
    void reserverPlace(Adherent adherent) throws SceanceCompleteException;

    /**
     * Indique le nombre de places encore disponibles.
     *
     * @return le nombre de places restantes, jamais négatif
     */
    int placesRestantes();
}