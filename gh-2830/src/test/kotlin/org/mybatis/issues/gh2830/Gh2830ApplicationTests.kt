package org.mybatis.issues.gh2830

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.beans.factory.annotation.Autowired
import com.example.mapper.SchedulerTransferMapper
import com.example.model.TransferTxn
import java.math.BigDecimal
import org.junit.jupiter.api.Assertions
import org.mybatis.spring.annotation.MapperScan

@SpringBootTest
class Gh2830ApplicationTests {

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
    val res = mapperTransfer.insertTransactionHistInit(tnx)
    Assertions.assertEquals(2, res)
    Assertions.assertNotNull(tnx.tnxId)
  }
}
