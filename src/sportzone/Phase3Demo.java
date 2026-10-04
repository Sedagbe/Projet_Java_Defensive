package sportzone;

import sportzone.exceptions.AdherentIntrouvableException;
import sportzone.exceptions.SceanceCompleteException;
import sportzone.modele.Adherent;
import sportzone.modele.Coach;
import sportzone.modele.CoursIndividuel;
import sportzone.modele.Sceance;
import sportzone.service.AdherentService;
import sportzone.service.ReservationService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Programme de démonstration de la Phase 3 : montre une
 * {@link SceanceCompleteException} et une {@link AdherentIntrouvableException}
 * réellement interceptées et journalisées, ainsi que l'import de fichier
 * avec try-with-resources.
 */
public class Phase3Demo {

    public static void main(String[] args) throws IOException {
        System.out.println("=== SportZone - Demonstration Phase 3 ===\n");

        // ---------- 1. SceanceCompleteException, interceptee et journalisee ----------
        System.out.println("--- 1. SceanceCompleteException (via ReservationService) ---");
        ReservationService reservationService = new ReservationService();
        CoursIndividuel coaching = new CoursIndividuel("Coaching perso", 60, 40.00);
        Coach coach = new Coach("Karim Benali", "Coaching");
        Sceance sceanceCoaching = new Sceance(coaching, coach, LocalDateTime.now().plusDays(2), "Salle B");
        Adherent premier = new Adherent("Lea Fontaine", "0611112222", LocalDate.now().minusDays(10));
        Adherent second = new Adherent("Noa Girard", "0633334444", LocalDate.now().minusDays(5));

        try {
            reservationService.reserverSceance(sceanceCoaching, premier);
            System.out.println("Premiere reservation acceptee.");
            reservationService.reserverSceance(sceanceCoaching, second);
        } catch (SceanceCompleteException e) {
            System.out.println("[OK] Exception interceptee : " + e.getMessage());
        }
        System.out.println("Journal d'activite : " + reservationService.getJournalActivite());

        // ---------- 2. AdherentIntrouvableException, interceptee et journalisee ----------
        System.out.println("\n--- 2. AdherentIntrouvableException (via AdherentService) ---");
        AdherentService adherentService = new AdherentService();
        adherentService.ajouterAdherent(premier);
        try {
            adherentService.rechercherParTelephone("0600000000");
        } catch (AdherentIntrouvableException e) {
            System.out.println("[OK] Exception interceptee : " + e.getMessage());
        }

        // ---------- 3. Import de fichier avec try-with-resources ----------
        System.out.println("\n--- 3. Import d'adherents depuis un fichier (try-with-resources) ---");
        int importes = adherentService.importerDepuisFichier("data/adherents.txt");
        System.out.println(importes + " adherent(s) importe(s) avec succes sur 6 lignes lues.");
        System.out.println("(Les lignes mal formees ci-dessus apparaissent en WARNING dans le journal java.util.logging.)");
        adherentService.getAdherents().forEach(System.out::println);
    }
}