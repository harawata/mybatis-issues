package net.harawata.jmh;

import java.lang.reflect.Proxy;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;

@Threads(8)
public class MyBenchmark {

  @State(Scope.Benchmark)
  public static class MyState {

    public FooMapper mapperNoCache;
    public FooMapper mapperCached;
    public FooMapper mapperCacheWithPreCheck;

    @Setup(Level.Trial)
    public void setUp() {
      mapperNoCache = (FooMapper) Proxy.newProxyInstance(FooMapper.class.getClassLoader(),
          new Class[] { FooMapper.class },
          new TestInvocationHandler());
      mapperCached = (FooMapper) Proxy.newProxyInstance(FooMapper.class.getClassLoader(),
          new Class[] { FooMapper.class },
          new TestInvocationHandlerCached());
      mapperCacheWithPreCheck = (FooMapper) Proxy.newProxyInstance(FooMapper.class.getClassLoader(),
          new Class[] { FooMapper.class },
          new TestInvocationHandlerCacheWithPreCheck());
    }
  }

  @Benchmark
  public Object invokeDefaultMethod(MyState state) throws Throwable {
    FooMapper mapper = state.mapperNoCache;
    int sum = 0;
    for (int i = 0; i < 1000; i++) {
      sum += mapper.count("a");
    }
    return sum;
  }

  @Benchmark
  public Object invokeDefaultMethodWithCache(MyState state) throws Throwable {
    FooMapper mapper = state.mapperCached;
    int sum = 0;
    for (int i = 0; i < 1000; i++) {
      sum += mapper.count("a");
    }
    return sum;
  }

  @Benchmark
  public Object invokeDefaultMethodWithCacheWithPreCheck(MyState state) throws Throwable {
    FooMapper mapper = state.mapperCacheWithPreCheck;
    int sum = 0;
    for (int i = 0; i < 1000; i++) {
      sum += mapper.count("a");
    }
    return sum;
  }

  interface FooMapper {
    default int count(String s) {
      return 1;
    }
  }
}
