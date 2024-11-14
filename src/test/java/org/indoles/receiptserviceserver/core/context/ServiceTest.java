package org.indoles.receiptserviceserver.core.context;

import org.indoles.receiptserviceserver.core.receipt.infra.ReceiptRepository;
import org.indoles.receiptserviceserver.core.receipt.service.ReceiptService;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public abstract class ServiceTest {

    @Autowired
    public ReceiptRepository receiptRepository;

    @Autowired
    public ReceiptService receiptService;
}
