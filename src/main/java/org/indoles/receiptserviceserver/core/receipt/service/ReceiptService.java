package org.indoles.receiptserviceserver.core.receipt.service;


import lombok.RequiredArgsConstructor;
import org.indoles.receiptserviceserver.core.receipt.domain.Receipt;
import org.indoles.receiptserviceserver.core.receipt.domain.enums.Role;
import org.indoles.receiptserviceserver.core.receipt.dto.request.BuyerReceiptSearchConditionRequest;
import org.indoles.receiptserviceserver.core.receipt.dto.request.CreateReceiptRequest;
import org.indoles.receiptserviceserver.core.receipt.dto.request.SellerReceiptSearchConditionRequest;
import org.indoles.receiptserviceserver.core.receipt.dto.response.BuyerReceiptSimpleInfoResponse;
import org.indoles.receiptserviceserver.core.receipt.dto.response.ReceiptInfoResponse;
import org.indoles.receiptserviceserver.core.receipt.dto.response.SellerReceiptSimpleInfoResponse;
import org.indoles.receiptserviceserver.core.receipt.dto.request.SignInfoRequest;
import org.indoles.receiptserviceserver.core.receipt.infra.ReceiptCoreRepository;
import org.indoles.receiptserviceserver.global.exception.AuthorizationException;
import org.indoles.receiptserviceserver.global.exception.ErrorCode;
import org.indoles.receiptserviceserver.global.exception.NotFoundException;
import org.indoles.receiptserviceserver.global.util.Mapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ReceiptService {

    private final ReceiptCoreRepository receiptCoreRepository;

    /**
     * 거래 내역 상세 조회 서비스
     *
     * @param memberInfo
     * @param receiptId
     * @return
     */

    public ReceiptInfoResponse getReceiptInfo(
            SignInfoRequest memberInfo,
            UUID receiptId
    ) {
        Receipt receipt = receiptCoreRepository.findById(receiptId)
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

    public List<BuyerReceiptSimpleInfoResponse> getBuyerReceiptSimpleInfos(
            SignInfoRequest buyerInfo,
            BuyerReceiptSearchConditionRequest condition
    ) {
        List<Receipt> receipts = receiptCoreRepository.findAllByBuyerId(buyerInfo.id(), condition);
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

    public List<SellerReceiptSimpleInfoResponse> getSellerReceiptSimpleInfos(
            SignInfoRequest sellerInfo,
            SellerReceiptSearchConditionRequest condition
    ) {
        List<Receipt> receipts = receiptCoreRepository.findAllBySellerId(sellerInfo.id(), condition);
        return receipts.stream()
                .map(Mapper::convertToSellerReceiptSimpleInfo)
                .toList();
    }

    /**
     * 거래 내역 생성 서비스(구매자 전용)
     *
     * @param signInfoRequest
     * @param request
     */

    public void createReceipt(
            SignInfoRequest signInfoRequest,
            CreateReceiptRequest request
    ) {
        vertifyCreateReceiptRole(signInfoRequest, request);

        Receipt receipt = new Receipt(
                request.receiptId(),
                request.productName(),
                request.price(),
                request.quantity(),
                request.receiptStatus(),
                request.auctionId(),
                request.sellerId(),
                request.buyerId(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        receiptCoreRepository.save(receipt);
    }

    private void vertifyCreateReceiptRole(SignInfoRequest signInfoRequest, CreateReceiptRequest request) {
        if (request.buyerId() != signInfoRequest.id()) {
            throw new AuthorizationException("거래 내역 생성 시, 구매자만 생성할 수 있습니다.", ErrorCode.R003);
        }
    }

    /**
     * 거래 내역 환불 조회 서비스(구매자 전용)
     *
     * @param receiptId
     * @return
     */

    public ReceiptInfoResponse getReceiptById(UUID receiptId) {

        Receipt receipt = findRefundTargetReceiptForUpdate(receiptId);

        return ReceiptInfoResponse.builder()
                .receiptId(receipt.getId())
                .productName(receipt.getProductName())
                .price(receipt.getPrice())
                .quantity(receipt.getQuantity())
                .receiptStatus(receipt.getReceiptStatus())
                .auctionId(receipt.getAuctionId())
                .sellerId(receipt.getSellerId())
                .buyerId(receipt.getBuyerId())
                .createdAt(receipt.getCreatedAt())
                .updatedAt(receipt.getUpdatedAt())
                .build();
    }

    private void verifyHasBuyerRole(SignInfoRequest buyerInfo) {
        if (!buyerInfo.isType(Role.BUYER)) {
            throw new AuthorizationException("구매자만 환불을 할 수 있습니다.", ErrorCode.P000);
        }
    }

    private void verifySameBuyer(SignInfoRequest buyerInfo, long receiptBuyerId) {
        if (buyerInfo.id() != receiptBuyerId) {
            throw new AuthorizationException("환불할 입찰 내역의 구매자만 환불을 할 수 있습니다.", ErrorCode.P004);
        }
    }

    private Receipt findRefundTargetReceiptForUpdate(UUID receiptId) {
        return receiptCoreRepository.findByIdForUpdate(receiptId).orElseThrow(
                () -> new NotFoundException("환불할 입찰 내역을 찾을 수 없습니다. 내역 id=" + receiptId, ErrorCode.P002));
    }

    public void processRefund(UUID receiptId, SignInfoRequest signInfoRequest) {
        Receipt receipt = findRefundTargetReceiptForUpdate(receiptId);
        verifyHasBuyerRole(signInfoRequest);
        verifySameBuyer(signInfoRequest, receipt.getBuyerId());
        receipt.markAsRefund();
        receiptCoreRepository.save(receipt);
    }
}
