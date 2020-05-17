JMH benchmark comparing default method handling strategies for MapperProxy.

https://github.com/mybatis/mybatis-3/issues/1754
https://github.com/mybatis/mybatis-3/issues/1929

```sh
$ svn export https://github.com/harawata/mybatis-issues/trunk/gh-1929
$ cd gh-1929
$ mvn clean install
$ java -jar target/benchmarks.jar
```

The result on JDK 1.8.0_211 (macOS)

```
Benchmark                                              Mode  Cnt     Score     Error  Units
MyBenchmark.invokeDefaultMethod                       thrpt   25     3.223 ±   0.712  ops/s
MyBenchmark.invokeDefaultMethodWithCache              thrpt   25  2217.516 ± 109.955  ops/s
MyBenchmark.invokeDefaultMethodWithCacheWithPreCheck  thrpt   25  2262.707 ± 119.406  ops/s
```

