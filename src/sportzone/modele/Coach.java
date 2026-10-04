package sportzone.modele;

/**
 * Représente un coach de SportZone, responsable d'animer des séances.
 * Invariant: le nom n'est jamais vide.
 */
public class Coach {

    private String nom;
    private String specialite;

    /**
     * Constructeur
     * @param nom        nom du coach, non vide
     * @param specialite spécialité du coach (peut être null si non renseignée)
     * @throws IllegalArgumentException si le nom est vide
     */
    public Coach(String nom, String specialite) {
        if (nom == null || nom.isBlank()) {
            throw new IllegalArgumentException("Le nom du coach ne peut pas être vide.");
        }
        this.nom = nom;
        this.specialite = specialite;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        if (nom == null || nom.isBlank()) {
            throw new IllegalArgumentException("Le nom du coach ne peut pas être vide.");
        }
        this.nom = nom;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    @Override
    public String toString() {
        return "Coach{nom='" + nom + "', specialite='" + specialite + "'}";
    }
}