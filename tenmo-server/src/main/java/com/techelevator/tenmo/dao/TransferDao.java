<<<<<<< HEAD
package com.techelevator.tenmo.dao;public interface TransferDao {

=======
package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransferDao {

    List<Transfer> getAllTransfers();

    Transfer getTransferById(int transfer_id);

    Transfer sendTEBucks(Transfer transfer);

    Transfer receiveTEBucks(Transfer transfer);

    int denyTransferRequest(int transfer_type_id);
>>>>>>> eb052d73b15fd14580771eb0b4f9ef7ece1be287

}
