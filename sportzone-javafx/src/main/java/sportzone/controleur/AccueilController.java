package sportzone.controleur;

import javafx.fxml.FXML;
import sportzone.MainApp;

/**
 * Contrôleur de l'écran d'accueil. Ne contient aucune logique métier : il
 * se contente de déclencher la navigation vers les autres écrans.
 */
public class AccueilController {

    @FXML
    private void allerAuPlanning() {
        MainApp.changerEcran("planning.fxml");
    }

    @FXML
    private void allerALaFacturation() {
        MainApp.changerEcran("facturation.fxml");
    }
}
