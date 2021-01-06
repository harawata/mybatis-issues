Demo for https://stackoverflow.com/q/65592472

This demo requires a running DB2 instance.
You may need to edit the data source settings in `/src/test/resources/test/mybatis-config.xml`.

Tested with DB2/LINUXX8664 SQL110540
Driver version : 4.28.11

```sh
$ svn export https://github.com/harawata/mybatis-issues/trunk/so-65592472
$ cd so-65592472
$ mvn test
```
