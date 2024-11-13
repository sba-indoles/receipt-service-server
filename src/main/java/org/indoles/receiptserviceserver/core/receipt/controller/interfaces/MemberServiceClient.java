package org.indoles.receiptserviceserver.core.receipt.controller.interfaces;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "memberService", url = "http://localhost:7070")
public interface MemberServiceClient {
}
