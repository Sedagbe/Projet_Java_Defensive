package sportzone.modele;

/**
 * Cours individuel : coaching en tête-à-tête, une seule place par séance,
 * facturé plus cher qu'un cours collectif.
 */
public class CoursIndividuel extends Cours implements Reservable {

    private double tarifHoraire;
    private Adherent adherentReserve;

    /**
     * Constructeur
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

    @Override
    public double calculerTarifSceance() {
        return tarifHoraire * (getDureeMinutes() / 60.0);
    }

    @Override
    public void reserverPlace(Adherent adherent) {
        if (adherent == null) {
            throw new IllegalArgumentException("L'adhérent ne peut pas être null.");
        }
        if (adherentReserve != null) {
            throw new IllegalStateException("Ce cours individuel est déjà réservé.");
        }
        this.adherentReserve = adherent;
    }

    @Override
    public int placesRestantes() {
        return adherentReserve == null ? 1 : 0;
    }
}