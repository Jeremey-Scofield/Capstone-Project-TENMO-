package com.techelevator.tenmo.controller;
import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.exception.DaoException;

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

    //Transfer sendTEBucks(Transfer transfer);

    //Transfer receiveTEBucks(Transfer transfer);



    @RequestMapping(path = "/{id}/Approve", method = RequestMethod.PUT)
    public Transfer approveTransfer(@PathVariable int id, Principal principal){
        try {
            Transfer localTransfer = getTransferById(id);
            localTransfer.setTransfer_status_id(2);
            return transferDao.updateTransfer(localTransfer);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(path = "/{id}/Reject", method = RequestMethod.PUT)
    public Transfer rejectTransfer(@PathVariable int id, Principal principal) {
        try {
            Transfer localTransfer = getTransferById(id);
            localTransfer.setTransfer_status_id(3);
            return transferDao.updateTransfer(localTransfer);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
