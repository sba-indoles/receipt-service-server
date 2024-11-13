package org.indoles.receiptserviceserver.core.receipt.infra;



import org.indoles.receiptserviceserver.core.receipt.dto.BuyerReceiptSearchCondition;
import org.indoles.receiptserviceserver.core.receipt.dto.SellerReceiptSearchCondition;
import org.indoles.receiptserviceserver.core.receipt.entity.ReceiptEntity;

import java.util.List;

public interface ReceiptQueryDslRepository {

    List<ReceiptEntity> findAllByBuyerId(Long buyerId, BuyerReceiptSearchCondition condition);

    List<ReceiptEntity> findAllBySellerId(Long sellerId, SellerReceiptSearchCondition condition);
}
