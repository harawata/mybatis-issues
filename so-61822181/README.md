Verifying PooledDataSource behavior under heavy load

https://stackoverflow.com/q/61822181

This demo requires a running PostgreSQL instance.
You may need to edit the data source settings in `/src/test/resources/test/mybatis-config.xml`.

Tested wtih PostgreSQL 11.4
Driver version : 42.2.12

```sh
$ svn export https://github.com/harawata/mybatis-issues/trunk/so-61822181
$ cd so-61822181
$ mvn test
```
