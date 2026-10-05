package sportzone.exceptions;

/**
 * Levée lorsqu'une réservation est demandée sur une séance ayant déjà
 * atteint sa capacité maximale.
 * <p>
 * Exception vérifiée (hérite d'{@link Exception}, pas de {@code RuntimeException})
 * car une séance complète est un cas métier normal et attendu de
 * l'application, pas un bug de programmation : l'appelant doit être obligé
 * de le traiter.
 */
public class SceanceCompleteException extends Exception {

    /**
     * Crée l'exception avec un message décrivant la séance concernée.
     *
     * @param message description du cas (par exemple l'intitulé du cours et
     *                la date de la séance)
     */
    public SceanceCompleteException(String message) {
        super(message);
    }
}
