A demo executing a stored procedure that takes a user defined table type parameter on Microsoft SQL Server (tested on 2017).
https://stackoverflow.com/q/56633915/1261766

Tested with:

- Microsoft SQL Server 2017 (RTM-CU15) (KB4498951) - 14.0.3162.1 (X64)
- mssql-jdbc 7.3.1.jre8-preview

This demo requires a running SQL Server.
You may need to edit the data source settings in `/src/test/resources/test/mybatis-config.xml`.

```sh
$ svn export https://github.com/harawata/mybatis-issues/trunk/so-56633915
$ cd so-56633915
$ mvn test
```
