package rs.webshop.service;

import java.util.List;
import rs.webshop.domain.PayPalAccount;
import rs.webshop.dto.paypal_account.CreatePayPalAccountCmd;
import rs.webshop.dto.paypal_account.PayPalAccountInfo;
import rs.webshop.dto.paypal_account.PayPalAccountResult;
import rs.webshop.dto.paypal_account.UpdatePayPalAccountCmd;
import rs.webshop.exception.ServiceException;

public interface PayPalAccountService {

  PayPalAccount save(CreatePayPalAccountCmd cmd) throws ServiceException;

  List<PayPalAccountResult> findAll();

  PayPalAccountInfo findById(Long id);

  void update(UpdatePayPalAccountCmd cmd) throws ServiceException;

  void delete(Long id) throws ServiceException;
}
