
package com.techelevator.tenmo.dao;




import com.techelevator.tenmo.model.Transfer;

import java.lang.annotation.Target;
import java.util.List;


public interface TransferDao {

    List<Transfer> getAllTransfers();

    Transfer getTransferById(int transfer_id);

    Transfer createTransfer(Transfer transfer, int toUserId);

    Transfer updateTransfer(Transfer transfer, int user_id);

    Transfer sendTEBucks(Transfer transfer);

    Transfer receiveTEBucks(Transfer transfer);

    int denyTransferRequest(int transfer_id);

    int approveTransferRequest(int transfer_id);

    List<Transfer> pendingTransfers();



}
