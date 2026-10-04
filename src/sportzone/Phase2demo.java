package sportzone;

import sportzone.modele.Adherent;
import sportzone.modele.Cours;
import sportzone.modele.CoursCollectif;
import sportzone.modele.CoursIndividuel;
import sportzone.modele.Reservable;
import sportzone.modele.StageIntensif;
import sportzone.service.FacturationService;

import java.time.LocalDate;
import java.util.List;

/**
 * Programme de démonstration de la Phase 2 : montre le calcul de tarif
 * polymorphe (même appel, résultats différents selon le sous-type réel) et
 * l'usage de l'interface {Reservable}.
 * <p>
 * Preuve que { Cours} est abstraite : la ligne suivante, si elle était
 * décommentée, ne compilerait pas :
 * <pre>{@code
 * // Cours c = new Cours("Test", 30, 5); // ERREUR DE COMPILATION :
 * //   Cours is abstract; cannot be instantiated
 * }</pre>
 */
public class Phase2demo {

    public static void main(String[] args) {
        System.out.println("=== SportZone - Demonstration Phase 2 ===\n");

        Adherent a1 = new Adherent("Amina Diallo", "0612345678", LocalDate.now().minusMonths(2));
        Adherent a2 = new Adherent("Yanis Cherif", "0698765432", LocalDate.now().minusMonths(1));

        CoursCollectif collectif = new CoursCollectif("Zumba", 45, 2, 8.00);
        CoursIndividuel individuel = new CoursIndividuel("Coaching perso", 60, 40.00);
        StageIntensif stage = new StageIntensif("Stage remise en forme", 90, 12, 300.00, 5);

        // ---------- 1. Polymorphisme : même appel, resultats differents ----------
        System.out.println("--- 1. calculerTarifSceance() polymorphe (via FacturationService) ---");
        FacturationService service = new FacturationService();
        List<Cours> coursDuCatalogue = List.of(collectif, individuel, stage);
        for (Cours c : coursDuCatalogue) {
            // Le meme appel service.calculerMontantPourSceance(c) produit un resultat
            // different selon le sous-type reel de c, sans aucun instanceof.
            double tarif = service.calculerMontantPourSceance(c);
            System.out.printf("%-25s (%-16s) -> %6.2f EUR / sceance%n",
                    c.getIntitule(), c.getClass().getSimpleName(), tarif);
        }

        // ---------- 2. Reservable : implemente seulement par certains sous-types ----------
        System.out.println("\n--- 2. Interface Reservable ---");
        collectif.reserverPlace(a1);
        collectif.reserverPlace(a2);
        System.out.println("CoursCollectif places restantes apres 2 reservations : " + collectif.placesRestantes());
        try {
            collectif.reserverPlace(new Adherent("Trop", "0600000000", LocalDate.now()));
        } catch (IllegalStateException e) {
            System.out.println("[OK] 3e reservation refusee : " + e.getMessage());
        }

        individuel.reserverPlace(a1);
        System.out.println("CoursIndividuel places restantes apres reservation : " + individuel.placesRestantes());
        try {
            individuel.reserverPlace(a2);
        } catch (IllegalStateException e) {
            System.out.println("[OK] 2e reservation refusee : " + e.getMessage());
        }

        // StageIntensif n'implemente pas Reservable : impossible d'appeler
        // stage.reserverPlace(...) ici, ce qui est voulu (voir Javadoc de la classe).
        System.out.println("StageIntensif n'implemente pas Reservable (reservation en bloc, hors du champ de l'interface).");
    }
}