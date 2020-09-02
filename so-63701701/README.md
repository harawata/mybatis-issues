Calling PostgreSQL FUNCTION

https://stackoverflow.com/q/63701701

This demo requires a running PostgreSQL instance.
With docker, the following command launches the database.

```sh
docker run -d --rm --name pgtest -e POSTGRES_USER=test -e POSTGRES_PASSWORD=test -e POSTGRES_DB=test -p 5432:5432 postgres:11
```

If you use your own PostgreSQL environment, you may need to edit the data source settings in `/src/test/resources/test/mybatis-config.xml`.

Tested with PostgreSQL PostgreSQL 11.9 (Debian 11.9-1.pgdg90+1)
Driver version : 42.2.16

tag-postgresql
tag-function

```sh
$ svn export https://github.com/harawata/mybatis-issues/trunk/so-63701701
$ cd so-63701701
$ mvn test
```
