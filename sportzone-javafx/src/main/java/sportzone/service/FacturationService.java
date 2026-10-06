package sportzone.service;

import sportzone.modele.Cours;
import sportzone.modele.Facture;

/**
 * Service regroupant les opérations métier liées à la facturation des
 * cours.
 */
public class FacturationService {

    /**
     * Calcule le montant à facturer pour une séance d'un cours donné.
     * <p>
     * Cette méthode ne connaît que le type le plus général {@link Cours} : elle
     * ne teste jamais le sous-type réel (pas de {@code instanceof}) et délègue
     * entièrement le calcul à {@link Cours#calculerTarifSceance()}, qui se
     * comporte différemment selon qu'il s'agit d'un cours collectif, individuel
     * ou d'un stage intensif. C'est la démonstration du polymorphisme demandée
     * en Phase 2.
     *
     * @param cours cours dont on facture une séance, non null
     * @return le montant à facturer pour cette séance
     * @throws IllegalArgumentException si cours est null
     */
    public double calculerMontantPourSceance(Cours cours) {
        if (cours == null) {
            throw new IllegalArgumentException("Le cours ne peut pas être null.");
        }
        return cours.calculerTarifSceance();
    }

    /**
     * Calcule le montant total à payer pour une facture.
     * <p>
     * Précondition : {@code facture} n'est pas null (fail-fast).
     * Postcondition : retourne un montant strictement positif (délégué à
     * {@link Facture#calculerMontantTotal()}, qui garantit ce résultat par
     * son propre invariant interne).
     *
     * @param facture facture à totaliser, non null
     * @return le montant total de la facture, strictement positif
     * @throws IllegalArgumentException si facture est null
     */
    public double calculerFacture(Facture facture) {
        if (facture == null) {
            throw new IllegalArgumentException("La facture ne peut pas être null.");
        }
        return facture.calculerMontantTotal();
    }

    /** Nombre de séances dans le mois au-delà duquel le tarif dégressif s'applique. */
    private static final int SEUIL_SEANCES_DEGRESSIF = 8;

    /** Taux de réduction appliqué une fois le seuil dépassé (10 %). */
    private static final double TAUX_REDUCTION_DEGRESSIF = 0.10;

    /**
     * Applique une réduction au montant total d'une facture lorsque le
     * nombre de séances réservées par l'adhérent dans le mois dépasse
     * {@value #SEUIL_SEANCES_DEGRESSIF}.
     * <p>
     * Précondition : {@code facture} non null, {@code nombreSeancesDuMois}
     * non négatif (fail-fast).
     * Postcondition : retourne un montant inférieur ou égal au montant
     * initial de la facture (jamais de majoration).
     *
     * @param facture             facture dont on calcule le montant final, non null
     * @param nombreSeancesDuMois nombre de séances réservées par l'adhérent
     *                            ce mois-ci, non négatif
     * @return le montant à payer, réduit de {@value #TAUX_REDUCTION_DEGRESSIF}
     *         si le seuil est dépassé
     * @throws IllegalArgumentException si facture est null ou si
     *                                  nombreSeancesDuMois est négatif
     */
    public double appliquerTarifDegressif(Facture facture, int nombreSeancesDuMois) {
        if (facture == null) {
            throw new IllegalArgumentException("La facture ne peut pas être null.");
        }
        if (nombreSeancesDuMois < 0) {
            throw new IllegalArgumentException("Le nombre de séances du mois ne peut pas être négatif.");
        }
        double montant = facture.calculerMontantTotal();
        if (nombreSeancesDuMois > SEUIL_SEANCES_DEGRESSIF) {
            return montant * (1 - TAUX_REDUCTION_DEGRESSIF);
        }
        return montant;
    }
}