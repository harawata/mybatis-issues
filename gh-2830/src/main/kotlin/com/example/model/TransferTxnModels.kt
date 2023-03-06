package com.example.model

import org.apache.ibatis.type.Alias
import java.math.BigDecimal

@Alias("transferDto")
class TransferTxn(
    val toAccountNo: String,
    val toAccountName: String?,
    //
    val amount: BigDecimal,
    val amountCcy: String,
) {
    var tnxId: Long? = null
    var tnxDetailId: Long? = null

    constructor(tnxId: Long, tnxDetailId: Long) : this(
        "", "",
        BigDecimal.ZERO,
        ""
    ) {
        this.tnxId = tnxId;
        this.tnxDetailId = tnxDetailId;
    }
}
