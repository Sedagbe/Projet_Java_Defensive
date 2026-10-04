package sportzone.modele;

import java.time.LocalDateTime;

/**
 * Représente la réservation d'une {@link Sceance} par un {@link Adherent}.
 * <p>
 * Relation de conception : Reservation est en <b>agrégation</b> avec Sceance
 * et Adherent (losange vide côté Reservation sur le diagramme UML) — une
 * séance ou un adhérent existent indépendamment d'une réservation donnée :
 * si la réservation est supprimée, ni la séance ni l'adhérent ne
 * disparaissent.
 * <p>
 * Invariant garanti : {@code dateReservation} n'est pas postérieure à la
 * date/heure de la séance réservée.
 */
public class Reservation {

    private Sceance sceance;
    private Adherent adherent;
    private final LocalDateTime dateReservation;

    /**
     * Crée une nouvelle réservation.
     *
     * @param sceance          séance réservée, non null
     * @param adherent        adhérent qui réserve, non null
     * @param dateReservation date de la réservation, ne peut pas être
     *                        postérieure à la date/heure de la séance
     * @throws IllegalArgumentException si sceance ou adherent sont null, ou si
     *                                  dateReservation est postérieure à la
     *                                  date/heure de la séance
     */
    public Reservation(Sceance sceance, Adherent adherent, LocalDateTime dateReservation) {
        if (sceance == null) {
            throw new IllegalArgumentException("La séance d'une réservation ne peut pas être null.");
        }
        if (adherent == null) {
            throw new IllegalArgumentException("L'adhérent d'une réservation ne peut pas être null.");
        }
        if (dateReservation == null || dateReservation.isAfter(sceance.getDateHeure())) {
            throw new IllegalArgumentException(
                    "La date de réservation ne peut pas être postérieure à la date de la séance.");
        }
        this.sceance = sceance;
        this.adherent = adherent;
        this.dateReservation = dateReservation;
    }

    public Sceance getSceance() {
        return sceance;
    }

    public void setSceance(Sceance sceance) {
        if (sceance == null) {
            throw new IllegalArgumentException("La séance d'une réservation ne peut pas être null.");
        }
        this.sceance = sceance;
    }

    public Adherent getAdherent() {
        return adherent;
    }

    public void setAdherent(Adherent adherent) {
        if (adherent == null) {
            throw new IllegalArgumentException("L'adhérent d'une réservation ne peut pas être null.");
        }
        this.adherent = adherent;
    }

    public LocalDateTime getDateReservation() {
        return dateReservation;
    }

    @Override
    public String toString() {
        return "Reservation{sceance=" + sceance + ", adherent=" + adherent.getNom()
                + ", dateReservation=" + dateReservation + "}";
    }
}