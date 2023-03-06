------------------------------------------------------------------------------------------------------------------------
create sequence MB_TRANSACTION_SEQ  start with 1 increment by 1 nocache order;
create sequence MB_TRANSACTION_DETAIL_SEQ  start with 1 increment by 1 nocache order;
------------------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------------------
create table MB_TRANSACTION
(
    TRANX_ID               NUMBER,
    AMOUNT                 NUMBER(16, 2),
    CCY                    VARCHAR2(10 char)  default 'USD',
    CREDIT_NAME            VARCHAR2(500 char),
    CONSUMER_NAME          VARCHAR2(800)
);
------------------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------------------
-- auto-generated definition
create table MB_TRANSACTION_DETAIL
(
    TRANX_DETAIL_ID NUMBER,
    TRANX_ID        NUMBER,
    TRANX_PHARSE    NUMBER,
    RES_CODE        VARCHAR2(10 char)   default '05',
    CREATED_DATE    TIMESTAMP(6)        default sysdate,
    TRANX_NOTE      VARCHAR2(2000 char) default 'Init transaction'
);
