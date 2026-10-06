package sportzone.tests;

import org.junit.jupiter.api.Test;
import sportzone.modele.Adherent;
import sportzone.modele.Facture;
import sportzone.service.FacturationService;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Développé en TDD strict : ce test a été écrit AVANT
 * FacturationService.appliquerTarifDegressif(). Voir le compte-rendu TDD
 * livré avec cette phase pour le détail du cycle rouge-vert-refactor.
 */
class FacturationServiceDegressifTest {

    private final FacturationService service = new FacturationService();
    private final Adherent adherent = new Adherent("Amina Diallo", "0612345678", LocalDate.now().minusMonths(2));

    @Test
    void appliquerTarifDegressif_sousLeSeuil_aucuneReduction() {
        Facture facture = new Facture(adherent, LocalDate.now(), "Séance de yoga", 10.0);
        double resultat = service.appliquerTarifDegressif(facture, 8);
        assertEquals(10.0, resultat, 0.0001);
    }

    @Test
    void appliquerTarifDegressif_auDessusDuSeuil_applique10PourcentDeReduction() {
        Facture facture = new Facture(adherent, LocalDate.now(), "Séance de yoga", 10.0);
        double resultat = service.appliquerTarifDegressif(facture, 9);
        assertEquals(9.0, resultat, 0.0001);
    }

    @Test
    void appliquerTarifDegressif_factureNull_leveIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> service.appliquerTarifDegressif(null, 9));
    }

    @Test
    void appliquerTarifDegressif_nombreSeancesNegatif_leveIllegalArgumentException() {
        Facture facture = new Facture(adherent, LocalDate.now(), "Séance de yoga", 10.0);
        assertThrows(IllegalArgumentException.class, () -> service.appliquerTarifDegressif(facture, -1));
    }
}