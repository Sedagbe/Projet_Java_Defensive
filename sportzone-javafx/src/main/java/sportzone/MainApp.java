package sportzone;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * Point d'entrée de l'application JavaFX SportZone.
 * <p>
 * Gère le changement d'écran : chaque écran FXML remplace la racine de
 * l'unique {@link Scene} de la fenêtre principale, ce qui évite de rouvrir
 * une fenêtre à chaque navigation.
 */
public class MainApp extends Application {

    private static Stage stagePrincipal;

    @Override
    public void start(Stage stage) {
        stagePrincipal = stage;
        ContexteApplication.getInstance().chargerDonneesDemo();

        stage.setTitle("SportZone");
        stage.setMinWidth(800);
        stage.setMinHeight(560);
        changerEcran("accueil.fxml");
        stage.show();
    }

    /**
     * Change l'écran affiché en chargeant un nouveau fichier FXML.
     *
     * @param nomFxml nom du fichier FXML (dans {@code resources/sportzone/vue/})
     * @throws IllegalStateException si le fichier FXML ne peut pas être chargé
     */
    public static void changerEcran(String nomFxml) {
        try {
            FXMLLoader chargeur = new FXMLLoader(
                    Objects.requireNonNull(MainApp.class.getResource("vue/" + nomFxml)));
            Parent racine = chargeur.load();

            Scene scene = stagePrincipal.getScene();
            if (scene == null) {
                scene = new Scene(racine, 900, 620);
                scene.getStylesheets().add(
                        Objects.requireNonNull(MainApp.class.getResource("vue/styles.css")).toExternalForm());
                stagePrincipal.setScene(scene);
            } else {
                scene.setRoot(racine);
            }
        } catch (IOException | NullPointerException e) {
            throw new IllegalStateException("Impossible de charger l'écran : " + nomFxml, e);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
