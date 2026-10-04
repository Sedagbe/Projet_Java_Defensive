package sportzone;

import sportzone.exceptions.SceanceCompleteException;
import sportzone.modele.Adherent;
import sportzone.modele.Coach;
import sportzone.modele.CoursCollectif;
import sportzone.modele.Reservation;
import sportzone.modele.Sceance;
import sportzone.service.AdherentService;
import sportzone.service.FacturationService;
import sportzone.service.ReservationService;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Programme de démonstration de la Phase 4 : programmation défensive.
 * <p>
 * Couvre les 4 cas limites exigés par l'énoncé :
 * <ol>
 *   <li>réservation sur une séance déjà passée ;</li>
 *   <li>annulation d'une réservation déjà annulée ;</li>
 *   <li>facture sans aucune ligne (rendue impossible par construction) ;</li>
 *   <li>cours dont la capacité maximale configurée est nulle ou négative
 *       (rejeté à la création, déjà couvert en Phase 1).</li>
 * </ol>
 * <p>
 * IMPORTANT : les assertions Java ({@code assert}) sont désactivées par
 * défaut. Pour que l'assert de {@code Facture.calculerMontantTotal()}
 * s'exécute réellement, lance ce programme avec l'option JVM {@code -ea}
 * (dans IntelliJ : Run > Edit Configurations > VM options > {@code -ea}).
 */
public class Phase4Demo {

    public static void main(String[] args) throws SceanceCompleteException {
        System.out.println("=== SportZone - Demonstration Phase 4 ===\n");

        ReservationService reservationService = new ReservationService();
        AdherentService adherentService = new AdherentService();
        FacturationService facturationService = new FacturationService();

        Adherent amina = new Adherent("Amina Diallo", "0612345678", LocalDate.now().minusMonths(2));
        adherentService.ajouterAdherent(amina);
        Coach coach = new Coach("Karim Benali", "Yoga");
        CoursCollectif yoga = new CoursCollectif("Yoga", 60, 10, 8.00);

        // ---------- Cas limite 1 : reservation sur une seance deja passee ----------
        System.out.println("--- Cas limite 1 : séance déjà passée ---");
        Sceance sceancePassee = construireSceancePassee(yoga, coach);
        try {
            reservationService.reserverSceance(sceancePassee, amina);
            System.out.println("[ÉCHEC] La réservation aurait dû être refusée !");
        } catch (IllegalArgumentException e) {
            System.out.println("[OK] Rejetée : " + e.getMessage());
        }

        // ---------- Cas limite 2 : annulation d'une reservation deja annulee ----------
        System.out.println("\n--- Cas limite 2 : annulation déjà annulée ---");
        Sceance sceanceFuture = new Sceance(yoga, coach, LocalDateTime.now().plusDays(1), "Salle A");
        Reservation reservation = new Reservation(sceanceFuture, amina, LocalDateTime.now());
        reservationService.annulerReservation(reservation);
        System.out.println("[OK] Première annulation acceptée (isAnnulee = " + reservation.isAnnulee() + ")");
        try {
            reservationService.annulerReservation(reservation);
            System.out.println("[ÉCHEC] La deuxième annulation aurait dû être refusée !");
        } catch (IllegalStateException e) {
            System.out.println("[OK] Deuxième annulation rejetée : " + e.getMessage());
        }

        // ---------- Cas limite 3 : facture sans aucune ligne ----------
        System.out.println("\n--- Cas limite 3 : facture sans ligne ---");
        System.out.println("Impossible à représenter : le constructeur de Facture exige toujours");
        System.out.println("une première ligne valide, et aucune méthode publique ne permet de vider");
        System.out.println("la liste ensuite (getLigneFactures() est non modifiable). Démonstration :");
        try {
            new sportzone.modele.Facture(amina, LocalDate.now(), "", 10.0); // désignation vide -> refusée
            System.out.println("[ÉCHEC] Aurait dû être refusée !");
        } catch (IllegalArgumentException e) {
            System.out.println("[OK] Tentative de ligne invalide rejetée : " + e.getMessage());
        }
        sportzone.modele.Facture facture = new sportzone.modele.Facture(amina, LocalDate.now(), "Séance de yoga", 8.0);
        System.out.println("Montant d'une facture valide (assert actif si -ea) : "
                + facturationService.calculerFacture(facture) + " €");

        // ---------- Cas limite 4 : capacite nulle ou negative (rappel Phase 1) ----------
        System.out.println("\n--- Cas limite 4 : capacité nulle ou négative ---");
        try {
            new CoursCollectif("Zumba", 45, 0, 8.00);
            System.out.println("[ÉCHEC] Aurait dû être refusée !");
        } catch (IllegalArgumentException e) {
            System.out.println("[OK] Capacité nulle rejetée : " + e.getMessage());
        }
        try {
            new CoursCollectif("Zumba", 45, -3, 8.00);
            System.out.println("[ÉCHEC] Aurait dû être refusée !");
        } catch (IllegalArgumentException e) {
            System.out.println("[OK] Capacité négative rejetée : " + e.getMessage());
        }

        // ---------- Optional vs exception pour la recherche d'adherent ----------
        System.out.println("\n--- Recherche par Optional (absence = cas normal, pas une erreur) ---");
        var trouve = adherentService.rechercherParTelephoneOptionnel("0612345678");
        var absent = adherentService.rechercherParTelephoneOptionnel("0699999999");
        System.out.println("Numéro existant   -> " + trouve.map(Adherent::getNom).orElse("aucun"));
        System.out.println("Numéro inexistant -> " + absent.map(Adherent::getNom).orElse("aucun (Optional vide, pas d'exception)"));
    }

    /**
     * Construit une séance dont la date est déjà passée AU MOMENT DE LA
     * RÉSERVATION, en simulant l'écoulement du temps entre la création de
     * la séance (date encore future, donc acceptée par le constructeur de
     * {@link Sceance}) et la tentative de réservation.
     */
    private static Sceance construireSceancePassee(CoursCollectif cours, Coach coach) {
        Sceance sceance = new Sceance(cours, coach, LocalDateTime.now().plusSeconds(1), "Salle A");
        try {
            Thread.sleep(1100); // laisse l'heure de la séance passer réellement
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return sceance;
    }
}