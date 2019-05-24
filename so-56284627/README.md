I used Docker for testing.

```sh
$ docker run -p 5433:5433 jbfavre/vertica:9.2.0-7_debian-8
```

If you use your own instance of Vertica, edit data source properties in `src/test/resources/test/mybatis-config.xml`.

```sh
$ svn export https://github.com/harawata/mybatis-issues/trunk/so-56284627
$ cd so-56284627
$ mvn test
```
