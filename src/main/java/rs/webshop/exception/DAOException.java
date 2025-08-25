package rs.webshop.exception;

@SuppressWarnings("unused")
public class DAOException extends Exception {

  public static final String ENTITY_NOT_FOUND = "{0} not found";

  public DAOException(final String pMessage) {
    super(pMessage);
  }

  public DAOException(final String pMessage, final Throwable throwable) {
    super(pMessage, throwable);
  }

  public DAOException(final Throwable throwable) {
    super(throwable);
  }

}
