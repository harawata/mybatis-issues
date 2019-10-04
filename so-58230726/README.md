This demo requires a running MS SQL Server instance.
You may need to edit the data source settings in `/src/test/resources/test/mybatis-config.xml`.

Tested wtih Microsoft SQL Server 14.00.3162 / mssql-jdbc 7.4.1.0

```sh
$ svn export https://github.com/harawata/mybatis-issues/trunk/so-58230726
$ cd so-58230726
$ mvn test
```
