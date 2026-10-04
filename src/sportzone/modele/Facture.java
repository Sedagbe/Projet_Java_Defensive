package sportzone.modele;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Représente une facture émise pour un {#Adherent}, composée d'au moins
 * une ligne de facturation.

 * Invariant : une facture contient toujours au moins une ligne.
 */
public class Facture {

    private final Adherent adherent;
    private final LocalDate dateEmission;
    private final List<LigneFacture> ligneFactures;

    /**
     * Constructeun
     *
     * @param adherent     adhérent facturé, non null
     * @param dateEmission date d'émission de la facture, non null
     * @param designation  désignation de la première ligne, non vide
     * @param montant      montant de la première ligne, strictement positif
     * @throws IllegalArgumentException si adherent ou dateEmission sont null,
     *                                  ou si la première ligne est invalide
     */
    public Facture(Adherent adherent, LocalDate dateEmission, String designation, double montant) {
        if (adherent == null) {
            throw new IllegalArgumentException("L'adhérent d'une facture ne peut pas être null.");
        }
        if (dateEmission == null) {
            throw new IllegalArgumentException("La date d'émission ne peut pas être null.");
        }
        this.adherent = adherent;
        this.dateEmission = dateEmission;
        this.ligneFactures = new ArrayList<>();
        this.ligneFactures.add(new LigneFacture(designation, montant));
    }

    /**
     * Ajoute une ligne de facturation à cette facture.
     *
     * @param designation désignation de la ligne, non vide
     * @param montant     montant de la ligne, strictement positif
     */
    public void ajouterLigne(String designation, double montant) {
        this.ligneFactures.add(new LigneFacture(designation, montant));
    }

    public Adherent getAdherent() {
        return adherent;
    }

    public LocalDate getDateEmission() {
        return dateEmission;
    }

    /** Retourne une vue non modifiable des lignes, pour préserver l'encapsulation. */
    public List<LigneFacture> getLigneFactures() {
        return Collections.unmodifiableList(ligneFactures);
    }

    /**
     * Calcule le montant total de la facture, somme des montants de chaque
     * ligne.
     *
     * @return le montant total
     */
    public double calculerMontantTotal() {
        double total = 0.0;
        for (LigneFacture ligne : ligneFactures) {
            total += ligne.getMontant();
        }
        return total;
    }

    @Override
    public String toString() {
        return "Facture{adherent=" + adherent.getNom() + ", dateEmission=" + dateEmission
                + ", nbLignes=" + ligneFactures.size() + ", total=" + calculerMontantTotal() + "}";
    }

    /**
     * Ligne de facturation, élément constitutif d'une {#Facture}.
     *
     * Classe interne (non static) : une ligne de facturation n'existe et n'a
     * de sens que rattachée à une facture précise, ce qui illustre la
     * relation de composition entre Facture et LigneFacture.
     */
    public class LigneFacture {

        private String designation;
        private double montant;

        /**
         * Constructeur
         *
         * @param designation désignation de la ligne, non vide
         * @param montant     montant de la ligne, strictement positif
         * @throws IllegalArgumentException si designation est vide ou montant
         *                                  n'est pas strictement positif
         */
        private LigneFacture(String designation, double montant) {
            if (designation == null || designation.isBlank()) {
                throw new IllegalArgumentException("La désignation d'une ligne ne peut pas être vide.");
            }
            if (montant <= 0) {
                throw new IllegalArgumentException("Le montant d'une ligne doit être strictement positif.");
            }
            this.designation = designation;
            this.montant = montant;
        }

        public String getDesignation() {
            return designation;
        }

        public double getMontant() {
            return montant;
        }

        @Override
        public String toString() {
            return "LigneFacture{designation='" + designation + "', montant=" + montant + "}";
        }
    }
}