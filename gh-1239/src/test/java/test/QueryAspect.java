package test;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class QueryAspect {
  @Before(value = "execution(* org.apache.ibatis.executor.Executor.query(..))")
  public void onQueryBoth(JoinPoint joinPoint) {
    System.out.println("Both");
    System.out.println(joinPoint);
    Object[] args = joinPoint.getArgs();
    System.out.println("Arg size: " + args.length);
  }

  @Before(value = "execution(* org.apache.ibatis.executor.Executor.query(org.apache.ibatis.mapping.MappedStatement,Object,org.apache.ibatis.session.RowBounds,org.apache.ibatis.session.ResultHandler))")
  public void onQuery4Args(JoinPoint joinPoint) {
    System.out.println("Four");
    System.out.println(joinPoint);
    Object[] args = joinPoint.getArgs();
    System.out.println("Arg size: " + args.length);
  }

  @Before(value = "execution(* org.apache.ibatis.executor.Executor.query(org.apache.ibatis.mapping.MappedStatement,Object,org.apache.ibatis.session.RowBounds,org.apache.ibatis.session.ResultHandler,org.apache.ibatis.cache.CacheKey,org.apache.ibatis.mapping.BoundSql))")
  public void onQuery6Args(JoinPoint joinPoint) {
    System.out.println("Six");
    System.out.println(joinPoint);
    Object[] args = joinPoint.getArgs();
    System.out.println("Arg size: " + args.length);
  }
}
