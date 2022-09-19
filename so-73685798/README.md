Mapping two independent result sets.

https://stackoverflow.com/q/73685798/1261766

This demo requires a running MS SQL Server instance.
You may need to edit the data source settings in /src/test/resources/test/mybatis-config.xml.

Tested with

- Microsoft SQL Server 15.00.4073
- mssql-jdbc 11.1.2.jre8-preview

```sh
$ svn export https://github.com/harawata/mybatis-issues/trunk/so-73685798
$ cd so-73685798
$ mvn test
```
