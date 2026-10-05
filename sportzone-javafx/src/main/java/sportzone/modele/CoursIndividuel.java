package sportzone.modele;

import sportzone.exceptions.SceanceCompleteException;

/**
 * Cours individuel : coaching en tête-à-tête, une seule place par séance,
 * facturé plus cher qu'un cours collectif.
 * <p>
 * Contrat de substitution : comme {@link CoursCollectif}, {@code CoursIndividuel}
 * respecte le contrat de {@link Cours} sans le restreindre.
 */
public class CoursIndividuel extends Cours implements Reservable {

    private double tarifHoraire;
    private Adherent adherentReserve;

    /**
     * Crée un cours individuel. La capacité est fixée à 1 automatiquement :
     * un cours individuel n'accueille qu'un seul adhérent par séance.
     *
     * @param intitule     intitulé du cours, non vide
     * @param dureeMinutes durée en minutes, strictement positive
     * @param tarifHoraire tarif horaire, strictement positif
     * @throws IllegalArgumentException si l'un des paramètres viole son
     *                                  invariant
     */
    public CoursIndividuel(String intitule, int dureeMinutes, double tarifHoraire) {
        super(intitule, dureeMinutes, 1);
        if (tarifHoraire <= 0) {
            throw new IllegalArgumentException("Le tarif horaire doit être strictement positif.");
        }
        this.tarifHoraire = tarifHoraire;
    }

    public double getTarifHoraire() {
        return tarifHoraire;
    }

    public void setTarifHoraire(double tarifHoraire) {
        if (tarifHoraire <= 0) {
            throw new IllegalArgumentException("Le tarif horaire doit être strictement positif.");
        }
        this.tarifHoraire = tarifHoraire;
    }

    /** {@inheritDoc} Ici : tarif horaire proratisé à la durée réelle de la séance. */
    @Override
    public double calculerTarifSceance() {
        return tarifHoraire * (getDureeMinutes() / 60.0);
    }

    @Override
    public void reserverPlace(Adherent adherent) throws SceanceCompleteException {
        if (adherent == null) {
            throw new IllegalArgumentException("L'adhérent ne peut pas être null.");
        }
        if (adherentReserve != null) {
            throw new SceanceCompleteException(
                    "Le cours individuel '" + getIntitule() + "' est déjà réservé.");
        }
        this.adherentReserve = adherent;
    }

    @Override
    public int placesRestantes() {
        return adherentReserve == null ? 1 : 0;
    }
}
