A workaround for `<idArg/>` with `resultMap`

A demo for https://stackoverflow.com/q/76395586/1261766

See the XML comments in `src/main/resources/test/Mapper.xml`

Tested with:

- MySQL 8.0.31
- mysql-connector-java-8.0.30

This demo requires a running MySQL instance.
You may need to edit the data source settings in `/src/test/resources/test/mybatis-config.xml`.


```sh
$ svn export https://github.com/harawata/mybatis-issues/trunk/so-76395586
$ cd so-76395586
$ mvn test
```
