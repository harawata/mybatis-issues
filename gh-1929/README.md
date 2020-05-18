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
MyBenchmark.invokeDefaultMethod                       thrpt   25     3.543 ±   0.214  ops/s
MyBenchmark.invokeDefaultMethodWithCache              thrpt   25  3412.905 ± 159.803  ops/s
MyBenchmark.invokeDefaultMethodWithCacheWithPreCheck  thrpt   25  7223.128 ± 349.206  ops/s
```
The result on JDK 14.0.1+7 (macOS)

```
Benchmark                                              Mode  Cnt     Score      Error  Units
MyBenchmark.invokeDefaultMethod                       thrpt   25  1738.828 ±  137.032  ops/s
MyBenchmark.invokeDefaultMethodWithCache              thrpt   25  3374.306 ± 1175.830  ops/s
MyBenchmark.invokeDefaultMethodWithCacheWithPreCheck  thrpt   25  4376.079 ± 1024.465  ops/s
```
