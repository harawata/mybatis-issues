package net.harawata.jmh;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TestInvocationHandlerCacheWithPreCheck implements InvocationHandler {
  private static final int ALLOWED_MODES = MethodHandles.Lookup.PRIVATE | MethodHandles.Lookup.PROTECTED
      | MethodHandles.Lookup.PACKAGE | MethodHandles.Lookup.PUBLIC;
  private static final Constructor<Lookup> lookupConstructor;
  private static final Method privateLookupInMethod;
  private final Map<Method, MethodHandle> cache = new ConcurrentHashMap<>();

  static {
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
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    if (privateLookupInMethod == null) {
      return invokeDefaultMethodJava8(proxy, method, args);
    } else {
      return invokeDefaultMethodJava9(proxy, method, args);
    }
  }

  private Object invokeDefaultMethodJava9(Object proxy, Method method, Object[] args)
      throws Throwable {
    return cache.computeIfAbsent(method, m -> {
      try {
        final Class<?> declaringClass = method.getDeclaringClass();
        return ((Lookup) privateLookupInMethod.invoke(null, declaringClass, MethodHandles.lookup()))
            .findSpecial(declaringClass, m.getName(),
                MethodType.methodType(m.getReturnType(), m.getParameterTypes()), declaringClass);
      } catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException
          | InvocationTargetException e) {
        throw new RuntimeException(e);
      }
    }).bindTo(proxy).invokeWithArguments(args);
  }

  private Object invokeDefaultMethodJava8(Object proxy, Method method, Object[] args)
      throws Throwable {
    MethodHandle handle = cache.get(method);
    if (handle == null) {
      handle = cache.computeIfAbsent(method, m -> {
        final Class<?> declaringClass = m.getDeclaringClass();
        try {
          return lookupConstructor.newInstance(declaringClass, ALLOWED_MODES).unreflectSpecial(m, declaringClass);
        } catch (IllegalAccessException | InstantiationException | IllegalArgumentException
            | InvocationTargetException e) {
          throw new RuntimeException(e);
        }
      });
    }
    return handle.bindTo(proxy).invokeWithArguments(args);
  }
}
