package com.example

import com.example.mapper.SchedulerTransferMapper
import com.example.model.TransferTxn
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal


@RestController
@RequestMapping("/health") /* , produces = ["text/plain;charset=utf-8"] */
class HealthController(
    val mapperTransfer: SchedulerTransferMapper
) {
    private val log = LoggerFactory.getLogger(HealthController::class.java)
    /*@Autowired
    lateinit var ordersService: OrdersService*/


    @GetMapping("alive")
    @ResponseBody
    fun checkLiveness(): ResponseEntity<String> {
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
        return ResponseEntity.ok(mapOf("ok" to "true").toString())
    }

}
