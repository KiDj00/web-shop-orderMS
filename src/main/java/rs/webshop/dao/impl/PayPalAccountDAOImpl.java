package rs.webshop.dao.impl;

import org.springframework.stereotype.Repository;
import rs.webshop.dao.PayPalAccountDAO;
import rs.webshop.domain.PayPalAccount;

@Repository
public class PayPalAccountDAOImpl extends AbstractDAOImpl<PayPalAccount, Long> implements
    PayPalAccountDAO {

}
