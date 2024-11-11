package org.indoles.receiptserviceserver.core.payment.domain;



import org.indoles.receiptserviceserver.core.payment.dto.BuyerReceiptSearchCondition;
import org.indoles.receiptserviceserver.core.payment.dto.SellerReceiptSearchCondition;

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
