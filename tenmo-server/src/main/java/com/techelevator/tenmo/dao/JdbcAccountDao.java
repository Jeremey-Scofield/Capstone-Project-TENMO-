package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exception.DaoException;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.RegisterUserDto;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcAccountDao implements AccountDao{
    private final JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // might need to change return type to Account
    @Override
    public BigDecimal getAccountBalance(int userID) {
        Account myAccount = new Account();
        String sql = "select balance, " +
                "user_id, " +
                "account_id " +
                "from account " +
                "where account_id = ? and user_id;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, myAccount.getAccount_id(), userID);
            if (results.next()) {
                myAccount = mapRowToAccount(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return myAccount.getBalance();
    }

    @Override
    public Account getAccountByAccountId(int accountId) {
        Account myAccount = new Account();
        String sql = "select balance, " +
                "user_id, " +
                "account_id " +
                "from account " +
                "where account_id = ? and user_id;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, myAccount.getAccount_id(), accountId);
            if (results.next()) {
                myAccount = mapRowToAccount(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return myAccount;
    }

    @Override
    public Account updateAccount(Account account){
        Account updatedAccount = null;

        String sql = "UPDATE public.account " +
                "SET user_id=?, balance=? " +
                "WHERE <condition>;";
        try {
            int rowsAffected = jdbcTemplate.update(sql, account.getUser_id(), account.getBalance(), account.getAccount_id());

            if (rowsAffected == 0) {
                throw new DaoException("Zero rows affected, expected at least one");
            } else {
                updatedAccount = getAccountByAccountId(account.getAccount_id());
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }

        return updatedAccount;
    }

    private Account mapRowToAccount(SqlRowSet rs) {
        Account localAccount = new Account();
        localAccount.setBalance(rs.getBigDecimal("balance"));
        localAccount.setUser_id(rs.getInt("user_id"));
        localAccount.setAccount_id(rs.getInt("account_id"));
        return localAccount;
    }
}
