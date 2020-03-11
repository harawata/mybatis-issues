Demo : mapping PostgreSQL's UUID column

https://stackoverflow.com/q/60613619

This demo requires a running PostgreSQL instance.
You may need to edit the data source settings in `/src/test/resources/test/mybatis-config.xml`.

Tested wtih PostgreSQL 11.4
Driver version : 42.2.10


```sh
$ svn export https://github.com/harawata/mybatis-issues/trunk/so-60613619
$ cd so-60613619
$ mvn test
```
