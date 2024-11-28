package org.indoles.receiptserviceserver.core.receipt.dto.request;

public record CreateReceiptRequestWrapper(
        SignInfoRequest signInfoRequest,
        CreateReceiptRequest createReceiptRequest
) {
}
