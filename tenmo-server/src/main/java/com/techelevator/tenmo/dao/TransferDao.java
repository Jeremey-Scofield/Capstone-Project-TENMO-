
package com.techelevator.tenmo.dao;




import com.techelevator.tenmo.model.Transfer;

import java.util.List;


public interface TransferDao {

    List<Transfer> getAllTransfers();

    Transfer getTransferById(int transfer_id);

    Transfer sendTEBucks(Transfer transfer);

    Transfer receiveTEBucks(Transfer transfer);

    int denyTransferRequest(int transfer_id);

    int approveTransferRequest(int transfer_id);

    List<Transfer> pendingTransfers();



}
