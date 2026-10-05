package sportzone.modele;

import sportzone.exceptions.SceanceCompleteException;

/**
 * Comportement d'un cours dont les places se réservent séance par séance.
 * <p>
 * N'est implémentée que par {@link CoursCollectif} et {@link CoursIndividuel}.
 * {@link StageIntensif} ne l'implémente volontairement pas : un stage
 * intensif se réserve en bloc pour toute sa durée, pas séance par séance —
 * ce qui illustre pourquoi une interface, contrairement à l'héritage,
 * ne s'applique qu'aux classes pour lesquelles le comportement a vraiment
 * un sens.
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
