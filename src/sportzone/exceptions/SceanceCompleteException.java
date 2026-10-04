package sportzone.exceptions;

/**
 * Levée lorsqu'une réservation est demandée sur une séance ayant déjà
 * atteint sa capacité maximale.
 */
public class SceanceCompleteException extends Exception {

    public SceanceCompleteException(String message) {
        super(message);
    }
}