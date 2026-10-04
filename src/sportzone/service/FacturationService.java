package sportzone.service;

import sportzone.modele.Cours;

/**
 * Service regroupant les opérations métier liées à la facturation des
 * cours.
 */
public class FacturationService {

    /**
     * Calcule le montant à facturer pour une séance d'un cours donné.
     * <p>
     * Cette méthode ne connaît que le type le plus général { Cours} : elle
     * ne teste jamais le sous-type réel (pas de {@code instanceof}) et délègue
     * entièrement le calcul à {Cours#calculerTarifSceance()}, qui se
     * comporte différemment selon qu'il s'agit d'un cours collectif, individuel
     * ou d'un stage intensif.
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
}