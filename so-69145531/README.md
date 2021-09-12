Calling a Db2 stored procedure that returns two result sets.  

https://stackoverflow.com/q/69145531/1261766

This demo requires a running DB2 instance.  
You may need to edit the data source settings in `/src/test/resources/test/mybatis-config.xml`.

Tested with DB2/LINUXX8664 SQL110540  
Driver version : 4.29.24

tag-db2  
tag-callable

```sh
$ svn export https://github.com/harawata/mybatis-issues/trunk/so-69145531
$ cd so-69145531
$ mvn test
```
