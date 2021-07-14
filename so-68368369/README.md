Demo for https://stackoverflow.com/q/68368369

This demo requires a running DB2 instance.
You may need to edit the data source settings in `/src/test/resources/test/mybatis-config.xml`.

Tested with DB2/LINUXX8664 SQL110540
Driver version : 4.28.11 ( https://mvnrepository.com/artifact/com.ibm.db2/jcc/11.1.4.4 )


```sh
$ svn export https://github.com/harawata/mybatis-issues/trunk/so-68368369
$ cd so-68368369
$ mvn test
```
