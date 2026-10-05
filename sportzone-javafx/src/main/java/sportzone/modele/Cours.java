package sportzone.modele;

/**
 * Représente un cours proposé par SportZone.
 * <p>
 * Invariants garantis : {@code dureeMinutes} et {@code capaciteMax} sont
 * strictement positifs.
 * <p>
 * Classe abstraite depuis la Phase 2 : {@code Cours} ne peut plus être
 * instanciée directement (toute tentative de {@code new Cours(...)} est
 * rejetée à la compilation). Les sous-classes concrètes {@link CoursCollectif},
 * {@link CoursIndividuel} et {@link StageIntensif} fournissent chacune leur
 * propre calcul de tarif via {@link #calculerTarifSceance()}.
 */
public abstract class Cours {

    private static final int CAPACITE_PAR_DEFAUT = 10;

    private String intitule;
    private int dureeMinutes;
    private int capaciteMax;

    /**
     * Constructeur complet, appelé par les sous-classes via {@code super(...)}.
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
     * {@value #CAPACITE_PAR_DEFAUT} places lorsque celle-ci n'est pas précisée.
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
     * Calcule le tarif d'une séance de ce cours. Le mode de calcul dépend
     * entièrement du sous-type réel (polymorphisme) : dégressif pour un cours
     * collectif, majoré pour un cours individuel, au forfait pour un stage
     * intensif.
     *
     * @return le tarif de la séance, strictement positif
     */
    public abstract double calculerTarifSceance();

    @Override
    public String toString() {
        return "Cours{intitule='" + intitule + "', dureeMinutes=" + dureeMinutes
                + ", capaciteMax=" + capaciteMax + "}";
    }
}
