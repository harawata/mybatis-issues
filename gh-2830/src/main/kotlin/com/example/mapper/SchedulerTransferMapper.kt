package com.example.mapper

import com.example.model.TransferTxn
import org.apache.ibatis.annotations.Mapper

@Mapper
interface SchedulerTransferMapper {

    fun insertTransactionHistInit(txnInfo: TransferTxn): Long

}
