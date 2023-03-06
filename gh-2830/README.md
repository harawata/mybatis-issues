Getting multiple sequence values using selectKey

https://github.com/mybatis/mybatis-3/issues/2830

This approach is not generally recommended because you can only get "the number of total rows inserted".  
If you need the number of rows inserted for each table separately, you should execute independent INSERT statement within a transaction.

It may also be possible to use a callable statement i.e. combination of the techniques explained in the following pages.

- https://stackoverflow.com/a/51149147/1261766
- https://github.com/harawata/mybatis-issues/tree/master/so-66377098


This demo requires a running Oracle instance.  
You may need to edit the data source settings in `/src/test/resources/application.properties`.

Tested wtih Oracle 19.3.0.0.0  
Driver version : 21.7.0.0.0


```sh
$ svn export https://github.com/harawata/mybatis-issues/trunk/gh-2830
$ cd gh-2830
$ mvn test
```
