package rs.webshop.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import rs.webshop.domain.PayPalAccount;
import rs.webshop.dto.paypal_account.CreatePayPalAccountCmd;
import rs.webshop.dto.paypal_account.PayPalAccountInfo;
import rs.webshop.dto.paypal_account.PayPalAccountResult;
import rs.webshop.dto.paypal_account.UpdatePayPalAccountCmd;
import rs.webshop.exception.ServiceException;
import rs.webshop.service.PayPalAccountService;

@RestController
@RequestMapping("/paypalaccount")
public class PayPalAccountController {

  private final PayPalAccountService payPalAccountService;

  public PayPalAccountController(PayPalAccountService payPalAccountService) {
    this.payPalAccountService = payPalAccountService;
  }

  @PostMapping()
  @PreAuthorize("hasAuthority('ADMIN')")
  public PayPalAccount save(@RequestBody @Valid CreatePayPalAccountCmd cmd)
      throws ServiceException {
    return payPalAccountService.save(cmd);
  }

  @GetMapping()
  @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
  @ResponseBody
  public List<PayPalAccountResult> findAll() {
    return payPalAccountService.findAll();
  }

  @GetMapping(value = "/{id}")
  @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
  public PayPalAccountInfo findById(@PathVariable Long id) {
    return payPalAccountService.findById(id);
  }

  @PutMapping()
  @PreAuthorize("hasAuthority('ADMIN')")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void update(@RequestBody @Valid UpdatePayPalAccountCmd cmd) throws ServiceException {
    payPalAccountService.update(cmd);
  }

  @DeleteMapping(value = "/{id}")
  @PreAuthorize("hasAuthority('ADMIN')")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) throws ServiceException {
    payPalAccountService.delete(id);
  }
}
