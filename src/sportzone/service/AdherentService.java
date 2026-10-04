package sportzone.service;

import sportzone.exceptions.AdherentIntrouvableException;
import sportzone.modele.Adherent;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Service regroupant les opérations métier liées aux adhérents : recherche
 * et import en masse depuis un fichier texte.
 */
public class AdherentService {

    private static final Logger LOGGER = Logger.getLogger(AdherentService.class.getName());

    private final List<Adherent> adherents = new ArrayList<>();

    /**
     * Enregistre un adhérent déjà construit (par exemple créé directement en
     * mémoire, sans passer par un fichier).
     *
     * @param adherent adhérent à enregistrer, non null
     */
    public void ajouterAdherent(Adherent adherent) {
        if (adherent == null) {
            throw new IllegalArgumentException("L'adhérent ne peut pas être null.");
        }
        adherents.add(adherent);
    }

    /**
     * Recherche un adhérent par son numéro de téléphone, utilisé ici comme
     * identifiant.
     *
     * @param telephone numéro recherché
     * @return l'adhérent correspondant
     * @throws AdherentIntrouvableException si aucun adhérent ne correspond à
     *                                       ce numéro
     */
    public Adherent rechercherParTelephone(String telephone) throws AdherentIntrouvableException {
        for (Adherent adherent : adherents) {
            if (adherent.getTelephone() != null && adherent.getTelephone().equals(telephone)) {
                return adherent;
            }
        }
        throw new AdherentIntrouvableException(
                "Aucun adhérent trouvé pour le numéro de téléphone : " + telephone);
    }

    /**
     * Importe des adhérents depuis un fichier texte, une ligne par adhérent,
     * au format {@code nom;telephone;dateInscription(AAAA-MM-JJ)}.
     * <p>
     * Utilise try-with-resources pour la lecture : le flux est fermé
     * automatiquement, même en cas d'exception. Chaque ligne mal formée est
     * journalisée en WARNING et ignorée, sans interrompre l'import des
     * lignes suivantes — seule une erreur d'accès au fichier lui-même
     * (fichier introuvable, droits insuffisants) est bloquante et remonte
     * en SEVERE.
     *
     * @param cheminFichier chemin du fichier à importer
     * @return le nombre d'adhérents effectivement importés
     * @throws IOException si le fichier ne peut pas être ouvert ou lu
     */
    public int importerDepuisFichier(String cheminFichier) throws IOException {
        int importes = 0;
        try (BufferedReader lecteur = new BufferedReader(new FileReader(cheminFichier))) {
            String ligne;
            int numeroLigne = 0;
            while ((ligne = lecteur.readLine()) != null) {
                numeroLigne++;
                if (ligne.isBlank()) {
                    continue;
                }
                try {
                    Adherent adherent = parserLigne(ligne);
                    adherents.add(adherent);
                    importes++;
                } catch (IllegalArgumentException | DateTimeParseException e) {
                    LOGGER.log(Level.WARNING,
                            "Ligne {0} ignorée (format invalide) : ''{1}'' -> {2}",
                            new Object[]{numeroLigne, ligne, e.getMessage()});
                }
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Échec de l'import du fichier " + cheminFichier, e);
            throw e;
        }
        return importes;
    }

    /**
     * Transforme une ligne du fichier en {@link Adherent}.
     *
     * @param ligne ligne au format {@code nom;telephone;dateInscription}
     * @return l'adhérent correspondant
     * @throws IllegalArgumentException si le nombre de champs est incorrect,
     *                                  ou si un champ viole un invariant
     *                                  d'{@link Adherent}
     * @throws DateTimeParseException   si la date n'est pas au format ISO
     *                                  (AAAA-MM-JJ)
     */
    private Adherent parserLigne(String ligne) {
        String[] champs = ligne.split(";");
        if (champs.length != 3) {
            throw new IllegalArgumentException(
                    "Nombre de champs incorrect (attendu : nom;telephone;dateInscription)");
        }
        String nom = champs[0].trim();
        String telephone = champs[1].trim();
        LocalDate dateInscription = LocalDate.parse(champs[2].trim());
        return new Adherent(nom, telephone, dateInscription);
    }

    public List<Adherent> getAdherents() {
        return List.copyOf(adherents);
    }
}