package rs.webshop.exception;

public class BudgetExceededException extends Exception {

  private ErrorCode errorCode;
  private Object[] params;

  public BudgetExceededException(ErrorCode errorCode) {
    this.errorCode = errorCode;
  }

  public BudgetExceededException(ErrorCode errorCode, Exception e) {
    super(e);
    this.errorCode = errorCode;
  }

  public BudgetExceededException(ErrorCode errorCode, Object... params) {
    this.errorCode = errorCode;
    this.params = params;
  }

  public BudgetExceededException(String errorMessage, Exception e) {
    super(errorMessage, e);
  }

  public BudgetExceededException(ErrorCode errorCode, String errorMessage) {
    super(errorMessage);
    this.errorCode = errorCode;
  }

  public BudgetExceededException(ErrorCode errorCode, String errorMessage, Exception e) {
    super(errorMessage, e);
    this.errorCode = errorCode;
  }

  public BudgetExceededException(ErrorCode errorCode, String errorMessage, Object... params) {
    super(errorMessage);
    this.errorCode = errorCode;
    this.params = params;
  }

  public BudgetExceededException() {

  }

  public ErrorCode getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(ErrorCode errorCode) {
    this.errorCode = errorCode;
  }

  public Object[] getParams() {
    return params;
  }

  public void setParams(Object[] params) {
    this.params = params;
  }

}
