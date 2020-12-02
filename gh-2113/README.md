A demo verifying rollback on exception.

https://github.com/mybatis/mybatis-3/issues/2113

You may need to edit the data source settings in `/src/test/resources/test/mybatis-config.xml`.

```sh
$ svn export https://github.com/harawata/mybatis-issues/trunk/gh-2113
$ cd gh-2113
$ mvn test
```
