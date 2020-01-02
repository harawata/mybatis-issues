Test case for https://github.com/mybatis/mybatis-3/issues/946

- This demo requires a running Oracle instance.
- You may need to edit the data source settings in `/src/test/resources/test/mybatis-config.xml`.

Tested with

- Oracle JDK 1.8.0_211
- Oracle 18c
- ojdbc8 19.3.0.0
- MyBatis 3.5.3


```sh
$ svn export https://github.com/harawata/mybatis-issues/trunk/gh-946
$ cd gh-946
$ mvn test
```
