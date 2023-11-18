A demo incrementing multiple keys in a JSONB column conditionally

https://stackoverflow.com/q/77490757/1261766

This demo requires a running PostgreSQL instance. You may need to edit the data source settings in `/src/test/resources/test/mybatis-config.xml`.


Tested with:
- PostgreSQL 13.0
- pgjdbc 42.6.0


```sh
$ svn export https://github.com/harawata/mybatis-issues/trunk/so-77490757
$ cd so-77490757
$ mvn test
```
