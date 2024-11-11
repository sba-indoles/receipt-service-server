package org.indoles.receiptserviceserver.core.payment.infra;


import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.indoles.receiptserviceserver.core.payment.domain.Receipt;
import org.indoles.receiptserviceserver.core.payment.domain.ReceiptRepository;
import org.indoles.receiptserviceserver.core.payment.dto.BuyerReceiptSearchCondition;
import org.indoles.receiptserviceserver.core.payment.dto.SellerReceiptSearchCondition;
import org.indoles.receiptserviceserver.core.payment.entity.QReceiptEntity;
import org.indoles.receiptserviceserver.core.payment.entity.ReceiptEntity;
import org.indoles.receiptserviceserver.global.util.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ReceiptCoreRepository implements ReceiptRepository {

    private final ReceiptJpaRepository receiptJpaRepository;

    @Override
    public Receipt save(Receipt receipt) {
        ReceiptEntity entity = Mapper.convertToReceiptEntity(receipt);
        ReceiptEntity saved = receiptJpaRepository.save(entity);
        return Mapper.convertToReceipt(saved);
    }

    @Override
    public Optional<Receipt> findById(UUID receiptId) {
        Optional<ReceiptEntity> found = receiptJpaRepository.findById(receiptId);
        return found.map(Mapper::convertToReceipt);
    }

    @Override
    public List<Receipt> findAllByBuyerId(Long buyerId, BuyerReceiptSearchCondition condition) {
        return receiptJpaRepository.findAllByBuyerId(buyerId, condition).stream()
                .map(Mapper::convertToReceipt)
                .toList();
    }

    @Override
    public List<Receipt> findAllBySellerId(Long sellerId, SellerReceiptSearchCondition condition) {
        return receiptJpaRepository.findAllBySellerId(sellerId, condition).stream()
                .map(Mapper::convertToReceipt)
                .toList();
    }

    @Override
    public Optional<Receipt> findByIdForUpdate(UUID receiptId) {
        Optional<ReceiptEntity> found = receiptJpaRepository.findByIdForUpdate(receiptId);
        return found.map(Mapper::convertToReceipt);
    }
}


