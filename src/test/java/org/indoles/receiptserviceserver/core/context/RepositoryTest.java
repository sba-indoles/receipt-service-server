package org.indoles.receiptserviceserver.core.context;

import org.indoles.receiptserviceserver.core.receipt.infra.ReceiptCoreRepository;
import org.indoles.receiptserviceserver.core.receipt.infra.ReceiptJpaRepository;
import org.indoles.receiptserviceserver.global.config.JpaConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import({JpaConfig.class, ReceiptCoreRepository.class})
@DataJpaTest
public abstract class RepositoryTest {

    @Autowired
    public ReceiptJpaRepository receiptJpaRepository;

}

