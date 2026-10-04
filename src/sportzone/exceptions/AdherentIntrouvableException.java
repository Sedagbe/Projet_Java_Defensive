package sportzone.exceptions;

/**
 * Levée par toute recherche d'un adhérent par identifiant (ici, son numéro
 * de téléphone) qui échoue.*/
public class AdherentIntrouvableException extends Exception {

    public AdherentIntrouvableException(String message) {
        super(message);
    }
}