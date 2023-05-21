A type handler mapping PostgreSQL's MACADDR to `String`.

https://stackoverflow.com/q/76293824/1261766

This demo requires a running PostgreSQL instance.
You may need to edit the data source settings in `/src/test/resources/test/mybatis-config.xml`.

Tested with:

- PostgreSQL 13.0
- Driver version : 42.6.0


```sh
$ svn export https://github.com/harawata/mybatis-issues/trunk/so-76293824
$ cd so-76293824
$ mvn test
```
