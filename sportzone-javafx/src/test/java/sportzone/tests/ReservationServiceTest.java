package sportzone.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sportzone.exceptions.SceanceCompleteException;
import sportzone.modele.Adherent;
import sportzone.modele.Coach;
import sportzone.modele.CoursIndividuel;
import sportzone.modele.Reservation;
import sportzone.modele.Sceance;
import sportzone.service.ReservationService;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Tests de ReservationService : cas nominal, SceanceCompleteException, et cas limites 1 et 2 de la Phase 4. */
class ReservationServiceTest {

    private ReservationService service;
    private Coach coach;
    private Adherent premier;
    private Adherent second;

    @BeforeEach
    void initialiser() {
        service = new ReservationService();
        coach = new Coach("Karim Benali", "Coaching");
        premier = new Adherent("Lea Fontaine", "0611112222", LocalDate.now().minusDays(10));
        second = new Adherent("Noa Girard", "0633334444", LocalDate.now().minusDays(5));
    }

    @Test
    void reserverSceance_casNominal_neLevePasException() {
        CoursIndividuel coaching = new CoursIndividuel("Coaching perso", 60, 40.00);
        Sceance sceance = new Sceance(coaching, coach, LocalDateTime.now().plusDays(1), "Salle A");

        assertDoesNotThrow(() -> service.reserverSceance(sceance, premier));
    }

    @Test
    void reserverSceance_sceanceComplete_leveSceanceCompleteException() throws SceanceCompleteException {
        CoursIndividuel coaching = new CoursIndividuel("Coaching perso", 60, 40.00); // capacité fixée à 1
        Sceance sceance = new Sceance(coaching, coach, LocalDateTime.now().plusDays(1), "Salle A");
        service.reserverSceance(sceance, premier); // occupe l'unique place

        SceanceCompleteException exception = assertThrows(SceanceCompleteException.class,
                () -> service.reserverSceance(sceance, second));
        assertTrue(exception.getMessage().contains("Coaching perso"));
    }

    // ---------- Cas limite 1 : réservation sur une séance déjà passée ----------
    @Test
    void reserverSceance_sceancePassee_leveIllegalArgumentException() throws InterruptedException {
        CoursIndividuel coaching = new CoursIndividuel("Coaching perso", 60, 40.00);
        Sceance sceance = new Sceance(coaching, coach, LocalDateTime.now().plusSeconds(1), "Salle A");
        Thread.sleep(1100); // laisse l'heure de la séance passer réellement

        assertThrows(IllegalArgumentException.class, () -> service.reserverSceance(sceance, premier));
    }

    @Test
    void reserverSceance_parametresNull_leveIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> service.reserverSceance(null, premier));
    }

    // ---------- Cas limite 2 : annulation d'une réservation déjà annulée ----------
    @Test
    void annulerReservation_casNominal_marqueLaReservationAnnulee() {
        CoursIndividuel coaching = new CoursIndividuel("Coaching perso", 60, 40.00);
        Sceance sceance = new Sceance(coaching, coach, LocalDateTime.now().plusDays(1), "Salle A");
        Reservation reservation = new Reservation(sceance, premier, LocalDateTime.now());

        service.annulerReservation(reservation);

        assertTrue(reservation.isAnnulee());
    }

    @Test
    void annulerReservation_dejaAnnulee_leveIllegalStateException() {
        CoursIndividuel coaching = new CoursIndividuel("Coaching perso", 60, 40.00);
        Sceance sceance = new Sceance(coaching, coach, LocalDateTime.now().plusDays(1), "Salle A");
        Reservation reservation = new Reservation(sceance, premier, LocalDateTime.now());
        service.annulerReservation(reservation);

        assertThrows(IllegalStateException.class, () -> service.annulerReservation(reservation));
    }

    @Test
    void getJournalActivite_apresReservationReussie_contientUneEntree() {
        CoursIndividuel coaching = new CoursIndividuel("Coaching perso", 60, 40.00);
        Sceance sceance = new Sceance(coaching, coach, LocalDateTime.now().plusDays(1), "Salle A");

        assertDoesNotThrow(() -> service.reserverSceance(sceance, premier));

        assertEquals(1, service.getJournalActivite().size());
    }
}