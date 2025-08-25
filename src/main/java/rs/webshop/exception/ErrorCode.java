package rs.webshop.exception;

@SuppressWarnings("unused")
public enum ErrorCode {

  // generic errors 5000 -
  ERR_GEN_001(5000, "error.storeFailed"),
  ERR_GEN_002(5002, "error.entityNotFound"),
  ERR_GEN_003(5003, "error.deleteFailed"),
  ERR_GEN_004(5004, "error.staleData"),
  ERR_GEN_005(5005, "error.stillExist"),


  // category 4201 - 4300
  ERR_CAT_001(4201, "error.categoryDoesNotExist"),
  ERR_CAT_002(4202, "error.categoryNameExists"),

  ERR_SC_001(4203, "error.shoppingCartIsInactive"),
  ERR_P_001(4204, "error.notEnoughProducts"),
  ERR_PP_001(4205, "error.budgetException");


  ErrorCode(int responseCode, String messageKey) {
    this.responseCode = responseCode;
    this.messageKey = messageKey;
  }

  private final String messageKey;
  private final int responseCode;

  public String getMessageKey() {
    return messageKey;
  }

  public int getResponseCode() {
    return responseCode;
  }


}
