Test case for https://github.com/mybatis/mybatis-3/issues/1082

First, install gh-1082-extra into the local maven repository.
This is to verify the VFS scans a nested JAR correctly.

```sh
$ svn export https://github.com/harawata/mybatis-issues/trunk/gh-1082-extra
$ cd gh-extra
$ mvn install
```

The next commands run the tomcat application.

```sh
$ svn export https://github.com/harawata/mybatis-issues/trunk/gh-1082
$ cd gh-1082
$ mvn clean package cargo:run
```

Access http://localhost:8081/gh-1082/test with your web browser to verify.
Tomcat starts using the `server.xml` in the same directory as this README.md. Edit it if necessary.
