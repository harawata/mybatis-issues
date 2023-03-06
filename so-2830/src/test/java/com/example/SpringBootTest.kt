package com.example

import com.example.mapper.SchedulerTransferMapper
import com.example.model.TransferTxn
import org.junit.jupiter.api.Test
import org.mybatis.spring.annotation.MapperScan
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.math.BigDecimal

//@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [Application::class])
@MapperScan("mapper") //@Import(DatabaseConfiguration.class)
internal class SpringBootTest(
) {
    private val log = LoggerFactory.getLogger(HealthController::class.java)

    @Autowired
    lateinit var mapperTransfer: SchedulerTransferMapper

    @Test
    fun contextLoads() {
        val tnx = TransferTxn(
            toAccountNo = "000194901",
            toAccountName = "YI SAKUN",
            amount = BigDecimal(101),
            amountCcy = "USD",
        )
        log.info("Data TEST INSERT :: {}", tnx.serializeToJsonString())
        val res = mapperTransfer.insertTransactionHistInit(tnx)
        log.info("Data TEST INSERT END :: {}", tnx.serializeToJsonString())
        log.info("Data TEST INSERT END RES :: {}", res)
        log.info("Data TEST INSERT END RES tnxId :: {}", tnx.tnxId)
        log.info("Data TEST INSERT END RES tnxDetailId :: {}", tnx.tnxDetailId)
    }
}
