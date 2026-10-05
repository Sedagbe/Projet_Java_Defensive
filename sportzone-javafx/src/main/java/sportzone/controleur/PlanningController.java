package sportzone.controleur;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import sportzone.ContexteApplication;
import sportzone.MainApp;
import sportzone.modele.Cours;
import sportzone.modele.Reservable;
import sportzone.modele.Sceance;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Contrôleur de l'écran planning. Toute la logique ici est de l'affichage
 * et du filtrage de données déjà calculées par la couche métier — aucune
 * règle métier (validation, calcul de tarif, décision de disponibilité)
 * n'est prise en charge ici.
 */
public class PlanningController {

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @FXML
    private TextField champCours;
    @FXML
    private TextField champCoach;
    @FXML
    private DatePicker champDate;
    @FXML
    private TableView<Sceance> tableSceances;
    @FXML
    private TableColumn<Sceance, String> colonneCours;
    @FXML
    private TableColumn<Sceance, String> colonneCoach;
    @FXML
    private TableColumn<Sceance, String> colonneDateHeure;
    @FXML
    private TableColumn<Sceance, String> colonneSalle;
    @FXML
    private TableColumn<Sceance, String> colonnePlaces;
    @FXML
    private Button boutonReserver;

    @FXML
    public void initialize() {
        colonneCours.setCellValueFactory(
                d -> new SimpleStringProperty(d.getValue().getCours().getIntitule()));
        colonneCoach.setCellValueFactory(
                d -> new SimpleStringProperty(d.getValue().getCoach().getNom()));
        colonneDateHeure.setCellValueFactory(
                d -> new SimpleStringProperty(d.getValue().getDateHeure().format(FORMAT)));
        colonneSalle.setCellValueFactory(
                d -> new SimpleStringProperty(d.getValue().getSalle()));
        colonnePlaces.setCellValueFactory(d -> {
            Cours cours = d.getValue().getCours();
            String texte = (cours instanceof Reservable r) ? String.valueOf(r.placesRestantes()) : "—";
            return new SimpleStringProperty(texte);
        });

        actualiserTable(ContexteApplication.getInstance().getPlanning());
        boutonReserver.disableProperty().bind(
                tableSceances.getSelectionModel().selectedItemProperty().isNull());
    }

    @FXML
    private void rechercher() {
        List<Sceance> toutes = ContexteApplication.getInstance().getPlanning();
        String filtreCours = texteOuVide(champCours).toLowerCase();
        String filtreCoach = texteOuVide(champCoach).toLowerCase();
        LocalDate filtreDate = champDate.getValue();

        List<Sceance> resultats = toutes.stream()
                .filter(s -> filtreCours.isBlank() || s.getCours().getIntitule().toLowerCase().contains(filtreCours))
                .filter(s -> filtreCoach.isBlank() || s.getCoach().getNom().toLowerCase().contains(filtreCoach))
                .filter(s -> filtreDate == null || s.getDateHeure().toLocalDate().equals(filtreDate))
                .toList();

        actualiserTable(resultats);
    }

    private void actualiserTable(List<Sceance> sceances) {
        tableSceances.setItems(FXCollections.observableArrayList(sceances));
    }

    @FXML
    private void reserverSceanceSelectionnee() {
        Sceance selection = tableSceances.getSelectionModel().getSelectedItem();
        if (selection == null) {
            return;
        }
        ContexteApplication.getInstance().setSceanceSelectionnee(selection);
        MainApp.changerEcran("reservation.fxml");
    }

    @FXML
    private void retourAccueil() {
        MainApp.changerEcran("accueil.fxml");
    }

    private String texteOuVide(TextField champ) {
        return champ.getText() == null ? "" : champ.getText().trim();
    }
}
