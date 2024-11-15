package org.indoles.receiptserviceserver.core.receipt.infra;

import org.indoles.receiptserviceserver.core.receipt.domain.Receipt;
import org.indoles.receiptserviceserver.core.receipt.dto.BuyerReceiptSearchCondition;
import org.indoles.receiptserviceserver.core.receipt.dto.SellerReceiptSearchCondition;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReceiptRepository {

    Receipt save(Receipt receipt);

    Optional<Receipt> findById(UUID receiptId);

    List<Receipt> findAllByBuyerId(Long buyerId, BuyerReceiptSearchCondition condition);

    List<Receipt> findAllBySellerId(Long sellerId, SellerReceiptSearchCondition condition);

    Optional<Receipt> findByIdForUpdate(UUID receiptId);
}
