package sportzone.modele;

/**
 * Représente un cours proposé par SportZone.
 * Invariants garantis : {dureeMinutes} et {capaciteMax} sont
 * strictement positifs.
 */
public abstract class Cours {

    private static final int CAPACITE_PAR_DEFAUT = 10;

    private String intitule;
    private int dureeMinutes;
    private int capaciteMax;

    /**
     * Constructeur complet, appelé par les sous-classes via {super(...)}.
     *
     * @param intitule     intitulé du cours, non vide
     * @param dureeMinutes durée en minutes, strictement positive
     * @param capaciteMax  capacité maximale, strictement positive
     * @throws IllegalArgumentException si l'intitulé est vide, ou si la durée
     *                                  ou la capacité ne sont pas strictement
     *                                  positives
     */
    protected Cours(String intitule, int dureeMinutes, int capaciteMax) {
        if (intitule == null || intitule.isBlank()) {
            throw new IllegalArgumentException("L'intitulé du cours ne peut pas être vide.");
        }
        if (dureeMinutes <= 0) {
            throw new IllegalArgumentException("La durée du cours doit être strictement positive.");
        }
        if (capaciteMax <= 0) {
            throw new IllegalArgumentException("La capacité maximale doit être strictement positive.");
        }
        this.intitule = intitule;
        this.dureeMinutes = dureeMinutes;
        this.capaciteMax = capaciteMax;
    }

    /**
     * Constructeur surchargé : applique une capacité par défaut de
     * {#CAPACITE_PAR_DEFAUT} places lorsque celle-ci n'est pas précisée.
     *
     * @param intitule     intitulé du cours, non vide
     * @param dureeMinutes durée en minutes, strictement positive
     */
    protected Cours(String intitule, int dureeMinutes) {
        this(intitule, dureeMinutes, CAPACITE_PAR_DEFAUT);
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        if (intitule == null || intitule.isBlank()) {
            throw new IllegalArgumentException("L'intitulé du cours ne peut pas être vide.");
        }
        this.intitule = intitule;
    }

    public int getDureeMinutes() {
        return dureeMinutes;
    }

    public void setDureeMinutes(int dureeMinutes) {
        if (dureeMinutes <= 0) {
            throw new IllegalArgumentException("La durée du cours doit être strictement positive.");
        }
        this.dureeMinutes = dureeMinutes;
    }

    public int getCapaciteMax() {
        return capaciteMax;
    }

    public void setCapaciteMax(int capaciteMax) {
        if (capaciteMax <= 0) {
            throw new IllegalArgumentException("La capacité maximale doit être strictement positive.");
        }
        this.capaciteMax = capaciteMax;
    }

    /**
     * Calcule le tarif d'une séance de ce cours.
     */
    public abstract double calculerTarifSceance();

    @Override
    public String toString() {
        return "Cours{intitule='" + intitule + "', dureeMinutes=" + dureeMinutes
                + ", capaciteMax=" + capaciteMax + "}";
    }
}