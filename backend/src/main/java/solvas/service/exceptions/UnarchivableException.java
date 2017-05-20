package solvas.service.exceptions;

/**
 * Thrown when the user requests to archive something from the database, but we can't do it. This is often because
 * other items still depend on it.
 *
 * @author Niko Strijbol
 */
public class UnarchivableException extends Exception {

}
