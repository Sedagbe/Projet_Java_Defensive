package sportzone.modele;

public interface Reservable {

    /**
     * Réserve une place pour l'adhérent donné.
     *
     * @param adherent adhérent qui réserve, non null
     * @throws IllegalArgumentException si adherent est null
     * @throws IllegalStateException    si le cours est déjà complet (cette
     *                                  exception générique sera remplacée par
     *                                  {SceanceCompleteException} en
     *                                  Phase 3)
     */
    void reserverPlace(Adherent adherent);

    /**
     * Indique le nombre de places encore disponibles.
     *
     * @return le nombre de places restantes, jamais négatif
     */
    int placesRestantes();
}