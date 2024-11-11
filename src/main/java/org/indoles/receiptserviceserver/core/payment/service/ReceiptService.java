package org.indoles.receiptserviceserver.core.payment.service;


import lombok.RequiredArgsConstructor;
import org.indoles.receiptserviceserver.core.member.dto.SignInInfo;
import org.indoles.receiptserviceserver.core.payment.domain.Receipt;
import org.indoles.receiptserviceserver.core.payment.domain.ReceiptRepository;
import org.indoles.receiptserviceserver.core.payment.dto.*;
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

//    public ReceiptInfo getReceiptInfo(SignInInfo memberInfo, UUID receiptId) {
//        Receipt receipt = receiptRepository.findById(receiptId)
//                .orElseThrow(() -> new NotFoundException("거래 내역을 찾을 수 없습니다. id=" + receiptId, ErrorCode.R000));
//
//        if (!receipt.isOwnedBy(memberInfo.id())) {
//            throw new AuthorizationException("자신이 판매한 거래 내역 혹은 구매한 거래 내역만 조회할 수 있습니다.", ErrorCode.R001);
//        }
//
//        return Mapper.convertToReceiptInfo(receipt);
//    }

//    public List<BuyerReceiptSimpleInfo> getBuyerReceiptSimpleInfos(SignInInfo buyerInfo,
//                                                                   BuyerReceiptSearchCondition condition) {
//        List<Receipt> receipts = receiptRepository.findAllByBuyerId(buyerInfo.id(), condition);
//        return receipts.stream()
//                .map(Mapper::convertToBuyerReceiptSimpleInfo)
//                .toList();
//    }
//
//    public List<SellerReceiptSimpleInfo> getSellerReceiptSimpleInfos(SignInInfo sellerInfo,
//                                                                     SellerReceiptSearchCondition condition) {
//        List<Receipt> receipts = receiptRepository.findAllBySellerId(sellerInfo.id(), condition);
//        return receipts.stream()
//                .map(Mapper::convertToSellerReceiptSimpleInfo)
//                .toList();
//    }
}
