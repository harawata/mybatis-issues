Mapping PostgreSQL array column to `java.util.List` via constructor.

https://stackoverflow.com/q/64930633

This demo requires a running PostgreSQL instance.
You may need to edit the data source settings in `/src/test/resources/test/mybatis-config.xml`.

Tested with PostgreSQL 11.9 (Debian 11.9-1.pgdg90+1)
Driver version : 42.2.18


```sh
$ svn export https://github.com/harawata/mybatis-issues/trunk/so-64930633
$ cd so-64930633
$ mvn test
```

tag-postgresql
tag-array
