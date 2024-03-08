package com.techelevator.tenmo.services;


import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserCredentials;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Scanner;
import com.techelevator.tenmo.model.AuthenticatedUser;
public class ConsoleService {
    private static final String API_BASE_URL = "http://localhost:8080/";
    private final RestTemplate restTemplate = new RestTemplate();

    private AuthenticatedUser localUser;
    private String authToken = null;

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
    private final Scanner scanner = new Scanner(System.in);

    public int promptForMenuSelection(String prompt) {
        int menuSelection =-1;
        System.out.print(prompt);
        try {
            menuSelection = Integer.parseInt(scanner.nextLine());
            while (menuSelection != 0) {

                if (menuSelection == 1) {
                    handleCurrentBalance();
                } else if (menuSelection == 2) {
                    handleAllTransfers();
                } else if (menuSelection == 3) {
                    handlePendingTransfers();
                } else if (menuSelection == 4) {
                    handleSendTeBucks();
                } else if (menuSelection == 5) {
                    handleRequestTEBucks();
                } else if (menuSelection == 0) {
                    continue;
                } else {
                    // anything else is not valid
                    System.out.println("Invalid Selection");
                }
            }
        } catch (NumberFormatException e) {
            menuSelection = -1;
        }
        return menuSelection;
    }



    public void printGreeting() {
        System.out.println("*********************");
        System.out.println("* Welcome to TEnmo! *");
        System.out.println("*********************");
    }

    public void printLoginMenu() {
        System.out.println();
        System.out.println("1: Register");
        System.out.println("2: Login");
        System.out.println("0: Exit");
        System.out.println();
    }

    public void printMainMenu() {
        System.out.println();
        System.out.println("1: View your current balance");
        System.out.println("2: View your past transfers");
        System.out.println("3: View your pending requests");
        System.out.println("4: Send TE bucks");
        System.out.println("5: Request TE bucks");
        System.out.println("0: Exit");
        System.out.println();
    }

    public UserCredentials promptForCredentials() {
        String username = promptForString("Username: ");
        String password = promptForString("Password: ");
        return new UserCredentials(username, password);
    }

    public String promptForString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public int promptForInt(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }

    public BigDecimal promptForBigDecimal(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return new BigDecimal(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a decimal number.");
            }
        }
    }

    public void pause() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    public void printErrorMessage() {
        System.out.println("An error occurred. Check the log for details.");
    }



    public void  handleCurrentBalance() {
        BigDecimal currentBalance;
        Account localAccount = null;
        try {
            ResponseEntity<Account> response = restTemplate.exchange(API_BASE_URL + "/account",
                    HttpMethod.GET, makeAuthEntity() , Account.class, );
            localAccount = response.getBody();
            currentBalance = localAccount.getBalance();
            System.out.println("Account Balance for Logged in User: " + currentBalance);
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }

    }




    public void handleAllTransfers(){}


    public void handlePendingTransfers(){}

    public void handleSendTeBucks(){}

    public void handleRequestTEBucks(){}


    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }

}
