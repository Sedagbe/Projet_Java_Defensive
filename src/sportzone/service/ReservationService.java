package sportzone.service;

import sportzone.exceptions.SceanceCompleteException;
import sportzone.modele.Adherent;
import sportzone.modele.Cours;
import sportzone.modele.Reservable;
import sportzone.modele.Reservation;
import sportzone.modele.Sceance;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Service regroupant les opérations métier liées à la réservation de
 * places sur les séances.
 */
public class ReservationService {

    private static final Logger LOGGER = Logger.getLogger(ReservationService.class.getName());

    private final List<String> journalActivite = new ArrayList<>();

    /**
     * Tente de réserver une place pour un adhérent sur une séance.
     * <p>
     * Préconditions :
     * <ul>
     *   <li>{@code sceance} et {@code adherent} ne sont pas null (fail-fast) ;</li>
     *   <li>la séance ne doit pas déjà être passée — cas limite dédié : une
     *       réservation sur une séance déjà passée est rejetée immédiatement,
     *       sans même consulter les places disponibles ;</li>
     *   <li>le cours de la séance doit être réservable séance par séance
     *       (implémenter {@link Reservable}) — un {@code StageIntensif} par
     *       exemple ne peut pas passer par cette méthode.</li>
     * </ul>
     * Postconditions :
     * <ul>
     *   <li>en cas de succès : l'adhérent occupe une place sur le cours de la
     *       séance, et une entrée de succès est ajoutée au journal ;</li>
     *   <li>en cas d'échec (séance complète) : aucune place n'est occupée,
     *       et une entrée d'échec est tout de même ajoutée au journal — c'est
     *       le rôle du bloc {@code finally} ci-dessous, qui garantit la mise
     *       à jour du journal que la réservation soit acceptée ou refusée.</li>
     * </ul>
     *
     * @param sceance  séance concernée, non null, non passée
     * @param adherent adhérent qui réserve, non null
     * @throws IllegalArgumentException si sceance ou adherent sont null, ou
     *                                  si la séance est déjà passée
     * @throws IllegalStateException    si le cours de la séance n'implémente
     *                                  pas {@link Reservable}
     * @throws SceanceCompleteException si la séance est déjà complète
     */
    public void reserverSceance(Sceance sceance, Adherent adherent) throws SceanceCompleteException {
        if (sceance == null) {
            throw new IllegalArgumentException("La séance ne peut pas être null.");
        }
        if (adherent == null) {
            throw new IllegalArgumentException("L'adhérent ne peut pas être null.");
        }
        // Cas limite : réservation sur une séance déjà passée -> rejet immédiat (fail-fast).
        if (sceance.getDateHeure().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException(
                    "Impossible de réserver une séance déjà passée (" + sceance.getDateHeure() + ").");
        }

        Cours cours = sceance.getCours();
        if (!(cours instanceof Reservable)) {
            throw new IllegalStateException(
                    "Le cours '" + cours.getIntitule() + "' ne se réserve pas séance par séance.");
        }
        Reservable reservable = (Reservable) cours;

        boolean succes = false;
        try {
            reservable.reserverPlace(adherent);
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
                LOGGER.log(Level.WARNING, "Réservation refusée pour {0} : séance complète",
                        adherent.getNom());
            }
        }
    }

    /**
     * Annule une réservation existante.
     * <p>
     * Précondition : {@code reservation} n'est pas null (fail-fast) et
     * n'est pas déjà annulée (cas limite dédié, délégué à
     * {@link Reservation#annuler()}).
     * Postcondition : {@link Reservation#isAnnulee()} retourne {@code true}.
     * <p>
     * Limite assumée de cette phase : la place libérée sur le cours n'est
     * pas remise en circulation automatiquement (cela suppose de relier
     * {@code Reservable} à une capacité modifiable après coup, hors du
     * périmètre de la programmation défensive — prévu en Phase 7 avec la
     * persistance).
     *
     * @param reservation réservation à annuler, non null, non déjà annulée
     * @throws IllegalArgumentException si reservation est null
     * @throws IllegalStateException    si la réservation est déjà annulée
     */
    public void annulerReservation(Reservation reservation) {
        if (reservation == null) {
            throw new IllegalArgumentException("La réservation ne peut pas être null.");
        }
        reservation.annuler();
        journalActivite.add(LocalDateTime.now() + " | " + reservation.getAdherent().getNom()
                + " | RÉSERVATION ANNULÉE");
        LOGGER.info("Réservation annulée pour " + reservation.getAdherent().getNom());
    }

    /** Retourne une vue du journal d'activité tenu par ce service. */
    public List<String> getJournalActivite() {
        return List.copyOf(journalActivite);
    }
}