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
}
