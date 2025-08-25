package rs.webshop.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.webshop.dao.PayPalAccountDAO;
import rs.webshop.dao.UserDAO;
import rs.webshop.domain.PayPalAccount;
import rs.webshop.domain.User;
import rs.webshop.dto.paypal_account.CreatePayPalAccountCmd;
import rs.webshop.dto.paypal_account.PayPalAccountInfo;
import rs.webshop.dto.paypal_account.PayPalAccountResult;
import rs.webshop.dto.paypal_account.UpdatePayPalAccountCmd;
import rs.webshop.exception.DAOException;
import rs.webshop.exception.ErrorCode;
import rs.webshop.exception.ServiceException;
import rs.webshop.mapper.PayPalAccountMapper;
import rs.webshop.service.PayPalAccountService;

import java.util.List;

@Service
@Transactional
public class PayPalAccountServiceImpl implements PayPalAccountService {

    private final static Logger LOGGER = LoggerFactory.getLogger(PayPalAccountServiceImpl.class);

    private final PayPalAccountDAO payPalAccountDAO;
    private final UserDAO userDAO;

    public PayPalAccountServiceImpl(PayPalAccountDAO payPalAccountDAO, UserDAO userDAO) {
        this.payPalAccountDAO = payPalAccountDAO;
        this.userDAO = userDAO;
    }

    @Override
    public PayPalAccount save(CreatePayPalAccountCmd cmd) throws ServiceException {
        User user = userDAO.findOne(cmd.getUserID());

        PayPalAccount payPalAccount = PayPalAccountMapper.INSTANCE.createPayPalAccountCmdToPayPalAccount(cmd);
        payPalAccount.setUser(user);

        try {
            payPalAccount = payPalAccountDAO.save(payPalAccount);
        } catch (DAOException e) {
            LOGGER.error(null, e);
            throw new ServiceException(ErrorCode.ERR_GEN_001, "Saving of Paypal Account failed!", e);
        }
        return payPalAccount;
    }

    @Override
    public List<PayPalAccountResult> findAll() {
        return PayPalAccountMapper.INSTANCE.listPayPalToListPayPalAccountResult(payPalAccountDAO.findAll());
    }

    @Override
    public PayPalAccountInfo findById(Long id) {
        return PayPalAccountMapper.INSTANCE.paypalToPayPalInfo(payPalAccountDAO.findOne(id));
    }

    @Override
    public void update(UpdatePayPalAccountCmd cmd) throws ServiceException {
        PayPalAccount payPalAccount;
        try {
            payPalAccount = payPalAccountDAO.findOne(cmd.getId());
            if (payPalAccount == null) {
                throw new ServiceException(ErrorCode.ERR_GEN_002);
            }

            PayPalAccountMapper.INSTANCE.updatePayPalAccountCmd(payPalAccount, cmd);
            payPalAccountDAO.update(payPalAccount);
        } catch (DAOException e) {
            LOGGER.error(null, e);
            throw new ServiceException(ErrorCode.ERR_GEN_001, "Update of Paypal Account failed!", e);
        }
    }

    @Override
    public void delete(Long id) throws ServiceException {
        PayPalAccount payPalAccount = payPalAccountDAO.findOne(id);
        if (payPalAccount != null) {
            try {
                User user = payPalAccount.getUser();
                user.setPayPalAccount(null);
                payPalAccountDAO.delete(payPalAccount);
            } catch (DAOException e) {
                LOGGER.error(null, e);
                throw new ServiceException(ErrorCode.ERR_GEN_001, "Delete of Paypal Account failed!", e);
            }
        } else throw new ServiceException(ErrorCode.ERR_CAT_001, "Paypal Account does not exist!");
    }
}
