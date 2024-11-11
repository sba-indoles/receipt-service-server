package org.indoles.receiptserviceserver.core.auction.service.auctioneer;


import lombok.RequiredArgsConstructor;
import org.indoles.receiptserviceserver.core.auction.dto.AuctionInfo;
import org.indoles.receiptserviceserver.core.auction.service.AuctionService;
import org.indoles.receiptserviceserver.core.auction.service.Auctioneer;
import org.indoles.receiptserviceserver.core.member.domain.Role;
import org.indoles.receiptserviceserver.core.member.dto.SignInInfo;
import org.indoles.receiptserviceserver.core.payment.domain.Receipt;
import org.indoles.receiptserviceserver.core.payment.domain.ReceiptRepository;
import org.indoles.receiptserviceserver.core.payment.domain.ReceiptStatus;
import org.indoles.receiptserviceserver.core.payment.service.PaymentService;
import org.indoles.receiptserviceserver.global.dto.AuctionPurchaseRequestMessage;
import org.indoles.receiptserviceserver.global.dto.AuctionRefundRequestMessage;
import org.indoles.receiptserviceserver.global.exception.AuthorizationException;
import org.indoles.receiptserviceserver.global.exception.ErrorCode;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

//@Primary
//@Service
//@RequiredArgsConstructor
//public class BasicAuctioneer implements Auctioneer {
//
//    private final AuctionService auctionService;
//    private final PaymentService paymentService;
//    private final ReceiptRepository receiptRepository;
//
//    /**
//     * 1. 구매자 확인 <br> 2. 구매자 포인트를 감소 <br> 3. 판매자에게 포인트 지급 <br> 4. 구매 요청 <br> - 실패하면 -> 예외 발생 및 구매자와 판매자 포인트 롤백 <br> -
//     * 성공하면 -> Receipt 저장 및 구매자, 판매자 업데이트 적용
//     */
//    @Override
//    @TransactionalTimeout
//    @Timed("purchase_process_time")
//    @DistributedLock("#message.auctionId + ':auction:lock'")
//    public void process(AuctionPurchaseRequestMessage message) {
//        AuctionInfo auctionInfo = auctionService.getAuction(message.auctionId());
//        auctionService.submitPurchase(message.auctionId(), message.price(), message.quantity(), message.requestTime());
//
//        long buyerId = message.buyerId();
//        long sellerId = auctionInfo.sellerId();
//        paymentService.pointTransfer(buyerId, sellerId, message.price() * message.quantity());
//
//        Receipt receipt = Receipt.builder()
//                .id(message.requestId())
//                .productName(auctionInfo.productName())
//                .price(message.price())
//                .quantity(message.quantity())
//                .receiptStatus(ReceiptStatus.PURCHASED)
//                .sellerId(sellerId)
//                .buyerId(buyerId)
//                .auctionId(message.auctionId())
//                .build();
//        receiptRepository.save(receipt);
//    }
//
//    /**
//     * 경매 환불을 진행한다.
//     *
//     * @param message
//     */
//    @Override
//    @Transactional
//    public void refund(AuctionRefundRequestMessage message) {
//        verifyHasBuyerRole(message.buyerInfo());
//
//        Receipt receipt = findRefundTargetReceiptForUpdate(message.receiptId());
//        verifySameBuyer(message.buyerInfo(), receipt.getBuyerId());
//        receipt.markAsRefund();
//
//        AuctionInfo auction = auctionService.getAuctionForUpdate(receipt.getAuctionId());
//        verifyEndAuction(message.requestTime(), auction.finishedAt());
//
//        auctionService.cancelPurchase(receipt.getAuctionId(), receipt.getQuantity());
//        paymentService.pointTransfer(receipt.getSellerId(), receipt.getBuyerId(),
//                receipt.getPrice() * receipt.getQuantity());
//
//        receiptRepository.save(receipt);
//    }
//
//    private void verifyHasBuyerRole(SignInInfo buyerInfo) {
//        if (!buyerInfo.isType(Role.BUYER)) {
//            throw new AuthorizationException("구매자만 환불을 할 수 있습니다.", ErrorCode.P000);
//        }
//    }
//
//    private void verifyEndAuction(LocalDateTime requestTime, LocalDateTime auctionFinishedAt) {
//        if (requestTime.isBefore(auctionFinishedAt)) {
//            throw new BadRequestException("종료된 경매만 환불할 수 있습니다.", ErrorCode.P007);
//        }
//    }
//
//    private void verifySameBuyer(SignInInfo buyerInfo, long receiptBuyerId) {
//        if (buyerInfo.id() != receiptBuyerId) {
//            throw new AuthorizationException("환불할 입찰 내역의 구매자만 환불을 할 수 있습니다.", ErrorCode.P004);
//        }
//    }
//
//    private Receipt findRefundTargetReceiptForUpdate(UUID receiptId) {
//        return receiptRepository.findByIdForUpdate(receiptId).orElseThrow(
//                () -> new NotFoundException("환불할 입찰 내역을 찾을 수 없습니다. 내역 id=" + receiptId, ErrorCode.P002));
//    }
//}
