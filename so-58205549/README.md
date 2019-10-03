# Oracle FUNCTION boolean OUT parameter

Tested with

- Oracle JDK 1.8.0_211
- Oracle 18c
- ojdbc8 19.3.0.0
- MyBatis 3.5.2

This demo requires a running Oracle instance.
You may need to edit the data source settings in `/src/test/resources/test/mybatis-config.xml`.

```sh
$ svn export https://github.com/harawata/mybatis-issues/trunk/so-58205549
$ cd so-58205549
$ mvn test
```
