package sportzone.modele;

import java.time.LocalDateTime;

/**
 * Invariant garanti : {dateHeure} n'est pas déjà passée au moment de
 * la création de la séance.
 */
public class Sceance {

    private Cours cours;
    private Coach coach;
    private final LocalDateTime dateHeure;
    private String salle;

    /**
     * Constructeur
     *
     * @param cours     cours concerné, non null
     * @param coach     coach qui anime la séance, non null
     * @param dateHeure date et heure de la séance, ne peut pas être passée
     * @param salle     salle où se déroule la séance
     * @throws IllegalArgumentException si cours ou coach sont null, ou si
     *                                  dateHeure est déjà passée
     */
    public Sceance(Cours cours, Coach coach, LocalDateTime dateHeure, String salle) {
        if (cours == null) {
            throw new IllegalArgumentException("Le cours d'une séance ne peut pas être null.");
        }
        if (coach == null) {
            throw new IllegalArgumentException("Le coach d'une séance ne peut pas être null.");
        }
        if (dateHeure == null || dateHeure.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("La date/heure de la séance ne peut pas être passée.");
        }
        this.cours = cours;
        this.coach = coach;
        this.dateHeure = dateHeure;
        this.salle = salle;
    }

    public Cours getCours() {
        return cours;
    }

    public void setCours(Cours cours) {
        if (cours == null) {
            throw new IllegalArgumentException("Le cours d'une séance ne peut pas être null.");
        }
        this.cours = cours;
    }

    public Coach getCoach() {
        return coach;
    }

    public void setCoach(Coach coach) {
        if (coach == null) {
            throw new IllegalArgumentException("Le coach d'une séance ne peut pas être null.");
        }
        this.coach = coach;
    }

    public LocalDateTime getDateHeure() {
        return dateHeure;
    }

    public String getSalle() {
        return salle;
    }

    public void setSalle(String salle) {
        this.salle = salle;
    }

    @Override
    public String toString() {
        return "Sceance{cours=" + cours.getIntitule() + ", coach=" + coach.getNom()
                + ", dateHeure=" + dateHeure + ", salle='" + salle + "'}";
    }
}