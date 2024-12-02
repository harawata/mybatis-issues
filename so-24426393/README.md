Using an user defined type as IN OUT parameter of a procedure

https://stackoverflow.com/q/24426393/1261766

This demo requires a running Oracle instance. You may need to edit the data source settings in /src/test/resources/test/mybatis-config.xml.

It is possible to use POJO instead of `java.sql.Struct` if you write a custom type handler.
See https://github.com/harawata/mybatis-issues/tree/master/so-56834806


- Tested wtih Oracle 19c-ee : 19.19.0.0.0
- Driver version : 23.6.0.24.10


- tag-oracle
- tag-callable
- tag-procedure
- tag-udt

```sh
$ svn export https://github.com/harawata/mybatis-issues/trunk/so-24426393
$ cd so-24426393
$ mvn test
```
