package sportzone;

import sportzone.modele.Adherent;
import sportzone.modele.Coach;
import sportzone.modele.Cours;
import sportzone.modele.CoursCollectif;
import sportzone.modele.Facture;
import sportzone.modele.Reservation;
import sportzone.modele.Sceance;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Programme de démonstration de la Phase 1 : montre la création d'objets
 * valides, la classe interne {@code LigneFacture} et le rejet des
 * invariants violés.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== SportZone – Démonstration Phase 1 ===\n");

        // ---------- 1. Création d'objets valides ----------
        System.out.println("--- 1. Objets valides ---");
        Adherent adherent = new Adherent("Amina Diallo", "0612345678", LocalDate.now().minusMonths(2));
        Coach coach = new Coach("Karim Benali", "Yoga");
        Cours yoga = new CoursCollectif("Yoga", 60, 15, 8.00);
        Cours pilates = new CoursCollectif("Pilates", 45, 10, 7.00);
        Sceance sceance = new Sceance(yoga, coach, LocalDateTime.now().plusDays(3), "Salle A");
        Reservation reservation = new Reservation(sceance, adherent, LocalDateTime.now());

        Facture facture = new Facture(adherent, LocalDate.now(), "Séance de yoga", 12.50);
        facture.ajouterLigne("Location de tapis", 2.00);

        System.out.println(adherent);
        System.out.println(coach);
        System.out.println(yoga);
        System.out.println(pilates);
        System.out.println(sceance);
        System.out.println(reservation);
        System.out.println(facture);
        for (Facture.LigneFacture ligne : facture.getLigneFactures()) {
            System.out.println("   " + ligne);
        }

        // ---------- 2. Invariants violés : chaque création doit être rejetée ----------
        System.out.println("\n--- 2. Invariants violés (rejets attendus) ---");
        testerRejet("Adhérent au nom vide",
                () -> new Adherent("   ", "0600000000", LocalDate.now()));
        testerRejet("Adhérent inscrit dans le futur",
                () -> new Adherent("Test", "0600000000", LocalDate.now().plusDays(1)));
        testerRejet("Coach au nom vide",
                () -> new Coach("", "Boxe"));
        testerRejet("Cours de durée nulle",
                () -> new CoursCollectif("Zumba", 0, 10, 8.00));
        testerRejet("Cours de capacité négative",
                () -> new CoursCollectif("Zumba", 45, -5, 8.00));
        testerRejet("Séance dans le passé",
                () -> new Sceance(yoga, coach, LocalDateTime.now().minusDays(1), "Salle B"));
        testerRejet("Réservation postérieure à la séance",
                () -> new Reservation(sceance, adherent, sceance.getDateHeure().plusHours(1)));
        testerRejet("Facture dont la première ligne est invalide (montant nul)",
                () -> new Facture(adherent, LocalDate.now(), "Séance", 0));

        // ---------- 3. Encapsulation ----------
        System.out.println("\n--- 3. Encapsulation ---");
        try {
            facture.getLigneFactures().clear();
            System.out.println("[ÉCHEC] La liste interne des lignes a pu être modifiée de l'extérieur !");
        } catch (UnsupportedOperationException e) {
            System.out.println("[OK] La liste des lignes est protégée : modification directe refusée.");
        }
        System.out.println("Total facture inchangé : " + facture.calculerMontantTotal() + " €");
    }

    /**
     * Exécute une création d'objet censée échouer et affiche le résultat.
     *
     * @param description description du cas testé
     * @param creation    création d'objet devant lever une IllegalArgumentException
     */
    private static void testerRejet(String description, Runnable creation) {
        try {
            creation.run();
            System.out.println("[ÉCHEC] " + description + " : aucune exception levée !");
        } catch (IllegalArgumentException e) {
            System.out.println("[OK] " + description + " -> rejeté : " + e.getMessage());
        }
    }
}