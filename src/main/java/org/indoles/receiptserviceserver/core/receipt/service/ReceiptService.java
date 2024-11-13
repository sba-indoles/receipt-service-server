package org.indoles.receiptserviceserver.core.receipt.service;


import lombok.RequiredArgsConstructor;
import org.indoles.receiptserviceserver.core.receipt.domain.Receipt;
import org.indoles.receiptserviceserver.core.receipt.dto.*;
import org.indoles.receiptserviceserver.core.receipt.infra.ReceiptRepository;
import org.indoles.receiptserviceserver.global.exception.AuthorizationException;
import org.indoles.receiptserviceserver.global.exception.ErrorCode;
import org.indoles.receiptserviceserver.global.exception.NotFoundException;
import org.indoles.receiptserviceserver.global.util.Mapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReceiptService {

    private final ReceiptRepository receiptRepository;

    /**
     * 거래 내역 상세 조회 서비스
     *
     * @param memberInfo
     * @param receiptId
     * @return
     */

    public ReceiptInfo getReceiptInfo(SignInInfo memberInfo, UUID receiptId) {
        Receipt receipt = receiptRepository.findById(receiptId)
                .orElseThrow(() -> new NotFoundException("거래 내역을 찾을 수 없습니다. id=" + receiptId, ErrorCode.R000));

        if (!receipt.isOwnedBy(memberInfo.id())) {
            throw new AuthorizationException("자신이 판매한 거래 내역 혹은 구매한 거래 내역만 조회할 수 있습니다.", ErrorCode.R001);
        }

        return Mapper.convertToReceiptInfo(receipt);
    }

    /**
     * 거래 내역 조회 서비스(구매자 전용)
     *
     * @param buyerInfo
     * @param condition
     * @return
     */

    public List<BuyerReceiptSimpleInfo> getBuyerReceiptSimpleInfos(SignInInfo buyerInfo,
                                                                   BuyerReceiptSearchCondition condition) {
        List<Receipt> receipts = receiptRepository.findAllByBuyerId(buyerInfo.id(), condition);
        return receipts.stream()
                .map(Mapper::convertToBuyerReceiptSimpleInfo)
                .toList();
    }

    /**
     * 거래 내역 조회 서비스(판매자 전용)
     *
     * @param sellerInfo
     * @param condition
     * @return
     */

    public List<SellerReceiptSimpleInfo> getSellerReceiptSimpleInfos(SignInInfo sellerInfo,
                                                                     SellerReceiptSearchCondition condition) {
        List<Receipt> receipts = receiptRepository.findAllBySellerId(sellerInfo.id(), condition);
        return receipts.stream()
                .map(Mapper::convertToSellerReceiptSimpleInfo)
                .toList();
    }
}
