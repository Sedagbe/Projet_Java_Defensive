package sportzone.modele;

import java.time.LocalDate;

/**
 * Représente un adhérent de la salle de sport SportZone.
 * Invariants: le nom n'est jamais vide et la date d'inscription
 * n'est jamais dans le futur.
 */


public class Adherent {

    private String nom;
    private String telephone;
    private final LocalDate dateInscription;

    /**
     * Constructeur
     * @param nom             nom de l'adhérent, non vide
     * @param telephone       numéro de téléphone (peut être null si non renseigné)
     * @param dateInscription date d'inscription, ne peut pas être future
     * @throws IllegalArgumentException si le nom est vide ou si la date d'inscription est future
     */

    public Adherent(String nom, String telephone, LocalDate dateInscription) {
        if (nom == null || nom.isBlank()) {
            throw new IllegalArgumentException("Le nom de l'adhérent ne peut pas être vide.");
        }
        if (dateInscription == null || dateInscription.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La date d'inscription ne peut pas être future.");
        }
        this.nom = nom;
        this.telephone = telephone;
        this.dateInscription = dateInscription;
    }

    public String getNom() {
        return nom;
    }

    /**
     * Modifie le nom de l'adhérent.
     * @param nom nouveau nom, non vide
     * @throws IllegalArgumentException si le nom est vide
     */
    public void setNom(String nom) {
        if (nom == null || nom.isBlank()) {
            throw new IllegalArgumentException("Le nom de l'adhérent ne peut pas être vide.");
        }
        this.nom = nom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public LocalDate getDateInscription() {
        return dateInscription;
    }

    @Override
    public String toString() {
        return "Adherent{nom='" + nom + "', telephone='" + telephone
                + "', dateInscription=" + dateInscription + "}";
    }
}