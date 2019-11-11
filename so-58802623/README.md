A demo executing a stored procedure that returns a result set on Microsoft SQL Server (tested on 2017).

https://stackoverflow.com/q/58802623

This demo requires a running MS SQL Server instance.
You may need to edit the data source settings in `/src/test/resources/test/mybatis-config.xml`.

Tested wtih Microsoft SQL Server 14.00.3162 / mssql-jdbc 8.1.0.jre8-preview

```sh
$ svn export https://github.com/harawata/mybatis-issues/trunk/so-58802623
$ cd so-58802623
$ mvn test
```
