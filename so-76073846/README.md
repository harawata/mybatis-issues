Universal INSERT (not recommended) with generated keys

https://stackoverflow.com/q/76073846


This demo requires a running Oracle instance.
You may need to edit the data source settings in `/src/test/resources/test/mybatis-config.xml`.

- Tested with Oracle 19.3.0.0.0  
- Driver version : 23.2.0.0.0

tag-oracle
tag-callable

```sh
$ svn export https://github.com/harawata/mybatis-issues/trunk/so-76073846
$ cd so-76073846
$ mvn test
```
