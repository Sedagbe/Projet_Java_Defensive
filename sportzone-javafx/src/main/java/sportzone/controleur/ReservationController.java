package sportzone.controleur;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sportzone.ContexteApplication;
import sportzone.MainApp;
import sportzone.exceptions.SceanceCompleteException;
import sportzone.modele.Adherent;
import sportzone.modele.Sceance;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Contrôleur de l'écran de réservation.
 * <p>
 * Toute la décision métier (séance complète, séance passée...) est prise
 * par {@link sportzone.service.ReservationService} ; ce contrôleur se
 * limite à valider le formulaire (champs requis non vides) et à traduire
 * les exceptions métier en {@link Alert} lisibles par l'utilisateur, sans
 * jamais laisser passer de trace de pile brute à l'écran.
 */
public class ReservationController {

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @FXML
    private Label labelSceance;
    @FXML
    private TextField champNom;
    @FXML
    private TextField champTelephone;

    @FXML
    public void initialize() {
        Sceance sceance = ContexteApplication.getInstance().getSceanceSelectionnee();
        if (sceance == null) {
            labelSceance.setText("Aucune séance sélectionnée — retournez au planning.");
        } else {
            labelSceance.setText(sceance.getCours().getIntitule() + "  —  " + sceance.getCoach().getNom()
                    + "  —  " + sceance.getDateHeure().format(FORMAT) + "  —  " + sceance.getSalle());
        }
    }

    @FXML
    private void confirmerReservation() {
        champNom.getStyleClass().remove("champ-erreur");
        champTelephone.getStyleClass().remove("champ-erreur");

        boolean formulaireValide = true;
        if (estVide(champNom)) {
            champNom.getStyleClass().add("champ-erreur");
            formulaireValide = false;
        }
        if (estVide(champTelephone)) {
            champTelephone.getStyleClass().add("champ-erreur");
            formulaireValide = false;
        }
        if (!formulaireValide) {
            return; // champs invalides signalés visuellement, rien d'autre à faire
        }

        Sceance sceance = ContexteApplication.getInstance().getSceanceSelectionnee();
        if (sceance == null) {
            afficherErreur("Aucune séance sélectionnée. Retournez au planning et choisissez-en une.");
            return;
        }

        Adherent adherent = new Adherent(champNom.getText().trim(), champTelephone.getText().trim(), LocalDate.now());

        try {
            ContexteApplication.getInstance().getReservationService().reserverSceance(sceance, adherent);
            ContexteApplication.getInstance().getAdherentService().ajouterAdherent(adherent);
            new Alert(AlertType.INFORMATION, "Réservation confirmée pour " + adherent.getNom() + ".").showAndWait();
            MainApp.changerEcran("planning.fxml");
        } catch (SceanceCompleteException e) {
            afficherErreur("Réservation impossible : " + e.getMessage());
        } catch (IllegalArgumentException | IllegalStateException e) {
            afficherErreur("Erreur : " + e.getMessage());
        }
    }

    @FXML
    private void retourPlanning() {
        MainApp.changerEcran("planning.fxml");
    }

    private boolean estVide(TextField champ) {
        return champ.getText() == null || champ.getText().isBlank();
    }

    private void afficherErreur(String message) {
        new Alert(AlertType.ERROR, message).showAndWait();
    }
}
