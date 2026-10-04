package sportzone.modele;

/**
 * Stage intensif : un forfait couvre l'intégralité du stage (plusieurs
 * séances), réservé en une seule fois pour toute sa durée.
 */
public class StageIntensif extends Cours {

    private double forfait;
    private int nombreSceances;

    /**
     * Constructeur
     *
     * @param intitule      intitulé du stage, non vide
     * @param dureeMinutes  durée d'une séance en minutes, strictement positive
     * @param capaciteMax   capacité maximale du stage, strictement positive
     * @param forfait       prix total du stage, strictement positif
     * @param nombreSceances nombre de séances composant le stage, strictement
     *                      positif
     * @throws IllegalArgumentException si l'un des paramètres viole son
     *                                  invariant
     */
    public StageIntensif(String intitule, int dureeMinutes, int capaciteMax, double forfait, int nombreSceances) {
        super(intitule, dureeMinutes, capaciteMax);
        if (forfait <= 0) {
            throw new IllegalArgumentException("Le forfait doit être strictement positif.");
        }
        if (nombreSceances <= 0) {
            throw new IllegalArgumentException("Le nombre de séances doit être strictement positif.");
        }
        this.forfait = forfait;
        this.nombreSceances = nombreSceances;
    }

    public double getForfait() {
        return forfait;
    }

    public int getNombreSceances() {
        return nombreSceances;
    }

    @Override
    public double calculerTarifSceance() {
        return forfait / nombreSceances;
    }
}