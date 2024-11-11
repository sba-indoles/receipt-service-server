package org.indoles.receiptserviceserver.core.payment.infra;


import lombok.RequiredArgsConstructor;
import org.indoles.receiptserviceserver.core.payment.dto.BuyerReceiptSearchCondition;
import org.indoles.receiptserviceserver.core.payment.dto.SellerReceiptSearchCondition;
import org.indoles.receiptserviceserver.core.payment.entity.ReceiptEntity;

import java.util.List;

//@RequiredArgsConstructor
//public class ReceiptQueryDslRepositoryImpl implements ReceiptQueryDslRepository {
//
//    private final JPAQueryFactory factory;
//
//    @Override
//    public List<ReceiptEntity> findAllByBuyerId(Long buyerId, BuyerReceiptSearchCondition condition) {
//        QReceiptEntity receipt = QReceiptEntity.receiptEntity;
//
//        return factory
//                .select(receipt)
//                .from(receipt)
//                .where(receipt.buyerId.eq(buyerId))
//                .orderBy(receipt.createdAt.desc())
//                .limit(condition.size())
//                .offset(condition.offset())
//                .fetch();
//    }
//
//    @Override
//    public List<ReceiptEntity> findAllBySellerId(Long sellerId, SellerReceiptSearchCondition condition) {
//        QReceiptEntity receipt = QReceiptEntity.receiptEntity;
//
//        return factory
//                .select(receipt)
//                .from(receipt)
//                .where(receipt.sellerId.eq(sellerId))
//                .orderBy(receipt.createdAt.desc())
//                .limit(condition.size())
//                .offset(condition.offset())
//                .fetch();
//    }
//}
