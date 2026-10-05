package sportzone.exceptions;

/**
 * Levée par toute recherche d'un adhérent par identifiant (ici, son numéro
 * de téléphone) qui échoue.
 * <p>
 * Exception vérifiée : une recherche sans résultat est un cas métier
 * normal (l'identifiant fourni peut simplement ne correspondre à aucun
 * adhérent connu), pas une erreur de programmation.
 */
public class AdherentIntrouvableException extends Exception {

    /**
     * Crée l'exception avec un message décrivant l'identifiant recherché.
     *
     * @param message description du cas (par exemple l'identifiant recherché)
     */
    public AdherentIntrouvableException(String message) {
        super(message);
    }
}
