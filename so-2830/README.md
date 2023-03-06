```sh
$ svn export https://github.com/harawata/mybatis-issues/trunk/so-2830
$ cd so-2830
$ mvn test
```
Retrieve auto-generated key from Oracle SEQUENCE or IDENTITY
Oracle 19c has a design that generates an ID key for the table automatically,
but in some cases, I need to use another key or a custom key and another project with the old Oracle version.
I use selectKey so google search reading some docs mybatis.org, 
I want when inserting to db can insert on multi-table as an example,
and output number row inserted and primary key it,
I see logs params have generated, I see logs params have generated.

https://stackoverflow.com/q/66252438

This demo requires a running Oracle instance.  
You may need to edit the data source settings in `scheduler_transfer.xml` and test via class SpringBootTest.

Tested wtih Oracle 18.3.0.0.0  Oracle Database 19c Enterprise Edition Release 19.0.0.0.0 insert success,
but not return primary key Id sequence out Java code or Kotlin code

```kotlin
fun insertTransactionHistInit(@Param("transferDto") txnInfo: TransferTxn): Long
```

```xml

<insert id="insertTransactionHistInit" parameterType="com.example.model.TransferTxn">
        <selectKey keyProperty="tnxId,tnxDetailId"  statementType="PREPARED" order="BEFORE" resultType="com.example.model.TransferTxn">
            SELECT MB_TRANSACTION_SEQ.NEXTVAL AS tnxId, MB_TRANSACTION_DETAIL_SEQ.nextval tnxDetailId FROM dual
        </selectKey>

        insert ALL
        into MB_TRANSACTION (
        TRANX_ID,
        AMOUNT,
        CCY,
        CREDIT_NAME,
        CONSUMER_NAME)
        values (
        #{tnxId},
        #{transferDto.amount,jdbcType=NUMERIC},
        #{transferDto.amountCcy},
        #{transferDto.toAccountNo},
        #{transferDto.toAccountName}
        )
        into MB_TRANSACTION_DETAIL (TRANX_DETAIL_ID, TRANX_ID, TRANX_PHARSE)
        values (#{tnxDetailId}, #{tnxId}, 1)
        select *
        from DUAL
    </insert>
```

``
val res = mapperTransfer.insertTransactionHistInit(tnx)
log.info("Data TEST INSERT END :: {}", tnx.serializeToJsonString()) // data ok
log.info("Data TEST INSERT END RES :: {}", res) // 2
log.info("Data TEST INSERT END RES tnxId :: {}", tnx.tnxId) // null
log.info("Data TEST INSERT END RES tnxDetailId :: {}", tnx.tnxDetailId) // null
``

tag-oracle
tag-usegeneratedkeys
tag-selectkey
tag-multikey
```sh
$ svn export https://github.com/harawata/mybatis-issues/trunk/so-2830
$ cd so-2830
$ mvn test
```
