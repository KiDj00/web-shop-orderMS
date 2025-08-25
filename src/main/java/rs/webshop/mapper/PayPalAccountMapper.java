package rs.webshop.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import rs.webshop.domain.PayPalAccount;
import rs.webshop.dto.paypal_account.CreatePayPalAccountCmd;
import rs.webshop.dto.paypal_account.PayPalAccountInfo;
import rs.webshop.dto.paypal_account.PayPalAccountResult;
import rs.webshop.dto.paypal_account.UpdatePayPalAccountCmd;

@Mapper
public interface PayPalAccountMapper {

  PayPalAccountMapper INSTANCE = Mappers.getMapper(PayPalAccountMapper.class);

  @Mapping(target = "billingAddress", source = "billingAddress")
  PayPalAccount createPayPalAccountCmdToPayPalAccount(CreatePayPalAccountCmd cmd);

  List<PayPalAccountResult> listPayPalToListPayPalAccountResult(List<PayPalAccount> list);

  @Mapping(target = "userInfo", source = "user")
  PayPalAccountInfo paypalToPayPalInfo(PayPalAccount payPalAccount);

  void updatePayPalAccountCmd(@MappingTarget PayPalAccount paypal, UpdatePayPalAccountCmd cmd);
}
