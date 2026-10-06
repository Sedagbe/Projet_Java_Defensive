package sportzone.tests;

import org.junit.jupiter.api.Test;
import sportzone.modele.Cours;
import sportzone.modele.CoursCollectif;
import sportzone.modele.CoursIndividuel;
import sportzone.modele.StageIntensif;
import sportzone.service.FacturationService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/** Tests du calcul de tarif polymorphe (Phase 2) et des invariants de Cours (Phase 1). */
class CoursTest {

    private final FacturationService facturationService = new FacturationService();

    @Test
    void calculerTarifSceance_coursCollectif_retourneLeTarifParPersonne() {
        CoursCollectif yoga = new CoursCollectif("Yoga", 60, 10, 8.00);
        assertEquals(8.00, yoga.calculerTarifSceance(), 0.0001);
    }

    @Test
    void calculerTarifSceance_coursIndividuel_retourneLeTarifHoraireProratise() {
        CoursIndividuel coaching = new CoursIndividuel("Coaching", 30, 40.00);
        assertEquals(20.00, coaching.calculerTarifSceance(), 0.0001); // 30 min = demi-tarif horaire
    }

    @Test
    void calculerTarifSceance_stageIntensif_retourneLeForfaitDiviseParLeNombreDeSceances() {
        StageIntensif stage = new StageIntensif("Stage", 90, 12, 300.00, 5);
        assertEquals(60.00, stage.calculerTarifSceance(), 0.0001);
    }

    @Test
    void calculerMontantPourSceance_estPolymorphe_memeAppelResultatsDifferents() {
        Cours collectif = new CoursCollectif("Zumba", 45, 15, 7.00);
        Cours individuel = new CoursIndividuel("Coaching", 60, 40.00);
        Cours stage = new StageIntensif("Stage", 90, 12, 300.00, 5);

        // Le même appel de méthode, sur le type général Cours, donne un
        // résultat différent selon le sous-type réel — sans instanceof.
        assertEquals(7.00, facturationService.calculerMontantPourSceance(collectif), 0.0001);
        assertEquals(40.00, facturationService.calculerMontantPourSceance(individuel), 0.0001);
        assertEquals(60.00, facturationService.calculerMontantPourSceance(stage), 0.0001);
    }

    // ---------- Cas limite : capacité nulle ou négative (rejetée à la création) ----------

    @Test
    void constructeur_capaciteNulle_leveIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> new CoursCollectif("Zumba", 45, 0, 8.00));
    }

    @Test
    void constructeur_capaciteNegative_leveIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> new CoursCollectif("Zumba", 45, -3, 8.00));
    }

    @Test
    void constructeur_dureeNulle_leveIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> new CoursCollectif("Zumba", 0, 10, 8.00));
    }
}