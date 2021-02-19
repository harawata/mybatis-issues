Retrieve auto-generated key from Oracle SEQUENCE or IDENTITY

https://stackoverflow.com/q/66252438

This demo requires a running Oracle instance.  
You may need to edit the data source settings in `/src/test/resources/test/mybatis-config.xml`.

Tested wtih Oracle 18.3.0.0.0  
Driver version : 21.1.0.0.0

tag-oracle
tag-usegeneratedkeys

```sh
$ svn export https://github.com/harawata/mybatis-issues/trunk/so-66252438
$ cd so-66252438
$ mvn test
```
