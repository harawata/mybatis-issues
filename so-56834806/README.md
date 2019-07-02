# Mapping Oracle user-defined object and its array.

Tested with

- Oracle JDK 1.8.0_211
- Oracle 18c
- ojdbc8 18.3.0.0 [download](https://www.oracle.com/technetwork/database/application-development/jdbc/downloads/index.html)
- MyBatis 3.5.1

This demo requires a running Oracle instance.
You may need to edit the data source settings in `/src/test/resources/test/mybatis-config.xml`.

```sh
$ svn export https://github.com/harawata/mybatis-issues/trunk/so-56834806
$ cd so-56834806
$ mvn test
```
