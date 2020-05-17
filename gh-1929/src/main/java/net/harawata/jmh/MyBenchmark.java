package net.harawata.jmh;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

public class MyBenchmark {
  @State(Scope.Thread)
  public static class MyState {
    public int ALLOWED_MODES = MethodHandles.Lookup.PRIVATE | MethodHandles.Lookup.PROTECTED
        | MethodHandles.Lookup.PACKAGE | MethodHandles.Lookup.PUBLIC;
    public Constructor<Lookup> lookupConstructor;
    public Method privateLookupInMethod;
    public Method method;

    @Setup(Level.Trial)
    public void setup() throws Exception {
      Method privateLookupIn;
      try {
        privateLookupIn = MethodHandles.class.getMethod("privateLookupIn", Class.class, MethodHandles.Lookup.class);
      } catch (NoSuchMethodException e) {
        privateLookupIn = null;
      }
      privateLookupInMethod = privateLookupIn;

      Constructor<Lookup> lookup = null;
      if (privateLookupInMethod == null) {
        // JDK 1.8
        try {
          lookup = MethodHandles.Lookup.class.getDeclaredConstructor(Class.class, int.class);
          lookup.setAccessible(true);
        } catch (NoSuchMethodException e) {
          throw new IllegalStateException(
              "There is neither 'privateLookupIn(Class, Lookup)' nor 'Lookup(Class, int)' method in java.lang.invoke.MethodHandles.",
              e);
        } catch (Throwable t) {
          lookup = null;
        }
      }
      lookupConstructor = lookup;
      method = FooMapper.class.getMethod("count", String.class);
    }
  }

  @Benchmark
  public Object invokeDefaultMethod(MyState state) throws Throwable {
    FooMapper mapper = (FooMapper) Proxy.newProxyInstance(FooMapper.class.getClassLoader(),
        new Class[] { FooMapper.class },
        new TestInvocationHandler());
    int sum = 0;
    for (int i =0; i< 1000; i++) {
      sum+= mapper.count("a");
    }
    return sum;
  }

   @Benchmark
  public Object invokeDefaultMethodWithCache(MyState state) throws Throwable {
    FooMapper mapper = (FooMapper) Proxy.newProxyInstance(FooMapper.class.getClassLoader(),
        new Class[] { FooMapper.class },
        new TestInvocationHandlerCached());
    int sum = 0;
    for (int i =0; i< 1000; i++) {
      sum+= mapper.count("a");
    }
    return sum;
  }

  @Benchmark
  public Object invokeDefaultMethodWithCacheWithPreCheck(MyState state) throws Throwable {
    FooMapper mapper = (FooMapper) Proxy.newProxyInstance(FooMapper.class.getClassLoader(),
        new Class[] { FooMapper.class },
        new TestInvocationHandlerCacheWithPreCheck());
    int sum = 0;
    for (int i =0; i< 1000; i++) {
      sum+= mapper.count("a");
    }
    return sum;
  }

  interface FooMapper {
    default int count(String s) {
      return 1;
    }
  }
}
