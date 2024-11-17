package org.indoles.receiptserviceserver.core.receipt.infra;



import org.indoles.receiptserviceserver.core.receipt.dto.request.BuyerReceiptSearchConditionRequest;
import org.indoles.receiptserviceserver.core.receipt.dto.request.SellerReceiptSearchConditionRequest;
import org.indoles.receiptserviceserver.core.receipt.entity.ReceiptEntity;

import java.util.List;

public interface ReceiptQueryDslRepository {

    List<ReceiptEntity> findAllByBuyerId(Long buyerId, BuyerReceiptSearchConditionRequest condition);

    List<ReceiptEntity> findAllBySellerId(Long sellerId, SellerReceiptSearchConditionRequest condition);
}
