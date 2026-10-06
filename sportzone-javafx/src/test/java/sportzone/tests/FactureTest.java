package sportzone.tests;

import org.junit.jupiter.api.Test;
import sportzone.modele.Adherent;
import sportzone.modele.Facture;
import sportzone.service.FacturationService;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/** Tests de Facture, dont le cas limite 3 de la Phase 4 : facture sans aucune ligne. */
class FactureTest {

    private final Adherent adherent = new Adherent("Amina Diallo", "0612345678", LocalDate.now().minusMonths(2));
    private final FacturationService facturationService = new FacturationService();

    // ---------- Cas limite 3 : facture sans aucune ligne ----------
    // Impossible à représenter : le constructeur exige toujours une première
    // ligne valide. On teste donc que toute tentative de première ligne
    // invalide (ce qui est la seule façon d'essayer de créer une facture
    // "vide") est rejetée, ce qui rend l'état invalide irreprésentable.
    @Test
    void constructeur_designationVide_leveIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Facture(adherent, LocalDate.now(), "", 10.0));
    }

    @Test
    void constructeur_montantNulOuNegatif_leveIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Facture(adherent, LocalDate.now(), "Séance", 0));
    }

    @Test
    void calculerMontantTotal_uneLigne_retourneLeMontantDeLaLigne() {
        Facture facture = new Facture(adherent, LocalDate.now(), "Séance de yoga", 8.0);
        assertEquals(8.0, facture.calculerMontantTotal(), 0.0001);
    }

    @Test
    void calculerMontantTotal_plusieursLignes_retourneLaSomme() {
        Facture facture = new Facture(adherent, LocalDate.now(), "Séance de yoga", 8.0);
        facture.ajouterLigne("Location de tapis", 2.0);

        assertEquals(10.0, facture.calculerMontantTotal(), 0.0001);
    }

    @Test
    void calculerFacture_viaFacturationService_delegueCorrectement() {
        Facture facture = new Facture(adherent, LocalDate.now(), "Séance de yoga", 8.0);
        assertEquals(8.0, facturationService.calculerFacture(facture), 0.0001);
    }

    @Test
    void calculerFacture_factureNull_leveIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> facturationService.calculerFacture(null));
    }
}