package sportzone.controleur;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sportzone.ContexteApplication;
import sportzone.MainApp;
import sportzone.exceptions.AdherentIntrouvableException;
import sportzone.modele.Adherent;
import sportzone.modele.Facture;

import java.time.LocalDate;

/**
 * Contrôleur de l'écran de facturation.
 * <p>
 * Valide le formulaire, puis délègue entièrement à
 * {@link sportzone.service.AdherentService} (recherche) et
 * {@link sportzone.service.FacturationService} (calcul) — aucune logique
 * métier ici, uniquement de la présentation et de la traduction des
 * exceptions en {@link Alert}.
 */
public class FacturationController {

    @FXML
    private TextField champTelephone;
    @FXML
    private TextField champDesignation;
    @FXML
    private TextField champMontant;
    @FXML
    private Label labelResultat;

    @FXML
    private void emettreFacture() {
        champTelephone.getStyleClass().remove("champ-erreur");
        champDesignation.getStyleClass().remove("champ-erreur");
        champMontant.getStyleClass().remove("champ-erreur");
        labelResultat.setText("");

        boolean valide = true;
        if (estVide(champTelephone)) {
            champTelephone.getStyleClass().add("champ-erreur");
            valide = false;
        }
        if (estVide(champDesignation)) {
            champDesignation.getStyleClass().add("champ-erreur");
            valide = false;
        }

        double montant = 0.0;
        if (estVide(champMontant)) {
            champMontant.getStyleClass().add("champ-erreur");
            valide = false;
        } else {
            try {
                montant = Double.parseDouble(champMontant.getText().trim());
                if (montant <= 0) {
                    champMontant.getStyleClass().add("champ-erreur");
                    valide = false;
                }
            } catch (NumberFormatException e) {
                champMontant.getStyleClass().add("champ-erreur");
                valide = false;
            }
        }

        if (!valide) {
            return; // champs invalides signalés visuellement, rien d'autre à faire
        }

        try {
            Adherent adherent = ContexteApplication.getInstance().getAdherentService()
                    .rechercherParTelephone(champTelephone.getText().trim());
            Facture facture = new Facture(adherent, LocalDate.now(), champDesignation.getText().trim(), montant);
            double total = ContexteApplication.getInstance().getFacturationService().calculerFacture(facture);
            labelResultat.setText("Facture émise pour " + adherent.getNom() + " — Total : " + total + " €");
        } catch (AdherentIntrouvableException e) {
            afficherErreur("Adhérent introuvable : " + e.getMessage());
        } catch (IllegalArgumentException e) {
            afficherErreur("Erreur : " + e.getMessage());
        }
    }

    @FXML
    private void retourAccueil() {
        MainApp.changerEcran("accueil.fxml");
    }

    private boolean estVide(TextField champ) {
        return champ.getText() == null || champ.getText().isBlank();
    }

    private void afficherErreur(String message) {
        new Alert(AlertType.ERROR, message).showAndWait();
    }
}
