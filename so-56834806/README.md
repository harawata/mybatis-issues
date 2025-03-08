# Mapping Oracle user-defined object and its array.

Tested with

- Open JDK 22
- Oracle 23ai Free
- ojdbc8 23.7.0.25.01
- MyBatis 3.5.19

This demo requires a running Oracle instance.
You may need to edit the data source settings in `/src/test/resources/test/mybatis-config.xml`.

```sh
$ svn export https://github.com/harawata/mybatis-issues/trunk/so-56834806
$ cd so-56834806
$ mvn test
```
