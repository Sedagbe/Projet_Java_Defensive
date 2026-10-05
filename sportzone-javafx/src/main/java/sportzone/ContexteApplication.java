package sportzone;

import sportzone.modele.Coach;
import sportzone.modele.CoursCollectif;
import sportzone.modele.CoursIndividuel;
import sportzone.modele.Sceance;
import sportzone.modele.StageIntensif;
import sportzone.service.AdherentService;
import sportzone.service.FacturationService;
import sportzone.service.ReservationService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Contexte applicatif partagé entre tous les contrôleurs JavaFX : contient
 * les instances uniques des services métier (pour que toutes les vues
 * travaillent sur les mêmes données) ainsi que l'état de navigation
 * (séance actuellement sélectionnée pour la réservation).
 * <p>
 * Un singleton est un choix pragmatique ici, le strict nécessaire pour
 * partager l'état entre contrôleurs chargés indépendamment par FXMLLoader ;
 * en Phase 7, les services seront construits avec une vraie source de
 * données (Hibernate) plutôt qu'avec les listes en mémoire utilisées ici.
 */
public final class ContexteApplication {

    private static final ContexteApplication INSTANCE = new ContexteApplication();

    private final AdherentService adherentService = new AdherentService();
    private final ReservationService reservationService = new ReservationService();
    private final FacturationService facturationService = new FacturationService();

    private final List<Sceance> planning = new ArrayList<>();

    private Sceance sceanceSelectionnee;

    private ContexteApplication() {
    }

    public static ContexteApplication getInstance() {
        return INSTANCE;
    }

    /** Peuple le contexte avec quelques cours, coachs et séances de démonstration. */
    public void chargerDonneesDemo() {
        if (!planning.isEmpty()) {
            return; // déjà chargé
        }
        Coach karim = new Coach("Karim Benali", "Yoga / Pilates");
        Coach lea = new Coach("Léa Dubois", "Coaching individuel");

        CoursCollectif yoga = new CoursCollectif("Yoga", 60, 10, 8.00);
        CoursCollectif zumba = new CoursCollectif("Zumba", 45, 15, 7.00);
        CoursIndividuel coaching = new CoursIndividuel("Coaching personnalisé", 60, 40.00);
        StageIntensif stage = new StageIntensif("Stage remise en forme", 90, 12, 300.00, 5);

        planning.add(new Sceance(yoga, karim, LocalDateTime.now().plusDays(1).withHour(9).withMinute(0), "Salle A"));
        planning.add(new Sceance(zumba, karim, LocalDateTime.now().plusDays(1).withHour(18).withMinute(30), "Salle B"));
        planning.add(new Sceance(coaching, lea, LocalDateTime.now().plusDays(2).withHour(10).withMinute(0), "Salle C"));
        planning.add(new Sceance(stage, lea, LocalDateTime.now().plusDays(3).withHour(14).withMinute(0), "Salle A"));
    }

    public AdherentService getAdherentService() {
        return adherentService;
    }

    public ReservationService getReservationService() {
        return reservationService;
    }

    public FacturationService getFacturationService() {
        return facturationService;
    }

    public List<Sceance> getPlanning() {
        return List.copyOf(planning);
    }

    public Sceance getSceanceSelectionnee() {
        return sceanceSelectionnee;
    }

    public void setSceanceSelectionnee(Sceance sceanceSelectionnee) {
        this.sceanceSelectionnee = sceanceSelectionnee;
    }
}
