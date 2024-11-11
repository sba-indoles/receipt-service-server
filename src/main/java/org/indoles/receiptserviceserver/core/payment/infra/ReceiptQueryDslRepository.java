package org.indoles.receiptserviceserver.core.payment.infra;



import org.indoles.receiptserviceserver.core.payment.dto.BuyerReceiptSearchCondition;
import org.indoles.receiptserviceserver.core.payment.dto.SellerReceiptSearchCondition;
import org.indoles.receiptserviceserver.core.payment.entity.ReceiptEntity;

import java.util.List;

public interface ReceiptQueryDslRepository {

    List<ReceiptEntity> findAllByBuyerId(Long buyerId, BuyerReceiptSearchCondition condition);

    List<ReceiptEntity> findAllBySellerId(Long sellerId, SellerReceiptSearchCondition condition);
}
