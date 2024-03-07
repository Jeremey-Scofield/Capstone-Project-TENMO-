package com.techelevator.tenmo.dao;


import com.techelevator.tenmo.exception.DaoException;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class JdbcTransferDao implements TransferDao {
    private JdbcTemplate jdbcTemplate;
    public JdbcTransferDao() {}

    public List<Transfer> getAllTransfers() {
        List<Transfer> allTransfers = new ArrayList<>();
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount FROM public.transfer;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                Transfer transfer = mapRowToTransfer(results);
                allTransfers.add(transfer);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return allTransfers;
    }

    public Transfer getTransfersById(int id){
        Transfer transfer = null;
        String sql = "SELECT transfer_id FROM public.transfer;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
            if (results.next()) {
                transfer = mapRowToTransfer(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return transfer;
        }



    private Transfer mapRowToTransfer(SqlRowSet rowSet){
        Transfer transfer = new Transfer();
        transfer.setAccount_from(rowSet.getInt("transfer_id"));
        transfer.setAccount_to(rowSet.getInt("transfer_id"));
        transfer.setAmount(rowSet.getBigDecimal("amount"));
        transfer.setTransfer_status_id(rowSet.getInt("transfer_status_id"));
        transfer.setTransfer_type_id(rowSet.getInt("transfer_type_id"));
        return transfer;
    }
}
