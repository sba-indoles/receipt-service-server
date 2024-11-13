package org.indoles.receiptserviceserver.core.receipt.infra;


import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.indoles.receiptserviceserver.core.receipt.dto.BuyerReceiptSearchCondition;
import org.indoles.receiptserviceserver.core.receipt.dto.SellerReceiptSearchCondition;
import org.indoles.receiptserviceserver.core.receipt.entity.QReceiptEntity;
import org.indoles.receiptserviceserver.core.receipt.entity.ReceiptEntity;

import java.util.List;

@RequiredArgsConstructor
public class ReceiptQueryDslRepositoryImpl implements ReceiptQueryDslRepository {

    private final JPAQueryFactory factory;

    @Override
    public List<ReceiptEntity> findAllByBuyerId(Long buyerId, BuyerReceiptSearchCondition condition) {
        QReceiptEntity receipt = QReceiptEntity.receiptEntity;

        return factory
                .select(receipt)
                .from(receipt)
                .where(receipt.buyerId.eq(buyerId))
                .orderBy(receipt.createdAt.desc())
                .limit(condition.size())
                .offset(condition.offset())
                .fetch();
    }

    @Override
    public List<ReceiptEntity> findAllBySellerId(Long sellerId, SellerReceiptSearchCondition condition) {
        QReceiptEntity receipt = QReceiptEntity.receiptEntity;

        return factory
                .select(receipt)
                .from(receipt)
                .where(receipt.sellerId.eq(sellerId))
                .orderBy(receipt.createdAt.desc())
                .limit(condition.size())
                .offset(condition.offset())
                .fetch();
    }
}
