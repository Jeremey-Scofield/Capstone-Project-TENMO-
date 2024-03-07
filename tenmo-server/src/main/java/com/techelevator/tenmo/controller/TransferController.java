package com.techelevator.tenmo.controller;
import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.exception.DaoException;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/transfers")
@PreAuthorize("isAuthenticated()")
public class TransferController {
    private AccountDao accountDao;
    private TransferDao transferDao;

    private UserDao userDao;

    public TransferController(AccountDao accountDao, TransferDao transferDao) {
        this.accountDao = accountDao;
        this.transferDao = transferDao;
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<Transfer> getAllTransfers(Principal principal){
        try {
            return transferDao.getAllTransfers();
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Transfer getTransferById(@PathVariable int id){
        try {
            return transferDao.getTransferById(id);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
//1. I should be able to choose from a list of users to send TE Bucks to.
//            2. I must not be allowed to send money to myself.   the tip of his tongue bloody hell bolucks silly american wankers
//            3. A transfer includes the User IDs of the from and to users and the amount of TE Bucks.
//            4. The receiver's account balance is increased by the amount of the transfer.
//            5. The sender's account balance is decreased by the amount of the transfer.
//            6. I can't send more TE Bucks than I have in my account.
//            7. I can't send a zero or negative amount.
//            8. A Sending Transfer has an initial status of *Approved*.
    public Transfer sendTEBucks(Transfer transfer){
        BigDecimal localTransferAmount = transfer.getAmount();
        Account localToAccount = accountDao.getAccountByAccountId(transfer.getAccount_to());
        Account localFromAccount = accountDao.getAccountByAccountId(transfer.getAccount_from());

       if(!localToAccount.equals(localFromAccount) ){
           if (!(localTransferAmount.compareTo(BigDecimal.valueOf(0)) == 0 || localTransferAmount.compareTo(BigDecimal.valueOf(0)) == -1)){
               if (localTransferAmount.compareTo(localFromAccount.getBalance()) == -1) {
 ///   Create function                   accountDao.   localToAccount.setBalance(localToAccount.getBalance().add(localTransferAmount));
                    localFromAccount.setBalance(localFromAccount.getBalance().subtract(localTransferAmount));
                    transfer.setTransfer_status_id(2);
               }
           }
       }
    return transferDao.getTransferById(transfer.getTransfer_id());
    }

    //Transfer receiveTEBucks(Transfer transfer);



    @RequestMapping(path = "/{id}/Approve", method = RequestMethod.PUT)
    public Transfer approveTransfer(@PathVariable int id, Principal principal){
        try {
            Transfer localTransfer = transferDao.getTransferById(id);
            localTransfer.setTransfer_status_id(2);
            return transferDao.updateTransfer(localTransfer);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(path = "/{id}/Reject", method = RequestMethod.PUT)
    public Transfer rejectTransfer(@PathVariable int id, Principal principal) {
        try {
            Transfer localTransfer = transferDao.getTransferById(id);
            localTransfer.setTransfer_status_id(3);
            return transferDao.updateTransfer(localTransfer);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




}
