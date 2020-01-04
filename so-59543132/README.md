A demo mapping multiple result sets returned from a MS SQL Server stored procedure.

https://stackoverflow.com/q/59543132

This demo requires a running MS SQL Server instance.
You may need to edit the data source settings in `/src/test/resources/test/mybatis-config.xml`.

Tested wtih Microsoft SQL Server 14.00.3162 / mssql-jdbc 8.1.1.jre8-preview

```sh
$ svn export https://github.com/harawata/mybatis-issues/trunk/so-59543132
$ cd so-59543132
$ mvn test
```
