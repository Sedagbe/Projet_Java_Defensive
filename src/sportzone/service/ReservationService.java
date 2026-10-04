package sportzone.service;

import sportzone.exceptions.SceanceCompleteException;
import sportzone.modele.Adherent;
import sportzone.modele.Reservable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Service regroupant les opérations métier liées à la réservation de
 * places sur les cours réservables.
 */
public class ReservationService {

    private static final Logger LOGGER = Logger.getLogger(ReservationService.class.getName());

    private final List<String> journalActivite = new ArrayList<>();

    /**
     * Tente de réserver une place pour un adhérent sur un cours réservable.
     * <p>
     * Précondition : {@code cours} et {@code adherent} ne sont pas null.
     * Postcondition (succès) : l'adhérent occupe une place sur le cours et
     * une entrée de succès est ajoutée au journal d'activité.
     * Postcondition (échec) : aucune place n'est occupée, et une entrée
     * d'échec est tout de même ajoutée au journal — c'est le rôle du bloc
     * {@code finally} ci-dessous, qui garantit la mise à jour du journal
     * que la réservation soit acceptée ou refusée.
     *
     * @param cours    cours réservable concerné, non null
     * @param adherent adhérent qui réserve, non null
     * @throws SceanceCompleteException si le cours est déjà complet
     */
    public void reserverSceance(Reservable cours, Adherent adherent) throws SceanceCompleteException {
        if (cours == null) {
            throw new IllegalArgumentException("Le cours ne peut pas être null.");
        }
        if (adherent == null) {
            throw new IllegalArgumentException("L'adhérent ne peut pas être null.");
        }

        boolean succes = false;
        try {
            cours.reserverPlace(adherent);
            succes = true;
        } finally {
            // Ce bloc s'exécute que la réservation réussisse ou qu'une
            // SceanceCompleteException soit levée juste au-dessus : le
            // journal d'activité est donc toujours tenu à jour.
            String entree = LocalDateTime.now() + " | " + adherent.getNom() + " | "
                    + (succes ? "RÉSERVATION ACCEPTÉE" : "RÉSERVATION REFUSÉE");
            journalActivite.add(entree);
            if (succes) {
                LOGGER.info("Réservation acceptée pour " + adherent.getNom());
            } else {
                LOGGER.log(Level.WARNING, "Réservation refusée pour {0} : cours complet",
                        adherent.getNom());
            }
        }
    }

    /** Retourne une vue du journal d'activité tenu par ce service. */
    public List<String> getJournalActivite() {
        return List.copyOf(journalActivite);
    }
}