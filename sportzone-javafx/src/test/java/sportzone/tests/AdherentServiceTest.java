package sportzone.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sportzone.exceptions.AdherentIntrouvableException;
import sportzone.modele.Adherent;
import sportzone.service.AdherentService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Tests d'AdherentService : recherche (exception vs Optional) et import try-with-resources. */
class AdherentServiceTest {

    private AdherentService service;

    @BeforeEach
    void initialiser() {
        service = new AdherentService();
        service.ajouterAdherent(new Adherent("Amina Diallo", "0612345678", LocalDate.now().minusMonths(2)));
    }

    @Test
    void rechercherParTelephone_existant_retourneLAdherent() throws AdherentIntrouvableException {
        Adherent trouve = service.rechercherParTelephone("0612345678");
        assertEquals("Amina Diallo", trouve.getNom());
    }

    @Test
    void rechercherParTelephone_introuvable_leveAdherentIntrouvableException() {
        AdherentIntrouvableException exception = assertThrows(AdherentIntrouvableException.class,
                () -> service.rechercherParTelephone("0600000000"));
        assertTrue(exception.getMessage().contains("0600000000"));
    }

    @Test
    void rechercherParTelephoneOptionnel_existant_retourneOptionalRempli() {
        Optional<Adherent> resultat = service.rechercherParTelephoneOptionnel("0612345678");
        assertTrue(resultat.isPresent());
    }

    @Test
    void rechercherParTelephoneOptionnel_absent_retourneOptionalVide() {
        Optional<Adherent> resultat = service.rechercherParTelephoneOptionnel("0600000000");
        assertFalse(resultat.isPresent());
    }

    @Test
    void importerDepuisFichier_ignoreLesLignesMalFormeesSansInterrompreLimport() throws IOException {
        Path fichier = Files.createTempFile("adherents-test", ".txt");
        Files.writeString(fichier, String.join("\n",
                "Fatou Ndiaye;0611111111;2024-01-10",
                "LigneSansPointsVirgules",
                "Jean Dupont;0622222222;2023-12-01",
                ";0633333333;2024-02-01",
                "Awa Traore;0644444444;date-invalide",
                "Moussa Keita;0655555555;2024-03-15"
        ));

        int importes = service.importerDepuisFichier(fichier.toString());

        assertEquals(3, importes); // 3 lignes valides sur 6 (en plus de l'adhérent déjà présent)
        Files.deleteIfExists(fichier);
    }

    @Test
    void importerDepuisFichier_fichierInexistant_leveIOException() {
        assertThrows(IOException.class, () -> service.importerDepuisFichier("fichier_qui_nexiste_pas.txt"));
    }
}