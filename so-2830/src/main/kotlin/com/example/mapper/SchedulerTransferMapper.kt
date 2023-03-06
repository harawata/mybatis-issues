package com.example.mapper

import com.example.model.TransferTxn
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param

@Mapper
interface SchedulerTransferMapper {

    //@Insert
    fun insertTransactionHistInit(@Param("transferDto") txnInfo: TransferTxn): Long

}
